package com.infoedge.plugin.gcmtester.icons;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

/**
 * Created by gagandeep on 30/3/16.
 */
public class PluginIcons {
    private static Icon load(String path) {
        return IconLoader.getIcon(path, PluginIcons.class);
    }

    public static final Icon LogIcon = load("/com/infoedge/plugin/gcmtester/log_icon.png");
}
