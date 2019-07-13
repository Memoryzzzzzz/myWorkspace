# Java基础

[TOC]

# Java 版本史

|       版本       | 更新内容                                                     |
| :--------------: | :----------------------------------------------------------- |
|     JDK 1.0      | 核心API/集成API/用户界面API/Java虚拟机(JVM)/AWT              |
|     JDK 1.1      | 引入JDBC/引入Java Bean/引入反射                              |
| J2SE 1.2 里程碑  | 改名为J2SE,引入集合包(ArrayList, HashMap)/Java 插件/JIT（Just In Time）即时编译 |
|     J2SE 1.3     | 引入Java Sound API/Jar文件索引                               |
|     J2SE 1.4     | 引入断言/Java打印服务/支持IPv6/支持正则表达式                |
| JavaSE5.0 里程碑 | 改名为JavaSE5.0, 引入泛型/枚举/For-Each循环/注解/迭代循环/可变参数/线程池包 |
|    JavaSE6.0     | 支持脚本语言/可插拔注解/继承Web Services                     |
|    JavaSE7.0     | Switch中可用String作为分支条件/一个语句块中捕获多种异常/支持动态语言 |
|    JavaSE8.0     | 增强日期时间API功能/垃圾回收性能改进/重复注解扩展注解的支持/支持Lambda 表达式 |
|    JavaSE9.0     | 集合工厂方法/轻量级的 JSON API/改进的 Stream API             |
|    JavaSE10.0    | 应用程序数据共享/JDK 提供一组默认证书/将 JDK 的多个存储库合并 |

# Java的特点

### 简单易用

1. Java的风格类似于C ++ ，因此可以快速掌握Java编程技术。
2. Java摒弃了C ++ 中指针、结构以及内存管理，实现自动垃圾收集。 
3. Java提供了丰富的类库。
4. Java源代码可以用多种文本编辑器软件来实现，并将源文件进行编译后直接运行。

### 面向对象

 Java便是一种面向对象的语言，也继承了面向对象的诸多好处，如代码扩展、代码复用等,且支持继承、重载、多态等面向对象的特性。使得应用程序的开发变得简单易用，节省代码。

### 跨平台性

 Java自带的虚拟机很好地实现了跨平台性。Java源程序代码经过编译后生成二进制的字节码是与平台无关的，是可被Java虚拟机识别的一种机器码指令。Java虚拟机提供了一个字节码到底层硬件平台及操作系统的屏障，使得Java语言具备跨平台性。

### 多线程

Java语言支持多线程的程序设计，使应用程序能够并行执行 ,同步机制也保证了对共享数据的共享操作，而且线程具有优先级的机制，有助于分别使用不同线程完成特定的行为，也就很容易地实现网络上的实时交互行为。

### 垃圾自动回收

Java自己负责内存管理，提供了自动垃圾内存回收机制，有效的避免了C++中的内存泄漏问题。对象的创建和放置都是在内存堆栈上面进行的。当一个对象没有任何引用的时候，Java的自动垃圾收集机制自动删除这个对象所占用的空间，释放内存以避免内存泄漏。

### 安全性

Java程序代码要经过代码校验、指针校验等测试步骤才能运行，避免了非法内存操作，未经允许的java程序不可能出现损害系统平台的行为，Java的安全架构也能确保恶意的代码不能随意访问我们本地计算机的资源, 另外使用java可以编写防病毒和防修改的系统。

### 动态性

Java允许程序动态地装入运行过程中所需要的类，使它适合于一个不断发展的环境。在类库中可以自由地加入新的方法和实例变量而不会影响用户程序的执行。并且 Java通过接口来支持多重继承 ,使之比严格的类继承具有更灵活的方式和扩展性。

# Java的编程环境

### JDK

JDK(Java Development Kit) 是 Java 语言的软件开发工具包，是Java开发环境。
JDK目录下面有四个重要文件夹：

- bin: 最主要的是编译器(javac.exe)         
- include: java和JVM交互用的头文件
- lib: 类库 (java.lang基础类/IO输入输出/URL网络/集合类/数据库类等)         
- jre: java运行环境

### JRE

JRE（Java Runtime Environment，Java运行环境）， JRE是Java运行环境，包含JVM标准实现及Java核心类库。不含任何开发工具（如编译器和调试器）。

### JVM

JVM（Java Virtual Machine  Java虚拟机）JVM是一种用于计算设备的规范，它是一个虚构出来的计算机，是通过在实际的计算机上仿真模拟各种计算机功能来实现的。
jre目录里面有两个文件夹bin和lib,在这里可以认为bin里的就是jvm，lib中则是jvm运行所需要的类库，而jvm和lib和起来就称为jre。JVM+Lib=JRE。

### 三者的关系

1. 利用JDK开发Java程序后，通过编译程序（javac）将文本java文件编译成Java字节码。
2. 在JRE上运行这些Java字节码。
3. JVM解析这些字节码，映射到CPU指令集或OS的系统调用。

JDK = JRE+Java工具（javac/java等）+Java开发类库Lib
JRE = JVM + Java运行类库Lib

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190329163439424.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1p5aE1lbW9yeQ==,size_16,color_FFFFFF,t_70)

# Java常用工具

### Eclipse（免费开源）

Eclipse是由IBM开发的跨平台的自由集成开发环境（IDE）。最初主要用来Java语言开发，它只是一个框架和一组服务，用于通过插件组件构建开发环境。
Eclipse的本身只是一个框架平台，许多软件开发商以Eclipse为框架开发自己的IDE。Eclipse允许在同一IDE中集成来自不同供应商的工具，并实现了工具之间的互操作性。

### MyEclipse（商用收费）

MyEclipse是Eclipse的插件，是一款功能强大的J2EE集成开发环境，由Genuitec公司发布，它是商用收费的。
MyEclipse 是对Eclipse IDE的扩展，利用它可以在数据库和JavaEE的开发、发布以及应用程序服务器的整合方面极大的提高工作效率。

### NetBeans（免费）

NetBeans是Sun公司(2009年被Oracle收购)推出的IDE集成开发环境。NetBeans包括开源的开发环境和应用平台，NetBeans IDE可以使开发人员利用Java平台能够快速创建Web、企业、桌面以及移动的应用程序。

### IntelliJ IDEA

Intellij IDEA是一款综合的Java 编程环境，被誉为市场上最好的IDE。它提供了一系列最实用的工具组合：智能编码辅助和自动控制，支持J2EE，Ant，JUnit和CVS集成，非平行的编码检查和创新的GUI设计器。IDEA能够显著地提高使用者的开发效率。

### 四者特点

- Swing开发：
  从事Swing开发，NetBeans无疑是最好的选择，如果你没有选择NetBeans而是选择了Eclipse，建议使用MyEclipse 和它的Matisse4Eclipse构造器。IDEA由于对JGoodies Forms的支持，所以在开发Swing方面与前两者相比并不占有优势。
- JSP/Struts开发：
  开发JSP/Struts首选的是IDEA，接下来是MyEclipse，然后是NetBeans，由于缺少对内建Struts的支持，Eclipse的基本版本没有竞争优势。
- JSF开发：
  在开发JSF方面选择IDEA会更好，接下来是Eclipse / MyEclipse，最后是NetBeans。
- 企业开发：
  企业开发方面，NetBeans是首选，接下来是IDEA，由于Eclipse的Dali项目的限制，Eclipse可能是最后选择的。如果放弃标准的JPA，从事Hibernate开发，MyEclipse是最好的选择。

### Java 基础语法

##### Java数据类型划分

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190329163941543.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1p5aE1lbW9yeQ==,size_16,color_FFFFFF,t_70)

##### 基本数据类型

Java编程语言定义了八种基本的数据类型，共分为四类：整数类(byte、short、int、long)、文本类(char)、浮点类(double、float)和逻辑类(boolean)。
默认转换：byte,short,char—int—long—float—double
byte,short,char相互之间不转换，他们参与运算首先转换为int类型
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190329164027884.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1p5aE1lbW9yeQ==,size_16,color_FFFFFF,t_70)

1. 整数类(byte、short、int、long)
   (1) 具有缺省int, 
   (2) 用字母“L”和“l”定义long。
   (3) 所有Java编程语言中的整数类型都是带符号的数字。
   (4) byte: 8位/-128~127   short: 16位/-32768 ~ 32767	  
   int(常用): 32位/-2^31~2^31-1 (21亿)     long(次常用): 64位/-2^63~2^63-1 
2. 文本类(char)
   (1) 代表一个16 bit Unicode字符, 0~2^16-1（65535）。
    (2) 必须包含用单引号(' ')引用的文字。
3. 浮点类(float、double)
       默认为double类型，如果一个数字包括小数点或指数部分，或者在数字后带有字母F或f(float 32位)、D或d(double 64位)。
4. 逻辑类
       boolean数据类型有两种值：true和false, 默认为false。例如：boolean flag = true。

##### 常量与变量

常量是指整个运行过程中不再发生变化的量，例如数学中的π= 3.1415……，在程序中需要设置成常量。

变量是指程序的运行过程中发生变化的量，通常用来存储中间结果，或者输出临时值。变量的声明也指变量的创建。执行变量声明语句时，系统根据变量的数据类型在内存中开辟相应的存储空间并赋予初始值。变量有一个作用范围，超出它声明语句所在的块就无效。

成员变量（全局变量）和局部变量：
成员变量是在类内方法外定义，作用范围是整个类，可以不赋初始值，系统自动分配该类型的默认值。
局部变量是在方法内定义，作用范围是从定义他的地方开始，到他所定义的代码块结束的地方，必须赋初值。

##### 标识符和关键字

标识符 

- 用来标志类名、对象名、变量名、方法名、类型名、数组名、包名的有效字符序列；
- 标识符由字母、数字、下划线、美元符号组成，第一个字符不能是数字，且区分大小写；
  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20190329164152542.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1p5aE1lbW9yeQ==,size_16,color_FFFFFF,t_70)

关键字 

- 在java语言中，有一些专门的词汇已经被赋予了特殊的含义，不能再使用这些词汇来命名标识符，这些专有词汇，称为“关键字”；
- java有50个关键字和3个保留字(true、false、null)，均不能用来命名标识符；

##### 运算符和表达式

分为五类：算术运算符、赋值运算符、关系运算符、布尔逻辑运算符、位运算符。
表达式是由常量、变量、对象、方法调用和操作符组成的式子，根据运算符的不同，表达式相应地分为以下四类：算术表达式、关系表达式、逻辑表达式、赋值表达式。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190329164223121.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1p5aE1lbW9yeQ==,size_16,color_FFFFFF,t_70)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190329164229231.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1p5aE1lbW9yeQ==,size_16,color_FFFFFF,t_70)

##### 布尔逻辑运算符

下图布尔逻辑运算符及其规则示例等。其中简洁与和简洁或的执行结果分别与非简洁与和非简洁或的执行结果是一致的，不同在于简洁与检测出符号左端的值为假时，不再判断符号右端的值，直接将运算结果置为假；而简洁或与非简洁或的不同在于简洁或检测出符号左端为真时，不再判断符号右端的值，直接将运算结果置为真。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190329164243562.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1p5aE1lbW9yeQ==,size_16,color_FFFFFF,t_70)
右移运算符对应的表达式为x>>a，运算的结果是操作数x被2的a次方来除，左移运算符对应的表达式为x<<a，运算的结果是操作数x乘以2的a次方。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190329164257332.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1p5aE1lbW9yeQ==,size_16,color_FFFFFF,t_70)

##### 赋值运算符

赋值运算符分为简单运算符和复杂运算符。
简单运算符指“=”，而复杂运算符是指算术运算符、逻辑运算符、位运算符中的双目运算符后面再加上“=”。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190329164325600.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1p5aE1lbW9yeQ==,size_16,color_FFFFFF,t_70)

##### 三目运算符(?: )

相当于条件判断，表达式x?y:z用于判断x是否为真，如果为真，表达式的值为y，否则表达式的值为z。
例如：
int x = 5;
int a = (x>3)?5:3;
则a的值为5。如果x = 2，则a的值为3。

```
public class test {
	public static void main(String[] args) {
		int max = 0; 				// 定义变量保存最大值
		int x = 3; 				// 定义整型变量x
		int y = 10; 				// 定义整型变量y
		max = x > y ? x : y;			// 通过三目运算符求最大值
		System.out.println("最大值为：" + max);	// 输出求出的最大值
	}
}
```

##### 一维数组

数组声明格式如下：数据类型 标识符[],   数组下标默认从0开始。
 int a[]；//声明一个数据类型为整型的数组a
 pencil b[]；//声明一个数据类型为pencil类的数组b
数组是一个对象，因此可以使用关键字new来创建一个数组
例如：
a = new int[10]；//创建存储10个整型数据的数组a
b = new pencil[20]；//创建存储20个pencil类数据的数组b

##### 二维数组

一维数组相当于表格中的一行记录，二维数组相当于该表格。
其声明与分配内存的格式如下所示：
动态初始化：
数据类型 数组名[][] = new 数据类型[行的个数][列的个数] ；
int[][] arr = new int[3][5] 定义了一个整型的二维数组，包含3个一维数组，每个一维数组存储5个整数。

##### 流程控制

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190329164704577.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1p5aE1lbW9yeQ==,size_16,color_FFFFFF,t_70)

##### if语句

根据判断的结构来执行不同的语句时，使用if 语句就是一个很好的选择，它会准确地检测判断条件成立与否，再决定是否要执行后面的语句。
![在这里插入图片描述](https://img-blog.csdnimg.cn/2019032916474288.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1p5aE1lbW9yeQ==,size_16,color_FFFFFF,t_70)

##### if...else语句

当程序中存在含有分支的判断语句时，就可以用if...else语句处理。当判断条件成立，即执行if语句主体；判断条件不成立时，则会执行else后面的语句主体。if…else语句的格式如下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190329164844111.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1p5aE1lbW9yeQ==,size_16,color_FFFFFF,t_70)

##### if…else if…else语句

如果需要在if..else里判断多个条件时，就需要if..else if … else语句了，其格式如下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190329164924173.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1p5aE1lbW9yeQ==,size_16,color_FFFFFF,t_70)

##### switch语句

要在许多的选择条件中找到并执行其中一个符合判断条件的语句时，除了可以使用if..else不断地判断之外，也可以使用另一种更方便的方式即多重选择switch语句，语法格式：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190329165007399.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1p5aE1lbW9yeQ==,size_16,color_FFFFFF,t_70)

##### while循环

while是循环语句，也是条件判断语句。当事先不知道循环该执行多少次的时，就要用到while循环。while循环的格式如下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190329165040886.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1p5aE1lbW9yeQ==,size_16,color_FFFFFF,t_70)

##### do…while循环

do…while循环也是用于未知循环执行次数的时候，而while循环及do…while循环最大不同就是进入while循环前，while语句会先测试判断条件的真假，再决定是否执行循环主体，而do…while循环则是先执行一次循环主体，然后再测试判断条件的真假，所以无论循环成立的条件是什么，使用do…while循环时，至少都会执行一次循环主体，语句格式：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190329165106878.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1p5aE1lbW9yeQ==,size_16,color_FFFFFF,t_70)

##### for循环

对于while和do…while两种循环来讲，操作时并不一定非要明确的知道循环的次数，而如果开发者已经明确的知道了循环次数的话，那么就可以使用另外一种循环语句——for循环。 格式如下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190329165144247.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1p5aE1lbW9yeQ==,size_16,color_FFFFFF,t_70)

##### break语句

break语句可以强迫程序中断循环，当程序执行到break语句时，即会离开循环，继续执行循环外的下一个语句，如果break语句出现在嵌套循环中的内层循环，则break 语句只会跳出当前层的循环。以下图的for循环为例，在循环主体中有break语句时，当程序执行到break，即会离开循环主体，而继续执行循环外层的语句。 格式如下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190329165219296.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1p5aE1lbW9yeQ==,size_16,color_FFFFFF,t_70)

##### continue语句

continue语句可以强迫程序跳到循环的起始处，当程序运行到continue语句时，即会停止运行剩余的循环主体，而是回到循环的开始处继续运行。以下图的for循环为例，在循环主体中有continue语句，当程序执行到continue，即会回到循环的起点，继续执行循环主体的部分语句。格式如下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190329165249182.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1p5aE1lbW9yeQ==,size_16,color_FFFFFF,t_70)

# Java面向对象概述

面向对象是一种符合人类思维习惯的编程思想，具有两个概念，即类和对象。
具有共同特性的对象的抽象就称之为类，该对象用来映射类中具体的事物 。

### 类的定义和声明

类由类声明和类体构成，类体又由变量和方法构成。

##### 类声明的基本格式

访问说明符 class 类名 extends 超类名 implements 接口名

1. 访问说明符：public或者缺省。public用来声明该类为公有类。
2. 类名：用户自定义的标识符，用来标志这个类的引用。
3. 超类名：是指已经存在的类，可以是用户已经定义的，也可以是系统类。
4. 接口名：即后面讲到的接口。

例如 public class SaleOrder extends Order 访问说明符为public，类名SaleOrder，这是SaleOrder继承Order 类，可以使用Order的公用方法，也可以重写那个方法。

##### 类体

类体包括成员变量和方法。

1. 成员变量：指类的一些属性定义，标志类的静态特征，它的基本格式如下：
   访问说明符 数据类型 变量名
      其中：
      访问说明符有public、private和protected三种：
      public：省略时默认为公有类型，可以由外部对象进行访问。
      private：私有类型，只允许类内部的方法中使用，若从外部访问，必须通过构造函数间接进行。
      Protected：受保护类型，子类访问受到限制。 

    数据类型包括基本类型以及用户自定义的扩展类型。

2. 方法：是类的操作定义，标志类的动态特征，它的基本格式如下：
   访问说明符 数据类型 方法名(数据类型1 变量名1, 数据类型2 变量名2)
     其中：
     访问说明符为public、private和protected，其使用方法与成员变量访问说明符的使用方法一致。
     数据类型：包括基本数据类型和用户自定义的扩展类型。
     数据类型为参数。

### 对象的创建与使用

应用程序想要完成具体的功能，仅有类是远远不够的，还需要根据类创建实例对象。
在Java程序中可以使用new关键字来创建对象，具体格式如下：
	类名 对象名称 = new 类名();
	
例如，创建一个Person对象，具体示例如下：

Person p = new Person();

“new Person()”用于创建Person类的一个实例对象，“Person p”则是声明了一个Person类型的变量p。中间的等号用于将Person对象在内存中的地址赋值给变量p，这样变量p便持有了对象的引用。

### 类的单继承性

 Java编程语言中允许用extends关键字从一个类扩展出一个新类，新类继承超类的成员变量和方法，并可以覆盖方法。
继承是所有OOP语言不可缺少的部分，在java中使用extends关键字来表示继承关系。当创建一个类时，总是在继承，如果没有明确指出要继承的类，就总是隐式地从根类Object进行继承。
类Man继承于Person类，这样一来的话，Person类称为父类（基类），Man类称为子类（导出类）。如果两个类存在继承关系，则子类会自动继承父类的方法和变量，在子类中可以调用父类的方法和变量。
在java中，只允许单继承，也就是说 一个类最多只能显示地继承于一个父类。但是一个类却可以被多个类继承，也就是说一个类可以拥有多个子类。

##### 子类继承父类的成员变量

当子类继承了某个类之后，便可以使用父类中的成员变量，但是并不是完全继承父类的所有成员变量。具体的原则如下：

1. 能够继承父类的public和protected成员变量；不能够继承父类的private成员变量；
2. 对于子类可以继承的父类成员变量，如果在子类中出现了同名称的成员变量，则子类的成员	变量会屏蔽掉父类的同名成员变量。要想访问父类中同名成员变量，需使用super关键字。

##### 子类继承父类的方法

同样地，子类也并不是完全继承父类的所有方法。

1. 能够继承父类的public和protected成员方法；不能够继承父类的private成员方法；
2. 对于子类可以继承的父类成员方法，如果在子类中出现了同名称的成员方法，则子类的成员	方法会覆盖掉父类的同名成员方法。要想访问父类中同名成员方法，需要使用super关键字。

### 特殊变量

类中有两个特殊变量super和this。

##### super

类声明中用关键字extends扩展了其超类之后，super用在扩展类中引用其超类中的成员变量。
super.成员变量/super.成员方法;用来在子类中调用父类的同名成员变量或者方法

##### this

this变量指向当前对象或实例。
str = "名字: " + name ;
可以换成下面的语句。
str = "名字: " + this.name ;

在Java编程语言中，系统自动将this关键字与当前对象的变量相关联。
通过this关键字可以明确地去访问一个类的成员变量，解决与局部变量名称冲突的问题，示例如下：
构造方法的参数被定义为age，它是一个局部变量，在类中还定义了一个成员变量，名称也是age。在构造方法中如果使用“age”，则是访问局部变量，但如果使用“this.age”则是访问成员变量

### 构造方法的定义

在一个类中定义的方法如果同时满足以下三个条件，该方法称为构造方法，具体如下：

1. 方法名和类名相同
2. 方法名的前面没有返回值类型的声明
3. 方法中不能使用return语句返回一个值

在一个类中，除了可以定义无参的构造方法，也可以定义有参的构造方法，通过有参的构造方法可以实现对属性的赋值。

在一个类中可以定义多个构造方法，只要每个构造方法的参数类型或参数个数不同即可。

在Java中的每个类至少都有一个构造方法，如果在一个类中没有定义构造方法，系统会自动为这个类创建一个默认的构造方法，这个默认的构造方法没有参数，在其方法体中没有任何代码，即什么都不做。

当为某个类定义了有参数的构造方法后，这时系统就不再提供默认的构造方法。

### 包

计算机操作系统使用文件夹或者目录来存放相关或者同类的文档，在Java编程语言中，提供了一个包的概念来组织相关的类。包在物理上就是一个文件夹，逻辑上代表一个分类概念。
        
包就是指一组类。例如一个名叫Company的包，可以包含一组类，如Employee(雇员)、Manager(管理者)和Department(部门)等。声明包的基本格式如下：

Package 包名;

 其中：Package为关键字，包名为标识符。

使用包时的注意事项如下：

1. Package语句要作为程序非注释语句的第一行语句。
2. 包内的类名惟一。
3. 引用包中的类时，使用import语句。import语句的基本格式为import 包名.类名，其中import为关键字，包名和类名之间用圆点(.)隔开。

### 接口

Java编程语言中禁止多继承属性，但可以通过接口来帮助类扩展方法。接口中可以定义大量的常量和方法，但其中的方法只是一种声明，没有具体的实现，使用接口的类自己实现这些方法。接口与类的不同在于：
 (1) 没有变量的声明，但可以定义常量。
 (2) 只有方法的声明，没有方法的实现。

 接口声明的基本格式如下：

```
     public interface 接口名 extends 接口列表
```

测试接口，定义接口文件test.java，定义了两个常量，声明了一个方法。接口文件如下：

```
public interface test{
	static final String MAKER = "计算机";
	static final String ADDRESS = "上海";
	public int getPrice();	
}
```

使用接口的源文件代码如下：

```
public class UseTest{
       public static void main(String[] args)
      {
	   Computer c = new Computer();
	   System.out.print(c.ADDRESS + c.MAKER);
	   System.out.println(" 计算机的价格：" + p.getPrice()+ " 万元");
       }
}

class Computer implements Test{
	public int getPrice()
	{
		return 1;
	}
}
```

结果：上海计算机 计算机的价格: 1万元

# 代码规范

[阿里巴巴Java开发手册](https://yq.aliyun.com/download/2720?utm_content=m_1000019584)