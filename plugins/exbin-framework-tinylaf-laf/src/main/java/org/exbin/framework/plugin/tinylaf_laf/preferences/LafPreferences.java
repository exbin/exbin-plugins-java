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
package org.exbin.framework.plugin.tinylaf_laf.preferences;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import org.exbin.framework.plugin.tinylaf_laf.options.LafOptions;
import org.exbin.framework.preferences.api.Preferences;

/**
 * TinyLaf preferences.
 *
 * @author ExBin Project (https://exbin.org)
 */
@ParametersAreNonnullByDefault
public class LafPreferences implements LafOptions {

    public static final String PREFERENCES_USE_BUILDIN_THEME = "tinylaf.useBuildInTheme";
    public static final String PREFERENCES_BUILDIN_THEME = "tinylaf.buildInTheme";
    public static final String PREFERENCES_CUSTOM_THEME_FILE = "tinylaf.customThemeFile";
    public static final String PREFERENCES_FRAMES_DECORATION = "tinylaf.framesDecoration";
    public static final String PREFERENCES_DIALOGS_DECORATION = "tinylaf.dialogsDecoration";
    public static final String PREFERENCES_NO_ERASE_ON_RESIZE = "tinylaf.noEraseOnResize";
    public static final String PREFERENCES_DYNAMIC_LAYOUT = "tinylaf.dynamicLayout";

    private final Preferences preferences;

    public LafPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public boolean isUseBuildInTheme() {
        return preferences.getBoolean(PREFERENCES_USE_BUILDIN_THEME, true);
    }

    @Override
    public void setUseBuildInTheme(boolean use) {
        preferences.putBoolean(PREFERENCES_USE_BUILDIN_THEME, use);
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
    public boolean isFramesDecoration() {
        return preferences.getBoolean(PREFERENCES_FRAMES_DECORATION, false);
    }

    @Override
    public void setFramesDecoration(boolean framesDecoration) {
        preferences.putBoolean(PREFERENCES_FRAMES_DECORATION, framesDecoration);
    }

    @Override
    public boolean isDialogsDecoration() {
        return preferences.getBoolean(PREFERENCES_DIALOGS_DECORATION, false);
    }

    @Override
    public void setDialogsDecoration(boolean dialogsDecoration) {
        preferences.putBoolean(PREFERENCES_DIALOGS_DECORATION, dialogsDecoration);
    }

    @Override
    public boolean isNoEraseOnResize() {
        return preferences.getBoolean(PREFERENCES_NO_ERASE_ON_RESIZE, true);
    }

    @Override
    public void setNoEraseOnResize(boolean noEraseOnResize) {
        preferences.putBoolean(PREFERENCES_NO_ERASE_ON_RESIZE, noEraseOnResize);
    }

    @Override
    public boolean isDynamicLayout() {
        return preferences.getBoolean(PREFERENCES_DYNAMIC_LAYOUT, true);
    }

    @Override
    public void setDynamicLayout(boolean dynamicLayout) {
        preferences.putBoolean(PREFERENCES_DYNAMIC_LAYOUT, dynamicLayout);
    }
}
