package com.dirzys.phpstormzf1.actions;

import com.dirzys.zf1.Action;
import com.dirzys.zf1.ActionFactory;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.php.lang.psi.elements.Method;
import com.jetbrains.php.lang.psi.elements.PhpClass;

public class ViewNavigator extends AnAction {
    public ViewNavigator() {
        super("Go to view script...");
    }

    public void actionPerformed(AnActionEvent e) {

        VirtualFile file = findViewScript(e);

        if (file != null) {
            FileEditorManager.getInstance(e.getProject()).openFile(file, true);
        }
    }

    @Override
    public void update(AnActionEvent e) {
        super.update(e);

        VirtualFile file = findViewScript(e);
        e.getPresentation().setEnabled(file != null);
    }

    private Action getZf1Action(AnActionEvent e) {

        Action action = null;

        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (psiFile == null || editor == null) {
            return null;
        }
        int offset = editor.getCaretModel().getOffset();
        PsiElement elementAt = psiFile.findElementAt(offset);
        PhpClass phpClass = PsiTreeUtil.getParentOfType(elementAt, PhpClass.class);
        Method method = PsiTreeUtil.getParentOfType(elementAt, Method.class);
        if (phpClass != null && method != null) {
            action = ActionFactory.createFromClassAndMethod(phpClass.getName(), method.getName());
        }
        return action;
    }

    private VirtualFile findViewScript(AnActionEvent e) {
        VirtualFile vf = null;
        String viewsPath = "views/scripts/";
        String viewSuffix = ".phtml";

        Action zf1Action = getZf1Action(e);

        if (zf1Action != null) {
            PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
            String viewRelativePath = viewsPath + zf1Action.getController() + "/" + zf1Action.getAction() + viewSuffix;
            vf = psiFile.getVirtualFile().getParent().getParent().findFileByRelativePath(viewRelativePath);
        }
        return vf;
    }
}
