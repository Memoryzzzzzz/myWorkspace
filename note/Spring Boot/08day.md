# Web 开发

创建 Spring Boot 应用，选中我们需要的模块，Spring Boot 已经默认将这些场景配置好了，只需要在配置文件中指定少量配置就可以运行起来，接下来编写业务代码即可

### 自动配置

自动帮我们配置了很多的 bean 

```
xxxAutoConfiguration	帮我们给容器中自动配置组件
xxxProperties	配置类来封装配置文件的内容
```

### Spring Boot 对静态资源的映射规则

```java
@ConfigurationProperties(prefix = "spring.resources", ignoreUnknownFields = false)
public class ResourceProperties {
	// 可以设置和静态资源有关的参数，缓存时间等
```

```java
@Override
public void addResourceHandlers(ResourceHandlerRegistry registry) {
    if (!this.resourceProperties.isAddMappings()) {
        logger.debug("Default resource handling disabled");
        return;
    }
    Duration cachePeriod = this.resourceProperties.getCache().getPeriod();
    CacheControl cacheControl = this.resourceProperties.getCache().getCachecontrol().toHttpCacheControl();
    if (!registry.hasMappingForPattern("/webjars/**")) {
        customizeResourceHandlerRegistration(registry.addResourceHandler("/webjars/**")
                                             .addResourceLocations("classpath:/META-INF/resources/webjars/")
                                             .setCachePeriod(getSeconds(cachePeriod)).setCacheControl(cacheControl));
    }
    String staticPathPattern = this.mvcProperties.getStaticPathPattern();
    if (!registry.hasMappingForPattern(staticPathPattern)) {
        customizeResourceHandlerRegistration(registry.addResourceHandler(staticPathPattern)
                                             .addResourceLocations(getResourceLocations(this.resourceProperties.getStaticLocations()))
                                             .setCachePeriod(getSeconds(cachePeriod)).setCacheControl(cacheControl));
    }
}

// 配置欢迎页映射
@Bean
public WelcomePageHandlerMapping welcomePageHandlerMapping(ApplicationContext applicationContext) {
    return new WelcomePageHandlerMapping(new TemplateAvailabilityProviders(applicationContext),applicationContext, getWelcomePage(), this.mvcProperties.getStaticPathPattern());
}

// 配置喜欢的图标
@Configuration
@ConditionalOnProperty(value = "spring.mvc.favicon.enabled", matchIfMissing = true)
public static class FaviconConfiguration implements ResourceLoaderAware {

    private final ResourceProperties resourceProperties;

    private ResourceLoader resourceLoader;

    public FaviconConfiguration(ResourceProperties resourceProperties) {
        this.resourceProperties = resourceProperties;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Bean
    public SimpleUrlHandlerMapping faviconHandlerMapping() {
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        // 所有 **/favicon.ico
      mapping.setUrlMap(Collections.singletonMap("**/favicon.ico", faviconRequestHandler()));
        return mapping;
    }

    @Bean
    public ResourceHttpRequestHandler faviconRequestHandler() {
        ResourceHttpRequestHandler requestHandler = new ResourceHttpRequestHandler();
        requestHandler.setLocations(resolveFaviconLocations());
        return requestHandler;
    }

    private List<Resource> resolveFaviconLocations() {
        String[] staticLocations = getResourceLocations(this.resourceProperties.getStaticLocations());
        List<Resource> locations = new ArrayList<>(staticLocations.length + 1);
        Arrays.stream(staticLocations).map(this.resourceLoader::getResource).forEach(locations::add);
        locations.add(new ClassPathResource("/"));
        return Collections.unmodifiableList(locations);
    }
}
```

##### 1、所有 webjars/** ，都去 classpath:/META-INF/resources/webjars/ 找资源

 	webjars：以 jar 包的方式引入静态资源

```
@Bean
public WelcomePageHandlerMapping welcomePageHandlerMapping(ApplicationContext applicationContext) {
return new WelcomePageHandlerMapping(new TemplateAvailabilityProviders(applicationContext),
applicationContext, getWelcomePage(), this.mvcProperties.getStaticPathPattern());
}
```

https://www.webjars.org/

![](https://img-blog.csdnimg.cn/20190718152942355.png)

```
localhost:8888/webjars/jquery/3.4.1/jquery.js
```

```xml
<!-- 引入 jQuery-webjar -->
<dependency>
    <groupId>org.webjars</groupId>
    <artifactId>jquery</artifactId>
    <version>3.4.1</version>
</dependency>
<!-- 在访问的时候只需要写 webjars 下面资源的名称即可 -->
```

##### 2、/** 访问当前项目的任何资源

```
classpath:/META-INF/resources/",
"classpath:/resources/", 
"classpath:/static/", 
"classpath:/public/,
"/"	当前项目的根路径
```

localhost:8888/abc	去静态资源文奸加里找 abc

##### 3、欢迎页，静态资源文件夹下的所有 index.htnl 页面，被"/**"映射

​	localhost:8888/	找 index 页面

##### 4、所有的 **/favicon.ico 都是在静态资源文件下找

### 定义静态文件夹路径

```properties
spring.resources.static-locations=classpath:/hello,classpath:/memory
```

