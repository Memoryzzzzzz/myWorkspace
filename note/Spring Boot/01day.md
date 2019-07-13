# 第一天

## 1、Spring Boot 介绍

> 简化Spring框架配置
>
> 整合Spring技术栈
>
> J2EE开发一站式解决方案
>
> [参考文件](https://docs.spring.io/spring-boot/docs/2.1.6.RELEASE/reference/html/)

## 2、微服务

> 架构风格（服务微化）
>
> 一个应用就是一组小型服务
>
> 可以通过HTTP的方式进行互通
>
> 每一个功能元素最终都是一个可独立替换和独立升级的软件单元
>
> [什么是微服务？](http://www.sohu.com/a/221400925_100039689)

## 3、Maven设置

maven\conf\settings.xml中的

```xml
<proxies>
    <profile>
        <id>jdk-1.8</id>
        <activation>
            <activeByDefault>true</activeByDefault>
            <jdk>1.8</jdk>
        </activation>
        <properties>
            <maven.compiler.source>1.8</maven.compiler.source>
            <maven.compiler.target>1.8</maven.compiler.target>
            <maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>
        </properties>
    </profile>
</proxies>
```

## 4、IDEA设置

> Settings — Build,Execution,Deployment — Build Tools — Maven
>
> Maven home directory 改成maven的路径，不要使用自带的
>
> 勾选 User setting file 与 Local reposiory 后面的Override
>
> User setting file 换成刚刚修改的 maven\conf\settings.xml
>
> Local reposiory 改成 maven\repository

![](https://img-blog.csdnimg.cn/20190705162935399.png)

## 5、Spring Boot HelloWorld

### 5.1  创建一个 maven 项目（jar）

IDEA 新建 maven 项目后，选择 Enable Auto-Import ，启动自动导入

![](https://img-blog.csdnimg.cn/2019062614552080.png)

### 5.2 导入 spring boot 相关依赖

在 pom.xml 中添加

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.1.6.RELEASE</version>
</parent>
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```

这里使用的是 Spring Boot 2.1.6 版本

### 5.3 写一个主程序，启动 spring boot 应用

```java
package com.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @SpringBootApplication
 * 来标注一个主程序类
 * 说明这是一个 spring boot 应用
 */
@SpringBootApplication
public class HelloWorld {

    public static void main(String[] args) {

        // Spring 应用启动
        SpringApplication.run(HelloWorld.class,args);
    }
}
```

### 5.4 编写 Controller、Service

```java
package com.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @ResponseBody
    @RequestMapping("/hello")
    public String hello(){
        return "Hello World!";
    }
}
```

### 5.5 运行主程序

启动项目后，访问 http://localhost:8080/hello 即可

### 5.6 简化部署

```xml
<!-- 此插件可以将应用打包成一个可执行的jar包 -->
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```

将这个应用打包jar包，直接使用 java -jar 命令进行执行

## 6、HelloWorld探索

### 6.1 POM 文件

#### 6.1.1 父项目

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>1.5.9.RELEASE</version>
</parent>

<!-- 它的父项目是 -->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-dependencies</artifactId>
    <version>1.5.9.RELEASE</version>
    <relativePath>../../spring-boot-dependencies</relativePath>
</parent>
<!-- 它来管理 Spring Boot 应用中的所有依赖版本 -->
```

Spring Boot 版本仲裁中心：

以后导入依赖默认是不需要写版本号的（没有在dependencies中管理的依赖需要声明版本号）

#### 6.1.2 导入的依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

**spring-boot-starter**-==web==

​		spring-boot-starter：spring boot场景启动器，帮我们导入了web模块正常运行所依赖的组件

Spring Boot 将所有的功能场景都抽取出来了，做成了一个个starters启动器，只需要在项目中引入这些starter相关场景的所有依赖就都会导入进来，要用什么就导入什么场景的启动器

[关于starters的官方介绍](https://docs.spring.io/spring-boot/docs/2.1.6.RELEASE/reference/html/using-boot-build-systems.html#using-boot-starter)

### 6.2 主程序类，主入口类

```java
package com.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @SpringBootApplication
 * 来标注一个主程序类
 * 说明这是一个 spring boot 应用
 */
@SpringBootApplication
public class HelloWorld {

    public static void main(String[] args) {

        // Spring 应用启动
        SpringApplication.run(HelloWorld.class,args);
    }
}
```

**@SpringBootApplication**：Spring Boot应用标注在某个类上说明这个类是SpringBoot的主配置类，SpringBoot就应该运行这个类的main方法来启动SpringBoot应用

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(
    excludeFilters = {@Filter(
    type = FilterType.CUSTOM,
    classes = {TypeExcludeFilter.class}
), @Filter(
    type = FilterType.CUSTOM,
    classes = {AutoConfigurationExcludeFilter.class}
)}
)
public @interface SpringBootApplication {
```

**@SpringBootConfiguration**：Spring Boot 的配置类

​		标注在某个类上，表示这是一个 Spring Boot 的配置类

​		**@Configuration**：配置类上来标注这个注解

​				配置类——配置文件；配置类也是容器中的一个组件；@Component

**@EnableAutoConfiguration**：开启自动配置功能

​		以前需要配置的东西，Spring Boot 直接帮我们自动配置；**@EnableAutoConfiguration**告诉Spring Boot 开启自动配置功能；这样自动配置才能生效

```java
@AutoConfigurationPackage
@Import({EnableAutoConfigurationImportSelector.class})
public @interface EnableAutoConfiguration {
```

​		**@AutoConfigurationPackage**：自动配置包

​				@Import({Registrar.class})

​				Spring 的底层注解 @Import，给容器中导入一个组件；导入的组件由Registrar.class

将主配置类（@SpringBootApplication标注的类）的所在包及以下所有子包里面的所有组件扫描到Spring容器中

​		**@Import({EnableAutoConfigurationImportSelector.class})**

​				给容器中导入组件

​				EnableAutoConfigurationImportSelector：导入哪些组件的选择器

​				将所有需要导入的组件以全类名的方式返回，这些组件就会被添加到容器中

​				会给容器中导入非常多的自动配置类（xxxAutoConfiguration），就是给容器中导入这个场景需要的所有组件，并配置好这些组件

![自动配置类](https://img-blog.csdnimg.cn/20190626193129772.png)

有了这些自动配置类，免去了手动编写配置注入功能组件等的工作

​				SpringFactoriesLoader.loadFactoryNames(EnableAutoConfiguration.class,classLoader);

Spring Boot在启动的时候从类路径下的 META-INF/spring.factories 中获取 EnableAutoConfiguration 指定的值，将这些值作为自动配置类导入到容器中，自动配置类就生效了，帮我们进行自动配置工作，以前需要自己配置，现在自动配置类都帮我们配置好了【classLoader：类加载器】

J2EE的整体整合方案和自动配置都在spring-boot-1.5.9.RELEASE.jar