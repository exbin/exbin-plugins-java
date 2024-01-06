/*
 * Copyright (C) ExBin Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.exbin.framework.plugins.flatlaf_laf;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.UIManager;
import org.exbin.xbup.plugin.LookAndFeelApplier;
import org.exbin.framework.api.XBApplication;
import org.exbin.framework.api.XBApplicationModule;
import org.exbin.xbup.plugin.XBModuleHandler;

/**
 * FlatLaf look and feel plugin.
 *
 * @author ExBin Project (https://exbin.org)
 */
@ParametersAreNonnullByDefault
public class FlatLafLafModule implements XBApplicationModule, LookAndFeelApplier {

    private XBApplication application;

    public FlatLafLafModule() {
    }

    @Override
    public void init(XBModuleHandler moduleHandler) {
        this.application = (XBApplication) moduleHandler;

        String flatDarkClassName = FlatDarkLaf.class.getName();
        UIManager.installLookAndFeel(new UIManager.LookAndFeelInfo("FlatDark", flatDarkClassName));
        application.registerLafPlugin(flatDarkClassName, this);

        String flatLightClassName = FlatLightLaf.class.getName();
        UIManager.installLookAndFeel(new UIManager.LookAndFeelInfo("FlatLight", flatLightClassName));
        application.registerLafPlugin(flatLightClassName, this);
    }

    @Override
    public void applyLookAndFeel(String className) {
        String flatDarkClassName = FlatDarkLaf.class.getName();
        if (className.equals(flatDarkClassName)) {
            try {
                FlatDarkLaf.install();
                UIManager.setLookAndFeel(new FlatDarkLaf());
            } catch (Throwable ex) {
                System.err.println("Failed to initialize LaF");
            }
        } else {
            try {
                FlatLightLaf.install();
                UIManager.setLookAndFeel(new FlatLightLaf());
            } catch (Throwable ex) {
                System.err.println("Failed to initialize LaF");
            }
        }
    }

    @Override
    public void unregisterModule(String moduleId) {
    }
}
