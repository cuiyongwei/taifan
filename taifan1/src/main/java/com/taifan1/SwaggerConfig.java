package com.taifan1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Configuration注解，表明它是一个配置类，
 * @EnableSwagger2开启swagger2。
 * apiINfo()配置一些基本的信息。
 * apis()指定扫描的包会生成文档。
 */

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-10-18T18:27:24.331+08:00")

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot 测试使用 Swagger2 构建RESTful API")
                .description("API 描述")
                .license("")
                .licenseUrl("http://unlicense.org")
                .termsOfServiceUrl("")
                .version("1.0.0")
                .contact(new Contact("","", ""))
                .build();
    }

    @Bean
    public Docket customImplementation(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.taifan1.controller"))//扫描的包
                .build()
                //.directModelSubstitute(org.joda.time.LocalDate.class, java.sql.Date.class)
                //.directModelSubstitute(org.joda.time.DateTime.class, java.util.Date.class)
                .apiInfo(apiInfo());
    }


/*  swagger通过注解表明该接口会生成文档，包括接口名、请求方法、参数、返回信息的等等。
    @Api：修饰整个类，描述Controller的作用
        1. tags：可以使用tags()允许您为操作设置多个标签的属性，而不是使用该属性。
        2. description：可描述描述该类作用。
    @ApiOperation：描述一个类的一个方法，或者说一个接口，用在方法上，说明方法的作用
        value: 表示接口名称
        notes: 表示接口详细描述
    @ApiParam()用于方法，参数，字段说明； 表示对参数的添加元数据（说明或是否必填等）
    @ApiProperty：用对象接收参数时，描述对象的一个字段
    @ApiResponse：用在@ApiResponses中，一般用于表达一个错误的响应信息
        code：状态码
        message：返回自定义信息
        response：抛出异常的类
    @ApiResponses：用于方法，一个允许多个ApiResponse对象列表的包装器。
        @ApiResponses(value = {
            @ApiResponse(code = 500, message = "2001:因输入数据问题导致的报错"),
            @ApiResponse(code = 500, message = "403:没有权限"),
            @ApiResponse(code = 500, message = "2500:通用报错（包括数据、逻辑、外键关联等，不区分错误类型）")})
    @ApiIgnore：使用该注解忽略这个API，表示该接口函数不对swagger2开放展示
    @ApiError ：发生错误返回的信息
    @ApiParamImplicitL：一个请求参数
    @ApiParamsImplicit 多个请求参数
    方法级安全控制
    @PreAuthoriz进入方法之前先满足括号内的内容
    @PostAuthorize一般用于对返回的值做验证授权
    参数描述信息
    @ApiImplicitParams各个参数的各个属性，内部须用到@ApiImplicitParam注解
    @GetMapping/Postmapping相当于@RequestMapping(method = RequestMethod.GET/POST)
    @ApiImplicitParams：用在方法上包含多个 @ApiImplicitParam：
      例如：@ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "book's name", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "date", value = "book's date", required = false, dataType = "string", paramType = "query")})
    @ApiImplicitParam：用在@ApiImplicitParams注解中，指定一个请求参数的各个方面 ，给出某请求参数（来自浏览器前台）的各个属性
        name：参数名
        value：参数的描述
        required：参数是否必须传
        dataType：参数类型
        defaultValue：参数的默认值
        paramType：参数位置
            header 参数在request headers 里边提交 对应获取参数值的注解：@RequestHeader
            query  直接跟参数完成自动映射赋值       对应获取参数值的注解：@RequestParam
            path   以地址的形式提交数据            对应获取参数值的注解： @PathVariable
            body   以流的形式提交 仅支持POST        对应获取参数值的注解: @RequestBody
            form   对应以form表单的形式提交         仅支持POST
    @ApiModel()用于类 ；表示对类进行说明，用于参数用实体类接收
        value–表示对象名
        description–描述
    都可省略
    @ApiModelProperty()用于方法，字段； 表示对model属性的说明或者数据操作更改
        value–字段说明
        name–重写属性名字
        dataType–重写属性类型
        required–是否必填
        example–举例说明
        hidden–隐藏
    @ResponseHeader：
        响应头设置，使用方法。
    */

}
