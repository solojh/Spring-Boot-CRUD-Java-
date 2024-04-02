package com.mycompany.project;

public class ProjectNotFoundException extends Throwable {
    public ProjectNotFoundException(String message) {
        super(message);
    }
}
