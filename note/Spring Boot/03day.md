## 1、properties 配置文件编码问题

idea properties 默认使用 urf-8，中文会乱码，故修改如下

File—Settings—Editor—File Encodings

修改Global Encoding、Project Encoding、Default encoding for properties files 以及勾选 Transparent native-to-ascii conversion，勾选后就会自动匹配ASCII码

![](https://img-blog.csdnimg.cn/20190701151517503.png)

## 2 、使用 properties 来配置 person 的值（顺便测试一下中文是否乱码）

注释 application.yml 里的配置，在 application.properties 中配置对应的值

```properties
# 配置 person 的值
person.last-name=张三
person.age=20
person.birth=2000/2/2
person.boss=false
person.maps.k1=11
person.maps.k2=22
person.lists=a,b,c
person.dog.name=dog
person.dog.age=3
```

![](https://img-blog.csdnimg.cn/20190701152522605.png)

## 3、 @Value 获取值和 @ConfigurationProperties 获取值比较

|                      | @ConfigurationProperties | @Value           |
| -------------------- | ------------------------ | ---------------- |
| 功能                 | 批量注入配置文件中的属性 | 一个一个指定属性 |
| 松散绑定（松散语法） | 支持                     | 不支持           |
| SpEL                 | 不支持                   | 支持             |
| JSR303 数据校验      | 支持                     | 不支持           |
| 复杂类型封装         | 支持                     | 不支持           |

##### 功能

###### @ConfigurationProperties 一句话搞定，直接批量注入

```java
@ConfigurationProperties(prefix = "person")
```

###### @Value 需要一个一个进行指定

```java
/**
     * <bean class="Person">
     *      <property name="LastMame" value=""></property>
     * </bean>
     *
     * value中可以输入：字面量/${key}从环境变量、配置文件中获取值/#{SpEL}
     */
@Value("${person.last-name}")
private String lastName;
@Value("#{5*2}")
private Integer age;
@Value("true")
private Boolean boss;
```

##### 松散语法：

```yml
lastName: zhangsan
last_name: zhangsan
last-name: zhangsan
```

以上三种都可以进行绑定

###### SpEL:

```java
@Value("#{5*2}")
private Integer age;
```

支持表达式，可以进行计算

###### JSR303 数据校验

```java
@ConfigurationProperties(prefix = "person")
@Validated
```

在 @ConfigurationProperties 后面加上 @Validated 注释，证明下面数据需要校验

例在字段上加上 @Email 注释，即说明 lastName 字段必须为邮件格式，否则不能运行成功

```
// lastName 必须是邮箱格式
@Email
private String lastName;
```

###### 复杂类型封装

如果有 Map 或者 List 字段等复杂类型封装，@Value 是获取不到值的，故：

如果说，我们只要再某个业务逻辑中需要获取一下配置文件中的某个值，使用 @Value

如果说，我们专门编写一个 javaBean 来和配置文件进行映射，使用 @ConfigurationProperties

配置文件不管是 yml 还是 properties 都能获取到值

## 4、@PropertySource 和 @ImportResource

@**PropertySource** ：加载指定的配置文件

```java
@PropertySource(value = {"classpath:person.properties"})
@Component
public class Person {
    private String lastName;
    private Integer age;
    private Boolean boss;
    private Date birth;

    private Map<String, Object> maps;
    private List<Object> lists;
    private Dog dog;
```

@**ImportResource**：导入 Spring 的配置文件，让配置文件里面的内容生效

Spring Boot 里面没有 Spring 的配置文件，我们自己编写的配置文件，也不能自动识别；

如果想让 Spring 的配置文件生效加载进来，把@**ImportResource**标注在一个配置类上

```java
package com.memory.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

// 导入 Spring 的配置文件让其生效
@ImportResource(locations = {"classpath:beans.xml"})
@SpringBootApplication
public class SpringBoot02ConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot02ConfigApplication.class, args);
    }
}
```

不来编写 Spring 的配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

  <bean id="helloService" class="com.memory.springboot.service.HelloService"></bean>
</beans>
```

SpringBoot 推荐给容器中添加组件的方式：推荐使用权注解的方式

1、配置类 —— Spring 配置文件

2、使用 @Bean 给容器中添加组件

```java
package com.memory.springboot.config;

import com.memory.springboot.service.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Configuration：指明当前类是一个配置类，就是来代替之前的 Spring 配置文件
 *
 * 在配置文件中用 <bean></bean> 标签添加组件
 */
@Configuration
public class MyAppConfig {

    // 将方法的返回值添加到容器中：容器中的这个组件默认的 Id 就是方法名
    @Bean
    public HelloService helloService(){
        System.out.println("配置类 @Bean 给容器中添加组件");
        return new HelloService();
    }
}
```

