package com.jooq.dbaccess.config;

import javax.sql.DataSource;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

@Configuration
public class JooqConfig {

  private DataSource dateSource;

  @Bean
  public DataSourceConnectionProvider connectionProvider() {
    return new DataSourceConnectionProvider
        (new TransactionAwareDataSourceProxy(dataSource));
  }



  @Bean
  public DefaultDSLContext dsl() {
    return new DefaultDSLContext(configuration());
  }

  public DefaultConfiguration configuration() {
    DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
    jooqConfiguration.set(connectionProvider());
    jooqConfiguration
        .set(new DefaultExecuteListenerProvider(exceptionTransformer()));

    return jooqConfiguration;
  }

  @Autowired
  public void setDateSource(DataSource dateSource) {
    this.dateSource = dateSource;
  }
}
