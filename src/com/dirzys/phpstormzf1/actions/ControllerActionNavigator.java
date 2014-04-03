package com.dirzys.phpstormzf1.actions;

import com.dirzys.zf1.ActionUtil;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.php.lang.psi.elements.Method;

import java.util.Collection;

public class ControllerActionNavigator extends AnAction {
    public ControllerActionNavigator() {
        super("Go to controller action...");
    }

    public void actionPerformed(AnActionEvent e) {
        Navigatable navigationElement = findNavigatableControllerAction(e);

        if (navigationElement != null) {
            navigationElement.navigate(true);
        }
    }

    @Override
    public void update(AnActionEvent e) {
        super.update(e);
        Navigatable navigationElement = findNavigatableControllerAction(e);

        e.getPresentation().setEnabled(navigationElement != null);
    }

    private Navigatable findNavigatableControllerAction(AnActionEvent e) {
        try {
            PsiFile currentPsiFile = e.getData(LangDataKeys.PSI_FILE);
            if (!currentPsiFile.getVirtualFile().getExtension().equals("phtml")) {
                return null;
            }
            String actionFilenameWithoutExtention = currentPsiFile.getVirtualFile().getNameWithoutExtension();

            String actionMethodName = ActionUtil.getActionMethodNameFromFileName(actionFilenameWithoutExtention);

            VirtualFile controllerClassVirtualFile = currentPsiFile.getVirtualFile().getParent().getParent().getParent().getParent().findFileByRelativePath("controllers").findFileByRelativePath(ActionUtil.getControllerFileNameFromViewDirName(currentPsiFile.getVirtualFile().getParent().getName()));

            PsiFile controllerClassPsiFile = PsiManager.getInstance(e.getProject()).findFile(controllerClassVirtualFile);

            Collection<Method> methods =  PsiTreeUtil.findChildrenOfType(controllerClassPsiFile.getFirstChild(), Method.class);

            for (Method method : methods) {
                if (method.getName().equals(actionMethodName)) {
                    PsiElement navigationElement = method.getNavigationElement();
                    if (navigationElement != null && navigationElement instanceof Navigatable && ((Navigatable) navigationElement).canNavigate()) {
                        return ((Navigatable) navigationElement);
                    }
                }
            }
        }
        catch (NullPointerException ex) {

        }

        return null;
    }
}
