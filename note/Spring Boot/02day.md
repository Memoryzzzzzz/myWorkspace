# 第二天

## 1、快速创建 Spring Boot 项目

### File—New—Project

![New Project](https://img-blog.csdnimg.cn/20190627190539914.png)

### 设置版本项目名等

![](https://img-blog.csdnimg.cn/20190627190731270.png)

### 选择自己要用到的模块（后面自己也可以引入）

![](https://img-blog.csdnimg.cn/20190627191014961.png)

创建之后会联网自动导入刚刚选好的模块

还会自动导入一个test的模块，用于以后的单元测试

```
<!-- Spring Boot 进行单元测试的模块 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

默认生成的 Spring Boot 项目：

- 主程序已经生成好了，我们只需要我们自己的逻辑

- resources 文件夹中目录结构

  - static：保存所有的静态资源（js css images）

  - templates：保存所有的模板页面（Spring Boot 默认 jar 包使用嵌入式的 Tomcat，默认不支持 JSP 页面），但可以使用模板引擎（freemarker、thymeleaf）

  - application.properties：Spring Boot 应用的配置文件，可以修改一些默认设置，例如指定端口号

    ```
    server.port=8888
    ```




## 2、Spring Boot 配置文件

### 2.1 配置文件

Spring Boot 使用一个全局的配置文件，配置文件名是固定的：

- application.properties
- application.yml

配置文件的作用：修改 Spring Boot 自动配置的默认值

Spring Boot 在底层都给我们自动配置好了

.yml是YAML（YAML Ain't Markup Language）

​		YAML A Markup Language：是一个标记语言

​		YAML isn't Markup Language：不是一个标记语言

标记语言：

​		以前的配置文件，大多使用的都是 .xml 文件

​		YAML ：以数据为中心，比 json、xml 等更适合做配置文件

​		YAML：配置实例

```yaml
server:
  port: 8888
```

​		XML：

```xml
<server>
	<port>8888</port>
</server>
```

## 3、YAML语法

### 3.1 基本语法

k:(空格)v：表示一对键值对（空格必须有）

以**空格**的缩进来控制层级关系，只要是左对齐的一列数据，都是同一层级的

```yaml
server:
	port:8888
	path:/hello
```

属性和值也是大小写敏感

### 3.2 值的写法

#### 字面量：普通的值（数字，字符串，布尔）

​		k: v：字面直接来写；

​				字符串默认不用加上单引号或者双引号

​				""：双引号	不会转义字符串里面的特殊字符，特殊字符会作为本身想表示的意思

```yaml
name: "zhangsan \n lisi"
```

​		输出：zhangsan 换行 lisi

​				''：单引号		会转义特殊字符，特殊字符最终只是一个普通的字符串数据

```yaml
name: 'zhangsan \n lisi'
```

​		输出：zhangsan \n lisi

#### 对象、Map（属性和值）（键值对）：

​		k: v：在下一行来写对象的属性和值的关系，注意缩进

​				对象还是 k: v的方式

```yaml
friends:
    lastName: zhangsan
    age: 20
```

行内写法：

```yaml
friends: {lastName: zjamgsam,age: 18}
```

#### 数组（List、Set）：

用-(空格)值表示数组中的一个元素

```yaml
pets:
 - cat
 - dog
 - pig
```

行内写法：

```yaml
pets: [cat,dog,pig]
```

### 配置文件值注入

##### application.yml

```yaml
server:
  port: 8888


person:
  lastName: zhangsan
  age: 20
  boss: false
  birth: 2019/06/06
  maps: {k1: v1, k2: v2}
  lists:
    - lisi
    - wangwu
  dog:
    name: 小狗
    age: 2
```

##### com.memory.springboot.bean.Dog

```java
package com.memory.springboot.bean;

/**
 * @ClassName Dog
 * @Description TODO
 * @Author Zhong Yanghao
 * @Param 狗
 * @Date 2019/6/28 16:03
 */
public class Dog {

    private String name;
    private Integer age;

    @Override
    public String toString() {
        return "Dog{" +
            "name='" + name + '\'' +
            ", age=" + age +
            '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
```

##### com.memory.springboot.bean.Person

```java
package com.memory.springboot.bean;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName Person
 * @Description TODO
 * @Author Zhong Yanghao
 * @Param
 * @Date 2019/6/28 15:54
 */

/**
 * 将配置文件中配置的每一个属性的值映射到这个组件中
 * @ConfigurationProperties：告诉 Spring Boot 将本类中的所有属性和配置文件中相关配置进行绑定
 *      prefix = "person"：配置文件中哪个下面的所有属性进行一一映射
 *
 * 只有这个组件是容器中的组件，才能使用容器提供的功能，比如@ConfigurationProperties功能
 */
@Component
@ConfigurationProperties(prefix = "person")
public class Person {

    private String lastName;
    private Integer age;
    private Boolean boss;
    private Date birth;

    private Map<String, Object> maps;
    private List<Object> lists;
    private Dog dog;

    @Override
    public String toString() {
        return "Person{" +
            "lastName='" + lastName + '\'' +
            ", age=" + age +
            ", boss=" + boss +
            ", birth=" + birth +
            ", maps=" + maps +
            ", lists=" + lists +
            ", dag=" + dog +
            '}';
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getBoss() {
        return boss;
    }

    public void setBoss(Boolean boss) {
        this.boss = boss;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Map<String, Object> getMaps() {
        return maps;
    }

    public void setMaps(Map<String, Object> maps) {
        this.maps = maps;
    }

    public List<Object> getLists() {
        return lists;
    }

    public void setLists(List<Object> lists) {
        this.lists = lists;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }
}
```

##### com.memory.springboot.SpringBoot02ConfigApplicationTests

```java
package com.memory.springboot;

import com.memory.springboot.bean.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Spring Boot 单元测试
 *
 * 可以在测试期间很方便的，类似编码一样进行自动注入等容器的功能
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBoot02ConfigApplicationTests {

    @Autowired
    Person person;

    @Test
    public void contextLoads() {
        System.out.println(person);
    }
}
```

##### 我们可以导入配置文件处理器，编写配置时就会有提示

```xml
<!-- 导入配置文件处理器，配置文件进行绑定就会有提示 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
</dependency>
```