package by.itechart.api.configuration.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class AppConfig {

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    /**
     * This bean changes the default parameter name of page size in the Pageable interface
     * that is passed in the controller. Also, this bean changes the default behavior of page indexes passed in URL path.
     * By default, to rich the first page, we need to use 0 index in the path.
     * But with setOneIndexedParameters we can reach the first page with 1 index, which is more user-friendly.
     */
    @Bean
    PageableHandlerMethodArgumentResolverCustomizer pageableResolverCustomizer() {
        return pageableResolver -> {
            pageableResolver.setSizeParameterName("pageSize");
            pageableResolver.setOneIndexedParameters(true);
        };
    }

}
