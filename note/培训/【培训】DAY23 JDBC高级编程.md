JDBC高阶编程

[TOC]

### JDBC背景原理

##### 背景

JDBC是Java应用程序访问数据库的里程碑式解决方案。Java研发者希望用相同的方式访问不同的数据库，以实现与具体数据库无关的Java操作界面。

JDBC定义了一套标准接口，即访问数据库的通用API，不同的数据库厂商根据各自数据库的特点去实现这些接口。

JDBC（Java DataBase Connectivity）就是Java数据库连接，说白了就是用Java语言来操作数据库。原来我们操作数据库是在控制台使用SQL语句来操作数据库，JDBC是用Java语言向数据库发送SQL语句。

早期SUN公司的天才们想编写一套可以连接天下所有数据库的API，但是当他们刚刚开始时就发现这是不可完成的任务，因为各个厂商的数据库服务器差异太大了。后来SUN开始与数据库厂商们讨论，最终得出的结论是，由SUN提供一套访问数据库的规范（就是一组接口），并提供连接数据库的协议标准，然后各个数据库厂商会遵循SUN的规范提供一套访问自己公司的数据库服务器的API出现。SUN提供的规范命名为JDBC，而各个厂商提供的，遵循了JDBC规范的，可以访问自己数据库的API被称之为驱动！

##### 原理

 JDBC是接口，而JDBC驱动才是接口的实现，没有驱动无法完成数据库连接！每个数据库厂商都有自己的驱动，用来连接自己公司的数据库。

###### 基本原理

![](https://img-blog.csdnimg.cn/20190416131206109.png)

###### 架构原理

![](https://img-blog.csdnimg.cn/20190416131058369.png)



### JDBC基本编辑

```
package com.test.spring.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class test {

    // JDBC驱动
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8";
    // 数据库的账户密码
    static final String USER = "root";
    static final String PASS = "123456";
    static ResultSet rs = null;

    public static void main(String[] args) throws SQLException {
        // 第一步，加载一个Driver驱动
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            // 这里会发生类没有找到的异常
            e.printStackTrace();
        }
        try {
            // 第二步，创建数据库连接
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            // 第三步，创建SQL命令发送器statement
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM tb_student";
            // 第四步，通过statement发送SQL命令并得到结果
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                // 第五步，处理结果
                String id = rs.getString("id");
                System.out.println(id);
            }
            // 第六步，关闭数据库资源
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // 这里会发生SQL异常，因为我们提供的账户和密码不一定能连接成功
        } finally {
            rs.close();
        }
    }
}
```



### JDBC核心API

##### JDBC核心类（接口）介绍

1、Driver接口：表示java驱动程序接口。所有的具体的数据库厂商要来实现此接口。

2、DriverManager类：驱动管理器类，用于管理所有注册的驱动程序。

3、Connection接口：表示java程序和数据库的连接对象。

4、Statement接口： 用于执行静态的sql语句。

5、ResultSet接口：用于封装查询出来的数据。



##### Driver（驱动）

所谓的驱动，其实就是实现了java.sql.Driver接口的类。如oracle的驱动类是 oracle.jdbc.driver.OracleDriver.class（此类可以在oracle提供的JDBC jar包中找到），此类实现了java.sql.Driver接口。

由于驱动本质上还是一个class，将驱动加载到内存和加载普通的class原理是一样的:使用Class.forName("driverName")。

```
// 加载Oracle数据库驱动
class.forName("oracle.jdbc.Driver.OracleDriver");
// 加载SQL Server数据库驱动
class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
// 加载MySQL数据库驱动
class.forName("com.mysql.jdbc.Driver");
```

注意：Class.forName()将对应的驱动类加载到内存中，然后执行内存中的static静态代码段，代码段中，会创建一个驱动Driver的实例，放入DriverManager中，供DriverManager使用。

##### DriverManager（驱动管理器类）

一般我们操作Driver，获取Connection对象都是交给DriverManager统一管理的。DriverManger可以注册和删除加载的驱动程序，可以根据给定的url获取符合url协议的驱动Driver或者是建立Conenction连接，进行数据库交互。

![](https://img-blog.csdnimg.cn/20190416131308560.png)

| More Actionsstatic void    | deregisterDriver(Driver driver)<br />从DriverManager的列表中删除一个驱动程序 |
| -------------------------- | ------------------------------------------------------------ |
| static Connection          | getConnection(String url)<br />试图建立到给定数据库URL的连接 |
| static Connection          | getConnection(String url,Properties info)<br />试图建立到给定数据库URL的连接 |
| static Connection          | getConnection(String url,String user,String password)<br />试图建立到给定数据库URL的连接 |
| static Driver              | getDriver(String url)<br />试图查找能理解给定URL的驱动程序   |
| static Enumeration<Driver> | getDrivers()<br />获取带有当前调用者可以访问的所有当前已加载JDBC驱动程序的Enumerate |
| static void                | registerDriver(Driver driver)<br />向DriverManager注册给定驱动程序 |



##### Connection（链接对象）

创建 Connection 连接对象，可以使用驱动Driver的 connect(url,props)，也可以使用 DriverManager 提供的getConnection()方法，此方法通过url自动匹配对应的驱动Driver实例，然后调用对应的connect方法返回Connection对象实例。

```
Driver driver  = DriverManager.getDriver(url);
Connection connection = driver.connect(url, props);
```

```
Class.forName("com.mysql.jdbc.Driver");
Connection connection = DriverManager.getConnection(url, props);
```



#####  ConnectionPool（链接池）

对于一个简单的数据库应用，由于对于数据库的访问不是很频繁。这时可以简单地在需要访问数据库时，就新创建一个连接，用完后就关闭它，这样做也不会带来什么明显的性能上的开销。但是对于一个复杂的数据库应用，情况就完全不同了。频繁的建立、关闭连接，会极大的减低系统的性能，因为对于连接的使用成了系统性能的瓶颈。

对于共享资源，有一个很著名的设计模式：资源池。该模式正是为了解决资源频繁分配、释放所造成的问题的。把该模式应用到数据库连接管理领域，就是建立一个数据库连接池，提供一套高效的连接分配、使用策略，最终目标是实现连接的高效、安全的复用。



##### Statement接口

Statement接口用来处理发送到数据库的SQL语句对象，通过Connection对象创建。主要有三个常用方法：

```
Statement stmt=conn.createStatement();
// 1.execute方法，
//  如果执行的sql是查询语句且有结果集则返回true,
//  如果是非查询语句或者没有结果集，返回false
boolean flag = stmt.execute(sql);
// 2.执行查询语句，返回结果集
ResultSetrs = stmt.executeQuery(sql);
// 3.执行DML语句，返回影响的记录数
int flag = stmt.executeUpdate(sql);
```



##### ResultSet接口

执行查询SQL语句后返回的结果集，由ResultSet接口接收。ResultSet代表SQL查询结果,其内部维护了一个读取数据的游标,默认情况在,游标在第一行数据之前, 当调用next() 方法时候, 游标会向下移动,并将返回结果集中是否包含数据, 如果包含数据就返回true. 结果集还提供了很好getXXX方法用于获取结果集游标指向当前行数据。

![](https://img-blog.csdnimg.cn/20190416132458880.png)



### JDBC事务管理

事务(Transaction)是并发控制的基本单位,指作为单个逻辑工作单元执行的系列操作,而这些逻辑工作单元需要满足ACD特性。

- 原子性：atomicity
- 一致性：consistency
- 隔离性：isolation
- 持久性：durability



Connection

- .setAutoCommit()	开启事务
- .commit()		       提交事务
- .rollback()		       回滚事务



原子性：该操作是最小逻辑单元整体，已经不可分隔。一致性：要么所有都执行，要么所有都不执行。隔离性：多个事务相互隔离，互不影响。持久性：事物的执行结果永久生效。

在JDBC中可以调用Connection对象的setAutoCommit(false)这个接口，将commit()之前的所有操作都看成是一个事物。同时，如果事务执行过程中发生异常，可以调用rollback()接口进行回滚到事务开始之前的状态。

### DAO模式

##### 什么是DAO？

Data Access Objects :数据存储对象

DAO指位于业务逻辑和持久化数据之间实现对持久化数据的访问，也就是将数据库操作都封装起来，对外提供相应的接口

采用面向接口，有利于代码的，低耦合高类聚原则



##### DAO的优势？

隔离业务逻辑代码和数据访问代码

隔离不同数据库实现



##### DAO的组成？

DAO接口：把对数据库的所有操作定义为抽象方法

DAO实现类：不同数据库给出的DAO接口定义方法的具体实现

实体类：用于存放和传输对象数据

数据库连接和关闭工具：避免了数据库连接和关闭代码的重复



![](https://img-blog.csdnimg.cn/20190416134114568.png)



##### 总结

1、一个DAO工厂类；

2、一个DAO接口；

3、一个实现DAO接口的具体类；

4、 数据传递对象（实体对象（Entity）或值对象（Value Object，简称VO））