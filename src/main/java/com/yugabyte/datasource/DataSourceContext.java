package com.yugabyte.datasource;

import java.util.Arrays;
import java.util.Optional;

public class DataSourceContext {

    private static final ThreadLocal<DataSource> context = new ThreadLocal<>();

    public static void setCurrentDataSource(DataSource dataSource) {
        context.set(dataSource);
    }

    public static DataSource getCurrentDataSource() {
        return context.get();
    }

    public static void clearContext() {
        context.remove();
    }

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

        DataSource(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public static Optional<DataSource> get(String dataSourceName) {
            return Arrays.stream(DataSource.values()).filter(ds -> ds.name.equalsIgnoreCase(dataSourceName))
                    .findFirst();
        }
    }

}
