package com.yugabyte.datasource;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import com.yugabyte.datasource.plant.PlantDataSourceConfiguration;
import com.yugabyte.datasource.plant.KeyValue;
import com.yugabyte.datasource.plant.KeyValueRepository;

import lombok.extern.apachecommons.CommonsLog;

/**
 * Entry point for the Spring Boot application.
 */
@SpringBootApplication
@CommonsLog
public class DataSourceDemoApplication {

	@Autowired
	KeyValueRepository keyValueRepository;

	@Autowired
	@Qualifier("plantRoutingDataSource")
	MultiRoutingDataSource plantRoutingDataSource;

	@Autowired
	PlantDataSourceConfiguration dataSourceConfiguration;

	/**
	 * Entry point for the application.
	 * 
	 * @param args Any command line arguments passed on start up.
	 */
	public static void main(String[] args) {
		SpringApplication.run(DataSourceDemoApplication.class, args);
	}

	/**
	 * Creates and populates all of the available data sources on start up.
	 */
	@EventListener(ApplicationStartedEvent.class)
	public void initializeDatabases() {

		int dataSourceCount = 1;

		for (DataSourceContext.DataSource ds : DataSourceContext.DataSource.values()) {

			if (dataSourceCount > dataSourceConfiguration.getDataSourceCount())
				break;

			ResourceDatabasePopulator rdp = new ResourceDatabasePopulator();
			rdp.addScript(new ClassPathResource("schema.sql"));
			rdp.execute(plantRoutingDataSource.getResolvedDataSources().get(ds));

			DataSourceContext.setCurrentDataSource(ds);
			log.info(String.format("Inserting test data into data source [%s]",
					DataSourceContext.getCurrentDataSource()));

			keyValueRepository.deleteAll();
			for (int i = 0; i < 10; i++) {
				String id = UUID.randomUUID().toString();
				KeyValue keyValue = new KeyValue();
				keyValue.setK(String.format("%s-key-%s", DataSourceContext.getCurrentDataSource().getName(), id));
				keyValue.setV(String.format("value-%s", id));
				keyValueRepository.save(keyValue);
				keyValueRepository.flush();
			}

			dataSourceCount++;
		}

	}

}
