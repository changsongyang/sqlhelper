
/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the LGPL, Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at  http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.fangjinuo.sqlhelper.dialect.internal.urlparser;

import com.github.fangjinuo.sqlhelper.dialect.DatabaseInfo;

import java.util.ArrayList;
import java.util.List;

public class UnKnownDatabaseInfo {
    public static final DatabaseInfo INSTANCE;

    public static DatabaseInfo createUnknownDataBase(final String url) {
        return createUnknownDataBase(null, url);
    }

    public static DatabaseInfo createUnknownDataBase(final String vendor, final String url) {
        final List<String> list = new ArrayList<String>();
        list.add("unknown");
        return new DefaultDatabaseInfo(vendor, url, url, list, "unknown", false);
    }

    static {
        final List<String> urls = new ArrayList<String>();
        urls.add("unknown");
        INSTANCE = new DefaultDatabaseInfo("JDBC", "unknown", "unknown", urls, "unknown", false);
    }
}
