package com.jianglibo.wx;

import javax.sql.DataSource;

import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.jianglibo.wx.config.ApplicationConfig;
import com.jianglibo.wx.config.KatharsisModuleConfig;
import com.jianglibo.wx.katharsis.dto.LoginAttemptDto;
import com.jianglibo.wx.katharsis.dto.RoleDto;
import com.jianglibo.wx.katharsis.dto.UserDto;

import io.katharsis.client.KatharsisClient;
import io.katharsis.client.http.apache.HttpClientAdapter;
import io.katharsis.client.http.apache.HttpClientAdapterListener;
import io.katharsis.spring.boot.v3.KatharsisConfigV3;

@SpringBootApplication
@EnableSpringDataWebSupport
@EnableJpaRepositories(basePackages="com.jianglibo.wx.repository")
@EnableWebMvc
@EnableAsync
@EnableScheduling
@EnableAspectJAutoProxy
@Import({ KatharsisConfigV3.class, KatharsisModuleConfig.class })
@ComponentScan(basePackages={"com.jianglibo"})
public class Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
    }

    
    
    @Bean
    public KatharsisClient katharsisClient(@Value("${katharsis.domainName}") String domainName, @Value("${katharsis.pathPrefix}") String pathPrefix) {
    	
    	KatharsisClient kc = new KatharsisClient(domainName + pathPrefix);
    	HttpClientAdapter hca = (HttpClientAdapter) kc.getHttpAdapter();
    	hca.addListener(new HttpClientAdapterListener() {
			@Override
			public void onBuild(HttpClientBuilder builder) {
				System.out.println(builder);
			}
		});
    	// load resource
    	kc.getRepositoryForType(UserDto.class);
    	kc.getRepositoryForType(RoleDto.class);
    	kc.getRepositoryForType(LoginAttemptDto.class);
    	return kc;
    }
	
    @Bean
    @Primary
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }
}
