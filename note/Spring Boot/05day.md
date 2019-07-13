# Profile

### 1、多 Profile 文件

在编写主配置文件的时候，文件名可以是 application-{profile}.properties/yml

默认使用 application.properties 的配置



### 2、yml 支持多文档块方式

```yml
server:
  port: 8888
spring:
  profiles:
    active: prod
---
server:
  port: 8887
spring:
  profiles: dev
---
server:
  port: 8886
spring:
  profiles: prod
```



### 3、激活指定 Profile

①在配置文件中指定

```properties
spring.profiles.active=dev
```

②命令行：


打包成jar包，然后使用cmd运行（spring.profiles.active 指定 profiles）

```
java -jar jar包 --spring.profiles.active=dev
```

③虚拟机：

启动时在Program arguments添加以下命令

```
-Dspring.profile.active=dev
```

![](https://img-blog.csdnimg.cn/20190709153412205.png)