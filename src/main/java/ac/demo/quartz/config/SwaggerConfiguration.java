package ac.demo.quartz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger配置类
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket testApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
//                .globalOperationParameters(operationParameters())
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")// base，最终调用接口后会和paths拼接在一起
                .select()
                .apis(RequestHandlerSelectors.basePackage("ac.demo.quartz"))
                .build();
    }

    /**
     * API文档名称和版本
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("测试API文档")
                .description("Quartz测试")
                .version("1.0-SNAPSHOT") // 版本
                .build();
    }

    /**
     * 全局配置参数说明
     *
     * @return
     */
//    private List<Parameter> operationParameters() {
//        // 国际化
//        ParameterBuilder i18nParameterBuilder = new ParameterBuilder();
//        i18nParameterBuilder.name(LocaleChangeInterceptor.DEFAULT_PARAM_NAME).description("国际化参数，默认zh_CN(简体中文), en_US(英文)")
//                .modelRef(new ModelRef("string"))
//                .parameterType("query")
//                .required(false);
//
//        // 组装
//        List<Parameter> paramlist = new ArrayList<>();
//        paramlist.add(i18nParameterBuilder.build());
//
//        return paramlist;
//    }
}
