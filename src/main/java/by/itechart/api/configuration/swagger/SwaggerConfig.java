package by.itechart.api.configuration.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket userApiApp() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(userApiAppInfo());
    }

    private ApiInfo userApiAppInfo() {
        return new ApiInfoBuilder()
            .title("UserApi App")
            .description("Mentoring JAVA project by Dauren Delmukhambetov")
            .termsOfServiceUrl("https://www.itechart.by")
            .build();
    }
}
