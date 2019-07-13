# JUnit测试 & Assert 断言

[TOC]



### JUnit 测试介绍
JUnit 是由 Erich Gamma 和 Kent Beck 编写的一个测试框架。

开源软件，[官网](https://junit.org/junit4/)

支持语言有Smalltalk、C++、Perl、Java等。

支持的IDE：JBuilder、VisualAge、Eclipse、IDEA等。

JUnit测试是程序员测试，即所谓白盒测试，因为程序员知道被测试的软件如何（How）完成功能和完成什么样（What）的功能。


### 相关概念
- 白盒测试
  把测试对象看作一个打开的盒子，程序内部的逻辑结构和其他信息对测试人员是公开的。
- 回归测试
  软件或环境的修复或更正后的“再测试”，自动测试工具对这类测试尤其有用。
- 单元测试
  是最小粒度的测试，以测试某个功能或代码块。一般由程序员来做，因为它需要知道内部程序设计和编码的细节。
- JUnit
  是一个开放源代码的Java测试框架，用于编写和运行可重复的测试。它是用于单元测试框架体系xUnit的一个实例（用于java语言）。主要用于白盒测试、回归测试。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190403142138533.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1p5aE1lbW9yeQ==,size_16,color_FFFFFF,t_70)
- 项目测试中单元测试是最小粒度的测试
- Unit testing（单元测试）：针对每一个单独的代码块进行测试。
- Integration testing（集成测试）：将多个模块结合在一起进行测试，确保多个组件可以正确交互。
- Functional testing（功能测试）：测试应用程序的全部的完整的功能。
- Stress/Load testing（压力/负载测试）：压力测试是模拟实际应用的软硬件环境及用户使用过程的系统负荷，长时间或超大负荷地运行测试软件，来测试被测系统的性能、可靠性、稳定性等。
- Acceptance testing（验收测试）：QA或者业务方对系统进行验收测试，以确保功能满足用户的要求。


目前的最流行的单元测试工具是xUnit系列框架，常用的根据语言的不同分为：

- JUnit（java）
- CppUnit（C ）
- DUnit （Delphi ）
- NUnit（.net）
- PhpUnit（Php ）
- 等等


##### 单元测试的好处
- 提高开发速度
  测试是以自动化方式执行的，提升了测试代码的执行效率。
- 提高软件代码质量
  它使用小版本发布至集成，便于实现人员除错。同时引入重构概念，让代码更干净和富有弹性。
- 提升系统的可信赖度
  它是回归测试的一种。支持修复或更正后的“再测试”，可确保代码的正确性。

单元测试不仅仅是保证代码在方法级别的正确性，它还能改进设计，易于对代码重构。经验证明，凡是容易编写单元测试的代码，往往是优秀的设计和松耦合的组件，凡是难于编写单元测试的代码，往往是设计不佳和耦合度高的系统，因此，编写单元测试不仅仅是掌握单元测试框架的用法，更重要的是在编写单元测试的过程中发现设计缺陷，改进系统结构，从而实现良好的可扩展性。

### JUnit4 的使用
##### 导入测试包
```
import org.junit.*
import static org.junit.Assert.*;
```
- org.junit.* ; 把junit包import进来之后，绝大部分功能就有了。 
- import static org.junit.Assert.*; 我们在测试的时候使用的一系列assertEquals等方法就来自这个包。

注：assertEquals是Assert类中的一系列的静态方法，一般的使用方式是Assert. assertEquals()，但是使用了静态包含后，前面的类名就可以省略了，使用起来更加的方便。

##### 测试类的申明
```
	public class CalculatorTest{}
```
- 测试类是一个独立的类，没有任何父类。测试类的名字也可以任意命名，没有任何局限性。但是建议都用   类名+Test    来命名。
- 我们不能通过类的声明来判断它是不是一个测试类，它与普通类的区别在于它内部的方法的声明。 

 注：单元测试代码必须写在如下工程目录：src/test/java，不允许写在业务代码目录下，源码构建时会跳过此目录，而单元测试框架默认是扫描此目录。

##### 创建一个待测试的对象
```
	private static Calculator calculator = new Calculator();
```
- 你要测试哪个类，那么你首先就要创建一个该类的对象。
- 为了测试Calculator类，我们必须创建一个calculator对象。

##### 测试方法的声明
- 在测试类中，并不是每一个方法都是用于测试的，你必须使用“标注”来明确表明哪些是测试方法。 
  @Before、@After、@Test、@Ignore 、@BeforeClass、@AfterClass

##### 编写一个简单的测试方法
- 使用@Test标注，以表明这是一个测试方法。
- 方法名字可以随便取，没有任何限制，但是规范写法是test+方法名，方法名第一个子母大写。
- 方法的返回值必须为void。
- 方法不能有任何参数。
- 如果违反这些规定，运行时会抛出一个异常。
```
	@Test
	public void testAdd(){
		assertEquals(6, calculate.add(3, 3));
	}
```

##### 忽略测试某些尚未完成的方法 
```
	@Ignore("Not yet implemented")
	public void testIgnore(){}
```
- 加上@Ignore标注的含义就是“某些方法尚未完成，暂不参与此次测试”。
- 这样的话测试结果就会提示你有几个测试被忽略，而不是失败 。

##### Fixture “固定代码段”
```
	@Before
    public void setUp() throws Exception {
        System.out.println("This is Before...");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("This is After...");
    }
```
- @Before，@After是每个方法测试时候必然被调用的代码。 
- 保证每一个测试都是独立的，相互之间没有任何耦合度。
- 这里不需要@Test标注，因为这不是一个test 。
- @BeforeClass ：只在测试用例初始化时执行@BeforeClass方法。
- @AfterClass：当所有测试执行完毕之后，执行@AfterClass进行收尾工作 。
- 用于测试读取文件等耗时的方法。
- 该方法必须是public和static的 。
- 执行顺序：@BeforeClass -> @Before -> @Test -> @After -> @AfterClass

##### 限时测试
```
	@Test(timeout = 1000)
    public void testSquareRoot(int n) {
        for (; ; ) {}
    }
```
- 对于那些逻辑很复杂，循环嵌套比较深的程序采用限时测试。
- 函数设定一个执行时间，超过了这个时间，他们就会被系统强行终止，并且系统还会向你汇报该函数结束的原因是因为超时。

##### 测试异常
```
	@Test(expected = ArithmeticException.class)
    public void divideByZero(int n) {
        calculator.divide(0);
    }
```
- expected属性用来测试函数应该抛出的异常

##### Runner (运行器)
- JUnit中有一个默认Runner，如果你没有指定，那么系统自动使用默认Runner来运行你的代码。
- @RunWith是用来修饰类 ,可以指定需要的Runner。

##### 参数化测试
可以将所有参数集中起来一起测试，步骤如下：
- 你要为这种测试专门生成一个新的类
- 定义一个待测试的类，并且定义两个变量，一个用于存放参数，一个用于存放期待的结果
- 定义测试数据的集合 ，用@Parameters标注进行修饰
- 创建构造函数对先前定义的两个参数进行初始化
```
@RunWith(Parameterized.class)
public class SquareTest {

    private static Calculator calculator = new Calculator();
    private int param;
    private int result;

    @Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][]{
            {2, 4},
            {0, 0},
            {-3, 9},
        });
    }

    // 构造函数，对变量进行初始化
    public SquareTest(int param, int result) {
        this.param = param;
        this.result = result;
    }

    @Test
    public void square() {
        calculator.square(param);
        assertEquals(result, calculator.getResult());
    }
}
```

##### 打包测试
```
@RunWith(Suite.class)
@Suite.SuiteClasses({
    CalculatorTest.class,
    SquareTest.class
})
public class Tests {}
```
步骤：
- 用@RunWith标注传递一个参数Suite.class。
- 用@Suite.SuiteClasses表明这个类是一个打包测试类。
- 把需要打包的类作为参数传递给该标注。

### JUnit4 执行结果示例
成功显示为绿色；失败显示为红色
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190403191101849.png)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190403190936298.png)

### JUnit 4.x 与 3.x 的区别
- 测试类不必再从 junit.framework.TestCase 派生了；
- setUp与tearDown也不再强制使用这两个方法名了，只要在任何方法名称前冠以@Before或@After，即可达到一样的效果；
- 测试方法也不必再以“test”作为前缀，而是代之以 @Test 注解来表示；
- 对setUp/tearDown的一大改进还包括，可以限定二者只在整个 test case 范围内执行一次，这是通过@BeforeClass和@AfterClass注解达成的；
- @Test 注解还可以带上timeout 参数和expected参数，前者代表测试方法超过指定时间即被认为失败，后者则声明了预期被抛出的异常类型。
  ![在这里插入图片描述](https://img-blog.csdnimg.cn/2019040319123056.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1p5aE1lbW9yeQ==,size_16,color_FFFFFF,t_70)
### MOCK工具介绍
##### MOCK工具
- MockMvc:实现了对Http请求的模拟，能够直接使用网络的形式，转换到Controller的调用，这样可以使得测试速度快、不依赖网络环境，而且提供了一套验证的工具，这样可以使得请求的验证统一而且很方便。
- Mockito:在单元测试中，模拟对象可以模拟复杂的、真实的（非模拟）对象的行为， 如果真实的对象无法放入单元测试中，使用模拟对象就很有帮助。
- 其他Mock 框架

##### MockMvc测试逻辑
-  MockMvcBuilder构造MockMvc的构造器
-  mockMvc调用perform，执行一个RequestBuilder请求，调用controller的业务处理逻辑
-  perform返回ResultActions，返回操作结果，通过ResultActions，提供了统一的验证方式
-  使用StatusResultMatchers对请求结果进行验证
-  使用ContentResultMatchers对请求返回的内容进行验证

##### Mockito测试逻辑
- 模拟任何外部依赖并将这些模拟对象插入测试代码中
- 执行测试中的代码
- 验证代码是否按照预期执行
  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20190403191525769.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1p5aE1lbW9yeQ==,size_16,color_FFFFFF,t_70)
##### 编写单元测试
- 建立单元测试类
- controller层单元测试编写
- ao层单元测试编写
- service层单元测试编写

###### controller层单元测试编写
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190403200943454.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1p5aE1lbW9yeQ==,size_16,color_FFFFFF,t_70)
###### ao层单元测试编写
![在这里插入图片描述](https://img-blog.csdnimg.cn/2019040320100953.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1p5aE1lbW9yeQ==,size_16,color_FFFFFF,t_70)

### Assert断言介绍
- Java 的 Assert 断言。
- JUnit 的 Assert 断言。

##### 概述
- 在防御式编程中经常会用断言（Assertion）对参数和环境做出判断，避免程序因不当的输入或错误的环境而产生逻辑异常，断言在很多语言中都存在，C、C++、Python都有不同的断言表示形式。
- 在Java中的断言使用的是assert关键字（自JAVA SE 1.4 引入），用法和含义都差不多。

##### 语法
- assert  <布尔表达式> 
  如果<布尔表达式>为true，则程序继续执行。
  如果为false，则程序抛出java.lang.AssertionError异常，并终止执行。
- assert  <布尔表达式> : <错误信息> 
  如果<布尔表达式>为true，则程序继续执行。
  如果为false，则程序抛出java.lang.AssertionError异常，并输出<错误信息>。

##### 说明
- assert默认是不启用的 
  断言是为调试程序服务的，目的是为了能够快速、方便地检查到程序异常，但Java在默认条件下是不启用的，要启用就需要在编译、运行时加上相关的关键字。
- assert抛出的异常AssertionError是继承自Error的 
  断言失败后，JVM会抛出一个AssertionError错误，它继承自Error，注意，这是一个错误，是不可恢复的，也就表示这是一个严重问题，开发者必须予以关注并解决。
- assert 不能等同于 if ... else 判断
  assert的判断和if语句差不多，但两者的作用有着本质的区别：assert关键字本意上是为测试 调试程序时使用的，但如果不小心用assert来控制了程序的业务流程，那在测试调试结束后去掉assert关键字就意味着修改了程序的正常的逻辑。
  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20190403201305349.png)

##### 使用说明
1. 开启 assert 断言
  - 使用 -enableassertions 或者 -ea 来开启断言。
    java [ -enableassertions | -ea  ] [:< package name>"..." | :< class name> ]
    例：java -ea Foo
  - DE中可以通过在 JVM option 设定 -ea 来开启。

2. 关闭 assert 断言
    - 使用 -disableassertions 或者 -da 来关闭断言。
      java [ -disableassertions | -da ] [:< package name>"..." | :< class name> ]

3. assert 是不具有继承性的
  如果开启父类的assert，则运行到子类的assert方法时，子类是默认不开启的。
  反之如果开启子类的assert，运行到父类的assert方法时，父类的assert也是不开启的。

##### -ea 和-da的详细说明
-ea和-da可以有效的指向到类和包路径的某一级中，使得可以更加灵活的控制assert的有效性。具体的使用如下：
| -ea\/-da | 详细说明                                  |
| :------- | :---------------------------------------- |
| -ea      | java -ea 打开所有用户类的断言             |
| -da      | java -da 关闭所有用户类的断言             |
| -ea:     | java -ea:MyClass1 打开MyClass1的断言      |
| -da:     | java -da: MyClass1 关闭MyClass1的断言     |
| -ea:     | java -ea:pkg1 打开pkg1包的断言            |
| -da:     | java -da:pkg1 关闭pkg1包的断言            |
| -ea:...  | java -ea:... 打开缺省包(无名包)的断言     |
| -da:...  | java -da:... 关闭缺省包(无名包)的断言     |
| -ea:...  | java -ea:pkg1... 打开pkg1包和其子包的断言 |
| -da:...  | java -da:pkg1... 关闭pkg1包和其子包的断言 |
| -esa     | java -esa 打开系统类的断言                |
| -dsa     | java -dsa 关闭系统类的断言                |

##### JUnit 中的 assert 方法全部放在Assert类中
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190403202600875.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1p5aE1lbW9yeQ==,size_16,color_FFFFFF,t_70)
##### assertTrue 与 assertFalse 断言
assertTrue与assertFalse可以判断某个条件是真还是假，如果和预期的值相同则测试成功，否则将失败。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190403202618232.png)

##### assertEquals 断言
比较实际的值和用户预期的值是否一样。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190403202646637.png)

##### assertNull 与 assertNotNull 断言
assertNull与assertNotNull可以验证所测试的对象是否为空或不为空，如果和预期的相同则测试成功，否则测试失败。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190403202658360.png)

##### assertSame 与 assertNotSame 断言
- assertSame 测试预期的值和实际的值是否为同一个参数(即判断是否为相同的引用)。
- assertNotSame 则测试预期的值和实际的值是不为同一个参数。
- assertSame 和 assertEquals 不同，assertEquals 是判断两个值是否相等，通过对象的equals方法比较，可以相同引用的对象，也可以不同。
  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20190403202721491.png)

##### fail 断言
“fail”断言能使测试立即失败，这种断言通常用于标记某个不应该被到达的分支。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190403202733314.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1p5aE1lbW9yeQ==,size_16,color_FFFFFF,t_70)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190403202740427.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1p5aE1lbW9yeQ==,size_16,color_FFFFFF,t_70)

##### assertThat 断言
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190403202803510.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1p5aE1lbW9yeQ==,size_16,color_FFFFFF,t_70)