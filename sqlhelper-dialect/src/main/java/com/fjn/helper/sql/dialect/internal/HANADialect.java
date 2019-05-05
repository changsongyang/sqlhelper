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

package com.fjn.helper.sql.dialect.internal;

import com.fjn.helper.sql.dialect.internal.limit.LimitHelper;
import com.fjn.helper.sql.dialect.RowSelection;
import com.fjn.helper.sql.dialect.internal.limit.AbstractLimitHandler;

import java.io.FilterReader;
import java.io.Reader;


public class HANADialect extends AbstractDialect {
    private static final long serialVersionUID = -379042275442752102L;

    private static class CloseSuppressingReader extends FilterReader {
        protected CloseSuppressingReader(Reader in) {
            super(in);
        }

        @Override
        public void close() {
        }
    }


    public HANADialect() {
        super();
        setLimitHandler(new AbstractLimitHandler() {
            @Override
            public String processSql(String sql, RowSelection selection) {
                boolean hasOffset = LimitHelper.hasFirstRow(selection);
                return getLimitString(sql, hasOffset);
            }

            @Override
            public String getLimitString(String sql, boolean hasOffset) {
                return sql + (hasOffset ? " limit ? offset ?" : " limit ?");
            }
        });
    }

    @Override
    public boolean isBindLimitParametersInReverseOrder() {
        return true;
    }

    @Override
    public boolean isSupportsLimit() {
        return true;
    }
}
