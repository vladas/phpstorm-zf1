package com.dirzys.zf1;

import com.google.common.base.CaseFormat;

public class ActionUtil {
    public static boolean isControllerClass(String className) {
        return className != null && className.endsWith("Controller");
    }

    public static boolean isActionMethod(String methodName) {
        return methodName != null && methodName.endsWith("Action");
    }

    public static String getModuleNameFromClassName(String className) {
        String moduleName = Action.getDefaultModule();

        int indexOfUnderscore = className.indexOf("_");

        if (indexOfUnderscore > -1) {
            moduleName = className.substring(0, indexOfUnderscore).toLowerCase();
        }

        return moduleName;
    }

    public static String getControllerNameFromClassName(String className) {
        String controllerName;

        int indexOfControllerStart = className.indexOf("_");

        controllerName = className.substring(indexOfControllerStart+1, className.length() - 10);

        controllerName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, controllerName);

        return controllerName;
    }

    public static String getActionNameFromMethodName(String methodName) {
        String actionName;

        actionName = methodName.substring(0, methodName.length() - 6);

        actionName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, actionName);

        return actionName;
    }

    public static String getControllerFileNameFromViewDirName(String dirName) {
        String controllerName;

        controllerName = CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, dirName);

        controllerName += "Controller.php";

        return controllerName;
    }

    public static String getActionMethodNameFromFileName(String fileNameWithoutExtention) {
        String actionName;

        actionName = CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, fileNameWithoutExtention);

        actionName += "Action";

        actionName = Character.toLowerCase(actionName.charAt(0)) + actionName.substring(1);

        return actionName;
    }

    public static String getModuleNameFromViewsParentDirName(String viewsParentDirName) {
        String moduleName = null;

        if (!viewsParentDirName.equals("application")) {
            moduleName = CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, viewsParentDirName);
        }

        return moduleName;
    }
}
