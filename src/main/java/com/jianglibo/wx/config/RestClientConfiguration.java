package com.jianglibo.wx.config;

import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpHost;
import org.apache.http.client.AuthCache;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfiguration {
	
    @Autowired
    private ApplicationConfig appConfig;

    @Bean(name="hbRestTemplate")
    public RestTemplate hbaseRestTemplate() {
        HttpHost host = new HttpHost(appConfig.getHbaseRestHost(), appConfig.getHbaseRestPort(), appConfig.getHbaseRestProtocol());
        RestTemplate restTemplate = new RestTemplate(
          new HttpComponentsClientHttpRequestFactoryWxSession(host));
        restTemplate.getInterceptors().add(
        		  new BasicAuthorizationInterceptor("username", "password"));
        restTemplate.getInterceptors().add(
      		  new HeaderRequestInterceptor("Accept", MediaType.APPLICATION_JSON_VALUE));
        restTemplate.getInterceptors().add(
        		  new HeaderRequestInterceptor("Content-Type", MediaType.APPLICATION_JSON_VALUE));

        return restTemplate;
    }
    
    @Bean(name="wxRestTemplate")
    public RestTemplate wxRestTemplate() {
        HttpHost host = new HttpHost(appConfig.getHbaseRestHost(), appConfig.getHbaseRestPort(), appConfig.getHbaseRestProtocol());
        RestTemplate restTemplate = new RestTemplate(
          new HttpComponentsClientHttpRequestFactoryWxSession(host));
        restTemplate.getInterceptors().add(
        		  new BasicAuthorizationInterceptor("username", "password"));
        restTemplate.getInterceptors().add(
      		  new HeaderRequestInterceptor("Accept", MediaType.APPLICATION_JSON_VALUE));
        restTemplate.getInterceptors().add(
        		  new HeaderRequestInterceptor("Content-Type", MediaType.APPLICATION_JSON_VALUE));

        return restTemplate;
    }
    
    public class HeaderRequestInterceptor  implements ClientHttpRequestInterceptor {

        private final String headerName;

        private final String headerValue;

        public HeaderRequestInterceptor(String headerName, String headerValue) {
            this.headerName = headerName;
            this.headerValue = headerValue;
        }

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            HttpRequest wrapper = new HttpRequestWrapper(request);
            wrapper.getHeaders().set(headerName, headerValue);
            return execution.execute(wrapper, body);
        }
    }
    
    public class HttpComponentsClientHttpRequestFactoryWxSession  extends HttpComponentsClientHttpRequestFactory {

        HttpHost host;
        
        public HttpComponentsClientHttpRequestFactoryWxSession(HttpHost host) {
            super();
            this.host = host;
        }
     
        @Override
        protected HttpContext createHttpContext(HttpMethod httpMethod, URI uri) {
            return createHttpContext();
        }
         
        private HttpContext createHttpContext() {
            AuthCache authCache = new BasicAuthCache();
     
            BasicScheme basicAuth = new BasicScheme();
            authCache.put(host, basicAuth);
     
            BasicHttpContext localcontext = new BasicHttpContext();
            localcontext.setAttribute(HttpClientContext.AUTH_CACHE, authCache);
            return localcontext;
        }
    }
    
}
