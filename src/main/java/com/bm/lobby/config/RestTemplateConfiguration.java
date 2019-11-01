package com.bm.lobby.config;

import com.bm.lobby.util.BmRestTemplate;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.nio.charset.Charset;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/11/1 17:44
 */
@Configuration
public class RestTemplateConfiguration {

    @Value("${restTemplate.timeout.connectionRequest:10000}")
    private int connectionRequestTimeOut;

    @Value("${restTemplate.timeout.connect:15000}")
    private int connectTimeOut;

    @Value("${restTemplate.timeout.read:20000}")
    private int readTimeout;

    @Value("${restTemplate.pooling.maxTotal:1000}")
    private int maxTotal;

    @Value("${restTemplate.pooling.maxPerRoute:500}")
    private int maxPerRoute;

    @Value("${restTemplate.pooling.retry:3}")
    private int retryTimes;

    @Value("${restTemplate.check.duration:3000}")
    private int checkDuration;

    @Bean
    @ConditionalOnMissingBean(value = {BmRestTemplate.class})
    public BmRestTemplate BmRestTemplate() throws Exception {
        CloseableHttpClient httpClient = initHttpClient();
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        httpRequestFactory.setConnectionRequestTimeout(connectionRequestTimeOut);
        httpRequestFactory.setConnectTimeout(connectTimeOut);
        httpRequestFactory.setReadTimeout(readTimeout);
        BmRestTemplate restTemplate = new BmRestTemplate(httpRequestFactory);
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        ResponseErrorHandler responseErrorHandler = new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse clientHttpResponse) {
                return true;
            }

            @Override
            public void handleError(ClientHttpResponse clientHttpResponse){

            }
        };
        restTemplate.setErrorHandler(responseErrorHandler);
        return restTemplate;
    }


    @Bean
    public CloseableHttpClient initHttpClient() throws Exception {
        HttpClientBuilder b = HttpClientBuilder.create();
        b.setKeepAliveStrategy((response,context)->readTimeout);
        b.setDefaultRequestConfig(RequestConfig.custom().build());
        HttpClientConnectionManager connMgr = buildHttpClientConnectionManager();
        b.setConnectionManager(connMgr);
        CloseableHttpClient client = b.build();
        return client;
    }

    @Bean
    public HttpClientConnectionManager buildHttpClientConnectionManager() throws Exception {
        SSLConnectionSocketFactory sslSocketFactory = sslConnectionSocketFactory();
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", sslSocketFactory)
                .build();

        PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        connMgr.setMaxTotal(maxTotal);
        connMgr.setDefaultMaxPerRoute(maxPerRoute);
        connMgr.setValidateAfterInactivity(checkDuration);
        return connMgr;
    }


    @Bean
    public SSLContext sslContext() throws Exception{
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (x509Certificates, args2)->true).build();
        return sslContext;
    }

    @Bean
    public SSLConnectionSocketFactory sslConnectionSocketFactory() throws Exception {
        HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext(), hostnameVerifier);
        return sslSocketFactory;
    }
}

