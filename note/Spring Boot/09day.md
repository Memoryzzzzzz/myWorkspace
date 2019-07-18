# 模板引擎

JSP、Velocity、Freemarker、Thymeleaf

SpringBoot 推荐使用 Thymeleaf



```xml
<!-- 引入 thymeleaf 模块 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

### Thymeleaf 使用与语法

```java
@ConfigurationProperties(prefix = "spring.thymeleaf")
public class ThymeleafProperties {
    private static final Charset DEFAULT_ENCODING = StandardCharsets.UTF_8;
    public static final String DEFAULT_PREFIX = "classpath:/templates/";
    public static final String DEFAULT_SUFFIX = ".html";
    // 只要我们把 HTML 页面放在 classpath:/templates/，thymeleaf就能自动渲染
```

