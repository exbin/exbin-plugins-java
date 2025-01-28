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
package org.exbin.framework.plugin.flatlaf_laf.preferences;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import org.exbin.framework.plugin.flatlaf_laf.options.LafOptions;
import org.exbin.framework.preferences.api.Preferences;

/**
 * FlatLaf preferences.
 *
 * @author ExBin Project (https://exbin.org)
 */
@ParametersAreNonnullByDefault
public class LafPreferences implements LafOptions {

    public static final String PREFERENCES_USE_BUILDIN_THEME = "flatlaf.useBuildInTheme";
    public static final String PREFERENCES_BUILDIN_THEME = "flatlaf.buildInTheme";
    public static final String PREFERENCES_CUSTOM_THEME_FILE = "flatlaf.customThemeFile";
    public static final String PREFERENCES_USE_WINDOW_DECORATIONS = "flatlaf.useWindowDecorations";
    public static final String PREFERENCES_EMBEDDED_MENU_BAR = "flatlaf.embeddedMenuBar";

    private final Preferences preferences;

    public LafPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public boolean isUseBuildInTheme() {
        return preferences.getBoolean(PREFERENCES_USE_BUILDIN_THEME, true);
    }

    @Override
    public void setUseBuildInTheme(boolean useBuildInTheme) {
        preferences.putBoolean(PREFERENCES_USE_BUILDIN_THEME, useBuildInTheme);
    }

    @Nonnull
    @Override
    public String getBuildInTheme() {
        return preferences.get(PREFERENCES_BUILDIN_THEME, "");
    }

    @Override
    public void setBuildInTheme(String buildInTheme) {
        preferences.put(PREFERENCES_BUILDIN_THEME, buildInTheme);
    }

    @Nonnull
    @Override
    public String getCustomFileTheme() {
        return preferences.get(PREFERENCES_CUSTOM_THEME_FILE, "");
    }

    @Override
    public void setCustomThemeFile(String customFileTheme) {
        preferences.put(PREFERENCES_CUSTOM_THEME_FILE, customFileTheme);
    }

    @Override
    public boolean isUseWindowDecorations() {
        return preferences.getBoolean(PREFERENCES_USE_WINDOW_DECORATIONS, false);
    }

    @Override
    public void setUseWindowDecorations(boolean useWindowDecorations) {
        preferences.putBoolean(PREFERENCES_USE_WINDOW_DECORATIONS, useWindowDecorations);
    }

    @Override
    public boolean isEmbeddedMenuBar() {
        return preferences.getBoolean(PREFERENCES_EMBEDDED_MENU_BAR, false);
    }

    @Override
    public void setEmbeddedMenuBar(boolean embeddedMenuBar) {
        preferences.putBoolean(PREFERENCES_EMBEDDED_MENU_BAR, embeddedMenuBar);
    }
}
