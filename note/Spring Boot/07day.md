# Spring Boot 与日志

### 日志框架的选择

日志的抽象层：SLF4J

日志实现：Logback



SpringBoot 底层是 Spring 框架，Spring 框架默认是用 JCL

​		**SpringBoot 选用 SLF4J 和 Logback**



### SLF4J 的使用

开发时，日志记录方法的调用，不直接调用日志的实现类，而是调用日志抽象层的方法

导入 slf4j 的 jar 包和 Logback 的实现 jar 包

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorld {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(HelloWorld.class);
        logger.info("Hello World");
    }
}
```

![](https://img-blog.csdnimg.cn/20190716151156609.png)

每一个日志实现框架都有自己的配置文件，使用 slf4j，配置文件还是做成日志实现框架的配置文件



统一日志记录，即使是别的框架，统一使用 slf4j 进行输出

![](https://img-blog.csdnimg.cn/2019071615310149.png)

##### 如何让系统中所有的日志都统一到 slf4j：

1. 将系统中其他日志框架先排除出去
2. 用中间包替换原有的日志框架
3. 导入 slf4j 其他的实现



### SpringBoot 日志关系

```java
  <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starters</artifactId>
    <version>2.1.6.RELEASE</version>
  </parent>
```

SpringBoot 使用它来作为日志功能

```java
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-logging</artifactId>
      <version>2.1.6.RELEASE</version>
      <scope>compile</scope>
    </dependency>
```

底层依赖关系

![](https://img-blog.csdnimg.cn/2019071615582360.png)

1. SpringBoot 底层也是使用 slf4j+logback 的方式进行日志记录
2. SpringBoot 把其他的日志都替换成了 slf4j
3. SpringBoot 使用中间包转换成 slf4j
```java
public class SLF4JLoggerContextFactory implements LoggerContextFactory {
    private static final StatusLogger LOGGER = StatusLogger.getLogger();
    private static LoggerContext context = new SLF4JLoggerContext();
```
4. 如果要引入其他框架，一定要把这个框架的默认日志依赖删掉

   Spring 框架用的是 commons-logging，在一点几的版本中直接禁用了 Spring 的默认日志，而在二点几的版本中直接删除了 Spring-core 需要导的包

![](https://img-blog.csdnimg.cn/20190716163027804.png)

![](https://img-blog.csdnimg.cn/20190716163121543.png)

​		SpringBoot 能自动适配所有的日志，底层使用 slf4j+logback 的方式记录日志，引入其他框架，只需要把这个框架的日志框架删除即可



### 日志使用

##### 默认配置

SpringBoot 默认配置了日志

```java
// 记录器
Logger logger = LoggerFactory.getLogger(getClass());
@Test
public void contextLoads() {

    // 日志的级别：由低到高
    // trace < debug < info < warn < error
    // 可以调整输出的日志级别，日志只打印此级别即以后的级别
    logger.trace("==========这是trace日志==========");
    logger.debug("==========这是debug日志==========");
    // SpringBoot 默认使用的是 info 级别，没有指定级别即使用默认级别（root级别）
    logger.info("==========这是info日志==========");
    logger.warn("==========这是warn日志==========");
    logger.error("==========这是error日志==========");
}
```

SpringBoot 修改日志的默认设置

```properties
# 指定日志级别
logging.level.com.memory = trace

# 当前磁盘的根路径下创建 spring 文件夹和里面的 log 文件夹，
# 使用 spring.log 作为默认文件
logging.path = /spring/log

# 当前项目下生成 springboot.log 日志
# 可以指定绝对路径
#logging.file = d:/springboot.log

# 在控制台输出的日志格式
logging.pattern.console = %d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} %msg%n
# 指定文件中日志输出的格式
logging.pattern.file = %d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} %msg%n
# 日志输出格式：
#       %d  表示日期时间
#       %Thread  表示线程名
#       %-5level  表示级别从左显示5个字符宽度
#       %logger{50}  表示logger名字最长50个字符，否则按照句点分割
#       %msg  表示日志消息
#       %n  表示换行符
# 例：
#		%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} %msg%n
```

###### 指定配置

给类路径下创建每个日志框架自己的配置文件即可，SpringBoot 就不使用默认配置了

| Logging System          | Customization                                                |
| ----------------------- | ------------------------------------------------------------ |
| Logback                 | `logback-spring.xml`, `logback-spring.groovy`, `logback.xml`, or `logback.groovy` |
| Log4j2                  | `log4j2-spring.xml` or `log4j2.xml`                          |
| JDK (Java Util Logging) | `logging.properties`                                         |

logback.xml：直接就被日志框架识别了

logback-spring.xml：日志框架不直接加载日志的配置项，由 springboot 解析日志配置，可以使用 springboot 的高级 profile 功能

```properties
<springProfile name="staging">
	<!-- configuration to be enabled when the "staging" profile is active -->
	可以指定某配置只在某个环境下生效
</springProfile>
```

否则会出错

```java
no applicable action for [springProfile]
```

例（logback-spring.xml）：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--
scan：当此属性设置为true时，配置文件如果发生改变，将会被重新加载，默认值为true。
scanPeriod：设置监测配置文件是否有修改的时间间隔，如果没有给出时间单位，默认单位是毫秒当scan为true时，此属性生效。默认的时间间隔为1分钟。
debug：当此属性设置为true时，将打印出logback内部日志信息，实时查看logback运行状态。默认值为false。
-->
<configuration scan="false" scanPeriod="60 seconds" debug="false">
  <!-- 定义日志的根目录 -->
  <property name="LOG_HOME" value="/app/log" />
  <!-- 定义日志文件名称 -->
  <property name="appName" value="atguigu-springboot"></property>
  <!-- ch.qos.logback.core.ConsoleAppender 表示控制台输出 -->
  <appender name="stdout" class="ch.qos.logback.core.ConsoleAppender">
    <!--
    日志输出格式：
  %d表示日期时间，
  %thread表示线程名，
  %-5level：级别从左显示5个字符宽度
  %logger{50} 表示logger名字最长50个字符，否则按照句点分割。 
  %msg：日志消息，
  %n是换行符
    -->
    <layout class="ch.qos.logback.classic.PatternLayout">
      <springProfile name="dev">
        <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} ----> [%thread] ---> %-5level %logger{50} - %msg%n</pattern>
      </springProfile>
      <springProfile name="!dev">
        <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} ==== [%thread] ==== %-5level %logger{50} - %msg%n</pattern>
      </springProfile>
    </layout>
  </appender>

  <!-- 滚动记录文件，先将日志记录到指定文件，当符合某个条件时，将日志记录到其他文件 -->
  <appender name="appLogAppender" class="ch.qos.logback.core.rolling.RollingFileAppender">
    <!-- 指定日志文件的名称 -->
    <file>${LOG_HOME}/${appName}.log</file>
    <!--
    当发生滚动时，决定 RollingFileAppender 的行为，涉及文件移动和重命名
    TimeBasedRollingPolicy： 最常用的滚动策略，它根据时间来制定滚动策略，既负责滚动也负责出发滚动。
    -->
    <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
      <!--
      滚动时产生的文件的存放位置及文件名称 %d{yyyy-MM-dd}：按天进行日志滚动 
      %i：当文件大小超过maxFileSize时，按照i进行文件滚动
      -->
      <fileNamePattern>${LOG_HOME}/${appName}-%d{yyyy-MM-dd}-%i.log</fileNamePattern>
      <!-- 
      可选节点，控制保留的归档文件的最大数量，超出数量就删除旧文件。假设设置每天滚动，
      且maxHistory是365，则只保存最近365天的文件，删除之前的旧文件。注意，删除旧文件是，
      那些为了归档而创建的目录也会被删除。
      -->
      <MaxHistory>365</MaxHistory>
      <!-- 
      当日志文件超过maxFileSize指定的大小是，根据上面提到的%i进行日志文件滚动 注意此处配置SizeBasedTriggeringPolicy是无法实现按文件大小进行滚动的，必须配置timeBasedFileNamingAndTriggeringPolicy
      -->
      <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
        <maxFileSize>100MB</maxFileSize>
      </timeBasedFileNamingAndTriggeringPolicy>
    </rollingPolicy>
    <!-- 日志输出格式： -->
    <layout class="ch.qos.logback.classic.PatternLayout">
      <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [ %thread ] - [ %-5level ] [ %logger{50} : %line ] - %msg%n</pattern>
    </layout>
  </appender>

  <!-- 
  logger主要用于存放日志对象，也可以定义日志类型、级别
  name：表示匹配的logger类型前缀，也就是包的前半部分
  level：要记录的日志级别，包括 TRACE < DEBUG < INFO < WARN < ERROR
  additivity：作用在于children-logger是否使用 rootLogger配置的appender进行输出，
  false：表示只用当前logger的appender-ref，true：
  表示当前logger的appender-ref和rootLogger的appender-ref都有效
  -->
  <!-- hibernate logger -->
  <logger name="com.atguigu" level="debug" />
  <!-- Spring framework logger -->
  <logger name="org.springframework" level="debug" additivity="false"></logger>


  <!-- 
  root与logger是父子关系，没有特别定义则默认为root，任何一个类只会和一个logger对应，
  要么是定义的logger，要么是root，判断的关键在于找到这个logger，然后判断这个logger的appender和level。 
  -->
  <root level="info">
    <appender-ref ref="stdout" />
    <appender-ref ref="appLogAppender" />
  </root>
</configuration> 
```

