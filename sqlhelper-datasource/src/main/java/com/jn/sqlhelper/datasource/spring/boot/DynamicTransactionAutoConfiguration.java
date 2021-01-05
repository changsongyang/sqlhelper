/*
 * Copyright 2021 the original author or authors.
 *
 * Licensed under the Apache, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at  http://www.gnu.org/licenses/lgpl-2.0.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jn.sqlhelper.datasource.spring.boot;

import com.jn.agileway.spring.aop.AspectJExpressionPointcutAdvisorBuilder;
import com.jn.langx.invocation.aop.expression.AspectJExpressionPointcutAdvisorProperties;
import com.jn.sqlhelper.common.transaction.DefaultTransactionManager;
import com.jn.sqlhelper.common.transaction.TransactionManager;
import com.jn.sqlhelper.datasource.config.DataSourcesProperties;
import com.jn.sqlhelper.datasource.spring.aop.LocalizeGlobalTransactionInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "sqlhelper.dynamicDataSource.enabled", havingValue = "true")
@ConditionalOnClass(AspectJExpressionPointcutAdvisorProperties.class)
public class DynamicTransactionAutoConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourcesAutoConfiguration.class);

    @ConditionalOnProperty(prefix = "sqlhelper.dynamicDataSource.transaction", name = "expression")
    public TransactionManager transactionManager() {
        DefaultTransactionManager transactionManager = new DefaultTransactionManager();
        return transactionManager;
    }

    @Bean("dynamicDataSourceTransactionAdvisor")
    @ConditionalOnProperty(prefix = "sqlhelper.dynamicDataSource.transaction", name = "expression")
    @ConditionalOnMissingBean(name = "dynamicDataSourceTransactionAdvisor")
    public AspectJExpressionPointcutAdvisor dynamicDataSourceTransactionAdvisor(
            DataSourcesProperties properties,
            TransactionManager transactionManager) {

        LocalizeGlobalTransactionInterceptor interceptor = new LocalizeGlobalTransactionInterceptor();
        interceptor.setTransactionManager(transactionManager);

        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisorBuilder()
                .properties(properties.getTransaction())
                .interceptor(interceptor)
                .build();
        logger.info("===[SQLHelper & Dynamic DataSource]=== Add dynamic datasource transaction interceptor, use an expression: {}", properties.getTransaction().getExpression());
        return advisor;
    }
}
