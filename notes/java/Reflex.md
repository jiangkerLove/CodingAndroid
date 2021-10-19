#### 获取构造函数

```kotlin
// 获取public的构造函数
val constructors = typeB.constructors
// 获取所有构造函数
val declaredConstructors = typeB.declaredConstructors
```

 

#### 方法

```kotlin
//  获取所有public方法，包括其父类的public方法
c1.methods
//  获取所有访问权限的方法，但不包括父类的方法
c1.declaredMethods
// 获取指定名字的方法
val method = c1.getDeclaredMethod("printSelf")
// 调用方法，若是静态方法则传入空即可
method.invoke(b)
```



#### 成员变量

```kotlin
// 获取此类的public变量
c1.fields
// 获取此类的所有成员变量，不包括父类的
c1.declaredFields
val filed = c1.superclass.getDeclaredField("name")
// 设置不对访问权限进行校验
filed.isAccessible = true
// 若是静态属性，第一个值传入null
filed.set(b, "jiang")
assert(b.name == "jiang")
```



#### 注解

```kotlin
// 类上的注解
c1.annotations
// 包括继承下来的注解，需要注解有@Inherited属性
c1.declaredAnnotations
```

