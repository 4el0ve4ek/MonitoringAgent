package ru.hse.monitoringagent.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.stereotype.Component;

@OpenAPIDefinition(
        info = @Info(
                title = "Monitoring Agent Api",
                description = "Monitoring Agent", version = "0.1.0",
                contact = @Contact(
                        name = "Aksenov Daniil",
                        email = "aksenoff.dany@yandex.ru"
                )
        )
)
@Component
public class OpenApiConfig {
}
