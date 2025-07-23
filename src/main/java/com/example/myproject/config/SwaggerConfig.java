package com.example.myproject.config;

import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
        .info(new Info()
                .title("VMS Project API Documentation")
                .description("This is the API documentation for Vulnerability Management System.\n\n"
                +"GitHub Repository: [https://github.com/parul662006/VMS-project/tree/dev](https://github.com/parul662006/VMS-project/tree/dev)"
                )
                .version("1.0.0")
                .contact(new Contact()
                        .name("Parul Kumawat")
                        .email("parulkumawat666@gmail.com")
                        .url("https://github.com/parul662006/VMS-project.git")
                )
                .license(new License()
                        .name("Apache 2.0")
                        .url("https://www.apache.org/licenses/LICENSE-2.0")))

                .servers(List.of(
                        new Server().url("http://localhost:9094").description("Local server")
                ));

    }
}
