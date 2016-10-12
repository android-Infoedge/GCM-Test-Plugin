package com.infoedge.plugin.gcmtester.ui;

import javax.swing.*;

/**
 * Created by gagandeep on 29/3/16.
 */
public class DummyProgressBarThread implements Runnable {

    private JProgressBar progressBar;
    private boolean keepLooping = true;

    public DummyProgressBarThread(JProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public void doneLoading() {
        keepLooping = false;
    }

    @Override
    public void run() {
        while (keepLooping) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    int currentValue = progressBar.getValue();
                    currentValue = currentValue + 10;
                    if(currentValue >= 100) {
                        currentValue = 0;
                    }
                    progressBar.setValue( currentValue);
                }
            });
        }
    }
}
