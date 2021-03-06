package br.com.southsystem.votos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {            
	
	@Bean
	public Docket apiV10() {
	    return new Docket(DocumentationType.SWAGGER_2)
	        .groupName("votos-api-1.0")
	        .select()
	            .apis(RequestHandlerSelectors.basePackage("br.com.southsystem.votos.controller"))
	            .paths(PathSelectors.regex("/.*/v1.0.*"))
	        .build()
	        .apiInfo(apiInfo());
	}

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Votos API")
                .description("Documentação Votos API V1.0 <br>" + 
                		"Na Pauta controller é possível criar a nova pauta e solicitar contagem e resultado da votação.<br>" +
                		"Na Sessão controller é onde será solicitado a abertura da sessão<br>" +
                		"No Voto controller é onde é feito o voto.")
                .build();
    }
    
}