package com.infoedge.plugin.gcmtester.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by gagandeep on 29/3/16.
 */
public class GcmInputFormDialog extends DialogWrapper {

    private final GcmInputFormPanel gcmInputForm;

    public GcmInputFormDialog(@Nullable Project project) {
        super(project);
        gcmInputForm = new GcmInputFormPanel(this,project);
        init();
        setTitle("GCM Test Tool");
        setOKButtonText("Send");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return gcmInputForm.getPanel();
    }

    @Nullable
    @Override
    public JComponent getPreferredFocusedComponent() {
        return gcmInputForm.getPreferredFocusComponent();
    }

    @Override
    protected void doOKAction() {
        gcmInputForm.onSendGcmButtonClicked();
    }

    public void toogleOkButtonEnable(boolean isEnabled) {
        getOKAction().setEnabled(isEnabled);
    }
}
