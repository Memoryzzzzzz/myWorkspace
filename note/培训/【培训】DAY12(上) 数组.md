# 数组

[TOC]

# 一维数组
### 数组变量
```
int[] a;    // 推荐方法
int b[];    // C 写法
```

### 创建数组
```
int[] c = new int[4];
```
#### 注意：不同于List()，数据创建即占据内存空间
```
	int[] data = new int[4];
	System.out.println(data.length);

	List<String> dataList = new ArrayList<>();
	System.out.println(dataList.size());
	dataList.add("dataList");
	System.out.println(dataList.size());
	dataList.remove(0);
	System.out.println(dataList.size());
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190328152404820.png)

### 初始化
##### 动态初始化
```
		int[] c = new int[4];

        c[0] = 1;
        c[1] = 2;
        c[2] = 3;
        c[3] = 4;
```

##### 静态初始化
```
int[] d = {1, 2, 3, 4};
```
##### 注意：不支持混搭风

### 数组引用
#### 数组下标越界的情况（ArrayIndexOutOfBoundsException）
```
int[] d = {1, 2, 3, 4};
System.out.println(d[4]);
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190328153414150.png)
原因：数组的下标从0开始，范围是0~n-1

#### 数组的长度（length）
```
int[] d = {1, 2, 3, 4};
System.out.println(d.length);
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190328153750722.png)
可以通过.length获取数组的长度
.length-1就是最大下标值，也是该数组的最后一个值的下标
而List()是通过.size()方法获取，要区分

#### 注意：栈内存当中方法只是一个地址，实际的东西是放在堆内存当中的
#### 引用
```
		int[] d = {1, 2, 3, 4};
        int[] e = new int[5];

        e = d;
        System.out.println(e.length);

        e[4] = 8;
        System.out.println(e.length);
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190328160603359.png)
这里的数组e = 数组d，指的是数组e指向了数组d的堆内存，故数组e的长度就变成了4，而再往这个数组e下标为4存值就存不进去了，返回数组下标越界的错误。

### 数组的使用
#### 遍历
##### 方法一
```
		int[] d = {1, 2, 3, 4};

        for (int i = 0; i < d.length; i++) {
            System.out.print(d[i]);
        }
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190328161156176.png)
##### 方法二

```
		int[] d = {1, 2, 3, 4};

        for (int i : d) {
            System.out.print(i + "");
        }
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190328161305615.png)

#### 数组的排序
##### 冒泡排序
- 比较相邻的元素。如果第一个比第二个大，就交换它们两个；
- 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对，这样在最后的元素应该会是最大的数；
- 针对所有的元素重复以上的步骤，除了最后一个；
- 重复步骤1~3，直到排序完成。

```
	public static void bubbleSort() {
        int[] arr = {88, 11, 44, 22, 99, 33, 77, 55, 66};
        for (int i = 0; i < arr.length - 1; i++) {
            boolean judge = false;
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {        // 相邻元素两两对比
                    int temp = arr[j + 1];        // 元素交换
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                    judge = true;
                }
            }
            if (!judge){
                break;
            }
        }
        System.out.println(Arrays.toString(arr));
    }
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190328162001506.png)
##### 选择排序
- 初始状态：无序区为R[1..n]，有序区为空；
- 第i趟排序(i=1,2,3…n-1)开始时，当前有序区和无序区分别为R[1..i-1]和R(i..n）。该趟排序从当前无序区中-选出关键字最小的记录 R[k]，将它与无序区的第1个记录R交换，使R[1..i]和R[i+1..n)分别变为记录个数增加1个的新有序区和记录个数减少1个的新无序区；
- n-1趟结束，数组有序化了。

```
	public static void selectionSort() {
        int[] arr = {88, 11, 44, 22, 99, 33, 77, 55, 66};
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {     // 寻找最小的数
                    minIndex = j;                 // 将最小数的索引保存
                }
            }
            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
        System.out.println(Arrays.toString(arr));
    }
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190328162701162.png)
##### 插入排序
- 从第一个元素开始，该元素可以认为已经被排序；
- 取出下一个元素，在已经排序的元素序列中从后向前扫描；
- 如果该元素（已排序）大于新元素，将该元素移到下一位置；
- 重复步骤3，直到找到已排序的元素小于或者等于新元素的位置；
- 将新元素插入到该位置后；
- 重复步骤2~5。
```
	public static void insertionSort() {
        int[] arr = {88, 11, 44, 22, 99, 33, 77, 55, 66};
        for (int i = 1; i < arr.length; i++) {
            int preIndex = i - 1;
            int current = arr[i];
            while (preIndex >= 0 && arr[preIndex] > current) {
                arr[preIndex + 1] = arr[preIndex];
                preIndex--;
            }
            arr[preIndex + 1] = current;
        }

        System.out.println(Arrays.toString(arr));
    }
```

第二种

```
		int[] arr = {88, 11, 44, 22, 99, 33, 77, 55, 66};
		int aa = 0;
        int bb = 0;
        for (int i=1; i< array.length; i++) {
            int j = i;
            while (j >=1 && array[j]<array[j-1]) {
                int a = array[j];
                array[j] = array[j-1];
                array[j-1] = a;
                j--;
                aa++;
            }
            bb++;
        }
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190328162657783.png)
##### 其他请看[十大经典排序算法](https://www.cnblogs.com/onepixel/p/7674659.html)

#### 数组的查找
##### 二分查找算法
- 首先确定整个查找区间的中间位置 mid = （ left + right ）/ 2
- 用待查关键字值与中间位置的关键字值进行比较；
  - 若相等，则查找成功
  - 若大于，则在后（右）半个区域继续进行折半查找
  - 若小于，则在前（左）半个区域继续进行折半查找
- 对确定的缩小区域再按折半公式，重复上述步骤。
- 最后，得到结果：要么查找成功， 要么查找失败。

```
	public static void main(String[] args) {
        int srcArray[] = {11, 22, 33, 44, 55, 66, 77, 88, 99};
        System.out.println(binSearch(srcArray, 0, srcArray.length - 1, 88));
    }

	private static int binSearch(int srcArray[], int start, int end, int key) {
        if (start > end) {
            return -1;
        }
        int mid = (end - start) / 2 + start;
        if (srcArray[mid] == key) {
            return mid;
        } else if (key > srcArray[mid]) {
            return binSearch(srcArray, mid + 1, end, key);
        } else if (key < srcArray[mid]) {
            return binSearch(srcArray, start, mid - 1, key);
        }
        return -1;
    }
```

### Arrays类
java.util.Arrays 类能方便地操作数组，它提供的所有方法都是静态的

#### 填充数组 fill()
将指定的 int 值分配给指定 int 型数组指定范围中的每个元素。同样的方法适用于所有的其他基本数据类型（Byte，short，Int等）

```
	public static void main(String[] args) {
        // 给数组所有值批量赋值为2
        int[] arr = new int[5];
        Arrays.fill(arr,3);
        System.out.println(Arrays.toString(arr));

        // 给第1位到第4位赋值8（注：下标是从0开始，4不包括）
        int[] arr2 = new int[5];
        Arrays.fill(arr2,1,4,8);
        System.out.println(Arrays.toString(arr2));
    }
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190328171239861.png)

#### 数组元素排序 sort()
对指定对象数组根据其元素的自然顺序进行升序排列。同样的方法适用于所有的其他基本数据类型（Byte，short，Int等）

```
	public static void main(String[] args) {
        // 给所有数按照升序排列
        int[] arr = {88, 11, 44, 22, 99, 33, 77, 55, 66};
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));

        // 给第3位到第6位排序（注：下标是从0开始，4不包括）
        int[] arr2 = {88, 11, 44, 22, 99, 33, 77, 55, 66};
        Arrays.sort(arr2,3,6);
        System.out.println(Arrays.toString(arr2));
    }
```

#### 比较数组元素是否相等 equals()
如果两个指定的数组彼此相等，则返回 true。如果两个数组包含相同数量的元素，并且两个数组中的所有相应元素对都是相等的，则认为这两个数组是相等的。
换句话说，如果两个数组以相同顺序包含相同的元素，则两个数组是相等的。同样的方法适用于所有的其他基本数据类型（Byte，short，Int等）

```
		public static void main(String[] args) {
        int[] arr = {1, 2, 3};
        int[] arr2 = {1, 2, 3};
        System.out.println(Arrays.equals(arr, arr2));
    }
```
##### 注意：这里比较的是俩个地址而不是里面的数值！

# 二维数组
### 声明数组
```
		int a[][];
        int[][] b;
        int[] c [];
```

### 创建数组
```
		int datas[][] = new int[3][];
        datas[0] = new int[6];
        datas[1] = new int[8];
        datas[2] = new int[5];
```

### 初始化
##### 动态初始化
```
		int datas[][] = new int[3][];
        datas[0] = new int[6];
        datas[1] = new int[8];
        datas[2] = new int[5];
```

##### 静态初始化
```
int data[][] = {{1,2,3},{4,5,6,7,8},{9,10}};
```

### 数组元素的访问

```
		int data[][] = {{1,2,3},{4,5,6,7,8},{9,10}};
        System.out.println("第一行第三个的值：" + data[0][2]);
        System.out.println("第二行第二个的值：" + data[1][1]);
        System.out.println("第三行第一个的值：" + data[2][0]);
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190328190925570.png)

### 二维数组的应用
##### 二维数组的长度（.[下标].length）

```
		int data[][] = {{1,2,3},{4,5,6,7,8},{9,10}};
        System.out.println(data.length);
        System.out.println(data[1].length);
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190328191227190.png)

##### 二维数组的遍历（俩个for循环）

```
		int data[][] = {{1,2,3},{4,5,6,7,8},{9,10}};
		for(int i = 0; i < data.length; i++){
            for(int j = 0; j < data[i].length; j++){
                System.out.print(data[i][j] + " ");
            }
            System.out.println();
        }
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/2019032819152727.png)

### 杨辉三角
```
	public static void main(String[] args) {
        // 根据行数定义好二维数组，由于每一行的元素不同，所以设置动态初始化
        int[][] arr = new int[5][];
        // 遍历二维数组
        for(int i = 0; i < arr.length; i++){
            // 初始化每一行的一维数组
            arr[i] = new int[i+1];
            // 遍历这个一维数组，添加元素
            for(int j = 0; j <= i; j++){
                // 每一列的开头和结尾元素为1，开头的时候，j = 0,结尾的时候，j = i
                if(j == 0 || j == i){
                    arr[i][j] = 1;
                } else {
                    // 每一个元素是它上一行的元素和斜对角元素之和
                    arr[i][j] = arr[i-1][j] + arr[i - 1][j - 1];
                }
                System.out.print(arr[i][j] + "\t");
            }
            System.out.println();
        }
    }
```