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
package org.exbin.framework.plugin.flatlaf_laf.options.impl;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import org.exbin.framework.options.api.OptionsData;
import org.exbin.framework.plugin.flatlaf_laf.options.LafOptions;

/**
 * LaF options.
 *
 * @author ExBin Project (https://exbin.org)
 */
@ParametersAreNonnullByDefault
public class LafOptionsImpl implements OptionsData, LafOptions {

    private boolean useBuildInTheme = true;
    private String buildInTheme = "";
    private String customFileTheme = "";
    private boolean useWindowDecorations = false;
    private boolean embeddedMenuBar = false;

    @Override
    public boolean isUseBuildInTheme() {
        return useBuildInTheme;
    }

    @Override
    public void setUseBuildInTheme(boolean use) {
        useBuildInTheme = use;
    }

    @Nonnull
    @Override
    public String getBuildInTheme() {
        return buildInTheme;
    }

    @Override
    public void setBuildInTheme(String buildInTheme) {
        this.buildInTheme = buildInTheme;
    }

    @Nonnull
    @Override
    public String getCustomFileTheme() {
        return customFileTheme;
    }

    @Override
    public void setCustomThemeFile(String customFileTheme) {
        this.customFileTheme = customFileTheme;
    }

    @Override
    public boolean isUseWindowDecorations() {
        return useWindowDecorations;
    }

    @Override
    public void setUseWindowDecorations(boolean useWindowDecorations) {
        this.useWindowDecorations = useWindowDecorations;
    }

    @Override
    public boolean isEmbeddedMenuBar() {
        return embeddedMenuBar;
    }

    @Override
    public void setEmbeddedMenuBar(boolean embeddedMenuBar) {
        this.embeddedMenuBar = embeddedMenuBar;
    }
}
