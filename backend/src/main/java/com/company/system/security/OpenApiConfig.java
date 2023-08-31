package com.company.system.security;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.stereotype.Component;

@Component
@OpenAPIDefinition(
        info = @Info(title = "My REST API", version = "1.0.0",
            description = "My OpenAPIDefinition description"),
                servers = { @Server(url = "/", description = "Default URL")}
)
public class OpenApiConfig { }