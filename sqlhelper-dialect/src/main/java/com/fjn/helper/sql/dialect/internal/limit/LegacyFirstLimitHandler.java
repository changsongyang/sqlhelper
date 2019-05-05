/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the LGPL, Version 2.1 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at  http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fjn.helper.sql.dialect.internal.limit;

import com.fjn.helper.sql.dialect.RowSelection;

import java.util.Locale;


public class LegacyFirstLimitHandler
        extends AbstractLimitHandler {
    public static final LegacyFirstLimitHandler INSTANCE = new LegacyFirstLimitHandler();


    public String processSql(String sql, RowSelection selection) {
        return new StringBuilder(sql.length() + 16).append(sql).insert(sql.toLowerCase(Locale.ROOT).indexOf("select") + 6, " first " + getMaxOrLimit(selection)).toString();
    }
}
