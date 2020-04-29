package com.hhn.vs.centerapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;
import com.hhn.vs.centerapp.util.Constants;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
          .paths(PathSelectors.any())                          
          .build().apiInfo(apiEndPointsInfo());                                           
    }
	
    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title(Constants.SwaggerConfig.TITLE)
            .description(Constants.SwaggerConfig.DESCRIPTION)
            .contact(new Contact(Constants.SwaggerConfig.CONTACT_NAME, Constants.SwaggerConfig.CONTACT_WEBSITE, Constants.SwaggerConfig.CONTACT_EMAIL))
            .version(Constants.SwaggerConfig.VERSION)
            .build();
    }
}
