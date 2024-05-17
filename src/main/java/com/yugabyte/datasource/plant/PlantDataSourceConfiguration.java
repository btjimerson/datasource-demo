package com.yugabyte.datasource.plant;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.yugabyte.datasource.DataSourceContext;
import com.yugabyte.datasource.MultiRoutingDataSource;

import lombok.Getter;

/**
 * Configuration for the data sources for all of the individual plant databases.
 * All of the individual plant data sources are
 * wrapped in a MultiRoutingDataSource, so that the data source context can be
 * switched based on the current user. This
 * MultiRoutingDataSource is registered with the entity manager.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "plantEntityManagerFactory", transactionManagerRef = "plantTransactionManager", basePackages = "com.yugabyte.datasource.plant")
public class PlantDataSourceConfiguration {

    @Value("${plant.datasources.count}")
    @Getter
    private int dataSourceCount;

    /**
     * Creates the properties for the plant1 data source.
     * 
     * @return The properties for the plant1 data source.
     */
    @Bean
    @ConfigurationProperties("spring.datasource.plant1")
    public DataSourceProperties plant1DataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * Creates the data source for the plant1 database.
     * 
     * @return The data source for the plant1 database.
     */
    @Primary
    @Bean
    public DataSource plant1DataSource() {
        return plant1DataSourceProperties().initializeDataSourceBuilder().build();
    }

    /**
     * Creates the properties for the plant2 data source.
     * 
     * @return The properties for the plant2 data source.
     */
    @Bean
    @ConfigurationProperties("spring.datasource.plant2")
    public DataSourceProperties plant2DataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * Creates the data source for the plant2 database.
     * 
     * @return The data source for the plant2 database.
     */
    @Bean
    public DataSource plant2DataSource() {
        return plant2DataSourceProperties().initializeDataSourceBuilder().build();
    }

    /**
     * Creates the properties for the plant3 data source.
     * 
     * @return The properties for the plant3 data source.
     */
    @Bean
    @ConfigurationProperties("spring.datasource.plant3")
    public DataSourceProperties plant3DataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * Creates the data source for the plant3 database.
     * 
     * @return The data source for the plant3 database.
     */
    @Bean
    public DataSource plant3DataSource() {
        return plant3DataSourceProperties().initializeDataSourceBuilder().build();
    }

    /**
     * Creates the properties for the plant4 data source.
     * 
     * @return The properties for the plant4 data source.
     */
    @Bean
    @ConfigurationProperties("spring.datasource.plant4")
    public DataSourceProperties plant4DataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * Creates the data source for the plant4 database.
     * 
     * @return The data source for the plant4 database.
     */
    @Bean
    public DataSource plant4DataSource() {
        return plant4DataSourceProperties().initializeDataSourceBuilder().build();
    }

    /**
     * Creates the properties for the plant5 data source.
     * 
     * @return The properties for the plant5 data source.
     */
    @Bean
    @ConfigurationProperties("spring.datasource.plant5")
    public DataSourceProperties plant5DataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * Creates the data source for the plant5 database.
     * 
     * @return The data source for the plant5 database.
     */
    @Bean
    public DataSource plant5DataSource() {
        return plant5DataSourceProperties().initializeDataSourceBuilder().build();
    }

    /**
     * Creates the properties for the plant6 data source.
     * 
     * @return The properties for the plant6 data source.
     */
    @Bean
    @ConfigurationProperties("spring.datasource.plant6")
    public DataSourceProperties plant6DataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * Creates the data source for the plant6 database.
     * 
     * @return The data source for the plant6 database.
     */
    @Bean
    public DataSource plant6DataSource() {
        return plant6DataSourceProperties().initializeDataSourceBuilder().build();
    }

    /**
     * Creates the properties for the plant7 data source.
     * 
     * @return The properties for the plant7 data source.
     */
    @Bean
    @ConfigurationProperties("spring.datasource.plant7")
    public DataSourceProperties plant7DataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * Creates the data source for the plant7 database.
     * 
     * @return The data source for the plant7 database.
     */
    @Bean
    public DataSource plant7DataSource() {
        return plant7DataSourceProperties().initializeDataSourceBuilder().build();
    }

    /**
     * Creates the properties for the plant8 data source.
     * 
     * @return The properties for the plant8 data source.
     */
    @Bean
    @ConfigurationProperties("spring.datasource.plant8")
    public DataSourceProperties plant8DataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * Creates the data source for the plant8 database.
     * 
     * @return The data source for the plant8 database.
     */
    @Bean
    public DataSource plant8DataSource() {
        return plant8DataSourceProperties().initializeDataSourceBuilder().build();
    }

    /**
     * Creates the properties for the plant9 data source.
     * 
     * @return The properties for the plant9 data source.
     */
    @Bean
    @ConfigurationProperties("spring.datasource.plant9")
    public DataSourceProperties plant9DataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * Creates the data source for the plant9 database.
     * 
     * @return The data source for the plant9 database.
     */
    @Bean
    public DataSource plant9DataSource() {
        return plant9DataSourceProperties().initializeDataSourceBuilder().build();
    }

    /**
     * Creates the properties for the plant10 data source.
     * 
     * @return The properties for the plant10 data source.
     */
    @Bean
    @ConfigurationProperties("spring.datasource.plant10")
    public DataSourceProperties plant10DataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * Creates the data source for the plant10 database.
     * 
     * @return The data source for the plant10 database.
     */
    @Bean
    public DataSource plant10DataSource() {
        return plant10DataSourceProperties().initializeDataSourceBuilder().build();
    }

    /**
     * Creates a {@link com.yugabyte.datasource.MultiRoutingDataSource} that holds
     * of the plants' data sources.
     * 
     * @return A {@link com.yugabyte.datasource.MultiRoutingDataSource} for the
     *         plants.
     */
    @Bean
    public DataSource plantRoutingDataSource() {

        Map<Object, Object> targetDataSources = new HashMap<>();
        if (getDataSourceCount() >= 1)
            targetDataSources.put(DataSourceContext.DataSource.PLANT_1, plant1DataSource());
        if (getDataSourceCount() >= 2)
            targetDataSources.put(DataSourceContext.DataSource.PLANT_2, plant2DataSource());
        if (getDataSourceCount() >= 3)
            targetDataSources.put(DataSourceContext.DataSource.PLANT_3, plant3DataSource());
        if (getDataSourceCount() >= 4)
            targetDataSources.put(DataSourceContext.DataSource.PLANT_4, plant4DataSource());
        if (getDataSourceCount() >= 5)
            targetDataSources.put(DataSourceContext.DataSource.PLANT_5, plant5DataSource());
        if (getDataSourceCount() >= 6)
            targetDataSources.put(DataSourceContext.DataSource.PLANT_6, plant6DataSource());
        if (getDataSourceCount() >= 7)
            targetDataSources.put(DataSourceContext.DataSource.PLANT_7, plant7DataSource());
        if (getDataSourceCount() >= 8)
            targetDataSources.put(DataSourceContext.DataSource.PLANT_8, plant8DataSource());
        if (getDataSourceCount() >= 9)
            targetDataSources.put(DataSourceContext.DataSource.PLANT_9, plant9DataSource());
        if (getDataSourceCount() >= 10)
            targetDataSources.put(DataSourceContext.DataSource.PLANT_10, plant10DataSource());

        MultiRoutingDataSource multiRoutingDataSource = new MultiRoutingDataSource();
        multiRoutingDataSource.setDefaultTargetDataSource(plant1DataSource());
        multiRoutingDataSource.setTargetDataSources(targetDataSources);
        return multiRoutingDataSource;

    }

    /**
     * Creates the entity manager factory for the multi-routing data source.
     * 
     * @param dataSource The multi-routing data source.
     * @param builder    An autowired factory builder.
     * @return The entity manager factory for the multi-routing data source.
     */
    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean plantEntityManagerFactory(
            @Qualifier("plantRoutingDataSource") DataSource dataSource,
            EntityManagerFactoryBuilder builder) {

        return builder.dataSource(dataSource).packages("com.yugabyte.datasource.plant").build();
    }

    /**
     * Creates the transaction manager for the multi-routing plant entity manager.
     * 
     * @param plantEntityManagerFactory The multi-routing manager factory for the
     *                                  common
     *                                  data source.
     * @return A transaction manager for the multi-routing entity manager.
     */
    @Primary
    @Bean
    public PlatformTransactionManager plantTransactionManager(
            @Qualifier("plantEntityManagerFactory") LocalContainerEntityManagerFactoryBean plantEntityManagerFactory) {
        return new JpaTransactionManager(plantEntityManagerFactory.getObject());
    }
}