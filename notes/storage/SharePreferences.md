#### 获取SharedPreferences对象
要想使用SharedPrefernces储存数据，首先要获取SharedPreferences对象。主要获取方式有以下两种

- Context类中的getSharedPreferences()方法
```kotlin
//第一个参数为储存的文件名，第二个参数是操作模式，现只有一种
val edit : SharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE)
```
- Activity类中的getPreferences()方法

```kotlin
//默认储存的文件名为Activity的类名
val edit : SharedPreferences = getPreferences(Context.MODE_PRIVATE)
```
SharedPrefernces文件都是存放在/data/data/<package name>/shared_prefs/目录下的

#### 写入数据

```kotlin
//获取操作对象
val edit : SharedPreferences.Editor = getSharedPreferences("data", Context.MODE_PRIVATE).edit()
//设置键值对
edit.putString("name","jiangker")
//原子操作，无返回值，先同步写入到内存，异步写入到磁盘，效率高，但写入是阻塞操作，线程安全
edit.apply()
//原子操作，有返回值，都是同步的操作
//edit.commit()
```
#### 读取数据

```kotlin
val sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE)
//第一个参数为查找的key，第二个参数为查找失败的返回值
val name = sharedPreferences.getString("name", "admin")
```
