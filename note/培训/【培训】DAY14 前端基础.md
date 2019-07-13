# 前端基础

[TOC]

# HTML
HTML：网页的内容

### 结构
< !DOCTYPE html>:不是 HTML 标签，声明浏览器页面使用的哪个 HTML 版本进行编写；
< html>< /html>:紧跟<!DOCTYPE html>之后，成对出现， 限定了文档的开始点和结束点；
< head>< /head>:用于定义文档的头部，紧跟在 < html> 后面，并处于 < body> 标签之前，可包含标签< title>, < meta>, < link>, < style>,< script> 
< body>< /body>:用于定义文档的主要内容，在此定义的标签会在浏览器中显示出来。
< script>< /script>:成对出现，用于定义客户端脚本，script 元素既可以包含脚本语句，也可以通过 src 属性指向外部脚本文件；标签可以放在head中也可放在body中，根据具体情况调整位置，通常我们放到body结束标签之前。

### 区块
HTML区块通过< div>和< span>将元素组合起来。大多数 HTML 元素被定义为块级元素或内联（行内）元素。
##### 块级元素：
< h1>~< h6>, < p>, < ul>, < table>,< div>……
特点：会以新行来开始（和结束）

##### 内联元素：
< b>, < td>, < a>, < img>,< span>......
特点：显示时通常不会以新行开始

##### div 元素:
1. < div> 元素是块级元素，在浏览器显示中单独占据一行。
2. < div> 元素没有特定的含义，可用于组合其他 HTML 元素的容器。
3. 与 CSS 一同使用，< div> 元素可作为最外层的包裹元素对大块内容设置样式属性。
4. 取代了使用表格定义布局的老式方法，用于文档布局中。

##### span 元素:
1. < span> 元素是内联元素，在浏览器显示中不会以新行开始。
2. < span> 元素也没有特定的含义，可用作文本的容器。
3. 与 CSS 一同使用，< span> 元素可用于为部分文本设置样式属性。

### 标签、属性、元素
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190401133244255.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1p5aE1lbW9yeQ==,size_16,color_FFFFFF,t_70) 

### 常用元素
注释：< !-- 在此处写注释 -->
图像：< img width=”100” height=”100” src="url" />
链接：< a href="url">Link text< /a>
表格：[例子](http://www.w3school.com.cn/tiy/t.asp?f=html_tables)
输入框：[例子](http://www.w3school.com.cn/tiy/t.asp?f=html_input_type_button)
下拉框：[例子](http://www.w3school.com.cn/tiy/t.asp?f=html_select)
表单：[例子](http://www.w3school.com.cn/tiy/t.asp?f=html_form_submit_id)

### 类
##### html设置类的作用：
1. 为元素的类定义 CSS 样式 
2. 为相同的类设置相同的样式，或者为不同的类设置不同的样式。

##### 分类块级元素:
```
<div class="test">
    <h3>1</h3>
    <p>
        test 1
    </p>
</div>

<div class="test">
    <h3>2</h3>
    <p>
        test 2
    </p>
</div>
```
两个div都设置了一个叫test的类，那么 .test设置样式的时候，则这两块div都会应用上相同的样式。

##### 分类行内元素:
```
	<p>
        test 1 <span class="red">test 2</span> test 3 <span class="red"> test 4 </span> test 5
    </p>
```
为两个span元素设置了一个叫red的类，如果给span.red {color:red;}，那么span包裹的两个文本信息颜色就会变成红色

# CSS
css：网页的布局
### 初识
![在这里插入图片描述](https://img-blog.csdnimg.cn/2019040113484171.png)
CSS 规则由两个主要的部分构成：**选择器**，以及一条或多条声明。每条声明由一个属性和一个值组成。

##### 元素选择器：
```
html{
	background-color:#000000;
	font-family: "Microsoft Yahei";
	font-size:14px;
}
```

##### 类选择器：

```
<div class="test">test</div>
```

```
.test{
	width:100px;
	height:100px;
	line-height:25px;
}
```


 ##### ID选择器：
```
 < div id="test">test</ div>
```
```
#test{
	margin-left:10px;
	padding:10px 5px 20px 15px;
}
```

##### 属性选择器：
```
<input type=”checkbox”><input type=”text”>
```

```
input[type=”text”]{
	border:1px solid #ddd;
}
```

##### 后代选择器：
```
<ul><li>测试</li></ul>
```
```
ul li{
	font-weight:bold;
}
```

##### 子元素选择器：
```
<div><b>是红色</b></div>
<div><h1><b>不是红色</b></h1></div>
```
```
div > b{
	color:red;
}
```

### 盒子模型
CSS将每个元素看成是一个矩形盒子，占据一定空间。 
盒子模型两方面理解：
1. 独立的盒子内部结构；
2. 多个盒子之间的相互关系。

##### 概念
独立盒子模型由：内容、border、padding、margin四部分组成。详见之前的文章内容。 
盒子的实际宽（高）= 内容（width/height）+padding（两边）+border（两边）+margin（两边） 
width和height指内容的宽高。

##### 关系
有标准流模式、“浮动”属性、“定位”属性。

### 盒子关系1（标准流）
标准文档流/标准流：指不使用一些特殊的排列和定位的css样式。

- 块级元素： 
  在父元素中会自动换行，且跟同级的兄弟元素按照出现的顺序依次垂直排列，宽度自动铺满父元素宽度。
- 行内元素： 
  - 在父元素中水平排列，直到父元素的最右端才自动换行。 
  - 他们本身不占据单独区域，仅仅是在其他元素的基础上指出一定范围。
  - 块级元素可以包含行内元素和块级元素，反过来不行。
  - 行内元素设置的width和height无效，设置margin和padding只有左右边距有效。

### 盒子关系2（浮动）
盒子的浮动：通过设置块级元素float属性，可使元素“浮动”。
- float默认值为none。还有left，right。 
- 设置浮动后，盒子的宽高也会有改变。要清除浮动。也就是：（块级元素设置为浮动后，将脱离“标准流”，但还占据着父元素的空间，父元素的高度不再受浮动的子元素的影响，而由没浮动的其他子元素高度确定。） 
- 浮动可使多个块级元素水平排列。
```
<div class="father">
    <div class="son">浮动区块1</div>
    <div class="son">浮动区块2</div>
    <div class="clear"></div>
</div>
...
.son{
    float:left;
    margin-left:10px;
    width:100px;
    height:100px;
    border:1px solid #ddd;
}
.clear{clear:both;}
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190401140338205.png)

### 盒子关系3（定位）
盒子的定位：除了可以使用float，还可以使用position定位。

position默认值为static，表示元素在标准流中的默认位置，无任何效果。还有absolute，relative，fixed。

##### relative(相对定位)：
生成相对定位的元素，相对于其正常位置进行定位，水平方向left、right，垂直方向top、bottom
```
<h2>这是位于正常位置的标题</h2>
<h2 class="pos_left">这个标题相对于其正常位置向左移动</h2>
<h2 class="pos_right">这个标题相对于其正常位置向右移动</h2>
...
h2.pos_left{position:relative;left:-20px}
h2.pos_right{position:relative;left:20px}
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190401140443518.png)

##### absolute(绝对定位)：
生成绝对定位的元素，相对于最近带有position:relative的父层进行定位，如果没有，将会以< body>为父级（参考级）进行绝对定位，配合使用"left", "top", "right" ,"bottom"
```
<div class="pos-rel">
  <span class="pos-abs">这是带有绝对定位的元素</span>
</div>
...
.pos-rel{position:relative;width:200px;height:100px;
background-color:#ff6600;}
.pos-abs{position:absolute;left:20px;bottom:10px}
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190401140538668.png)

##### fixed(固定定位)：
生成绝对定位的元素，相对于浏览器窗口进行定位。
元素的位置通过 "left", "top", "right" 以及 "bottom" 属性进行规定。

### 样式
```
color : #999999; /*文字颜色*/
font-family : "Microsoft Yahei",arial;/*文字字体*/
font-size : 14px; /*文字大小*/
line-height : 200%; /*设置行高*/
font-weight:bold; /*文字粗体*/
text-decoration:line-through; /*加删除线*overline加顶线*underline加下划线*none删除链接下划线*/
text-align:right; /*right文字右对齐*left文字左对齐*center文字居中对齐*/
```
[更多请点这里](http://www.mamicode.com/info-detail-2171628.html)

### 清除浮动
##### 方式一：在结尾处加上空div，样式设置为clear:both
```
<div> 
  <div class="left">Left</div> 
  <div class="right">Right</div>
  <div class="clear"></div>
</div>
...
.left{float:left;}
.right{float:right;}
.clear{clear:both;}
```
- 原理：添加一个空div，利用css提高的clear:both清除浮动，让父级div能自动获取到高度
- 优点：简单，代码少，浏览器支持好，不容易出现怪问题
- 缺点：不少初学者不理解原理；如果页面浮动布局多，就要增加很多空div，让人感觉很不爽
- 建议：不推荐使用，但此方法是以前主要使用的一种清除浮动方法

 ##### 方式二：使用伪元素来清除浮动(after意思:后来,以后)
```
.clearfix:after{
    content:"";//设置内容为空
    height:0;//高度为0
    line-height:0;//行高为0
    display:block;//将文本转为块级元素
    visibility:hidden;//将元素隐藏
    clear:both//清除浮动
}
.clearfix{zoom:1;为了兼容IE}
```
- 原理：IE8以上和非IE浏览器才支持:after，原理和方法2有点类似，zoom(IE转有属性)可解决ie6,ie7浮动问题
- 优点：简单，代码少，浏览器支持好，只需要定义一个类，需要清除浮动的地方可以直接引用该类名
- 缺点：当after伪元素需要设置样式时不能使用
- 建议：推荐使用，此方法是当前主要使用的一种清除浮动方法