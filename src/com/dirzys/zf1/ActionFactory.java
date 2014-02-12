package com.dirzys.zf1;

public class ActionFactory {
    public static Action createFromClassAndMethod(String className, String methodName) {
        Action action = null;

        if (ActionUtil.isControllerClass(className) && ActionUtil.isActionMethod(methodName)) {
            String moduleName = ActionUtil.getModuleNameFromClassName(className);
            String controllerName = ActionUtil.getControllerNameFromClassName(className);
            String actionName = ActionUtil.getActionNameFromMethodName(methodName);
            action = new Action(moduleName, controllerName, actionName);
        }

        return action;
    }
}
