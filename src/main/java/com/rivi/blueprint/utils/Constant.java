package com.rivi.blueprint.utils;

public class Constant {

    private Constant() {
        super();
    }

    public enum StoryState {
        DISCOVERY, DEFINED, IN_PROGRESS, COMPLETE
    }

    public enum UserType {
        PM, DEV
    }

    public static final String BASE_URI = "/api/v1/blueprint";
    public static final String STORY = "/story";
    public static final String TASK = "/task";
    public static final String USERS = "/users";

    public static final String SUCCESS = "SUCCESS";

}
