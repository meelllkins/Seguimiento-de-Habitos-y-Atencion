package com.mindfocus.api.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI / Swagger configuration for interactive API documentation.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI mindFocusOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MindFocus API 🧠")
                        .description("""
                                API robusta para la gestión de hábitos y análisis de atención.
                                
                                **Características:**
                                - Gestión de sesiones de productividad con métricas de enfoque.
                                - Categorización dinámica de actividades (Trabajo, Estudio, Ocio, Redes Sociales).
                                - Historial con integridad referencial completa.
                                
                                **Arquitectura:** Hexagonal (Ports & Adapters)
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("MindFocus Team")
                                .email("contact@mindfocus.io"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
}
