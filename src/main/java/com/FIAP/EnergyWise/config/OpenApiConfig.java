package com.FIAP.EnergyWise.config;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("EnergyWise API")
                        .description("API para Gerenciamento de Energia Solar em Comunidades")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Maurício Pereira")
                                .email("mauricio.pvieira1@gmail.com")
                                .url("https://www.linkedin.com/in/mauriciovpereira/")))
                .externalDocs(new ExternalDocumentation()
                        .description("Repositório no GitHub")
                        .url("https://github.com/Mauricio-Pereira/EnergyWise"));
    }
}
