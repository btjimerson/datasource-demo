package com.yugabyte.datasource;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.apachecommons.CommonsLog;

@Controller
@CommonsLog
public class KeyValueController {

	@Autowired
	private KeyValueRepository keyValueRepository;

	@Autowired
	DataSourceConfiguration dataSourceConfiguration;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = { "/", "/index", "/switchUser" })
	public String switchDataSource(Model model) {

		log.info(String.format("Fetching records from data source [%s]", DataSourceContext.getCurrentDataSource()));
		List<KeyValue> keyValues = keyValueRepository.findAll(Sort.by(Sort.Direction.ASC, "k"));

		model.addAttribute("keyValues", keyValues);
		model.addAttribute("message",
				String.format("%d results for %s", keyValues.size(),
						DataSourceContext.getCurrentDataSource().getProperName()));
		model.addAttribute("dataSourceName", DataSourceContext.getCurrentDataSource().getShortName());

		return "index";
	}

	@PostMapping("/addRecords")
	public String addRecords(Model model) {

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
		model.addAttribute("dataSourceName", DataSourceContext.getCurrentDataSource().getShortName());

		return "index";

	}
}
