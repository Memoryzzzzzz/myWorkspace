# Spring事务管理

[TOC]

### 事务简介

数据库事务(Database Transaction) ，是指作为单个逻辑工作单元执行的一系列操作，要么全部执行，要么全部不执行。 通过将一组相关操作组合为一个要么全部成功要么全部失败的单元，可以简化错误恢复并使应用程序更加可靠。事务是数据库运行中的逻辑工作单位，由DBMS中的事务管理子系统负责事务的处理。

#### 一、事务的基本要素（ACID）

##### 1、原子性（Atomicity）

事务开始后所有操作，要么全部做完，要么全部不做，不可能停滞在中间环节。事务执行过程中出错，会回滚到事务开始前的状态，所有的操作就像没有发生一样。也就是说事务是一个不可分割的整体，就像化学中学过的原子，是物质构成的基本单位。

##### 2、一致性（Consistency）

事务开始前和结束后，数据库的完整性约束没有被破坏 。比如A向B转账，不可能A扣了钱，B却没收到。

##### 3、隔离性（Isolation）

同一时间，只允许一个事务请求同一数据，不同的事务之间彼此没有任何干扰。比如A正在从一张银行卡中取钱，在A取钱的过程结束前，B不能向这张卡转账。

##### 4、持久性（Durability）

事务完成后，事务对数据库的所有更新将被保存到数据库，不能回滚。 

#### 二、事务的并发问题

##### 1、脏读

事务A读取了事务B更新的数据，然后B回滚操作，那么A读取到的数据是脏数据

##### 2、不可重复读

事务 A 多次读取同一数据，事务 B 在事务A多次读取的过程中，对数据作了更新并提交，导致事务A多次读取同一数据时，结果 不一致。

##### 3、幻读

系统管理员A将数据库中所有学生的成绩从具体分数改为ABCDE等级，但是系统管理员B就在这个时候插入了一条具体分数的记录，当系统管理员A改结束后发现还有一条记录没有改过来，就好像发生了幻觉一样，这就叫幻读。



### 事务模型

事务模型：常见的事务模型有三种：

1. 本地事务模型(Local Transaction Model)这个术语指的是这样一个事实：事务被底层数据库(DBMS)或在 JMS 中被底层消息服务提供者所管理。从开发人员的角度来看，在本地事务模型中，我们所管理的并非”事务”，而是“连接”。代码片段如下：

   ```
   	Connection connection = null;
       try{
           // 获取数据库连接
           connection = dataSource.getConnection();
           
           // 设置事务提交方式为非自动提交
           connection.setAutoCommit(false);
           Statement statement = connection.createStatement();
           statement.execute("UPDATE tb_name SET key1=value, key2=value2 WHERE condition");
           
           // 提交事务
           connection.commit();
       }catch (SQLException e){
           log.error("事务异常",e);
           connection.rollback();
       }
   ```

2. 编程式事务模型(Programmatic Transaction Model)和本地事务模型两者最大区别之一是，开发人员使用编程式模型，管理的是事务(transaction)，而不是连接(connection)。在Spring框架中通过 org.springframework.transaction 包下的TransactionTemplate 或 PlatformTransactionManager 完成的。代码片段：

   ```
       TransactionTemplate transactionTemplate = ...;
       PlatformTransactionManager transactionManager = transactionTemplate.getTransactionManager();
       TransactionStatus transactionStatus = ...;
       try{
           // 业务方法1
           serviceImpl1.method...
           // 业务方法2
           serviceImpl2.method...
           
           // 提交事务
           transactionManager.commit(transactionStatus);
       }catch(Exception e){
           log.error("事务异常", e);
           transactionManager.rollback(transactionStatus);
       }
   ```

3. 声明式事务模型(Declarative Transaction Model)，即由容器管理事务，这意味着开发人员不用编写任何代码，便可开始或提交事务了。然而，开发人员必须要告诉容器“如何”去管理事务。这也是我们在工作中经常使用的一个事务模型。在Spring框架中通过 org.springframework.transaction.annotation.@Transactional注解完成的。代码片段：

   ```
       //开启一个事务，事务的默认传播行为： PROPAGATTON_REQUIRED
       @Transactional
       @Override
       public Long update(Demo entity) {
           // 业务方法1
           serviceImpl1.method...
           // 业务方法2
           serviceImpl2.method...
           // 业务方法3
           serviceImpl3.method...
       }
   ```

   

### Spring事务概念

##### 事物传播行为（propagation behavior）：

当事务方法被另一个事务方法调用时，必须指定事务应该如何传播。例如：方法可能继续在现有事务中运行，也可能开启一个新事务，并在自己的事务中运行

| 传播行为                  | 含义                                                         |
| ------------------------- | ------------------------------------------------------------ |
| PROPAGATION_REQUIRED      | 表示当前方法必须运行在事务中。如果当前事务存在，方法将会在该事务中运行。否则，会启动一个新的事务。这也是<font color="#FF0000">默认的事务传播行为</font>。 |
| PROPAGATION_SUPPORTS      | 表示当前方法不需要事务上下文，但是如果存在当前事务的话，那么该方法会在这个事务中运行 |
| PROPAGATION_MANDATORY     | 表示该方法必须在事务中运行，如果当前事务不存在，则会抛出一个异常 |
| PROPAGATION_REQUIRED_NEW  | 表示当前方法必须运行在它自己的事务中。一个新的事务将被启动。如果存在当前事务，在该方法执行期间，当前事务会被挂起 |
| PROPAGATION_NOT_SUPPORTED | 表示该方法不应该运行在事务中。如果存在当前事务，在该方法运行期间，当前事务将被挂起 |
| PROPAGATION_NEVER         | 表示当前方法不应该运行在事务上下文中。如果当前正有一个事务在运行，则会抛出异常 |
| PROPAGATION_NESTED        | 表示如果当前已经存在一个事务，那么该方法将会在嵌套事务中运行。嵌套的事务可以独立于当前事务进行单独地提交或回滚。如果当前事务不存在，那么其行为与PROPAGATION_REQUIRED一样。注意各数据库厂商对这种传播行为的支持是有所差异的。可以参考资源管理器的文档来确认它们是否支持嵌套事务 |



##### 事物隔离级别（isolation level）：

隔离级别定义了一个事务可能受其他并发事务影响的程度，下面是事务隔离级别涉及到的一些概念

| 传播行为                   | 含义                                                         |
| -------------------------- | ------------------------------------------------------------ |
| ISOLATION_DEFAULT          | 使用后端数据库默认的隔离级别                                 |
| ISOLATION_READ_UNCOMMITTED | 最低的隔离级别，允许读取尚未提交的数据变更，可能会导致<font color="#FF0000">脏读</font>、<font color="#FF0000">幻读</font>或<font color="#FF0000">不可重复读</font> |
| ISOLATION_READ_COMMITTED   | 允许读取并发事务已经提交的数据，可以阻止<font color="#FF0000">脏读</font>，但是<font color="#FF0000">幻读</font>或<font color="#FF0000">不可重复读</font>仍有可能发生 |
| ISOLATION_REPEATABLE_READ  | 对同一字段的多次读取结果都是一致的，除非数据是被本身事务自己所修改，可以阻止<font color="#FF0000">脏读</font>和<font color="#FF0000">不可重复读</font>，但<font color="#FF0000">幻读</font>仍有可能发生 |
| ISOLATION_SERIALIZABLE     | 最高的隔离级别，完全服从ACID的隔离级别，确保阻止<font color="#FF0000">脏读</font>、<font color="#FF0000">不可重复读</font>以及<font color="#FF0000">幻读</font>，也是最慢的事务隔离级别，因为它通常是通过完全锁定事务相关的数据库表来实现的 |



##### 补充：

1、不可重复读的和幻读很容易混淆，不可重复读侧重于修改，幻读侧重于新增或删除。解决不可重复读的问题只需锁住满足条件的行，解决幻读需要锁表
2、事务隔离级别为读已提交时，写数据只会锁住相应的行
3、事务隔离级别为可重复读时，如果检索条件有索引（包括主键索引）的时候，默认加锁方式是next-key 锁；如果检索条件没有索引，更新数据时会锁住整张表。一个间隙被事务加了锁，其他事务是不能在这个间隙插入记录的，这样可以防止幻读。
4、事务隔离级别为串行化时，读写数据都会锁住整张表
5、隔离级别越高，越能保证数据的完整性和一致性，但是对并发性能的影响也越大。



### Spring事务原理

##### Spring事务处理模块类层次结构

![](https://img-blog.csdnimg.cn/20190410145635676.png)

Spring事务处理模块是通过AOP功能来实现声明式事务处理的，比如事务属性的配置和读取，事务对象的抽象等.在Spring事务处理中可以通过设计<font color="#FF0000">TransationProxyFactoryBean</font>来使用AOP功能，通过这个<font color="#FF0000">TransationProxyFactoryBean</font>可以生成Proxy代理对象，在这个代理对象中，通过TransationInterceptor来完成对代理方法的拦截，正是这些AOP的拦截功能，将事务处理的逻辑纺织进来。在Spring声明式事务处理中，这是AOP和IoC模块集成的部分。至于具体的事务处理实现，比如事务的生成、提交、回滚、挂起等，由于不同的底层的数据库有不同的实现方式，因此，在Spring事务处理中，对主要的事务实现做了一个抽象和适配。适配的具体事务处理器包括：对DataSource数据源的事务处理支持，对Hibernate数据源的事务处理支持，对JDO数据源的事务处理支持，对JPA和JTA等数据源的事务处理支持等。这一系列的事务处理支持，都是通过设计PlatformTransactionManager、AbstractPlatformTransactionManager以及一系列具体事务处理器来实现的，而PlatformTransactionManager又实现了TransationInterceptor接口，通过这样一个接口实现设计，就把这一系列事务处理的实现与前面提到的TransationProxyFactoryBean结合起来，从而形成了一个声明式事务处理的设计体系。

##### Spring事务设计原理

在使用Spring声明式事务处理的时候，一种常用的方法是结合IoC容器和Spring已有的TransationProxyFactoryBean对事务管理进行配置，比如，可以这个在TransationProxyFactoryBean中为事务方法配置传播行为、并发事务隔离级别等事务处理属性，从而对声明式事务的处理进行指导。声明式事务的处理实现大致分为以下几部分：

- a. 读取和配置在IoC容器中配置的事务处理属性，并转化为Spring事务处理需要的内部数据结构。具体来说，这里涉及的类是TransactionAttributeSourceAdvisor，从名字看出，它是一个AOP通知器，Spring使用这个通知器来完成对事务属性值的处理，处理结果是，在IoC容器中配置的事务处理属性信息，会被读取并转化成TransactionAttribute表示的数据对象，这个对象是Spring对事务处理属性值的数据抽象，对这些属性的处理是和TransationProxyFactoryBean拦截下来的事务方法的处理结合起来的。
- b. Spring事务处理模块实现统一的事务处理过程。这个通用的事务处理过程包含处理事务配置属性，以及与线程绑定完成事务处理的过程，Spring通过TransationInfo和TransationStatus这两个数据对象,在事务处理过程中记录和传递相关执行场景。
- c. 底层的事务处理实现。对于底层的事务操作，Spring委托给具体的事务处理器来完成，这些具体的事务处理器，就是在IoC容器中配置声明式事务处理时，配置的PlatformTransactionManager的具体实现，比如DataSourceTransactionManager和HibernateTransactionManager等。

##### Spring事务处理器的设计与实现

AbstractPlatformTransactionManager是事务管理器的基类，AbstractPlatformTransactionManager封装了Spring事务处理中的通用部分，比如事务的创建、提交、回滚、事务状态和信息的处理、与线程的绑定等，有了这些通用处理的支持，对于具体的事务管理器而言，它们只需要处理和具体数据源相关的组件设置就可以了，比如在HibernateTransactionManager，就只需配置好和Hibernate处理事务的接口以及相关的设置。所以从这个类设计关系上，我们也可以看到，Spring事务处理的主要过程是分两个部分完成的，通用的事务处理框架是在AbstractPlatformManager中完成，而Spring的事务接口与数据源实现的接口，多半是由具体的事务管理器来完成，它们都是作为AbstractPlatformTransactionManager的子类来使用的。
在PlatformTransactionManager的设计中，通过PlatformTransactionManager设计了一系列与事务息息相关的接口方法，如getConnection、commit、rollback这些和事务处理相关的统一接口。对于这些接口的实现，很大一部分是由AbstractPlatformTransactionManager来完成的。

##### Spring事务管理器的类设计

![](https://img-blog.csdnimg.cn/20190410153724103.png)

在DataSourceTransactionManager中，在事务开始的时候，会调用doBegin方法，首先会得到相应的Connection，然后可以根据事务设置的需要，对Connection的属性进行设置，比如将Connection的autoCommit功能关闭，将timeoutInSeconds这样的事务参数进行设置，最后通过TransactionSynchronizationManager来对资源进行绑定。

![](https://img-blog.csdnimg.cn/20190410153757239.png)

在HibernateTransactionManager中，调用doBegin方法后，会打开一个Session，Hibernate通过这个Session来管理数据对象的生命周期，通过它可以得到Hibernate的Transaction，并对Transaction参数进行设置，这些设置包括像timeout这样的参数，然后就可以启动Transaction，并最终通过TransactionSynchronizationManager来绑定资源。

![](https://img-blog.csdnimg.cn/2019041015382466.png)



### Spring事务配置

##### 开启声明式事务(配置文件)

![](https://img-blog.csdnimg.cn/20190410153900984.png)

##### tx:method的属性详解

<font color="#FF0000">name </font> 是必须的, 表示与事务属性关联的方法名(业务方法名),对切入点进行细化。通配符（*）可以用来指定一批关联到相同的事务属性的方法。如：'get*'、'handle*'、'on*Event'等等.
<font color="#FF0000">propagation</font>  不是必须的 ，默认值是REQUIRED：表示事务传播行为, 包括REQUIRED, SUPPORTS,MANDATORY, REQUIRES_NEW, NOT_SUPPORTED, NEVER, NESTED
<font color="#FF0000">isolation</font> 不是必须的默认值DEFAULT ：表示事务隔离级别(数据库的隔离级别) 
<font color="#FF0000">timeout</font>  不是必须的默认值-1(永不超时)：表示事务超时的时间（以秒为单位） 
<font color="#FF0000">read-only</font> 不是必须的默认值false不是只读的 ：表示事务是否只读？ 
<font color="#FF0000">rollback-for</font> 不是必须的：表示将被触发进行回滚的 Exception(s)；以逗号分开。如：'com.foo.MyBusinessException,ServletException' 
<font color="#FF0000">no-rollback-for</font> 不是必须的：表示不被触发进行回滚的 Exception(s)；以逗号分开。如：'com.foo.MyBusinessException,ServletException'，任何 RuntimeException 将触发事务回滚，但是任何checked Exception 将不触发事务回滚

##### 开启声明式事务(注解)

![](https://img-blog.csdnimg.cn/20190410154109987.png)

![](https://img-blog.csdnimg.cn/20190410154115759.png)

