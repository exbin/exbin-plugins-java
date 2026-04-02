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
package org.exbin.jaguif.plugin.flatlaf_laf.options;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import org.exbin.jaguif.options.settings.api.SettingsOptions;
import org.exbin.jaguif.options.api.OptionsStorage;

/**
 * FlatLaf options.
 *
 * @author ExBin Project (https://exbin.org)
 */
@ParametersAreNonnullByDefault
public class LafOptions implements SettingsOptions {

    public static final String KEY_USE_BUILDIN_THEME = "flatlaf.useBuildInTheme";
    public static final String KEY_BUILDIN_THEME = "flatlaf.buildInTheme";
    public static final String KEY_CUSTOM_THEME_FILE = "flatlaf.customThemeFile";
    public static final String KEY_USE_WINDOW_DECORATIONS = "flatlaf.useWindowDecorations";
    public static final String KEY_EMBEDDED_MENU_BAR = "flatlaf.embeddedMenuBar";

    private final OptionsStorage storage;

    public LafOptions(OptionsStorage storage) {
        this.storage = storage;
    }

    public boolean isUseBuildInTheme() {
        return storage.getBoolean(KEY_USE_BUILDIN_THEME, true);
    }

    public void setUseBuildInTheme(boolean useBuildInTheme) {
        storage.putBoolean(KEY_USE_BUILDIN_THEME, useBuildInTheme);
    }

    @Nonnull
    public String getBuildInTheme() {
        return storage.get(KEY_BUILDIN_THEME, "");
    }

    public void setBuildInTheme(String buildInTheme) {
        storage.put(KEY_BUILDIN_THEME, buildInTheme);
    }

    @Nonnull
    public String getCustomFileTheme() {
        return storage.get(KEY_CUSTOM_THEME_FILE, "");
    }

    public void setCustomThemeFile(String customFileTheme) {
        storage.put(KEY_CUSTOM_THEME_FILE, customFileTheme);
    }

    public boolean isUseWindowDecorations() {
        return storage.getBoolean(KEY_USE_WINDOW_DECORATIONS, false);
    }

    public void setUseWindowDecorations(boolean useWindowDecorations) {
        storage.putBoolean(KEY_USE_WINDOW_DECORATIONS, useWindowDecorations);
    }

    public boolean isEmbeddedMenuBar() {
        return storage.getBoolean(KEY_EMBEDDED_MENU_BAR, false);
    }

    public void setEmbeddedMenuBar(boolean embeddedMenuBar) {
        storage.putBoolean(KEY_EMBEDDED_MENU_BAR, embeddedMenuBar);
    }

    @Override
    public void copyTo(SettingsOptions options) {
        LafOptions with = (LafOptions) options;
        with.setBuildInTheme(getBuildInTheme());
        with.setCustomThemeFile(getCustomFileTheme());
        with.setEmbeddedMenuBar(isEmbeddedMenuBar());
        with.setUseBuildInTheme(isUseBuildInTheme());
        with.setUseWindowDecorations(isUseWindowDecorations());
    }
}
