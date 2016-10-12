package com.infoedge.plugin.gcmtester.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.infoedge.plugin.gcmtester.ui.GcmInputFormDialog;

/**
 * Created by gagandeep on 19/3/16.
 */
public class GcmParamsInputAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getProject();
        GcmInputFormDialog gcmInputFormDialog = new GcmInputFormDialog(project);
        gcmInputFormDialog.show();
    }

}
