package com.example.demo;

import com.google.common.collect.Ordering;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiDescription;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiListingReference;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@EnableAutoConfiguration
@EnableSwagger2
@SpringBootApplication(scanBasePackages = {"com.example.demo.controller","com.authority.controller"})
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public Docket createRestApi() {
		Docket docket = new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.forCodeGeneration(true)
				.useDefaultResponseMessages(false)
				.select()
//				.apis(RequestHandlerSelectors.basePackage("com.example.demo.controller"))
//				.apis(RequestHandlerSelectors.basePackage("com.htiwoo.authority.controller"))
				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				.build()
				.apiDescriptionOrdering(new Ordering<ApiDescription>() {
					@Override
					public int compare(ApiDescription left, ApiDescription right) {
						int position = left.getDescription().compareTo(right.getDescription());
						return position;
					}
				})
				.apiListingReferenceOrdering(new Ordering<ApiListingReference>() {
					@Override
					public int compare(ApiListingReference left, ApiListingReference right) {
						int position = left.getDescription().compareTo(right.getDescription());
						return position;
					}
				})
				.globalResponseMessage(RequestMethod.GET, responseMessages())
				.globalResponseMessage(RequestMethod.POST, responseMessages());
		return docket;
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("TKC 用户账号系统")
				.description("用户充值，体现系统")
				.version("1.0").termsOfServiceUrl("http://www.reeed.{env}.com/").build();
	}

	private List<ResponseMessage> responseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder()
						.code(200)
						.message("操作成功")
						.responseModel(new ModelRef("{\"code\": \"0\",\"data\": {},\"message\": \"操作成功\"}"))
						.build(),
				new ResponseMessageBuilder()
						.code(500)
						.message("系统异常")
						.responseModel(new ModelRef("{\"code\": \"0\",\"data\": {},\"message\": \"系统异常\"}"))
						.build(),
				new ResponseMessageBuilder()
						.code(400)
						.message("请求错误")
						.responseModel(new ModelRef("Error"))
						.build(),
				new ResponseMessageBuilder()
						.code(401)
						.message("参数为空错误")
						.responseModel(new ModelRef("Error"))
						.build()
		);
	}
}
