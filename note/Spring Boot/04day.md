

# 配置文件占位符

### 1、随机数

```java
${random.value}、${random.int}、${random.long}、${random.int(10)}、${random.int[2048, 65536]}
```

### 2、占位符获取之前配置的值，如果没有可以用：指定默认值

```properties
person.last-name=zxc_${random.uuid}
person.age=${random.int}
person.birth=2000/2/2
person.boss=false
person.maps.k1=11
person.maps.k2=22
person.lists=a,b,c
person.dog.name=${person.last-name:Hello}_dog
person.dog.age=3
```



