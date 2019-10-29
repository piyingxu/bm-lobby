package com.bm.lobby.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.nio.charset.Charset;


@Configuration
public class SwaggerConfiguration {

    @Value("${spring.application.name}")
    private String applicaitonName;

    @Value("${spring.application.author}")
    private String author;

    @Value("${spring.application.description}")
    private String description;

    @Value("${server.port}")
    private String port;

    @Bean
    @Primary
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        String address = "http://127.0.0.1:" + port;
        return new ApiInfoBuilder()
                .title(applicaitonName + " 服务API")
                .description(description)
                //.termsOfServiceUrl(address + "/swagger-ui.html#/")
                //.contact(new Contact(author, address + "/swagger-ui.html#/", "516609723@qq.com"))
                //.version("9.9")
                .build();
    }

    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
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

}
