package com.irdeto.jumpstart.workflow;

public class WorkflowArgumentException extends Exception {
    public WorkflowArgumentException(String message) {
        super(message);
    }

    public WorkflowArgumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
