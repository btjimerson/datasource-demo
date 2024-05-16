package com.yugabyte.datasource;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yugabyte.datasource.common.UserMapping;
import com.yugabyte.datasource.common.UserMappingRepository;
import com.yugabyte.datasource.plant.KeyValue;
import com.yugabyte.datasource.plant.KeyValueRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.apachecommons.CommonsLog;

@Controller
@CommonsLog
public class DataSourceController {

	@Autowired
	private KeyValueRepository keyValueRepository;

	@Autowired
	private UserMappingRepository userMappingRepository;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = { "/", "/index", "/switchUser" })
	public String switchDataSource(HttpServletRequest request, Model model) {

		log.info(String.format("Fetching records from data source [%s]", DataSourceContext.getCurrentDataSource()));
		List<KeyValue> keyValues = keyValueRepository.findAll(Sort.by(Sort.Direction.ASC, "k"));

		model.addAttribute("keyValues", keyValues);
		model.addAttribute("message",
				String.format("%d results for %s", keyValues.size(),
						DataSourceContext.getCurrentDataSource().getName()));
		model.addAttribute("userMappings", userMappingRepository.findAll());
		model.addAttribute("currentUser", getCurrentUser(request).getId());

		return "index";
	}

	@PostMapping("/addRecords")
	public String addRecords(HttpServletRequest request, Model model) {

		for (int i = 0; i < 10; i++) {
			String id = UUID.randomUUID().toString();
			KeyValue keyValue = new KeyValue();
			keyValue.setK(String.format("%s-key-%s", DataSourceContext.getCurrentDataSource().getName(), id));
			keyValue.setV(String.format("value-%s", id));
			keyValueRepository.save(keyValue);
		}

		log.info(String.format("Added records to data source [%s]", DataSourceContext.getCurrentDataSource()));
		List<KeyValue> keyValues = keyValueRepository.findAll(Sort.by(Sort.Direction.ASC, "k"));

		model.addAttribute("keyValues", keyValues);
		model.addAttribute("message",
				String.format("%d results for %s", keyValues.size(),
						DataSourceContext.getCurrentDataSource().getName()));
		model.addAttribute("userMappings", userMappingRepository.findAll());
		model.addAttribute("currentUser", getCurrentUser(request).getId());

		return "index";

	}

	private UserMapping getCurrentUser(HttpServletRequest request) {

		String userId = request.getParameter("user");
		if (userId != null && !"".equals(userId)) {
			Optional<UserMapping> userMapping = userMappingRepository.findById(Integer.parseInt(userId));
			Assert.isTrue(userMapping.isPresent(), "Error finding user");
			return userMapping.get();
		} else {
			return new UserMapping();
		}
	}

}
