package com.rlaghlwns.stronglifts

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("StrongLifts API")
                    .version("1")
                    .description("StrongLifts 훈련 목표를 설정해 줍니다.")
            )
    }
}