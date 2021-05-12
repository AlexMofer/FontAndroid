/*
 * Copyright (C) 2018 AlexMofer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.am.font;

/**
 * 字体读取器
 * Created by Alex on 2018/8/30.
 */
interface FontsReader {

    String DIR_CONFIG = "/system/etc";
    String DIR_FONTS = "/system/fonts";

    /**
     * 获取字体配置文件目录
     *
     * @return 目录
     */
    String getConfigDir();

    /**
     * 获取字体目录
     *
     * @return 目录
     */
    String getFontsDir();

    /**
     * 读取配置文件（最好异步执行）
     *
     * @return 字体族集
     */
    FamilySet readConfig();
}
