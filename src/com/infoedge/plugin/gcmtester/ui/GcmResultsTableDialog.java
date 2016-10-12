package com.infoedge.plugin.gcmtester.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.infoedge.plugin.gcmtester.model.GcmResponseStatus;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Vector;

/**
 * Created by gagandeep on 30/3/16.
 */
public class GcmResultsTableDialog extends DialogWrapper {

    private GcmResponseResultPanel gcmResponseResultPanel;


    protected GcmResultsTableDialog(@Nullable Project project, Vector<GcmResponseStatus> data,int failed,int success) {
        super(project);
        this.gcmResponseResultPanel = new GcmResponseResultPanel(this, data,failed,success);
        init();
        setTitle("Gcm Response Results");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return gcmResponseResultPanel.getMainPanel();
    }
}
