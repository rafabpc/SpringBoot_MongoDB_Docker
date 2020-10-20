package com.application.container;

import org.testcontainers.containers.GenericContainer;

import javax.validation.constraints.NotNull;

//This class is the MongoDB set up for tests
public class MongoDBContainer extends GenericContainer<MongoDBContainer> {

    public static final int MONGODB_PORT = 27017;
    public static final String IMAGE_AND_TAG = "mongo:latest";

    public MongoDBContainer() {
        this(IMAGE_AND_TAG);
    }

    public MongoDBContainer(@NotNull String image) {
        super(image);
        addExposedPort(MONGODB_PORT);
    }

    @NotNull
    public Integer getPort() {
        return getMappedPort(MONGODB_PORT);
    }

}
