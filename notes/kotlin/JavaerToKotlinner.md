# 面向Javaer的Kotlin指南

> 对比介绍一下java的语法在kotlin中如何去实现，以及kotlin中一些额外的语法

## 变量和方法

变量的声明

```java
String str = "java"; // var str = "kotlin" 或 var str: String = "kotlin"
```

在kotlin中，大多数地方的 ";" 都是可以省略的，并且声明变量时，若指明了值也是可以推断出来的。

kotlin中还有一种写法：

```kotlin
val str = "kotlin" // 对应的就为Java中 final String str = "kotlin";
```

kotlin和java有一个很大的区别就是非空检测，你需要在申明时指定这个变量是否可以为空的，若为非空的变量则不可以对其进行空的赋值。

```kotlin
var nullable: String? = null
var notnull: String = "kotlin"
```

在使用时可为空的变量需要进行非空判断

```kotlin
nullable?.equals(null)
```

