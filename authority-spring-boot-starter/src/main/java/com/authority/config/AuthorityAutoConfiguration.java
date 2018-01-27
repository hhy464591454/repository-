package com.authority.config;

import com.authority.service.IAuthorityService;
import com.authority.service.impl.AuthorityService;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import javax.sql.DataSource;

@Configuration//开启配置
@ConditionalOnBean(DataSource.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@EntityScan(basePackages = "com.authority.bean")
@EnableJpaRepositories(basePackages = "com.authority.repository")
@EnableSwagger2
public class AuthorityAutoConfiguration {
        @Bean
        public IAuthorityService authorityService() {
                return new AuthorityService();
        }

}
