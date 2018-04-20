package br.org.roger.exam.blog.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Configuration class for Swagger 2 documentation.
 *
 * @author Marcos (mroger.oliveira@gmail.com)
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket blogApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.org.roger.exam.blog"))
                .paths(PathSelectors.regex("/blog.*"))
                .build()
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET,
                        newArrayList(new ResponseMessageBuilder()
                                .code(404)
                                .message("Resource Not Found")
                                .responseModel(new ModelRef("Error"))
                                .build()))
                .globalResponseMessage(RequestMethod.POST,
                        newArrayList(new ResponseMessageBuilder()
                                .code(400)
                                .message("Bad Request")
                                .responseModel(new ModelRef("Error"))
                                .build()))
                .globalResponseMessage(RequestMethod.POST,
                        newArrayList(new ResponseMessageBuilder()
                                .code(400)
                                .message("Bad Request")
                                .responseModel(new ModelRef("Error"))
                                .build()))
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Blog API",
                "This API was built to manage Blog posts.",
                "1.0",
                "API TOS",
                contact(),
                "License of API",
                "license.html");
        return apiInfo;
    }

    private Contact contact() {
        return new Contact("Marcos",
                "https://mr0ger.wordpress.com/",
                "mroger.oliveira@gmail.com");
    }

}
