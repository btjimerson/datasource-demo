package com.yugabyte.datasource;

import java.util.Arrays;
import java.util.Optional;

import lombok.Getter;

/**
 * Represents the current data source context. This context is stored in a
 * ThreadLocal. The MultiRoutingDataSource sets /uses this ThreadLocal context
 * based on the current user's plant.
 */
public class DataSourceContext {

    private static final ThreadLocal<DataSource> context = new ThreadLocal<>();

    /**
     * Sets the data source context to use
     * 
     * @param dataSource The data source to use
     */
    public static void setCurrentDataSource(DataSource dataSource) {
        context.set(dataSource);
    }

    /**
     * Gets the current data source context
     * 
     * @return The current data source context
     */
    public static DataSource getCurrentDataSource() {
        return context.get();
    }

    /**
     * Clears the data source context
     */
    public static void clearContext() {
        context.remove();
    }

    /**
     * Enumeration for available plant data sources.
     */
    @Getter
    public enum DataSource {
        PLANT_1("plant1"),
        PLANT_2("plant2"),
        PLANT_3("plant3"),
        PLANT_4("plant4"),
        PLANT_5("plant5"),
        PLANT_6("plant6"),
        PLANT_7("plant7"),
        PLANT_8("plant8"),
        PLANT_9("plant9"),
        PLANT_10("plant10");

        private String name;

        /**
         * Default constructor for the DataSource enum.
         * 
         * @param name
         */
        DataSource(String name) {
            this.name = name;
        }

        /**
         * Gets a DataSource value by common name.
         * 
         * @param dataSourceName The common name of the DataSource.
         * @return The DataSource, if found by name.
         */
        public static Optional<DataSource> get(String dataSourceName) {
            return Arrays.stream(DataSource.values()).filter(ds -> ds.name.equalsIgnoreCase(dataSourceName))
                    .findFirst();
        }
    }

}
