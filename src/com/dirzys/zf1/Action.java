package com.dirzys.zf1;

public class Action {
    protected String module = "";

    protected boolean isDefaultModule = false;
    protected static String defaultModule = "default";
    protected String controller = "";
    protected String action = "";

    public Action(String module, String controller, String action) {
        this.module = module;
        this.isDefaultModule = module.equals(defaultModule);
        this.controller = controller;
        this.action = action;
    }

    public static String getDefaultModule() {
        return defaultModule;
    }

    public boolean isDefaultModule() {
        return isDefaultModule;
    }

    public String getModule() {
        return module;
    }

    public String getController() {
        return controller;
    }

    public String getAction() {
        return action;
    }

    public String toString() {
        return module + "_" + controller + "_" + action;
    }
}
