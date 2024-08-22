package com.karimzai.greenatomtask.API.Configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "File storage service", version = "v1"))
public class SwaggerConfig {
}
