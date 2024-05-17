package com.yugabyte.datasource;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.servlet.HandlerInterceptor;

import com.yugabyte.datasource.common.UserMapping;
import com.yugabyte.datasource.common.UserMappingRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Web Interceptor to set the current plant data source based on the selected
 * user in the view.
 */
@Component
public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMappingRepository userMappingRepository;

    /**
     * Sets the current plant data source context based on the user selected.
     * 
     * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(HttpServletRequest,
     *      HttpServletResponse, Object)
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String userId = request.getParameter("user");

        if (userId == null) {
            DataSourceContext.setCurrentDataSource(DataSourceContext.DataSource.PLANT_1);
        } else {
            Optional<UserMapping> userMapping = userMappingRepository.findById(Integer.parseInt(userId));
            Assert.isTrue(userMapping.isPresent(), "Error finding user");
            Optional<DataSourceContext.DataSource> dataSource = DataSourceContext.DataSource
                    .get(userMapping.get().getTargetdb());
            Assert.isTrue(dataSource.isPresent(), "Error getting the correct datasource");
            DataSourceContext.setCurrentDataSource(dataSource.get());
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

}
