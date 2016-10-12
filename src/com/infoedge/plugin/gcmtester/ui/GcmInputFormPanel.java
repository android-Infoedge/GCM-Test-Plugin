package com.infoedge.plugin.gcmtester.ui;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.infoedge.plugin.gcmtester.model.GcmRequest;
import com.infoedge.plugin.gcmtester.model.GcmResponseStatus;
import com.infoedge.plugin.gcmtester.model.GcmResultPerId;
import com.infoedge.plugin.gcmtester.network.NetworkHandler;
import com.infoedge.plugin.gcmtester.utils.Constants;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;
import com.infoedge.plugin.gcmtester.model.GcmResponse;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * Created by gagandeep on 29/3/16.
 */
public class GcmInputFormPanel {
    private JPanel mainPanel;
    private JTextField apiKeyInputField;
    private JTextArea messageInputField;
    private JTextArea regIdsInputField;
    private JProgressBar messageSendProgressBar;
    private JLabel errorMessageLabel;
    private List<String> lastRequestRegIds;
    private GcmResponse lastGcmResponse;
    private DummyProgressBarThread progressBarUpdatingThread;

    private final GcmInputFormDialog gcmInputFormDialog;
    private Project project;

    public GcmInputFormPanel(GcmInputFormDialog gcmInputFormDialog, Project project) {
        this.gcmInputFormDialog = gcmInputFormDialog;
        this.project = project;
        messageInputField.getDocument().addDocumentListener(messageTextDocumentListener);
        apiKeyInputField.setText(PropertiesComponent.getInstance(project).getValue(Constants.PREF_KEY_API_KEY));
        regIdsInputField.setText(PropertiesComponent.getInstance(project).getValue(Constants.PREF_KEY_REG_IDS));
        messageInputField.setText(PropertiesComponent.getInstance(project).getValue(Constants.PREF_KEY_JSON_MESSAGE));
    }

    private DocumentListener messageTextDocumentListener = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {

        }

        @Override
        public void removeUpdate(DocumentEvent e) {

        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            System.out.println(messageInputField.getText());
        }
    };

    public JPanel getPanel() {
        return mainPanel;
    }

    public JComponent getPreferredFocusComponent() {
        return apiKeyInputField;
    }

    public void toggleProgressBarVisibility(boolean isVisible) {
        gcmInputFormDialog.toogleOkButtonEnable(!isVisible);
        messageSendProgressBar.setVisible(isVisible);
        if(isVisible) {
            progressBarUpdatingThread = new DummyProgressBarThread(messageSendProgressBar);
            new Thread(progressBarUpdatingThread).start();
        } else {
            if(progressBarUpdatingThread != null) {
                progressBarUpdatingThread.doneLoading();
                progressBarUpdatingThread = null;
            }
        }
    }

    private GcmRequest prepareGcmRequest() {
        GcmRequest gcmRequest = new GcmRequest();

        String regIdsString = regIdsInputField.getText().trim();
        String[] regIds = regIdsString.split(",");
        gcmRequest.registrationIds = Arrays.asList(regIds);

        gcmRequest.message = messageInputField.getText().trim();
        return gcmRequest;
    }

    public void onSendGcmButtonClicked() {
        String errorMessage = validate();

        if(errorMessage != null) {
            errorMessageLabel.setText(errorMessage);
            errorMessageLabel.setVisible(true);
            return;
        } else {
            errorMessageLabel.setText("");
            errorMessageLabel.setVisible(false);
        }
        toggleProgressBarVisibility(true);
        final GcmRequest gcmRequest = prepareGcmRequest();
        String apiKey = apiKeyInputField.getText().trim();

        PropertiesComponent.getInstance(project).setValue(Constants.PREF_KEY_API_KEY,apiKey.trim());
        PropertiesComponent.getInstance(project).setValue(Constants.PREF_KEY_REG_IDS,regIdsInputField.getText().trim());
        PropertiesComponent.getInstance(project).setValue(Constants.PREF_KEY_JSON_MESSAGE,messageInputField.getText().trim());

        NetworkHandler.initGcmRequest(apiKey, gcmRequest, new NetworkHandler.NetworkResponseListener() {
            @Override
            public void onSuccess(final GcmResponse gcmResponse) {
                lastRequestRegIds = gcmRequest.registrationIds;
                lastGcmResponse = gcmResponse;

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        toggleProgressBarVisibility(false);
                        showGcmResponseResult(gcmResponse.failure,gcmResponse.success);
                    }
                });
            }

            @Override
            public void onFailure(final String failureMessage) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        toggleProgressBarVisibility(false);
                        errorMessageLabel.setText(failureMessage);
                        errorMessageLabel.setVisible(true);
                    }
                });
            }
        });
    }

    private void showGcmResponseResult(int failed,int success) {
        if(lastGcmResponse == null || lastRequestRegIds == null) {
            return;
        }

        if(lastRequestRegIds.size() != lastGcmResponse.resultPerIds.size()) {
            return;
        }

        Vector<GcmResponseStatus> vectorData = new Vector<GcmResponseStatus>(lastRequestRegIds.size());

        Iterator<GcmResultPerId> iterator = lastGcmResponse.resultPerIds.iterator();

        for (String lastRequestRegId : lastRequestRegIds) {
            GcmResponseStatus gcmResponseStatus = new GcmResponseStatus();
            gcmResponseStatus.gcmId = lastRequestRegId;
            GcmResultPerId gcmResultPerId = iterator.next();
            gcmResponseStatus.status = gcmResultPerId.errorMessage == null?gcmResultPerId.messageId:gcmResultPerId.errorMessage;
            vectorData.add(gcmResponseStatus);
        }

        GcmResultsTableDialog gcmResultsTableDialog = new GcmResultsTableDialog(project,vectorData,failed,success);
        gcmResultsTableDialog.show();
    }

    private String validate() {
        String apiKey = apiKeyInputField.getText().trim();
        if ("".equals(apiKey)) {
            return Constants.ERROR_MESSAGE_API_KEY_EMPTY;
        }

        String registrationIds = regIdsInputField.getText().trim();
        if ("".equals(registrationIds) || ",".equals(registrationIds)) {
            return Constants.ERROR_MESSAGE_REG_IDS_EMPTY;
        }

        String jsonMessage = messageInputField.getText().trim();
        if ("".equals(jsonMessage)) {
            return Constants.ERROR_MESSAGE_JSON_MESSAGE_EMPTY;
        }

        if(!isValidJson(jsonMessage)) {
            return Constants.ERROR_MESSAGE_JSON_MESSAGE_INVALID;
        }

        return null;
    }

    private boolean isValidJson(String jsonString) {
        boolean isValidJson = true;
        try {
            new Gson().fromJson(jsonString,Object.class);
        } catch (JsonSyntaxException ex) {
            isValidJson = false;
        }
        return isValidJson;
    }
}
