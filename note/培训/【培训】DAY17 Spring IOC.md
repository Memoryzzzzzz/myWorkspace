# 【培训】DAY17 Spring IOC

[TOC]

### 简介

Spring IoC（Inversion of Control）容器功能：通过配置元数据（Configuration Metadata）描述 Bean 与 Bean 之间的依赖关系，利用 Java 反射功能实例化 Bean，并建立各个Bean 之间的依赖关系。 
IoC容器控制对象的生命周期，所有类的创建、销毁都由 IoC容器来执行，也就是说控制对象生命周期的不再是使用方，而是IoC容器。之前是由使用方自身来控制的，现在交由IoC容器，所以叫控制反转。
ApplicationContext接口代表Spring IoC容器，它继承BeanFactory接口，并通过它来实例化、配置、组装bean。ApplicationContext主要实现类有：ClassPathXmlApplicationContext、FileSystemXmlApplicationContext、AnnotationConfigApplicationContext等。
Spring是一个轻量级框架，主要由IoC及AOP组成。IoC自动装配的方式有byName、byType等。



### 术语

IoC - IoC为控制反转，即一个对象依赖其他对象时，不需要自己去创建、配置被依赖的对象，控制权不在自己，而在于Spring IoC容器。IoC（Inversion of Control）也被称作为依赖注入（Dependency Injection - DI）。
Bean - 由Spring IoC容器管理的，构成应用程序的这些对象叫做Bean。Bean被Spring容器实例化、管理。一个Bean即为应用程序的一个简单对象。Bean和Bean之间的依赖关系通过Spring IoC容器管理。Bean在 Spring容器中有两种管理方式，原型模式和单例模式，默认使用单例模式。
Configuration Metadata - Spring IoC容器解析配置元数据，通过它Spring IoC容器才会知道怎么实例化、配置、组装对象。

Configuration Metadata包含多种格式：

- XML-based metadata - 基于XML文件的配置
- Annotation-based configuration - 基于注解的配置
- Java-based configuration - 基于Java的配置



### 优点

1. 降低了使用资源双方的依赖程度，也就是我们说的耦合度。
2. 享受单例的好处，效率高，不浪费系统资源。
3. 便于单元测试，方便切换mock组件。
4. 便于进行AOP操作，对于使用者是透明的。
5. 资源集中管理，实现资源的可配置和易管理。
6. 能更好的实现面向接口编程。



### 详解

##### XML配置 - 语法

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:p="http://www.springframework.org/schema/p"
  xsi:schemaLocation="http://www.springframework.org/schema/beans
  http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="..." class="...">
      <!-- collaborators and configuration for this bean go here -->
    </bean>

    <bean id="..." class="...">
      <!-- collaborators and configuration for this bean go here -->
    </bean>

    <!-- more bean defimitions go here -->
    <!-- 更多的Bean在这里定义 -->
</beans>
```

The id attribute is a string that identifies the individual bean definition.

id属性是标识单个bean定义的字符串。

The class attribute defines the type of the bean and uses the fully qualified classname.

class属性定义bean的类型并使用完全限定的类名。

##### XML配置 - id、name属性

id、name属性值必须在容器中唯一，id为bean对象的唯一标识符，name为当前bean的别名，id和name都可用来定位bean。
name属性值可以配置多个，以“,”、“;”或空格分隔。id和name属性值不是必填的，如果两个都没配置，则spring会自动生成一个唯一标识，如：com.xxx.User#0。



##### XML配置 - 基于构造函数的依赖注入

优点：1. 构造期即创建一个完整、合法的对象。2. 不需要写繁琐的setter方法的。3. 在构造函数中决定依赖关系的注入顺序。
Setter注入和构造函数注入的区别：1. 注入依赖关系注入时机不同。2. 构造注入方式依赖对象先注入。
构造函数的参数解析是通过参数的类型来匹配的。假如ThingTwo和ThingThree没有继承关系，下面的配置可以正常工作，开发者不需要去定义< constructor-arg>元素的索引或类型信息。

```
public class TestOne {
    public TestOne(TestTwo testTwo, TestThree testThree) {}
}
```

```
<beans>
	<bean id="testOne" class="com.test.spring.test.TestOne">
    <constructor-arg index="0" ref="testTwo"/>
    <constructor-arg index="1" ref="testThree"/>
  </bean>

  <bean id="testTwo" class="com.test.spring.test.TestTwo"/>
  <bean id="testThree" class="com.test.spring.test.TestThree"/></beans>
```



当定义构造函数参数时，类型不确定时，需要配置type属性来实现类型匹配或者使用index指定参数索引值。

```
package com.test.spring.test;

public class ExampleBean {
    private int years;
    private String ultimateAnswer;
    
    public ExampleBean(int years, String ultimateAnswer) {
        this.years = years;
        this.ultimateAnswer = ultimateAnswer;
    }
}
```

```
  <bean id="exampleBean" class="com.test.spring.test.ExampleBean">
    <constructor-arg type="int" value="88"/>
    <constructor-arg type="java.lang.String" value="66"/>
  </bean>
```

或者

```
  <bean id="exampleBean" class="com.test.spring.test.ExampleBean">
    <constructor-arg index="0" value="88"/>
    <constructor-arg index="1" value="66"/>
  </bean>
```



通过构造器的参数名来匹配参数（推荐方案）。使用此方法的两种场景：

1. 编译时启用debug，Apache Maven Compiler Plugin默认启动了该配置项。配置如下：

   ```
     <bean id="exampleBean" class="com.test.spring.test.ExampleBean">
       <constructor-arg name="years" value="88"/>
       <constructor-arg name="ultimateAnswer" value="66"/>
     </bean>
   ```

   

2. 编译时不启用debug，则可使用@ConstructorProperties代替。如下：

   ```
   package com.test.spring.test;
   
   import java.beans.ConstructorProperties;
   
   public class ExampleBean { 
       @ConstructorProperties({"years", "ultimateAnswer"})
       public ExampleBean(int years, String ultimateAnswer) {
           this.years = years;
           this.ultimateAnswer = ultimateAnswer;
       }
   }
   ```

   

##### XML配置 - 基于Setter的依赖注入

```
  <bean id="exampleBean" class="com.test.spring.test.ExampleBean">
    <property name="beanOne">
      <ref bean="anotherExampleBean"/>
    </property>

    <property name="beanTwo" ref="yetAnotherBean"/>
    <property name="integerProperty" value="1"/>
  </bean>

  <bean id="anotherExampleBean" class="com.test.spring.test.AnotherBean"/>
  <bean id="yetAnotherBean" class="com.test.spring.test.YetAnotherBean"/>
```

注：基于Setter的依赖注入，属性必须提供set方法，无所谓get方法。对于复杂的依赖关系，setter注入更简洁、直观。

```
package com.test.spring.test;

public class ExampleBean {
    private AnotherBean beanOne;
    private YetAnotherBean beanTwo;
    private int i;

    public void setBeanOne(AnotherBean beanOne) {
        this.beanOne = beanOne;
    }

    public void setBeanTwo(YetAnotherBean beanTwo) {
        this.beanTwo = beanTwo;
    }

    public void setIntegerProperty(int i) {
        this.i = i;
    }
}
```



##### XML配置 - 基本类型及字符串配置

当Bean的属性为基本类型或字符串时，可直接使用<property/>标签的value属性进行配置。

```
  <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource" destroy-method="close">
    <property name="user" value="root"></property>
    <property name="password" value="123456"></property>
    <property name="jdbcUrl"
      value="jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8"></property>
    <property name="driverClass" value="com.mysql.jdbc.Driver"></property>
  </bean>
```

可以使p-namespace用来简化配置。

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:p="http://www.springframework.org/schema/p"
  xsi:schemaLocation="http://www.springframework.org/schema/beans
  http://www.springframework.org/schema/beans/spring-beans.xsd">
  <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource"
    destroy-method="close"
    p:user="root"
    p:password="123456"
    p:jdbcUrl="jdbc:mysql://localhost:3306/test"
    p:driverClass="com.mysql.jdbc.Driver"/>
</beans>
```



##### XML配置 - 集合

```
  <bean>
    <property name="adminEmails">
      <props>
        <prop key="admin">admin@test.com</prop>
        <prop key="support">support@test.com</prop>
        <prop key="development">development@test.com</prop>
      </props>
    </property>
    <property name="someList">
      <list>
        <value>a list element followed by a reference</value>
        <ref bean="dataSource"/>
      </list>
    </property>
  </bean>
```

```
  <bean>
    <property name="someMap">
      <map>
        <entry key="an entry" value="just some string"/>
        <entry key="a ref" value-ref="dataSource"/>
      </map>
    </property>
    <property name="someSet">
      <set>
        <value>just some string</value>
        <ref bean="dataSource"/>
      </set>
    </property>
  </bean>
```



##### XML配置 - Null或空字符串

###### 空字符串

```
  <bean class="com.test.spring.test.ExampleBean">
    <property name="integerProperty">
      <null/>
    </property>
  </bean>
```

等于

```
exampleBean.setEmail("");
```

###### Null值

```
  <bean class="com.test.spring.test.ExampleBean">
    <property name="integerProperty" value=""/>
  </bean>
```

等于

```
exampleBean.setEmail(null);
```



##### XML配置 - 配置java.util.Properties

```
  <bean id="propertyPlaceholderConfigurer"
    class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
    <property name="properties">
      <value>
        jdbc.driver.className=com.mysql.jdbc.Driver
        jdbc.url=jdbc:mysql://localhost:3306/test
      </value>
    </property>
  </bean>
```



##### XML配置 - Static Factory Method实例化Bean

class属性表示指定类中包含静态工厂方法，factory-method属性表示静态工厂方法的方法名。

```
  <bean id="clientService" class="com.test.spring.test.ClientService"
    factory-method="clientService"/>
```

```
package com.test.spring.test;

public class ClientService {
    private static ClientService clientService = new ClientService();
    private ClientService() {}

    public static ClientService clientService() {
        return clientService;
    }
}
```



##### XML配置 - Instance Factory Method实例化Bean

通过bean的工厂方法来实例化bean。factory-bean指定依赖的工厂bean，factory-method指定bean的什么方法用于创建对象，此方法为非静态方法。

```
  <bean id="defaultServiceLocator" class="com.test.spring.test.DefaultServiceLocator">

  </bean>

  <bean id="clientService"
    factory-bean="defaultServiceLocator"
    factory-method="createClientServiceInstance"/>
```

```
package com.test.spring.test;

public class DefaultServiceLocator {
    private static ClientService clientService = new ClientServiceImpl();

    public ClientService createClientServiceInstance() {
        return clientService;
    }
}
```



##### XML配置 - 一个工厂类中可包含多个工厂方法

```
  <bean id="defaultServiceLocator" class="com.test.spring.test.DefaultServiceLocator">

  </bean>

  <bean id="clientService"
    factory-bean="defaultServiceLocator"
    factory-method="createClientServiceInstance"/>

  <bean id="accountService"
    factory-bean="defaultServiceLocator"
    factory-method="createClientServiceInstance"/>
```

```
package com.test.spring.test;

public class DefaultServiceLocator {

    private static ClientService clientService = new ClientServiceImpl();

    private static AccountService accountService = new AccountServiceImpl();

    public ClientService createClientServiceInstance() {
        return clientService;
    }

    public static AccountService getAccountService() {
        return accountService;
    }
}

```



##### Bean Scopes

| 作用域      | 描述                                                         |
| ----------- | ------------------------------------------------------------ |
| singleton   | （默认）每一个Spring IoC容器都拥有唯一的一个实例对象         |
| prototype   | 一个Bean定义可以创建任意多个实例对象                         |
| request     | 一个HTTP请求会产生一个Bean对象，也就是说，每一个HTTP请求都有自己的Bean实例。只在基于web的Spring ApplicationContext中可用 |
| session     | 限定一个Bean的作用域为HTTPsession的生命周期。同样，只有基于web的Spring ApplicationContext才能使用 |
| application | 限定一个Bean的作用域为ServletContext的生命周期。同样，只有基于web的Spring ApplicationContext可用 |
| websocket   | 限定一个Bean的作用域为WebSocket的生命周期。同样，只有基于web的Spring ApplicationContext可用 |



##### Lifecycle callbacks

实现Spring的InitializingBean和DisposableBean接口的类，容器会自动调用此Bean的生命周期回调函数，对应的回调函数为afterPropertiesSet（Bean创建后调用）、destroy（Bean销毁前调用）。
JSR-250的@PostConstruct和@PreDestroy注解可以达到同样的效果。



###### Initialization callbacks

InitializingBean接口允许Bean在所有的依赖 配置完成后执行初始化操作。InitializingBean定义了一个接口：void afterPropertiesSet() throws Exception;
建议开发者不要使用InitializingBean接口的，因为这样代码会与Spring耦合。建议使用@PostConstruct注解，或通过配置bean的特定方法来实现（如下图）。基于XML的配置时，开发者可以使用init-method属性来指定一个没有参数的方法。使用Java配置的开发者可以使用@Bean之中的initMethod属性，如下：

```
  <bean id="exampleBean" class="com.test.spring.test.AnotherExampleBean"/>
```

```
package com.test.spring.test;

import org.springframework.beans.factory.InitializingBean;

public class AnotherExampleBean implements InitializingBean {
    public void afterPropertiesSet() throws Exception {}
}
```

或者

```
  <bean id="exampleBean" class="com.test.spring.test.ExampleBean" init-method="init"/>
```

```
package com.test.spring.test;

public class ExampleBean {
    public void init() {}
}
```



###### Destruction callbacks

实现了DisposableBean接口的Bean，可以在Bean销毁前执行特定的逻辑（在destroy方法中释放资源等）。DisposableBean定义了一个接口：void destroy() throws Exception;
建议不用DisposableBean接口，因为会与Spring耦合。建议使用@PreDestroy注解，或通过配置bean的特定方法来实现。当基于XML配置时，可配置Bean标签的destroy-method属性。基于Java配置时，可以配置@Bean的destroyMethod来实现。

```
  <bean id="exampleBean" class="com.test.spring.test.AnotherExampleBean"/>
```

```
package com.test.spring.test;

import org.springframework.beans.factory.InitializingBean;

public class AnotherExampleBean implements InitializingBean {
    public void afterPropertiesSet() throws Exception {}
}
```

或者

```
  <bean id="exampleBean" class="com.test.spring.test.ExampleBean" destroy-method="cleanup"/>
```

```
package com.test.spring.test;

public class ExampleBean {
    private void cleanup() {}
}
```



##### Aware interfaces

| 接口名                         | 描述                                                         |
| ------------------------------ | ------------------------------------------------------------ |
| ApplicationContextAware        | 声明的ApplicationContext                                     |
| ApplicationEventPlulisherAware | ApplicationContext中的事件发布器                             |
| BeanClassLoaderAware           | 加载Bean使用的类加载器                                       |
| BeanFactoryAware               | 声明的BeanFactory                                            |
| BeanNameAware                  | Bean的名字                                                   |
| BootstrapContextAware          | 容器运行的资源适配器BootstrapContext，通常仅在JCA环境下有效  |
| LoadTimeWeaverAware            | 加载期间处理类定义的weaver                                   |
| MessageSourceAware             | 解析消息的配置策略                                           |
| NotificationPublisherAware     | Spring JMX通知发布器                                         |
| PortletConfigAware             | 容器当前运行的PortletConfig，仅在web下的Spring ApplicationContext中可见 |
| PortletContextAware            | 容器当前运行的PortletContext，仅在web下的Spring ApplicationContext中可见 |
| ResourceLoaderAware            | 配置的资源加载器                                             |
| ServletConfigAware             | 容器当前运行的ServletConfig，仅在web下的Spring ApplicationContext中可见 |
| ServletContextAware            | 容器当前运行的ServletContext，仅在web下的Spring ApplicationContext中可见 |



##### Annotation-based configuration

@Required - 适用于property setter方法，只能配置在方法上。表明此属性在配置期间必须设值，没有设值则抛异常。
@Autowired - 自动注入依赖的Bean，可用于构造函数、方法、参数、字段、注解类型。此为按类型查找bean。如果此类型在容器中存在多个bean，而你只需要一个，则你需要通过特殊手段（@Primary、@Qualifier）去过滤出你想要的bean。

 Autowired也可用于集合，如：

1. @Autowired private UserTemplate[] userTemplates
2. @Autowired(required = false)表示容器中没有候选bean时也不会报错
3. @Autowired private Set< UserTemplate> userTemplates

@Autowired private Map<String, UserTemplate> userTemplates

###### Autowired原理：

1. 从容器中找出指定类型的所有bean，把这些候选bean的name都统计出来。
2. 遍历所有候选bean的name，验证他们是否为合格的候选bean。此判断的依据为：是否使用@Qualifier。如果没有使用@Qualifier则所有的bean都是合格的，如果使用了@Qualifier则只有@Qualifier value值相同的才是合格的候选bean（创建bean时Qualifier Name默认为bean name，所以可不配置。但使用方（依赖方）需配置@Qualifier value值）。
3. 筛选出合格的候选bean name后，判断此时集合的大小。如果为0则报NoSuchBeanDefinitionException异常。如果整好等于1，则使用此bean。
4. 当合格的候选bean大于1时，则首先去找@Primary bean，有则使用它，没有则找出候选bean 中name与@Autowired对象名（如：field name）相同的bean，如果还没匹配则抛NoUniqueBeanDefinitionException异常。

注：源码请参考DefaultListableBeanFactory类doResolveDependency方法



@Primary - 当容器中相同类型有多个bean时，可用Primary表明哪个bean享有优先权。
@Qualifier - 当容器中相同类型有多个bean时，可用Qualifier指定使用哪些bean，多个bean的Qualifier值可以相同。
@Resource - 

- @Resource没配置name属性
  1. 按规约获取默认名，@Resource置于field上则取field name，@Resource置于property setter方法上则取property name。
  2. 如果容器中包含此默认名的bean，则找出此bean并判断此bean的类型是否是你需要的。
  3. 如果容器中没有此默认名的bean，则使用备选方案，按类型查找bean（和Autowired判断逻辑一样）。
- @Resource已配置name属性
  1. 不管容器中是否包含此name的bean，都依据此name值及类型去容器中找出唯一的bean。

注：源码请参考CommonAnnotationBeanPostProcessor类的autowireResource方法





##### Java-based configuration

Spring基于Java配置的核心在于@Configuration（用于类）、@Bean（用于方法）。@Bean注解和< bean>元素扮演相同的角色，@Configuration类似于< beans>。

```
package com.test.spring.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public MyService myService() {
        return new MyServiceImpl();
    }
}
```

等于

```
<beans>
  <bean id="myService" class="com.test.spring.test.MyServiceImpl"/>
</beans>
```



###### @Bean

@Bean是一个方法级的注解，与XML的< bean/>元素功能相同。该注解支持一些< bean/>上的属性，如:init-method, destroy-method, autowiring 和 name。 你可以在@Configuration或@Component类里使用@Bean注解。
要声明一个bean，可以使用@Bean注解到一个方法上。默认bean name与方法名相同。

```
package com.test.spring.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public TransferSerivceImpl transferSerivce() {
        return new TransferSerivceImpl();
    }
}
```

等于

```
<beans>
  <bean id="transferSerivce" class="com.test.spring.test.TransferSerivceImpl"/>
</beans>
```



###### Lifecycle Callbacks

默认情况下，基于Java配置的Bean，包含public close/shutdown 方法，会被自动的置为destruction callback（应用关闭时自动执行close/shutdown方法）。如果你不需要此默认特性，你可以配置成空字符@Bean(destroyMethod="")。

```
package com.test.spring.test;

public class TestOne {
    public void init() {}
}
```

```
package com.test.spring.test;

public class TestTwo {

    public void cleanup() {

    }
}
```

```
package com.test.spring.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean(initMethod = "init")
    public TestOne testOne() {
        return new TestOne();
    }

    @Bean(destroyMethod = "cleanup")
    public TestTwo testTwo() {
        return new TestTwo();
    }
}
```



###### @Scope

```
package com.test.spring.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class MyConfiguration {

    @Bean
    @Scope("prototype")
    public Encryptor encryptor() {

    }
}
```



###### 自定义Bean Name

```
package com.test.spring.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean(name = "myThing")
    public Thing thing() {
        return new Thing();
    }
}
```

```
package com.test.spring.test;

import javax.activation.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean(name = {"dataSource", "subsystemA-dataSource", "subsystemB-dataSource"})
    public DataSource dataSource() {

    }
}
```



###### Bean之间的依赖

```
package com.test.spring.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ClientService clientService1() {
        ClientServiceImpl clientService = new ClientServiceImpl();
        clientService.setClientDao(clientDao());
        return clientService;
    }

    @Bean
    public ClientService clientService2() {
        ClientServiceImpl clientService = new ClientServiceImpl();
        clientService.setClientDao(clientDao());
        return clientService;
    }

    @Bean
    public ClientDao clientDao() {
        return new ClientDaoImpl;
    }
}
```

等于

```
package com.test.spring.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public TransferSerivce transferSerivce(AccountRepository accountRepository) {
        return new TransferSerivce(accountRepository);
    }

    @Bean
    public TestOne testOne() {
        return new TestOne(testTwo());
    }

    @Bean
    public TestTwo testTwo() {
        return new TestTwo();
    }
}
```



###### Bean Description - 常用于JMX

```
package com.test.spring.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

@Configuration
public class AppConfig {
    @Bean
    @Description("Provides a basic example of a bean")
    public Thing thing() {
        return new Thing();
    }
}
```



###### @Import

```
package com.test.spring.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigA {
    @Bean
    public A a() {
        return new A();
    }
}
```



```
package com.test.spring.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ConfigA.class)
public class ConfigB {
    @Bean
    public B b() {
        return new B();
    }
}
```





