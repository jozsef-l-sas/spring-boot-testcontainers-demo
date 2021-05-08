package com.jozsef.testcontainersdemo;

import org.testcontainers.containers.PostgreSQLContainer;

public class TestDBContainer extends PostgreSQLContainer<TestDBContainer> {

    private static final String IMAGE_VERSION = "postgres:12.4";
    private static TestDBContainer container;

    private TestDBContainer() {
        super(IMAGE_VERSION);
    }

    public static TestDBContainer getInstance() {
        if (container == null) {
            container = new TestDBContainer()
            .withUsername("test")
            .withPassword("test")
            .withDatabaseName("test")
            .withInitScript("script/postgresql-init.sql");
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}
