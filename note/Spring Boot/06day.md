# 配置文件加载位置

```properties
# Spring Boot 启动会扫描一下位置的 application.properties 或者 application.yml 文件作为 springboot 的默认配置文件
-file:./config/
-file:./
-classpath:/config/
-classpath:/
```

优先级由高到底，高优先级的配置会覆盖低优先级的配置

Spring Boot 会从这四个位置全部加载主配置文件，形成了**互补配置**

还可以通过 spring.config.location 来改变默认的配置文件位置

```properties
spring.config.location=D:/application.properties
```

项目打包后，可以使用命令行参数的形式，启动项目的时候来指定配置文件的新位置，指定配置文件和默认加载的这些配置文件共同起作用形成互补配置。

# 外部配置加载顺序

Spring Boot 也可以从以下位置加载配置，优先级由高到底，高优先级的配置会覆盖低优先级的配置，所有的配置会形成互补配置

> 1、命令行参数

```
java -jar jar包 --server.port=8087 --service.context-path=/test
多个配置用空格分开 --配置项=值
```

> 2、来自 java:comp/env 的 JNDI 属性
> 3、Java 系统属性（System.getProperties()）
> 4、操作系统环境变量
> 5、RandomValuePropertySource 配置的 random.* 属性值
> 6、jar 包外部的 application-{profile}.properties 或 application.yml（带 spring.profile）配置文件
> 7、jar 包内部的 application-{profile}.properties 或 application.yml（带 spring.profile）配置文件
> 8、jar 包外部的 application.properties 或 application.yml（不带 spring.profile）配置文件
> 9、jar 包内部的 application.properties 或 application.yml（不带 spring.profile）配置文件
> 10、@Configuration 注解类上的 @PropertySource
> 11、通过 SpringApplication.setDefaultProperties 指定的默认属性

[官方文档中有17种](https://docs.spring.io/spring-boot/docs/2.2.0.BUILD-SNAPSHOT/reference/html/spring-boot-features.html#boot-features-external-config)

# 自动配置原理

[配置文件配置属性的参照](https://docs.spring.io/spring-boot/docs/2.1.6.RELEASE/reference/html/common-application-properties.html)

1> Spring Boot 启动的时候加载主配置类，开启自动配置功能 @EnableAutoConfiguration

2> @EnableAutoConfiguration 作用

  - 利用 AutoConfigurationImportSelector 给容器中导入一些组件

  - 可以查看 selectImports() 方法的内容

  - AutoConfigurationEntry autoConfigurationEntry = getAutoConfigurationEntry(autoConfigurationMetadata,      annotationMetadata);		// 获取候选的配置

      - ```
        AutoConfigurationMetadataLoader.loadMetadata()
        扫描所有 jar包类路径下 META-INF/spring-autoconfigure-metadata.properties
        把扫描到的这些文件内容包装成 properties 对象
        从 properties 中获取到 spring-autoconfigure-metadata.properties 里的值，把他们添加到容器中
        ```

将类路径下的 META-INF/spring-autoconfigure-metadata.properties 文件里的值，添加到容器中，用他们来自动配置

3> 每一个自动配置类进行自动配置功能

4> 以 HttpEncodingAutoConfiguration （Http 编码自动配置）为例深入自动配置原理

```java
@Configuration		// 表示这是一个配置类，可以给容器中添加组件
@EnableConfigurationProperties(HttpProperties.class)	// 启用指定类的 ConfigurationProperties 功能，将配置文件中对应的值和 HttpProperties 绑定起来，并把 HttpProperties 加入到 IOC 容器中
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)	// Spring 底层 @Conditional 注解，根据不同的条件，如果满足指定的条件，整个配置类里面的配置就会生效，判断当前应用是否web应用，是则当前配置类生效
@ConditionalOnClass(CharacterEncodingFilter.class)	// 判断当前项目有没有整个类，CharacterEncodingFilter：SpringMVC 中进行乱码解决的过滤器
@ConditionalOnProperty(prefix = "spring.http.encoding", value = "enabled", matchIfMissing = true)	// 判断配置文件中是否存在某个配置 spring.http.encoding.enabled 如果不存在，判断也是成立的；即使配置文件中不配置 spring.http.encoding.enabled=true ，也是默认生效的
public class HttpEncodingAutoConfiguration {
    
    // 他已经和 SpringBoot 的配置文件映射了
    private final HttpProperties.Encoding properties;

    // 只有一个有参构造器的情况下，参数的值就会从容器中拿到
	public HttpEncodingAutoConfiguration(HttpProperties properties) {
		this.properties = properties.getEncoding();
	}
    
    @Bean	// 给容器中添加一个组件，这个组件的某些值需要从 properties 中获取
	@ConditionalOnMissingBean
	public CharacterEncodingFilter characterEncodingFilter() {
		CharacterEncodingFilter filter = new OrderedCharacterEncodingFilter();
		filter.setEncoding(this.properties.getCharset().name());
		filter.setForceRequestEncoding(this.properties.shouldForce(Type.REQUEST));
		filter.setForceResponseEncoding(this.properties.shouldForce(Type.RESPONSE));
		return filter;
	}
```

根据当前不同的条件判断，决定这个配置类是否生效

5> 所有配置文件中能配置的属性都是在 xxxProperties 类中封装着的，配置文件能配置什么就可以参照某个工能对应的这个属性类

```java
@ConfigurationProperties(prefix = "spring.http")	// 从配置文件中获取指定的值和 bean 的属性进行绑定
public class HttpProperties {
	public static class Encoding {
		public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
```

