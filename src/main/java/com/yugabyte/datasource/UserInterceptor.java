package com.yugabyte.datasource;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        // String user = request.getHeader("user");
        String user = request.getParameter("user");

        Optional<DataSourceContext.DataSource> dataSource = DataSourceContext.DataSource.get(user);
        if (dataSource.isPresent()) {
            DataSourceContext.setCurrentDataSource(dataSource.get());
        } else {
            DataSourceContext.setCurrentDataSource(DataSourceContext.DataSource.DATA_SOURCE_1);
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

}
