package com.yugabyte.datasource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.extern.apachecommons.CommonsLog;

@Controller
@CommonsLog
public class KeyValueController {

	@Autowired
	private KeyValueRepository keyValueRepository;

	@Autowired
	DataSourceConfiguration dataSourceConfiguration;

	@GetMapping({ "/", "/index" })
	public String index(Model model) {
		model.addAttribute("message", "Select a user to show records for");
		model.addAttribute("dataSourceName", "none");
		return "index";
	}

	@GetMapping("/dataSources/{dataSourceName}")
	public String getDataSource(@PathVariable String dataSourceName, Model model) {

		Optional<DataSourceContext.DataSource> dataSource = DataSourceContext.DataSource.get(dataSourceName);
		if (dataSource.isPresent()) {
			DataSourceContext.setCurrentDataSource(dataSource.get());
		}

		log.info(String.format("Fetching records from data source [%s]", DataSourceContext.getCurrentDataSource()));
		List<KeyValue> keyValues = keyValueRepository.findAll(Sort.by(Sort.Direction.ASC, "k"));

		model.addAttribute("keyValues", keyValues);
		model.addAttribute("message",
				String.format("%d results for %s", keyValues.size(),
						DataSourceContext.getCurrentDataSource().getProperName()));
		model.addAttribute("dataSourceName", dataSourceName);

		return "index";

	}

	@GetMapping("/dataSources/all")
	public String getAllDataSources(Model model) {

		int dataSourceCount = 1;
		List<KeyValue> keyValues = new ArrayList<>();
		for (DataSourceContext.DataSource ds : DataSourceContext.DataSource.values()) {
			if (dataSourceCount > dataSourceConfiguration.getDataSourceCount())
				break;
			DataSourceContext.setCurrentDataSource(ds);
			keyValues.addAll(keyValueRepository.findAll(Sort.by(Sort.Direction.ASC, "k")));
			dataSourceCount++;
		}

		model.addAttribute("keyValues", keyValues);
		model.addAttribute("message", String.format("%d results for all users", keyValues.size()));
		model.addAttribute("dataSourceName", "all");

		return "index";
	}

	@GetMapping("/dataSources/{dataSourceName}/add")
	public String addRecords(@PathVariable String dataSourceName, Model model) {

		Optional<DataSourceContext.DataSource> dataSource = DataSourceContext.DataSource.get(dataSourceName);
		if (dataSource.isPresent()) {
			DataSourceContext.setCurrentDataSource(dataSource.get());
		}

		for (int i = 0; i < 10; i++) {
			String id = UUID.randomUUID().toString();
			KeyValue keyValue = new KeyValue();
			keyValue.setK(String.format("%s-key-%s", DataSourceContext.getCurrentDataSource().getShortName(), id));
			keyValue.setV(String.format("value-%s", id));
			keyValueRepository.save(keyValue);
		}

		log.info(String.format("Added records to data source [%s]", DataSourceContext.getCurrentDataSource()));
		List<KeyValue> keyValues = keyValueRepository.findAll(Sort.by(Sort.Direction.ASC, "k"));

		model.addAttribute("keyValues", keyValues);
		model.addAttribute("message",
				String.format("%d results for %s", keyValues.size(),
						DataSourceContext.getCurrentDataSource().getProperName()));
		model.addAttribute("dataSourceName", dataSourceName);

		return "index";

	}
}
