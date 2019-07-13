# JavaScript基础

[TOC]



### 函数和事件

```
<!DOCTYPE html>
<html>
<head>
    <script src="/jquery/jquery-1.11.1.min.js"></script>
    <script type="text/javascript">
        function myFunction(val) {
            document.getElementById("demo").innerHTML = val;
        }
    </script>
</head>
<body>
    <p id="demo">默认段落</p>
    <button type="button" onclick="myFunction('段落已修改')">点击这里</button>
</body>
</html>
```
< script>< /script>中就是JS脚本

上述代码指在点击时触发myFunction函数

通常，我们需要在某个事件发生时执行代码，比如当用户点击按钮时。
如果我们把 JavaScript 代码放入函数中，就可以在事件发生时调用该函数。
使用函数时，可以发送任意多的参数，由逗号 (,) 分隔：myFunction(argument1,argument2)
声明函数时，请把参数作为变量来声明，变量和参数必须以一致的顺序出现。

可以在 HTML 文档中放入不限数量的脚本。
脚本可位于 HTML 的 <body> 或 <head> 部分中，或者同时存在于两个部分中。
我们通常的做法是把函数放在页面底部。这样就可以把它们安置到同一处位置，不会干扰页面的内容

### 作用域
局部变量
```
// 此处不能调用 carName 变量
function myFunction() {
    var carName = "Volvo";
    // 函数内可调用 carName 变量
}
```
全局变量
```
var carName = " Volvo";
// 此处可调用 carName 变量
function myFunction() {
    // 函数内可调用 carName 变量
}
```
- 函数参数只在函数内起作用，是局部变量。
- 如果变量在函数内没有声明（没有使用 var 关键字），该变量为全局变量。
- 在 HTML 中, 全局变量是 window 对象: 所有数据变量都属于 window 对象。

### 变量
var x=5,y=6,z=x+y;

JavaScript 变量可用于存放值（比如 x=5）和表达式（比如 z=x+y）。

变量可以使用短名称（比如 x 和 y），也可以使用描述性更好的名称（比如 age, sum, totalvolume）。
变量可以以字母开头
变量也能以 $ 和 _ 符号开头（不推荐）
变量名称对大小写敏感（y 和 Y 是不同的变量）

### 数据类型
值类型（基本类型）：
- 字符串（String）
- 数字（Number）
- 布尔（Boolean）
- 对空（Null）
- 未定义（Undefined）
- Symbol（不常用）

引用数据类型：
- 对象(Object)
- 数组(Array)
- 函数(Function)

JavaScript 动态类型
```
	var x;              // x 为 undefined
	x = 5;              // 现在 x 为数字
    x = "John";         // 现在 x 为字符串
    x = 5 + "John";     // 现在 x 为字符串
```

对象：var person = {firstname:"John", lastname:"Doe", id:5566};
var name=person.lastname;
var name=person["lastname"];

数组：var persons=["Saab","Volvo","BMW"];
```
	var persons=[
           {firstname:"John", lastname:"Doe", id:5566},
           {firstname:"tom", lastname:"Doe", id:5577}
    ];
```
```
    var family={
        firstnames:["Saab","Volvo","BMW"],
        lastname:"Doe"
    };
```

### 数组创建
```
var BAT = ['Alibaba', 'Tencent', 'Baidu'];
var students = [{name : 'Bosn', age : 27}, {name : 'Nunnly', age : 3}];
var arr = ['Nunnly', 'is', 'big', 'keng', 'B', 123, true, null];
var arrInArr = [[1, 2], [3, 4, 5]];
var commasArr1 = [1, , 2]; 				// 1, undefined, 2
var commasArr2 = [,,];	 				// undefined * 3
var arr = new Array(); 
var arrWithLength = new Array(100); 	// undefined * 100
var arrLikesLiteral = new Array(true, false, null, 1, 2, "hi");		// 等价于[true, false, null, 1, 2, "hi"];
```

### 数组读写
```
var arr = [1, 2, 3, 4, 5];
arr[1]; 		// 2
arr.length; 	// 5
arr[5] = 6;
arr.length; 	// 6
delete arr[0];
arr[0]; 		// undefined
```

### 数组的length属性
```
var arr = [1, 2, 3, 4, 5]
arr.length = 2;
arr; 		// [1, 2];
```

### 数组元素增删
```
var arr = [];
arr[0] = 1;
arr[1] = 2;
arr.push(3);
arr; 					// [1, 2, 3]

arr[arr.length] = 4; 	// equal to arr.push(4);
arr; 					// [1, 2, 3, 4]

arr.unshift(0);
arr; 					// [0, 1, 2, 3, 4];
delete arr[2];
arr; 					// [0, 1, undefined, 3, 4]
arr.length; 			// 5
2 in arr; 				// false

arr.length -= 1;
arr; 					// [0, 1, undefined, 3, 4],  4 is removed

arr.pop(); 				// 3 returned by pop
arr; 					// [0, 1, undefined], 3 is removed

arr.shift(); 			// 0 returned by shift
arr; 					// [1, undefined]
```

### 数组迭代
```
var i = 0, n = 10;
var arr = [1, 2, 3, 4, 5];
for (; i < n; i++) {
    console.log(arr[i]); 		// 1, 2, 3, 4, 5
}

for(i in arr) {
    console.log(arr[i]); 		// 1, 2, 3, 4, 5
}

Array.prototype.x = 'inherited';

for(i in arr) {
    console.log(arr[i]); 		// 1, 2, 3, 4, 5, inherited
}

for(i in arr) {
    if (arr.hasOwnProperty(i)) {
        console.log(arr[i]); 	// 1, 2, 3, 4, 5
    }
}
```

### 二维数组
```
var arr = [[0, 1], [2, 3], [4, 5]];
for (var i = 0; i < arr.length; i++) {
     var row = arr[i];
     console.log('下标为 ' + i);
     for (var j = 0; j < row.length; j++) {
          console.log(row[j]);
     }
}
```
结果：
```
下标为 0
0
1
下标为 1
2
3
下标为 2
4
5
```

### 数组方法介绍
```
Array.prototype.join
Array.prototype.reverse
Array.prototype.sort
Array.prototype.concat
Array.prototype.slice
Array.prototype.splice
Array.prototype.forEach
Array.prototype.map
Array.prototype.filter
Array.prototype.every
Array.prototype.some
Array.prototype.reduce/reduceRight
Array.prototype.indexOf/lastIndexOf
Array.isArray
```
[详细请看](https://www.cnblogs.com/bosnma/p/4261243.html#section-6)

### 事件
HTML 事件是发生在 HTML 元素上的事情。
当在 HTML 页面中使用 JavaScript 时， JavaScript 可以触发这些事件。

下面是一些常见的HTML事件的列表:
事件     | 类型
|:--------:|:-------------|
|onchange|HTML元素改变|
|onclick|用户点击HTML元素|
|onmouseover|用在一个HTML元素上移动鼠标|
|onmouseout|用户从一个HTML元素上移开鼠标|
|onkeydown|用户按下键盘按键|
|onload|浏览器已完成页面的加载|


### jQuery
jQuery 是一个 JavaScript 库。
jQuery 极大地简化了 JavaScript 编程。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190402140715255.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1p5aE1lbW9yeQ==,size_16,color_FFFFFF,t_70)
| 语法                     | 描述                                               |
| :----------------------- | :------------------------------------------------- |
| $("*")                   | 选取所有元素                                       |
| $(this)                  | 选取当前HTML元素                                   |
| $("p.intro")             | 选取class为intro的< p>元素                         |
| $("p:first")             | 选取第一个< p>元素                                 |
| $("ul li:first")         | 选取第一个< ul>元素的第一个< li>元素               |
| $("ul li:first-child”)   | 选取每个< ul>元素的第一个< li>元素                 |
| $("[href]")              | 选取带有href属性的元素                             |
| $("a[target='_blank']")  | 选取所有target属性等于'_blank'的< a>元素           |
| $("a[target!='_blank']") | 选取所有target属性不等于'_blank'的< a>元素         |
| $(":button")             | 选取所有type="button"的< inpet>元素和< button>元素 |
| $("tr:even")             | 选取偶数位置的< tr>元素                            |
| $("tr:odd")              | 选取奇数位置的< tr>元素                            |

### jQuery事件函数
| Event 函数                      | 绑定函数至                                     |
| :------------------------------ | :--------------------------------------------- |
| $(document).ready(function)     | 将函数绑定到文档的就绪时间（当文档完成加载时） |
| $(selector).click(function)     | 触发或将函数绑定到被选元素的点击事件           |
| $(selector).dblclick(function)  | 触发或将函数绑定到被选元素的双击事件           |
| $(selector).focus(function)     | 触发或将函数绑定到被选元素的获得焦点事件       |
| $(selector).mouseover(function) | 触发或将函数绑定到被选元素的鼠标悬停事件       |

为了易于维护，一般我们将 jQuery 函数放到独立的自定义 .js 文件中，但页面要引入js函数文件，一定
要先引入jquery插件，如下：
< script type="text/javascript" src="jquery.js">< /script>
< script type="text/javascript" src="my_jquery_functions.js">< /script>

### jQuery效果
[隐藏和显示](http://www.w3school.com.cn/jquery/jquery_hide_show.asp)
[淡入淡出](http://www.w3school.com.cn/jquery/jquery_fade.asp)
[滑动](http://www.w3school.com.cn/jquery/jquery_slide.asp)
[动画](http://www.w3school.com.cn/jquery/jquery_animate.asp)
[停止动画](http://www.w3school.com.cn/jquery/jquery_stop.asp)
[Callback 函数](http://www.w3school.com.cn/jquery/jquery_callback.asp)
[Chaining](http://www.w3school.com.cn/jquery/jquery_chaining.asp)

### jQuery AJAX
AJAX 是一种在无需重新加载整个网页的情况下，能够更新部分网页的技术。
通过 jQuery AJAX 方法，您能够使用 HTTP Get 和 HTTP Post 从远程服务器上请求文本、HTML、XML 
或 JSON - 同时您能够把这些外部数据直接载入网页的被选元素中。

以下列出了几种常用的 jQuery AJAX 方法：
| 方法        | 描述                                           |
| :---------- | :--------------------------------------------- |
| $.ajax()    | 执行异步 AJAX 请求                             |
| $.get()     | 使用 AJAX 的 HTTP GET 请求从服务器加载数据     |
| $.getJSON() | 使用 HTTP GET 请求从服务器加载 JSON 编码的数据 |
| $.post()    | 使用 AJAX 的 HTTP POST 请求从服务器加载数据    |

HTTP 请求：GET / POST   两种在客户端和服务器端进行请求-响应的常用方法
- GET - 从指定的资源请求数据
- POST - 向指定的资源提交要处理的数据
- GET 基本上用于从服务器获得（取回）数据。注释：GET 方法可能返回缓存数据。
- POST 也可用于从服务器获取数据。不过，POST 方法不会缓存数据，并且常用于连同请求一起发送数据。

```
$.ajax({
            type: "GET",
            url: "test.json",
            data: { username: $("#username").val(), content: $("#content").val() },
            dataType: "json",
            success: function (data) {
                $('#resText').empty();   //清空resText里面的所有内容
                var html = '';
                $.each(data, function (commentIndex, comment) {
                    html += '<div class="comment"><h6>' + comment['username'] + ':</h6>
                        < p class="para"' + comment['content'] + '</p ></div > ';
                });
                $('#resText').html(html);
            }
        });
```

```
$.get("test.json",
    {
        username:$("#username").val(),
        content:$("#content").val()
    },function(data,status,xhr){
        //data - 包含来自请求的结果数据
        //status - 包含请求的状态（"success"、"notmodified"、"error"、"timeout"、"parsererror"）
    },"json")
```

###  [jQuery手册](http://jquery.cuishifeng.cn/)