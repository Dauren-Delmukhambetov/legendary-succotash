package by.itechart.api.configuration.swagger;

import by.itechart.api.entity.User;
import by.itechart.api.entity.UserRole;
import com.fasterxml.classmate.TypeResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    private final TypeResolver typeResolver;

    @Bean
    public Docket userApiApp() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(userApiAppInfo())
                .additionalModels(typeResolver.resolve(User.class)).additionalModels(typeResolver.resolve(UserRole.class));
    }

    private ApiInfo userApiAppInfo() {
        return new ApiInfoBuilder()
                .title("UserApi App")
                .description("Mentoring JAVA project by Dauren Delmukhambetov")
                .termsOfServiceUrl("https://www.itechart.by")
                .build();
    }

}
