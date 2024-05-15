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
        DATA_SOURCE_1("jimsmith", "Jim Smith"),
        DATA_SOURCE_2("sallystruthers", "Sally Struthers"),
        DATA_SOURCE_3("matteberflus", "Matt Eberflus"),
        DATA_SOURCE_4("donovanmitchell", "Donovan Mitchell"),
        DATA_SOURCE_5("nickchubb", "Nick Chubb"),
        DATA_SOURCE_6("tombrady", "Tom Brady"),
        DATA_SOURCE_7("lebronjames", "LeBron James"),
        DATA_SOURCE_8("tommyjohn", "Tommy John"),
        DATA_SOURCE_9("hannahmontana", "Hannah Montana"),
        DATA_SOURCE_10("pacmanjones", "Pacman Jones");

        private String shortName;
        private String properName;

        DataSource(String shortName, String properName) {
            this.shortName = shortName;
            this.properName = properName;
        }

        public String getShortName() {
            return this.shortName;
        }

        public String getProperName() {
            return this.properName;
        }

        public static Optional<DataSource> get(String dataSourceName) {
            return Arrays.stream(DataSource.values()).filter(ds -> ds.shortName.equalsIgnoreCase(dataSourceName))
                    .findFirst();
        }
    }

}
