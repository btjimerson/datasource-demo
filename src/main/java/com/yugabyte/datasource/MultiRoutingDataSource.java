package com.yugabyte.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Implementation for the AbstractRoutingDataSource to allow switching
 * between plant data sources.
 */
public class MultiRoutingDataSource extends AbstractRoutingDataSource {

    /**
     * Gets the current data source by the
     * {@link com.yugabyte.datasource.DataSourceContext.DataSource} key.
     * 
     * @see org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContext.getCurrentDataSource();
    }

}
