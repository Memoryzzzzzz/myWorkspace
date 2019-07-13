# Mybatis

[TOC]

### 简介

MyBatis 是一款优秀的持久层框架，它支持定制化 SQL、存储过程以及高级映射。MyBatis 避免了几乎所有的 JDBC 代码和手动设置参数以及获取结果集。MyBatis 可以使用简单的 XML 或注解来配置和映射原生信息，将接口和 Java 的 POJOs(Plain Old Java Objects,普通的 Java对象)映射成数据库中的记录。

##### ORM框架对比：

| 框架对比  | 门槛                               | SQL语句                     | 优化难度                    | 适用场景                                                     |
| --------- | ---------------------------------- | --------------------------- | --------------------------- | ------------------------------------------------------------ |
| Hibernate | 标准的ORM框架，门槛较高            | 不需要编写SQL，系统自动生成 | 对SQL进行优化和修改比较困难 | 适用需求变化不多的中小型项目，比如后台管理系统，ERP、ORM、OA |
| Mybatis   | 不完全的ORM框架，编写SQL，门槛较低 | 专注SQL                     | 优化难度低                  | 适用需求变化较多，比如：互联网项目                           |



### 框架原理

![](https://img-blog.csdnimg.cn/20190415111901423.png)



### 入门程序

##### 配置文件

###### datasource.properties

```
jdbc.user=root
jdbc.password=123456
jdbc.driverClass=com.mysql.jdbc.Driver
jdbc.jdbcUrl=jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8
```

###### mybatis/Mybatis-config.xml

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
  PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-config.dtd">

<configuration>
  <!-- 加载属性文件 -->
  <properties resource="datasource.properties"/>
  <!-- 和Spring整合后废除 -->
  <environments default="development">
    <environment id="development">
      <!-- 使用JDBC事务管理，事务由mybatis来进行控制 -->
      <transactionManager type="JDBC"/>
      <!-- 数据库连接池属性 -->
      <dataSource type="POOLED">
        <property name="driver" value="${jdbc.driverClass}"/>
        <property name="url" value="${jdbc.jdbcUrl}"/>
        <property name="username" value="${jdbc.user}"/>
        <property name="password" value="${jdbc.password}"/>
      </dataSource>
    </environment>
  </environments>
  <!-- 加载配置文件 -->
  <mappers>
    <mapper resource="mybatis/sqlmap/user.xml"/>
  </mappers>
</configuration>
```

###### User.java(com.test.mybatis.domain.User)

```
package com.test.mybatis.domain;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String name;
    private String sex;

    public int getId(int id) {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
```

UserMapper.java(com.test.mybatis.mapper.UserMapper)

```
package com.test.mybatis.mapper;

import com.test.mybatis.domain.User;

public interface UserMapper {

    User findUserById(int id,String name);

}
```

##### 根据用户id主键查询用户信息

###### user.xml(mybatis/sqlmap/user.xml)

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!-- 命名空间，不同的sql进行隔离 -->
<mapper namespace="user">
  <!-- 在映射文件中配置sql语句 -->
  <!-- id:唯一标志一个sql，一个statement -->
  <!-- parameterType:指定参数的类型，这里指定int类型 -->
  <!-- #{}:表示一个占位符 -->
  <!-- #{id}:其中的id表示接入参数的，参数名称就是id，如果输入参数是简单类型，#{}中的参数名可以任意，可以value或者其他名称 -->
  <!-- resultType:指定sql输出结果的映射java对象类型，select指定resultType表示将单条记录映射的java对象 -->
  <select id="findUserById" parameterType="java.lang.Integer" resultType="com.test.mybatis.domain.User">
    select * from tb_student where id=#{id}
  </select>
</mapper>
```

###### MyBatis.java(com.test.mybatis.MyBatis)

```
package com.test.mybatis;

import com.test.mybatis.domain.User;
import java.io.InputStream;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import com.alibaba.fastjson.JSONObject;

public class MyBatis {
    /**
     * 根据id获取对应的用户信息
     */
    @Ignore
    @Test
    public void testFindUserById(){
        // mybatis配置文件
        String resource = "mybatis/Mybatis-config.xml";
        // 得到配置文件流
        InputStream inputStream = MyBatis.class.getClassLoader().getResourceAsStream(resource);
        // 创建会话工厂构造类
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        // 通过会话工厂构造类来和mybatis配置文件来获得会话工厂类
        SqlSessionFactory sqlSessionFactory = builder.build(inputStream);
        // 通过会话工厂类获取对应的会话
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 通过会话操作数据库
        // 第一个参数：映射文件中的statement的id,格式=namespace+"."+id
        // 第二个参数：指定和映射文件中所匹配的parameterType类型的参数
        // sqlSession.selectOne结果是映射文件中所匹配的resultType
        User user = sqlSession.selectOne("user.findUserById",1L);
        // 释放资源
        sqlSession.close();

        System.out.println(JSONObject.toJSONString(user));
    }
}
```

##### 根据用户名称模糊查询用户信息

###### user.xml(mybatis/sqlmap/user.xml)

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!-- 命名空间，不同的sql进行隔离 -->
<mapper namespace="user">
  <!-- 根据用户名称模糊搜索用户信息，可能返回多条 -->
  <!-- resultType:指定单条记录所记录的java映射对象 -->
  <!-- ${}:表示拼接sql字符串，将接收的参数的内容不加修饰拼接在sql中，使用${}拼接sql，可能引起SQL注入 -->
  <!-- ${value}:接收输入参数的内容，如果传入类型是简单类型，${}中只能使用value -->
  <select id="findUserByName" parameterType="java.lang.Integer" resultType="com.test.mybatis.domain.User">
    select * from tb_student where name LIKE '%${value}%'
  </select>
</mapper>
```

###### MyBatis.java(com.test.mybatis.MyBatis)

```
package com.test.mybatis;

import com.test.mybatis.domain.User;
import java.io.InputStream;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import com.alibaba.fastjson.JSONObject;

public class MyBatis {
    /**
     * 根据用户名称获取多个用户
     */
    @Test
    public void testFindUserByName(){

        // mybatis配置文件
        String resource = "mybatis/Mybatis-config.xml";
        // 得到配置文件流
        InputStream inputStream = MyBatis.class.getClassLoader().getResourceAsStream(resource);
        // 创建会话工厂构造类
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        // 通过会话工厂构造类来和mybatis配置文件来获得会话工厂类
        SqlSessionFactory sqlSessionFactory = builder.build(inputStream);
        // 通过会话工厂类获取对应的会话
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 通过sqlSession查询出多条数据
        List<User> list = sqlSession.selectList("user.findUserByName","小");

        System.out.println(JSONObject.toJSONString(list));

        sqlSession.close();
    }
}
```

##### 添加用户

###### user.xml(mybatis/sqlmap/user.xml)

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!-- 命名空间，不同的sql进行隔离 -->
<mapper namespace="user">
  <!-- 添加用户 -->
  <!-- parameterType:指定传入参数的java对象pojo -->
  <!-- ${}中指定javapojo的属性名，接收pojo对象的属性值，mybatis通过OGNL获取对象的属性值 -->
  <insert id="insertUser" parameterType="com.test.mybatis.domain.User" useGeneratedKeys="true">
    <!-- 将插入数据的主键进行返回 -->
    <!-- select last_insert_id:得到insert进去记录的主键值，只适用自增主键 -->
    <!-- keyProperty:将查询的主键值设置到parameterType对象的某个属性 -->
    <!-- order:select last_insert_id()执行顺序，相对于insert语句来说它的执行顺序 -->
    <!-- resultType:指定select last_insert_id()的执行顺序 -->
    <selectKey keyProperty="id" order="AFTER" resultType="java.lang.Integer">
      select last_insert_id()
    </selectKey>
    <!-- 执行顺序：首先通过uuid()得到主键，将主键设置到user对象的id属性中，其次再insert执行时，从user对象取出id属性值，注意：插入语句必须包含id -->
    <!-- <selectKey keyProperty="id" order="BEFORE" resultType="java.lang.String">
      select uuid()
    </selectKey> -->
    insert into tb_student (name,sex) value (#{name},#{sex})
  </insert>
</mapper>
```

###### MyBatis.java(com.test.mybatis.MyBatis)

```
package com.test.mybatis;

import com.test.mybatis.domain.User;
import java.io.InputStream;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import com.alibaba.fastjson.JSONObject;

public class MyBatis {
    /**
     * 添加用户
     */
    @Test
    public void testInsertUser(){
        // mybatis配置文件
        String resource = "mybatis/Mybatis-config.xml";
        // 得到配置文件流
        InputStream inputStream = MyBatis.class.getClassLoader().getResourceAsStream(resource);
        // 创建会话工厂构造类
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        // 通过会话工厂构造类来和mybatis配置文件来获得会话工厂类
        SqlSessionFactory sqlSessionFactory = builder.build(inputStream);
        // 通过会话工厂类获取对应的会话
        SqlSession sqlSession = sqlSessionFactory.openSession();

        User user = new User();

        user.setName("小蓝");
        user.setSex("男");

        // 通过SqlSession插入对象
        sqlSession.insert("user.insertUser",user);
        // 提交事务
        sqlSession.commit();

        // mysql自增主键，执行insert提交之前自动生成的一个自增主键，通过mysql函数last_insert_id()获取刚刚插入的自增主键

        // 获取用户信息的主键
        System.out.println(JSONObject.toJSONString(user));

        sqlSession.close();
    }
}
```

##### 删除用户

###### user.xml(mybatis/sqlmap/user.xml)

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!-- 命名空间，不同的sql进行隔离 -->
<mapper namespace="user"> 
  <!-- 根据id删除用户，需要id值 -->
  <delete id="deleteUserById" parameterType="java.lang.Integer">
    delete from tb_student where id=#{id}
  </delete>
</mapper>
```

###### MyBatis.java(com.test.mybatis.MyBatis)

```
package com.test.mybatis;

import com.test.mybatis.domain.User;
import java.io.InputStream;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

public class MyBatis {
    /**
     * 根据id删除用户
     */
    @Test
    public void testDeleteUser(){
        // mybatis配置文件
        String resource = "mybatis/Mybatis-config.xml";
        // 得到配置文件流
        InputStream inputStream = MyBatis.class.getClassLoader().getResourceAsStream(resource);
        // 创建会话工厂构造类
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        // 通过会话工厂构造类来和mybatis配置文件来获得会话工厂类
        SqlSessionFactory sqlSessionFactory = builder.build(inputStream);
        // 通过会话工厂类获取对应的会话
        SqlSession sqlSession = sqlSessionFactory.openSession();

        Integer id = 21;
        // 根据传入的id删除用户信息
        sqlSession.delete("user.deleteUserById",id);
        // 提交事务
        sqlSession.commit();

        // 输出信息
        System.out.println("删除用户id为"+id);

        sqlSession.close();
    }
}
```

##### 更新用户

###### user.xml(mybatis/sqlmap/user.xml)

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!-- 命名空间，不同的sql进行隔离 -->
<mapper namespace="user">
  <!-- 更新用户 -->
  <!-- 需要传入用户的id，需要传入用户的更新信息 -->
  <!-- parameterType:需要更新的对象，主键id必须存在 -->
  <update id="updateUser" parameterType="com.test.mybatis.domain.User">
    update tb_student set name=#{name},sex=#{sex} where id=#{id}
  </update>
</mapper>
```

###### MyBatis.java(com.test.mybatis.MyBatis)

```
package com.test.mybatis;

import com.test.mybatis.domain.User;
import java.io.InputStream;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

public class MyBatis {
    /**
     * 根据id更新用户
     */
    @Test
    public void testUpdateUser(){
        // mybatis配置文件
        String resource = "mybatis/Mybatis-config.xml";
        // 得到配置文件流
        InputStream inputStream = MyBatis.class.getClassLoader().getResourceAsStream(resource);
        // 创建会话工厂构造类
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        // 通过会话工厂构造类来和mybatis配置文件来获得会话工厂类
        SqlSessionFactory sqlSessionFactory = builder.build(inputStream);
        // 通过会话工厂类获取对应的会话
        SqlSession sqlSession = sqlSessionFactory.openSession();

        User user = new User();

        user.setName("小蓝");
        user.setSex("男");

        // id必须传入
        user.setId(22);

        // 更新用户信息
        sqlSession.update("updateUser",user);
        // 提交事务
        sqlSession.commit();

        sqlSession.close();
    }
}
```

##### 字段说明

###### parameterType

指定参数的传入类型

###### resultType

指定返回结果的传出类型

###### {}和${}

#表示占位符，类型可以是简单类型，pojo，hashmap，如果接收的是简单类型，#{}中可以写成value或者其他名称。#{}接收pojo对象值，通过OGNL读取对象属性值，通过属性.属性...的方式获取对象属性值。
$表示拼接符号，存在SQL注入风险，不建议使用

###### selectOne和selectList

selectOne表示查询一条记录进行映射。如果使用selectOne可以实现使用selectList也可以实现。
selectList表示查询一个列表（多条记录进行映射）。如果查询返回多条记录，那么则不能使用selectOne。

###### 主键返回（自增和非自增）

last_insert_id()和uuid()



### 基础开发方法

##### 名词解释

###### SqlSessionFactoryBuilder

通过SqlSessionFactoryBuilder创建SqlSessionFactory。

###### SqlSessionFactory

通过SqlSessionFactory创建SqlSession，适用单例模式管理SqlSessionFactory(工厂一旦创建，适用一个实例);将来mybatis和spring整合，适用单例模式管理SqlSessionFactory。

###### SqlSession

SqlSession是一个面向用户的的接口，提供了很多操作数据库的方法：比如selectOne、selectList、delete和update；SqlSession是线程不安全的，因此最佳应用场合是在方法体内，定义局部变量来进行适用；

##### 原始DAO开发

###### UserDao.java(com.test.mybatis.dao.UserDao)

```
package com.test.mybatis.dao;

import com.test.mybatis.domain.User;

public interface UserDao {

    // 根据用户主键id获取用户
    User findUserById(int userId);

    // 插入用户
    void insertById(User user);

    // 根据用户id删除用户
    void deleteUserById(int userId);
}
```

###### UserDaoImpl.java(com.test.mybatis.dao.UserDaoImpl)

```
package com.test.mybatis.dao;

import com.test.mybatis.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class UserDaoImpl implements UserDao {

    private SqlSessionFactory sqlSessionFactory;
    public UserDaoImpl(SqlSessionFactory sqlSessionFactory) {
        // 需要向dao实现类中注入SqlSessionFactory
        // 这里通过构造方法进行注入
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public User findUserById(int userId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = sqlSession.selectOne("user.findUserById",userId);
        sqlSession.close();
        return user;
    }

    @Override
    public void insertById(User user) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.insert("user.insertUser",user);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void deleteUserById(int userId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.delete("user.deleteUserById",userId);
        sqlSession.commit();
        sqlSession.close();
    }
}
```

###### UserDaoImplTest.java(com.test.mybatis.test.UserDaoImplTest)

```
package com.test.mybatis.test;

import com.alibaba.fastjson.JSONObject;
import com.test.mybatis.MyBatis;
import com.test.mybatis.dao.UserDao;
import com.test.mybatis.dao.UserDaoImpl;
import com.test.mybatis.domain.User;
import java.io.InputStream;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

public class UserDaoImplTest {

    // 创建 sqlSessionFactory
    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void setUp(){

        // mybatis配置文件
        String resource = "mybatis/mybatis-config.xml";
        // 得到配置文件流
        InputStream inputStream = MyBatis.class.getClassLoader().getResourceAsStream(resource);
        // 创建会话工厂构造类
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        // 通过会话工厂构造类来和mybatis配置文件来获得会话工厂类
        sqlSessionFactory = builder.build(inputStream);
    }

    @Test
    public void testFindUserById(){
        // 创建UserDao的对象
        UserDao userDao = new UserDaoImpl(sqlSessionFactory);

        User user = userDao.findUserById(1);

        System.out.println(JSONObject.toJSONString(user));
    }
}
```

##### 原始DAO(data access object)的开发方法

###### 思路

程序员编写dao和dao实现类，需要向dao实现类中注入SqlSessionFactory，在方法体内通过SqlSessionFactory创建SqlSession;

###### 总结

1、dao接口实现类方法中存在大量的模板方法，能否将这些代码提取出来，提升开发效率；
2、调用sqlSession方法时将statement的id进行硬编码；
3、调用sqlSession方式时传入的变量，由于sqlSession方法使用泛型，即使变量类型传错，在编译阶段		没有进行报错，不利于开发；

##### Mapper代理开发方法

###### UserMapper.java(com.test.mybatis.mapper.UserMapper)

```
package com.test.mybatis.mapper;

import com.test.mybatis.domain.User;

/**
 * 用户映射代理接口
 */
public interface UserMapper {

    User findUserById(int id,String name);

    void insertUser(User user);

    void deleteUserById(int userId);
}
```

###### mybatis/mybatis-config.xml

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
  PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
  <!-- 加载属性文件 -->
  <properties resource="datasource.properties"/>
  <!-- 和Spring整合后废除 -->
  <environments default="development">
    <environment id="development">
      <!-- 使用JDBC事务管理，事务由mybatis来进行控制 -->
      <transactionManager type="JDBC"/>
      <!-- 数据库连接池属性 -->
      <dataSource type="POOLED">
        <property name="driver" value="${jdbc.driverClass}"/>
        <property name="url" value="${jdbc.jdbcUrl}"/>
        <property name="username" value="${jdbc.user}"/>
        <property name="password" value="${jdbc.password}"/>
      </dataSource>
    </environment>
  </environments>

  <!-- 加载配置文件 -->
  <mappers>
    <mapper resource="mybatis/mapper/user-mapper.xml"/>
  </mappers>
</configuration>
```

###### mybatis/mapper/user-mapper.xml

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!-- 命名空间，不同的sql进行隔离 -->
<!-- 注意：使用mapper代理方法开发，namespace有特殊重要作用，namespace等于mapper的接口地址 -->
<mapper namespace="com.test.mybatis.mapper.UserMapper">
  <!-- 在映射文件中配置sql语句 -->
  <!-- id：唯一标志一个sql，一个statement -->
  <!-- parameterType：指定参数的类型，这里指定java.lang.Integer类型 -->
  <!-- #{ }：表示一个占位符 -->
  <!-- #{id}：其中的id表示接入参数的，参数名称就是id，如果输入参数是简单类型，#{ }中的参数名可以任意，可以value或者其他名称 -->
  <!-- resultType：指定sql输出结果的映射java对象类型，select指定resultType表示将单条记录映射的java对象 -->
  <select id="findUserById" parameterType="java.lang.Integer" resuleType="com.test.mybatis.domain.User">
    select * from tb_student where id=#{id}
  </select>
</mapper>
```

###### UserMapperTest.java(com.test.mybatis.test.UserMapperTest)

```
package com.test.mybatis.test;

import com.alibaba.fastjson.JSONObject;
import com.test.mybatis.MyBatis;
import com.test.mybatis.mapper.UserMapper;
import java.io.InputStream;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

public class UserMapperTest {

    // 创建sqlSessionFactory
    private SqlSessionFactory sqlSessionFactory;

    @Before
        public void setUp(){

            // mybatis配置文件
            String resource = "mybatis/mybatis-config-new.xml";
            // 得到配置文件流
            InputStream inputStream = MyBatis.class.getClassLoader().getResourceAsStream(resource);
            // 创建会话工厂构造器
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            // 通过会话工厂构造类和mybatis配置文件来获取会话工厂类
            sqlSessionFactory = builder.build(inputStream);
    }

    @Test
    public void testFindUserById(){
        // 通过 SqlSessionFactory 获取 SqlSession
        System.out.println(sqlSessionFactory);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 通过 SqlSession 获取 userMapper代理对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        System.out.println(JSONObject.toJSONString(userMapper.findUserById(1)));

        sqlSession.close();
    }
}
```

##### Mapper代理的开发方法

###### 思路

1、编写mapper.xml；	
2、程序员编写Mapper接口，相当于dao的接口，需要遵循一些开发方法；mybatis自动生成mapper接口实现类的代理对象；

###### 开发规范

1、在mapper.xml中的namespace等于mapper的接口地址；
2、mapper.java接口中的方法名和mapper.xml中statement的id一致；	
3、mapper.java接口中方法输入参数类型和mapper.xml中statement的parameterType指定的类型一致；
4、mapper.java接口中方法返回值类型中和statement的resultType指定的类型一致；

###### 代理对象selectOne和selectList问题

1、如果mapper方法返回单个pojo对象（非集合对象），代理对象内部通过selectOne查询数据库；
2、如果mapper方法返回一个集合对象，代理对象内部通过selectList查询数据库；

###### mapper接口方法参数只有一个是否影响系统开发

系统框架中，dao层的代码是被业务层共用的。
即使mapper接口只有一个参数，可以使用包装类型的pojo满足不同的业务方法的需求。

注意：持久层方法的参数可以使用包装类型、map，但是业务层(service)方法中建议不要使用包装类型（不利于业务层的可扩展）。

### Mybatis配置文件

##### Properties属性

```
<configuration>

  <!-- 加载属性文件 -->
  <properties resource="datasource.properties"/>

  <!-- 直接配置属性值和属性名 -->
  <properties>
    <property name="driver" value="com.mysql.jdbc.Driver"/>
    <property name="url" value="jdbc:mysql://localhost:3306/test"/>
    <property name="username" value="root"/>
    <property name="password" value="123456"/>
  </properties>
</configuration>
```

注意事项：
1、在properties元素体内首先被读取；
2、然后读取propertis元素中resource或url加载的属性，它会覆盖已读取的同名属性；

##### settings(全局参数配置)

```
<!-- 该配置影响的所有映射器中配置的缓存的全局开关。默认值true -->
<setting name="cacheEnabled" value="true"/>
<!--延迟加载的全局开关。当开启时，所有关联对象都会延迟加载。 特定关联关系中可通过设置fetchType属性来覆盖该项的开关状态。默认值false  -->
<setting name="lazyLoadingEnabled" value="true"/>
<!-- 是否允许单一语句返回多结果集（需要兼容驱动）。 默认值true -->
<setting name="multipleResultSetsEnabled" value="true"/>
<!-- 使用列标签代替列名。不同的驱动在这方面会有不同的表现， 具体可参考相关驱动文档或通过测试这两种不同的模式来观察所用驱动的结果。默认值true -->
<setting name="useColumnLabel" value="true"/>
<!-- JDBC 支持自动生成主键，需要驱动兼容。如果设置为 true，则这个设置强制使用自动生成主键，尽管一些驱动不能兼容但仍可正常工作（Derby）。默认值false  -->
<setting name="useGeneratedKeys" value="false"/>
<!--  指定 MyBatis 应如何自动映射列到字段或属性。 NONE 表示取消自动映射；PARTIAL 只会自动映射没有定义嵌套结果集映射的结果集。 FULL 会自动映射任意复杂的结果集（无论是否嵌套）。 -->
<!-- 默认值PARTIAL -->
<setting name="autoMappingBehavior" value="PARTIAL"/>
<setting name="autoMappingUnknownColumnBehavior" value="WARNING"/>
<!--  配置默认的执行器。SIMPLE 就是普通的执行器；REUSE 执行器会重用预处理语句（prepared statements）； BATCH 执行器将重用语句并执行批量更新。默认SIMPLE  -->
<setting name="defaultExecutorType" value="SIMPLE"/>
<!-- 设置超时时间，它决定驱动等待数据库响应的秒数。 -->
<setting name="defaultStatementTimeout" value="25"/>
<setting name="defaultFetchSize" value="100"/>
<!-- 允许在嵌套语句中使用分页（RowBounds）默认值False -->
<setting name="safeRowBoundsEnabled" value="false"/>
<!-- 是否开启自动驼峰命名规则（camel case）映射，即从经典数据库列名 A_COLUMN 到经典 Java 属性名 aColumn 的类似映射。  默认false -->
<setting name="mapUnderscoreToCamelCase" value="false"/>
<!-- MyBatis 利用本地缓存机制（Local Cache）防止循环引用（circular references）和加速重复嵌套查询。默认值为 SESSION，这种情况下会缓存一个会话中执行的所有查询。若设置值为 STATEMENT，本地会话仅用在语句执行上，对相同 SqlSession 的不同调用将不会共享数据。  -->
<setting name="localCacheScope" value="SESSION"/>
<!-- 当没有为参数提供特定的 JDBC 类型时，为空值指定 JDBC 类型。 某些驱动需要指定列的 JDBC 类型，多数情况直接用一般类型即可，比如 NULL、VARCHAR 或 OTHER。 -->
<setting name="jdbcTypeForNull" value="OTHER"/>
<!-- 指定哪个对象的方法触发一次延迟加载。 -->
<setting name="lazyLoadTriggerMethods" value="equals,clone,hashCode,toString"/>
```

##### typeAliases别名

1、在mapper.xml中，定义很多的statement，statement需要parameterType指定输入参数的类型、需要resultType指定输出结果的映射类型。
2、如果在指定类型时输入类型的全路径，不方便进行开发，可以针对parameterType或resultType指定的类型定义一些别名，在mapper.xml中通过定义别名，方便进行开发。

| 别名       | 映射的类型 |
| ---------- | ---------- |
| _byte      | byte       |
| _long      | long       |
| _short     | short      |
| _int       | int        |
| _integer   | int        |
| _double    | double     |
| _float     | float      |
| _boolean   | boolean    |
| string     | String     |
| byte       | Byte       |
| long       | Long       |
| short      | Short      |
| int        | Integer    |
| integer    | Integer    |
| double     | Double     |
| float      | Float      |
| boolean    | Boolean    |
| date       | Date       |
| decimal    | BigDecimal |
| bigdecimal | BigDeciaml |

##### typeAliases别名

```
  <!-- 自定义别名 -->
  <typeAliases>
    <!-- 单个别名定义 -->
     <typeAlias type="com.test.mybatis.domain.User" alias="user" />
    <!-- 批量别名定义 -->
    <!-- mybatis自动扫描包中的po类，自动定义别名，别名就是类名(首字母大写或者小写都可以) -->
     <package name="com.test.mybatis.domain"/>
  </typeAliases>
```

```
  <select id="findUserById" parameterType="int" resultType="user">
    select * from tb_student where id=#{id}
  </select>
```

##### typeHandlers(类型处理器)

mybatis中通过typeHandlers解决java类型和jdbc类型的映射，通常mybatis默认提供的已经满足，一般不需要自定义。

![](https://img-blog.csdnimg.cn/20190415203324468.png)

##### 映射配置

```
<!-- 加载配置文件 -->
<mappers>
    <!-- 1. 通过resource方法一次性加载一个配置文件 -->
    <mapper resource="mybatis/mapper/user.xml" />
    <mapper resource="mybatis/mapper/user-mapper.xml" />
    <mapper resource="mybatis/mapper/employee-mapper.xml" />
    <!-- 2. 通过url方法一次性加载一个配置文件 -->
    <mapper url="D:\IdeaProjects\Spring\demo\src\main\resources\mybatis\mapper\user-mapper.xml" />
    <!-- 3. 通过mapper接口加载映射文件 -->
    <!-- 前提是遵循mapper接口类名 -->
    <!-- 遵循一些接口：需要将mapper接口类名和mapper.xml映射文件名称保持一致，且在一个目录中 -->
    <mapper class="com.test.mybatis.mapper.UserMapper" />
    <!-- 4. 批量加载mapper指定mapper接口的包名，mybatis自动扫描包下所有mapper接口进行加载 -->
    <!-- 遵循一些规范：需要将mapper接口类名和mapper.xml映射文件名称保持一致，且在一个目录中上边规范的前提是：使用的是mapper代理方法 -->
    <package name="com.test.mybatis.mapper"/>
</mappers>
```

### 输入映射与输出映射

##### 输入映射

通过parameterType指定输入参数的类型，类型可以是简单类型、hashmap、pojo的包装类型。

##### 输出映射

resultType(支持简单类型(注意输出结果只有一列)、hashmap和pojo)
	使用resultType进行输出映射，只有查询出来的列名和pojo中的属性值一致，该列才可以映射成功。如果查询出来的列名和pojo中的属性名全部不一致，没有创建pojo对象。只要查询出来的列名和pojo中的属性有一个一致，就会创建pojo对象。

resultMap
	使用resultMap完成高级输出结果映射，如果查询出来的列名和pojo属性不一致。通过定义resultMap对列名和pojo属性名之间作映射关系。
	（1）定义resultMap
	（2）使用resultMap作为statement的输出映射类型

##### 输入输出映射-接口

```
package com.test.mybatis.mapper;

import com.test.mybatis.domain.User;

/**
 * 用户映射代理接口
 */
public interface UserMapper {

    /**
     * 根据id查找用户
     * @param userId
     * @return
     */
    User findUserById(int userId);

    /**
     * 插入用户
     * @param user
     */
    void insertUser(User user);

    /**
     * 根据id删除用户
     * @param userId
     */
    void deleteUserById(int userId);
}
```

```
package com.test.mybatis.dto;

import java.io.Serializable;

/**
 * 查询对应的查询条件
 */
public class UserSearchParam implements Serializable {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
```

```
package com.test.mybatis.domain;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String name;
    private String sex;

    public int getId(int id) {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}	
```

##### 输入映射-映射文件

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!-- 命名空间，不同的sql进行隔离 -->
<!-- 注意：使用mapper代理方法开发，namespace有特殊重要作用，namespace等于mapper的接口地址 -->
<mapper namespace="com.test.mybatis.mapper.UserMapper">
  <!-- 根据用户名称模糊搜索用户信息，可能返回多条 -->
  <!-- resultType:指定单条记录所记录的java映射对象 -->
  <!-- ${}:表示拼接sql字符串，将接收的参数的内容不加修饰拼接在sql中，使用${}拼接sql，可能引起SQL注入，为了规避可以使用函数拼接 -->
  <!-- ${value}:接收输入参数的内容，如果传入类型是简单类型，${}中只能使用value -->
  <select id="findUserByName" parameterType="java.lang.String"  resultType="com.test.mybatis.domain.User">
    select * from tb_student where name LIKE concat('%',#{value},'%')
  </select>
</mapper>
```

##### 输出映射resultType-接口

```
package com.test.mybatis.mapper;

import com.test.mybatis.domain.User;

/**
 * 用户映射代理接口
 */
public interface UserMapper {

    /**
     * 根据id查找用户
     * @param userId
     * @return
     */
    User findUserById(int userId);

    /**
     * 根据名字模糊查询用户信息
     * @param name
     */
    void findUserByName(String name);

    /**
     * 插入用户
     * @param user
     */
    void insertUser(User user);

    /**
     * 根据id删除用户
     * @param userId
     */
    void deleteUserById(int userId);
}
```

##### 输出映射resultTupe-映射文件

```
<!-- 根据用户名称模糊搜索用户信息，可能返回多条 -->
<select id="findUserByName" parameterType="java.lang.String"  resultType="com.test.mybatis.domain.User">
select * from tb_student where name LIKE concat('%',#{value},'%')
</select>
```

##### 高级输出映射resultMap-映射文件

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.test.mybatis.mapper.UserMapper">
  <!-- 定义resultMap -->
  <!-- 将数据库表tb_student的列名和pojo的属性进行映射 -->
  <!-- id:对resultMap的唯一标识 -->
  <resultMap id="student" type="tb_student">
    <!-- id:标识查询结果集中的唯一标识 -->
    <!-- columm:数据库查询出来的列名 -->
    <!-- property:pojo的属性名 -->
    <id columm="id" property="id"/>
    <!-- result:对普通映射列的定义 -->
    <!-- columm:数据库查询出来的列名 -->
    <!-- property:pojo的属性名 -->
    <trsult colum="name" property="name"/>
    <trsult colum="sex" property="sex"/>
  </resultMap>

  <!-- 通过sql链接查询将相应需要的数据查询 -->
  <select id="querystudent" parameterType="studentSearch" resultMap="studentResultMap">
    select * from tb_student
    where id=#{id}
    and name LIKE concat('%',#{value},'%')
    and sex=#{sex}
  </select>
</mapper>
```



### 动态SQL

Mybatis核心是对SQL语句进行灵活操作，通过表达式进行判断，对SQL进行灵活拼接、组装。

##### 动态SQL标签

```
<if>:条件判断标签
<where>:where语句标签，能自动去掉条件中第一个and
<choose>:条件选择语句
<set>:set语句标签，主要用于update语句，能自动去掉最后一个逗号
<sql>:sql片段
<foreach>:循环遍历SQL语句标签
```

##### 动态sql-if语句

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!-- 员工信息的高级查询 -->
<mapper namespace="com.test.mybatis.mapper.UserMapper">
  <select id="queryStudent" parameterType="com.test.mybatis.dto.UserSearchParam"
    resultType="com.test.mybatis.domain.User">
    select * from tb_student
    -- 为防止重复出现and，这里手动设置1=1
    where 1=1
    <if test="id != null">
        and id=#{id}
    </if>
    <if test="name != null and name != ' '">
        and name LIKE concat('%',#{value},'%')
    </if>
    <if test="sex != null">
      and sex=#{sex}
    </if>
  </select>
</mapper>
```

##### 动态sql-if+where语句

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!-- 员工信息的高级查询 -->
<mapper namespace="com.test.mybatis.mapper.UserMapper">
  <select id="queryStudent" parameterType="com.test.mybatis.dto.UserSearchParam"
    resultType="com.test.mybatis.domain.User">
    select * from tb_student
    -- where标签会自动删除第一个and或or
    <where>
      <if test="id != null">
        and id=#{id}
      </if>
      <if test="name != null and name != ' '">
        and name LIKE concat('%',#{value},'%')
      </if>
      <if test="sex != null">
        and sex=#{sex}
      </if>
    </where>
  </select>
</mapper>
```

##### 动态sql-choose语句

```
    <choose>
      <if test="exactName != null and exactName != ' '">
        and name = #{exactName}
      </if>
      <if test="name != null and name != ' '">
        and name LIKE concat('%',#{value},'%')
      </if>
      <otherwise>
        and name = #{defaultName}
      </otherwise>
    </choose>
```

##### 动态sql-if+set语句

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!-- 员工信息的高级查询 -->
<mapper namespace="com.test.mybatis.mapper.UserMapper">
  <select id="queryStudent" parameterType="com.test.mybatis.dto.UserSearchParam"
    resultType="com.test.mybatis.domain.User">
    select * from tb_student
    -- set标签会自动删除最后一个
    <set>
      <if test="id != null">
        and id=#{id}
      </if>
      <if test="name != null and name != ' '">
        and name LIKE concat('%',#{value},'%')
      </if>
      <if test="sex != null">
        and sex=#{sex}
      </if>
    </set>
    where id=#{id}
  </select>
</mapper>
```

##### 动态sql-sql片段

```
  <!-- sql:片段 -->
  <!-- id:唯一标识sql片段，跨mapper文件前面增加namespace -->
  <sql id="studentQuerySql">
    <if test="id != null">
      and id=#{id}
    </if>
    <if test="name != null and name != ' '">
      and name LIKE concat('%',#{value},'%')
    </if>
    <if test="sex != null">
      and sex=#{sex}
    </if>
    where id=#{id}
  </sql>
```

```
  <select id="queryStudent" parameterType="studentSearch" resultMap="studentResultMap">
    select * from tb_student
    -- where标签会自动删除第一个and或or
    <where>
        -- 通过include来引用sql片段
        -- refid:表示所要引用sql片段的id
        <include refid="studentQuerySql"/>
    </where>
  </select>
```

##### 动态sql-foreach

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!-- 员工信息的高级查询 -->
<mapper namespace="com.test.mybatis.mapper.UserMapper">
  <!-- sql:片段 -->
  <!-- id:唯一标识sql片段，跨mapper文件前面增加namespace -->
  <sql id="studentQuerySql">
    <if test="id != null">
      and id=#{id}
    </if>
    <if test="ids != null">
      -- 使用foreach遍历ids
      -- 指定输入对象中的集合属性
      -- item:每个遍历生成的对象
      -- open:遍历开始的拼接串
      -- close:遍历结束的拼接串
      -- separator:遍历对象之间所拼接串
      and id in
      <foreach collection="ids" item="id" open="(" close=")" separator=",">
        -- 每个拼接的字符串
        #{id}
      </foreach>
    </if>
    <if test="name != null and name != ' '">
      and name LIKE concat('%',#{value},'%')
    </if>
    <if test="sex != null">
      and sex=#{sex}
    </if>
    where id=#{id}
  </sql>
</mapper>
```

