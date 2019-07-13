# 【培训】DAY19 Spring MVC

[TOC]

### Spring MVC介绍

MVC即是围绕 DispatcherServlet 设计的一套架构模式又是一种新的思考方式；程序之间分层,分工合作,既相互独立，又协同工作;在什么情况下需要将什么信息展示给用户,如何布局，调用哪些业务逻辑。

M指的是Model(模型),提供要展示的数据;通常是由多个业务实体组合而成;包括的对象有实体类entity,属性的抽象类等。同时一个模型就能为多个视图提供数据。

V指的是View（视图）代表用户交互界面,负责进行模型的展示。就是我们需要给用户呈现的页面,对于Web应用来说对象可以是HTML,JSP,XML,APP等。

C指的是Controller(控制),可以理解为从用户接收请求，将选择用什么样的模型与选择什么样的视图匹配在一起的事情，就是Controller来完成的；在整个MVC的设计中Controller就相当于扮演着调度员角色。



### Spring MVC主要组件

- 前端控制器(DispatcherServlet)

  接收请求，响应结果，相当于转发器，中央处理器。用户请求到达前端控制器，它就相当于mvc模式中的c,返回可以是json,String等数据类型，也可以是页面（Model）。

- 处理器映射器(HandlerMapping)

  根据请求的url查找Handler,SpringMvc提供不同的映射器实现不同的映射方式，例如：配置文件方式，实现接口方式，注解方式等。

- 处理器适配器(HandlerAdapter)

  按照特定规则（HandlerAdapter要求的规则）去执行Handler。

- 处理器(Handler)

  Handler 是继DispatcherServlet前端控制器的后端控制器，在DispatcherServlet的控制下Handler对具体的用户请求进行处理。相当于我们需要实现的具体业务逻辑。

- 视图解析器(View resolver)

  进行视图解析，根据逻辑视图名解析成真正的视图（view）。一般情况下需要通过页面标签或页面模版技术将模型数据通过页面展示给用户。

- 视图(View)

  View是一个接口，实现类支持不同的View类型（jsp、freemarker）,就是我们真正呈现的用户效果。



### Spring MVC工作原理

![](https://img-blog.csdnimg.cn/20190409105647962.png)

步骤说明：

1. 用户发送请求到前端控制器。
2. 前端控制器请求处理器映射器去查找处理器。
3. 找到以后处理器映射器向前端控制器返回执行链。
4. 前端控制器调用处理器适配器去执行处理器。
5. 处理器适配器去执行Handler。
6. 处理器执行完给处理器适配器返回ModelAndView。
7. 处理器适配器向前端控制器返回ModelAndView。
8. 前端控制器请求视图解析器去进行视图解析。
9. 视图解析器向前端控制器返回View。
10. 前端控制器对视图进行渲染。
11. 前端控制器向用户响应结果。



### Spring MVC框架优势

- 低耦合性

  视图层和业务层分离,这样就允许更改视图层代码而不用重新编译模式和控制层代码。

- 分工明确

  清晰的角色划分,例如:前端控制器（DispatcherServlet）是作为一个全局的流程控制，内部不做任何处理;ModelAndView的逻辑视图名通过ViewResolver将逻辑视图分析为具体的View即可。

- 功能强大的数据验证、格式化、绑定机制

  能使用任何对象进行数据绑定，不必实现特定框架的API。      

- 对RESTFUL风格的支持

  通过注解方式实现REST风格的统一。

- 可维护性

  分离视图层和业务逻辑层也使得开发人员更易于维护和修改。

- 与Spring无缝连接

  和Spring其他的框架无缝集成，是其他Web框架所不能具备的。

- 灵活的单元测试

  利用Spring提供的Mock对象能够简单的进行Web单元测试，更加的接近实际的业务场景。



特定名词解释:

- RESTFUL

  即Representational State Transfer的缩写。直接翻译的意思是"表现层状态转化"。它是一种互联网应用程序的API设计理念：URL定位资源，用HTTP动词（GET,POST,DELETE,PUT）描述操作。

- Mock

  MockMvcBuilder是用来构造MockMvc的构造器，其主要有两个实现：StandaloneMockMvcBuilder和DefaultMockMvcBuilder，分别对应两种测试方式，即独立安装和集成Web环境测试（此种方式并不会集成真正的web环境，而是通过相应的Mock API进行模拟测试，无须启动服务器）。对于我们来说直接使用静态工厂MockMvcBuilders创建即可。
  https://static.javadoc.io/org.mockito/mockito-core/2.11.0/org/mockito/Mockito.html#39



SpingMvc对比Struts1、Struts2

- Struts1是基于servlet；Struts2是基于filter；springmvc是基于servlet 。
- Struts1的action是单例模式，线程不安全的；Struts2 action是原型模式 prototype，每次访问对象都会创建新的实例，保证线程安全性；springmvc controller是单例模式，整个程序只有一个对象实例。Spring的安全性是通过绑定threadlocal实现。
- Struts1使用JSTL EL表达式，但是对集合和索引属性的支持很弱。Struts2采用 OGNL。
- struts2是类级别的拦截， 一个类对应一个request上下文，springmvc是方法级别的拦截，一个方法对应一个request上下文，而方法同时又跟一个url对应。
- springmvc可以认为已经100%零配置了,就是消除配置。



### Spring MVC常用注解

| 注解方式                 | 含义                                                         |
| ------------------------ | ------------------------------------------------------------ |
| @Controller              | 表示用于标识是当前类属于SpringMVC Controller中的对象,当分发处理器扫描该注解类，把这个类交给Spring来处理,首先要添加该注解里面的方法才能够被外部访问。 |
| @RequestMapping          | 表示是一个用来处理请求地址映射的注解，可用于类或方法上。用于类上，表示类中的所有响应请求的方法都是以该地址作为父路径。 |
| @ResponseBody            | 该注解用于将Controller的方法返回的对象，通过适当的HttpMessageConverter转换为指定格式后，写入到Response对象的body数据区。 |
| @Service                 | 声明Service组件, @Service("myMovieLister")，当只使用@Service时,Spring会默认将类的首写小写。 |
| @Autowired               | 用于注入，(srping提供的) 默认按类型装配。注入一个DAO,或者Service。 |
| @Component               | 相当于通用的注解，当不知道一些类归到哪个层时使用。           |
| @Transactional           | 事务管理。属性分为:事物传播,事物超时设置(默认30S),事物隔离等。 |
| @Value                   | 该注解的作用是将我们配置文件的属性读出来。注解为:@Value(“${server.port}”),表示读取配置文件中server.port的属性。设置为空默认属性@Value(“${server.port:8080}”)。 |
| @ConfigurationProperties | 该注解同样是读取配置文件,只不过是通过不同的前缀来获取不同的数据源信息。方式为:@ConfigurationProperties(prefix = "server.port")，表示获取server.port开头的所有配置项。 |
| @PathVariable            | 当使用@RequestMapping注解样式映射时， 即 someUrl/{paramId}, 这时的paramId可通过 @Pathvariable的方式注解绑定它传过来的值到方法的参数上。 |



###  Spring MVC实例解析

##### @Controller

只需要在所在类上加上此注解即可,使用它标记的类就是一个SpringMVCController对象,分发处理器将会扫描使用了该注解类的方法,则外部可以访问类的方法。

```
@Controller
@RequestMapping(value = "/test")
public class TestController extends BaseFormController{}
```



##### @RequestMapping

当@RequestMapping 标记在Controller 类上的时候，里面使用@RequestMapping 标记的方法的请求地址都是相对于类上的@RequestMapping 而言的；当Controller 类上没有标记@RequestMapping 注解时，方法上的@RequestMapping 都是绝对路径。这种绝对路径和相对路径所组合成的最终路径都是相对于根路径“/ ”而言的。



当该注解用在类上代表访问的父路径;
访问路径:127.0.0.1:8080/项目名/test

```
@Controller
@RequestMapping(value = "/test")
public class TestController extends BaseFormController{}
```



当该注解用于方法上时,访问路径则是 /test/test/mvc。
访问路径:127.0.0.1:8080/项目名/test/test/mvc

```
@RequestMapping(value = "/test/mvc",method = RequestMethod.GET)
public ModelAndView testMethod(){
    return new ModelAndView("/test/mvc");
}
```



当该注解为REST风格的URL，URL地址为动态;则结合@PathVariable注解使用。
访问路径:127.0.0.1:8080/项目名/test/test/123
上面路径的123指的是参数的值。

```
@RequestMapping(value = "/test/{param}")
public ModelAndView testMethod(@pathVariable("param") String param){
    return new ModelAndView("/test/mvc");
}
```



##### @ResponseBody

将该注解用于方法上即可,不添加此注解,则404。返回一个123前缀的页面。   
   通过适当的HttpMessageConverter转换为指定格式后，写入到Response对象的body数据区。
即可以将返回的对象（带有数据的javabean的集合List或Map）转换成JSON。可以返回任意指定类型。
访问路径:127.0.0.1:8080/项目名/test/mvc。返回123的字符串数据

```
 @ResponseBody
 @RequestMapping(value = "/test/mvc")
 public String testMethod(){
    return "123";
}
```



访问路径:127.0.0.1:8080/项目名/test/mvc。返回true的布尔类型数据

```
 @ResponseBody
 @RequestMapping("/test/mvc")
 public boolean testMethod(){
    return "true;
}
```

同样的例子，可以返回多种数据类型。例如Json,object,对象,List等...



##### @Service

只需要在所在实现类上加上此注解即可,不指定value值默认会将首页字母小写。
此注解属于业务逻辑层，service或者manager层默认按照名称进行装配，如果名称可以通过name属性指定，如果没有name属性，注解写在字段上时，默认去字段名进行查找，如果注解写在setter方法上，默认按照方法属性名称进行装配。当找不到匹配的bean时，才按照类型进行装配，如果name名称一旦指定就会按照名称进行装配

```
@Service("TestService")
public class TestServiceImpl implements TestService{}
```

```
@Service
public class ReleaseDomainServiceImpl extends AbstractService<ReleseDomain>{}
```

不指定value值,只需注入即可:

```
@Autowired
private ReleaseDomainService releaseDomainService;
```



##### @Autowired

只需要在所在需要使用时引用注入即可；注入DAO和Service以及一些工具类都可以。调用则直接boTestService.方法即可。
在默认情况下使用 @Autowired 注释进行自动注入时，Spring 容器中匹配的候选 Bean 数目必须有且仅有一个。当找不到一个匹配的 Bean 时，Spring 容器将抛出 BeanCreationException 异常，并指出必须至少拥有一个匹配的 Bean。

```
@Controller
@RequestMapping(value = "/test")
public class TestController extends BaseFormController{
    @Autowired
    private TestService testService;
}
```

当设置Autowired注解时,设置required为false时,则表示告诉spring:在找不到匹配Bean时也不报错。如若不加此注释,则当找不到改bean时,会出现异常。

```
@Autowired(required = false)
private ReleaseDomainService releaseDomainService;
```



##### @Component

当前类不属于service层,以及dao层等,但同时其他类需要调用则在类上加注解即可,不指定value值时,spring会将首页字母小写testUtils;使用则@Autowired注入即可。

```
@Component
public class TestUtile{}
```



##### @Transactional

为了防止异常代码导致错误数据提交,则需要使用该注解,如出现异常,则所有执行方法都将事物回滚,在需要事物的方法处添加即可。

作用于接口、接口方法、类以及类方法上。当作用于类上时，该类的所有 public 方法将都具有该类型的事务属性，同时，我们也可以在方法级别使用该标注来覆盖类级别的定义。

虽然此注解可以作用于接口、接口方法、类以及类方法上，但是 Spring 建议不要在接口或者接口方法上使用该注解，因为这只有在使用基于接口的代理时它才会生效。另外， @Transactional 注解应该只被应用到 public 方法上，这是由 Spring AOP 的本质决定的。如果你在 protected、private 或者默认可见性的方法上使用 @Transactional 注解，这将被忽略，也不会抛出任何异常。    



注解用于方法上，所有的事物机制和模式都是默认的。

```
 @Transactional
 @Override
 public String getNickName(){
     userDao.update(null);
     productLineDao.update(null);
     return "123";
 }
```



注解类上,属性为readOnly为true
注解方法上,属性为readOnly为false
方法上注解属性会覆盖类注解上的相同属性。

```
@Service
@Transactional(readOnly = true)
public vlass ReleaseDomainServiceImpl extends AbstractService<ReleseDomain>{
    @Transactional(readOnly = false,propagetion = Propageation.REQUIRES_NEW)
    public void update(){}
}
```



##### @Value

当前类为配置属性类时,则在所定义的属性加上此注解即可。此注解将会读取改配置文件中的值对应。
读取properties属性文件的属性值 
注解作用的两种方式;@Value(“${}”),@Value(“#{}”)
第一个注入的是外部配置文件对应的property，第二个则是SpEL表达式对应的内容。 那个default_value，就是前面的值为空时的默认值。注意二者的不同，#{}里面那个obj代表对象。

```
sched.rpc.port=8080
env=dev
```

第一种方式:@Value(“${}”) 

```
@Setter
@Getter
@Component("testProperties")
public class TestProperties{
    @Value("${env:dev}")
    private String env;
}
```

第二种方式:Value(“#{}”)

```
@Value("#{env}")
private String env;
```



##### @ConfigurationProperties

当多个配置项前缀一样时,则需要使用此注解，在类上定义,并设置prefix属性即可。

```
sched.rpc.port=8080
env=dev
test.test=123
test.test2=321
```

```
@Setter
@Getter
@Component("testProperties")
@ConfigurationProperties(prefix = "test")
public class TestProperties{
    @Value("${env:dev}")
    private String env;
    private String test;
    private String test2;
}
```



##### @PathVariable

该注解结合@RequestMapping使用,在方法参数添加此注解时会将请求URL映射到功能方法的参数上,即取出URI模板中的变量作为参数。@PathVariable中参数名称必须和url中参数名称一致
@PathVariable注解只支持一个属性value，类型为String，表示绑定的名称，如果省略绑定同名参数

###### 绑定同名参数

```
@PathVariable
@RequestMapping(value = "/test/{paramAble}")
public String TestRestMethod(@PathVariable("paramAble")String param){
    testProperties.getEnv();
    log.info(param);
    return "123";
}
```

###### 省略同名参数

```
@PathVariable
@RequestMapping(value = "/test/{paramAble}")
public String TestRestMethod(@PathVariable String param){
    return "123";
}
```

