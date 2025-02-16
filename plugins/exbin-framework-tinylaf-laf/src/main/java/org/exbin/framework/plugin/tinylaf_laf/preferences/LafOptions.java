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
import org.exbin.framework.options.api.OptionsData;
import org.exbin.framework.preferences.api.OptionsStorage;

/**
 * TinyLaf options.
 *
 * @author ExBin Project (https://exbin.org)
 */
@ParametersAreNonnullByDefault
public class LafOptions implements OptionsData {

    public static final String KEY_USE_BUILDIN_THEME = "tinylaf.useBuildInTheme";
    public static final String KEY_BUILDIN_THEME = "tinylaf.buildInTheme";
    public static final String KEY_CUSTOM_THEME_FILE = "tinylaf.customThemeFile";
    public static final String KEY_FRAMES_DECORATION = "tinylaf.framesDecoration";
    public static final String KEY_DIALOGS_DECORATION = "tinylaf.dialogsDecoration";
    public static final String KEY_NO_ERASE_ON_RESIZE = "tinylaf.noEraseOnResize";
    public static final String KEY_DYNAMIC_LAYOUT = "tinylaf.dynamicLayout";

    private final OptionsStorage storage;

    public LafOptions(OptionsStorage storage) {
        this.storage = storage;
    }

    public boolean isUseBuildInTheme() {
        return storage.getBoolean(KEY_USE_BUILDIN_THEME, true);
    }

    public void setUseBuildInTheme(boolean use) {
        storage.putBoolean(KEY_USE_BUILDIN_THEME, use);
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

    public boolean isFramesDecoration() {
        return storage.getBoolean(KEY_FRAMES_DECORATION, false);
    }

    public void setFramesDecoration(boolean framesDecoration) {
        storage.putBoolean(KEY_FRAMES_DECORATION, framesDecoration);
    }

    public boolean isDialogsDecoration() {
        return storage.getBoolean(KEY_DIALOGS_DECORATION, false);
    }

    public void setDialogsDecoration(boolean dialogsDecoration) {
        storage.putBoolean(KEY_DIALOGS_DECORATION, dialogsDecoration);
    }

    public boolean isNoEraseOnResize() {
        return storage.getBoolean(KEY_NO_ERASE_ON_RESIZE, true);
    }

    public void setNoEraseOnResize(boolean noEraseOnResize) {
        storage.putBoolean(KEY_NO_ERASE_ON_RESIZE, noEraseOnResize);
    }

    public boolean isDynamicLayout() {
        return storage.getBoolean(KEY_DYNAMIC_LAYOUT, true);
    }

    public void setDynamicLayout(boolean dynamicLayout) {
        storage.putBoolean(KEY_DYNAMIC_LAYOUT, dynamicLayout);
    }

    @Override
    public void copyTo(OptionsData options) {
        LafOptions with = (LafOptions) options;
        with.setBuildInTheme(getBuildInTheme());
        with.setCustomThemeFile(getCustomFileTheme());
        with.setDialogsDecoration(isDialogsDecoration());
        with.setDynamicLayout(isDynamicLayout());
        with.setFramesDecoration(isFramesDecoration());
        with.setNoEraseOnResize(isNoEraseOnResize());
        with.setUseBuildInTheme(isUseBuildInTheme());
    }
}
