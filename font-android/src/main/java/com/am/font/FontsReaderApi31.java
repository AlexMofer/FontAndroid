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

import org.xmlpull.v1.XmlPullParser;

/**
 * API 31
 * font标签增加postScriptName字段
 * Created by Alex on 2022/12/19.
 */
@SuppressWarnings("WeakerAccess")
class FontsReaderApi31 extends FontsReaderApi28 {

    protected static final String ATTR_POSTSCRIPTNAME = "postScriptName";

    @Override
    protected void startFont(XmlPullParser parser) {
        super.startFont(parser);
        if (mFont != null) {
            mFont.setPostScriptName(parser.getAttributeValue(null, ATTR_POSTSCRIPTNAME));
        }
    }
}
