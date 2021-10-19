## 1主要文件介绍

展开可以看到C和Java对应文件的位置，C中文件的注册主要是通过CMake文件来指定的
在CMakeLists.txt文件中
```c++
//引入cpp和h文件
add_library( 
	//第一个参数就是库的名称,在Java中引入时需要，也对应target_link_libraries的前一个参数
    native-lib
    SHARED
    //cpp文件的相对路径，可以为多个，在同一个library中注册了都才能相互引用
    native-lib.cpp)
```
在java引入的类中添加，用于引入上面定义的动态库

```java
static {
	//对应上面申明的名字
    System.loadLibrary("native-lib");
}
```
下面native-lib.cpp中默认生成的C方法
```c++
extern "C" JNIEXPORT jstring JNICALL
Java_com_ponovo_jnitest_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
```
这是JNI中注册方法的比较固定的写法，对应的是java的native方法，jstring为返回值类型，方法名由Java_包名_类名_方法名 构成，其中点由下划线表示，若方法或路径中存在下划线，则由阿拉伯数字1加下划线表示。然后参数前两个为固定的格式，其中JNIEnv相对于native世界中Java环境的代码，一切Java对象相关的操作都需要依赖于他，而native中的c语言是可以直接使用的。
## 2java与jni数据类型的对应关系
Java中的基础数据类型和String在jni中都有相应的对应，对象统一对应jobject

Java | Jni | Signature
---|---|---
byte | jbyte | B
char | jchar | C
double |jdouble | D
float | jfloat | F
int |jint| I
short |jshort| S
long |jlong |J
boolean| jboolean |Z
void| void |V
所有对象 jobject L路径;

```c++
Class jclass Ljava/lang/Class;
String jstring Ljava/lang/String;
Throwable jthrowable Ljava/lang/Throwable;
```

若为数组对象，基础数据类型签名是在前面前面加上[，而对象类型则全部都等同于jobject，例如
Java |Jni |Signature
---|---|---
int[]| jintArray| [I
String[]|jobjectArray|[Ljava/lang/String;
注意，对象签名为路径的，后面的分号是不能省略的，与java交互的部分，例如方法的参数和返回值等都需要用对应的jni类型，例如上面的native方法对应java中的为

```java
public native String stringFromJNI();
```
public native也是属于类似的固定字段，只需要关注方法特征即可
## 3字符串的相关操作
因为基本数据类型操作都一样的，string稍微特殊一点，所以主要介绍这个，java中的string默认是utf的，所以在把java在jni中jstring转化为c中的变量时，通常就需要使用带有UTF的那个方法，字符串相关方法如下
```c++
//获取字符串长度
jsize GetStringLength(jstring string)
//第二个参数接收一个jboolean指针，表示是拷贝一份到本地，还是直接指向Java的数组
const char* GetStringUTFChars(jstring string, jboolean* isCopy)

//这个方法是获取局部的字符串，start为起始位置，len为长度
void GetStringRegion(jstring str, jsize start, jsize len, jchar* buf)

//调用前面两个方法之后都需要调用这个来释放，因为操作是在native区域，就和c一样了
void ReleaseStringUTFChars(jstring string, const char* utf)

//若是C中的字符串要回传给Java，这时需要转化为jstring再使用
jstring NewStringUTF(const char* bytes)
```
可以发现jni中的很多对象都可以通过类似的New来生成的
## 4jni对java类的操作
Java的类主要可以分为成员变量和方法，Class包含着类所有的信息，所有要知道一个object对象的变量和方法首先需要获取他的Class信息，对应的就是jclass，可以通过jclass jcls = env->FindClass(“绝对路径”)的形式获取到
```java
public class StringTokObj{
	private String str1;
	String str2;
	public StringTokObj(String str){
		this.str1 = str;
	}
	public String getStr1(){
		return str1;
	}
}
```
例如获取StringTokObj的jclass对象就可以表示为

```c++
jclass jcls = env->FindClass("com/ponovo/jniwork/StringTokObj");
```
获取到jclass之后便可以通过这个对象去获取成员变量和方法的id

```c++
jfieldID GetFieldID(jclass clazz, const char* name, const char* sig)
```


上面的方法可以获取到对应成员的id，其中第一个参数为前面获取到的jclass，第二个参数为Java类中这个成员的name字符串，最后一个参数为前面表格中的签名字段
例如此处若要获取str1则为：

```c++
jfieldID str1Id = env->GetFieldID(strClass, "str1", "Ljava/lang/String;");
```
当获取到这个成员id时，我们便可以对相应对象的这个成员进行读取或赋值的操作了

```c++
//赋值
void SetObjectField(jobject obj, jfieldID fieldID, jobject value)
//读取：
jobject GetObjectField(jobject obj, jfieldID fieldID)
```

传入参数的jobject是对应的对象实例，因为是对实例对象的操作
注意，这里的因为是对应的String对象，所以使用的是object，需要对返回值强制转换为jstring来使用，而若成员变量为基础数据类型，都会有对应的方法，入参和返回值也为所选类型

```c++
//如果是基础类型int的话
jint GetIntField(jobject obj, jfieldID fieldID)
void SetIntField(jobject obj, jfieldID fieldID, jint value)
```


对应的获取方法也是类似的，

```c++
jmethodID GetMethodID(jclass clazz, const char* name, const char* sig)
```


首先第一个参数仍然和成员变量一样，而第二个参数因为有特殊的构造方法，所以若为构造方法时，则写为"<init>"（类似于字节码中构造函数一样，需要注意的是AS中这个是会报错，但是可以正常编译使用），其余方法都是按照方法名来写的，对于签名字段来说，因为是方法，所以方法的特征除了方法名还有就是入参以及返回值，所以签名的格式为" ()V",括号内为入参的签名，括号后是返回值的签名，若参数为空则不写，若返回值为空则写void对应的签名V，返回参数只能有一个，所以入参存在多个的时候就依次写入，例如
int add(int a,int b) 对应的签名就为" (II)I"（有点像kotlin的方法写法）
注意这里的签名里面是没有空格以及标点符号分割的，所以特殊对象的签名分号也不能省，不然就分辨不清楚了，例如

```c++
String add(String a,String b)
//签名-->
 "(Ljava/lang/String; Ljava/lang/String;) Ljava/lang/String;"
```
若获取到的是构造方法id，则可以用来构造此类的对象，这里一共有三个方法

```c++
//第三个参数为动态参数列表
jobject NewObject(jclass clazz, jmethodID methodID, ...)
```

这里的第三个参数为参数数组

```c++
jobject NewObjectA(jclass clazz, jmethodID methodID, const jvalue* args)
```


这里的第三个参数为指向列表的指针

```c++
jobject NewObjectV(jclass clazz, jmethodID methodID, va_list args)

//若为一般的方法，则对应的也有三种方法
void CallVoidMethod(jobject obj, jmethodID methodID, ...)
void CallVoidMethodV(jobject obj, jmethodID methodID, va_list args)
void CallVoidMethodA(jobject obj, jmethodID methodID, const jvalue* args)
```
需要注意的是，方法名中的Void也和之前对成员变量的操作类似，方法名中的中间字段对应了这个方法返回值的类型
//对于java中的静态的方法和成员，只需要使用对应的带有static参数的方法即可，例如：

```c++
jint GetStaticIntField(jclass clazz, jfieldID fieldID)
jfieldID GetStaticFieldID(jclass clazz, const char* name, const char* sig)
void SetStaticObjectField(jclass clazz, jfieldID fieldID, jobject value)
```
当我们需要保存一个传入的object对象以及对应的class之类的信息，就可以使用变量或者结构体来保存，比如

```c++
struct CallBackStruct {
    jobject obj;
    jclass clazz;
    jmethodID callBack;
    jmethodID callBackStr;
};
static CallBackStruct callBackStruct;
```


需要注意的是，Native方法返回的时候，本地的引用也会随之被JVM虚拟机给回收，所以对于对象类型的保存需要通过这个方法来进行转换为全局引用

```c++
jobject NewGlobalRef(jobject obj)
```


例如jobject对象以及jclass对象等，但一般的基本数据类型以及jfieldID，jmethodID是不需要转换的
当然，转化为全局引用之后以及不属于JVM管理了，所以引用也是需要手动释放的，调用

```c++
void DeleteGlobalRef(jobject globalRef)
```


## 5数组对象的使用
对于数组对象的操作，和上面表格一样，主要可以分为两类，一是基础数据类型，还有就是对象类型，基础数据类型都有对应的Array类型，而对象类型都是为jobjectArray类型
创建一个jintArray类型的数组，参数为数组长度

```c++
jintArray NewIntArray(jsize length)
```


获取数组长度都为

```c++
jsize GetArrayLength(jarray array)
```


获取数组元素首地址，

```c++
jint* GetIntArrayElements(jintArray array, jboolean* isCopy)
// 对应于上一个方法还有其对应的释放的方法，需要对应使用，
void ReleaseIntArrayElements(jintArray array, jint* elems,jint mode)
// mode = 0 
// 原始数据: 对象数组将不会被限制.
// 拷贝数据: 数据将会拷贝回原始数据, 同时释放拷贝数据.
// mode = JNI_COMMIT
// 原始数据: 什么都不作.
// 拷贝数据: 数据将会拷贝回原始数据, 不释放拷贝数据.
// mode = JNI_ABORT
// 原始数据: 对象数组将不会被限制, 之前的数据操作有效
// 拷贝数据: 释放拷贝数据, 之前的任何数据操作会丢弃.
```

从jint数组指针复制对应的元素到jintArray中

```c++
void SetIntArrayRegion(jintArray array, jsize start, jsize len, const jint* buf)
```


对应的获取jintArray的元素到jint数组中

```c++
void GetIntArrayRegion(jintArray array, jsize start, jsize len, jint* buf)
```


对于对象数组的操作

```c++
//创建对象数组，elementClass表示创建数组的类型，initialElement表示数组每个元素的初始化值
jobjectArray NewObjectArray(jsize length, jclass elementClass, jobject initialElement)

//获取对应位置的对象
jobject GetObjectArrayElement(jobjectArray array, jsize index)

//设置对应位置的对象
void SetObjectArrayElement(jobjectArray array, jsize index, jobject value)
```


## 6方法总结

总的来说，主要是通过Get方法来是java中传过来的数据转化为C中的数据，最后在返回的时候再生成java对应的jni数据类型
7动态注册
静态注册是根据方法名将Java方法和JNI函数建立关联的，在初次调用时需要建立native方法和jni方法的关联，影响效率
动态注册
Jni中有一个结构体定义了native方法与jni方法的关联关系，我们可以通过模仿系统中native方法的注册方式即可

```c++
typedef struct {
    const char* name; //java方法的name
    const char* signature; //方法参数和返回值类型
    void*       fnPtr; //jni中方法的签名
} JNINativeMethod;
```

当java native方法为

```java
public native String sayHello(long lo);
```


jni方法为

```c++
jstring sayHello(JNIEnv *env, jobject, jlong lo)
```


这时注册结构体时就可以写为

```c++
{"sayHello","(J)Ljava/lang/String;", (void *) sayHello}
```


这里的jni方法与java的方法名可以不同，方法签名也与前面获取Java方法的写法是一样的。
这里列出动态注册代码
基本的注册写在代码中的

```c++
#ifdef __cplusplus

extern "C" {
#endif


static const char *dynamicName = "com/ponovo/jniwork/utils/DynamicLib";

jstring sayHello(JNIEnv *env, jobject, jlong handle) {
    return env->NewStringUTF("Hello");
}
static const JNINativeMethod gJni_Methods_table[] = {
        {"sayHello",    "(J)Ljava/lang/String;",     (void *) sayHello},
};

// 注册的部分
jint jniRegisterNativeMethods(JNIEnv *env, const char *name,
                              const JNINativeMethod *methods, jint nMethods) {
    //找到这个类对应的Java的类
    jclass jcls = env->FindClass(name);
    if (jcls == nullptr) {
        return JNI_FALSE;
    }
    //注册为native方法的方法，
    //第一个是jclass，第二个是JNINativeMethod*，最后一个参数为方法个数
    //返回值若成功为JNI_OK值为0，其余所有状态都为小于0的，所以这样判断
    if ((env)->RegisterNatives(jcls, methods, nMethods) < 0) {
        return JNI_FALSE;
    }
    return JNI_TRUE;
}

//jni部分初始化会调用的方法
JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env;
    if (vm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_6) != JNI_OK) {
        return JNI_FALSE;
    }
    jniRegisterNativeMethods(env, dynamicName, gJni_Methods_table,
                             sizeof(gJni_Methods_table) / sizeof(JNINativeMethod));
    return JNI_VERSION_1_6;
}


#ifdef __cplusplus
}
#endif
```

## 8旧项目添加ndk支持
前面的介绍是重新创建的工程来实现的jni过程，若要给以前的项目增加jni功能，则需要添加相应的文件即可，主要的更改就为图中的两个地方

第一个为一个cpp目录以及cpp的文件，定义jni方法的，CMakeLists文件主要定义的是jni方法与java native方法之间的关系，
第二个地方是对应项目结构的build.gradle文件，不是主项目的这个文件，这个文件主要是配置编译的环境以及项目的依赖文件，主要新增在android下添加，表明CMake文件路径和使用的版本

```groovy
externalNativeBuild {
    cmake {
        path "src/main/cpp/CMakeLists.txt"
        version "3.10.2"
    }
}
```


，还可以指定C++使用的版本，在android下的defaultConfig中添加

```groovy
externalNativeBuild {
    cmake {
        cppFlags "-std=c++14"
    }
}
```


若你之前新建项目时候使用的是default，则这里会是cppFlags ""
## 参考文章
本文主要介绍了jni中对java的基本使用，不涉及太过庞大的原理等，主要参考
- [Android Developers NDK指南](https://developer.android.google.cn/training/articles/perf-jni)
- [Android JNI(一)——NDK与JNI基础](https://www.jianshu.com/p/87ce6f565d37)
- [JNI完全指南(一)——数据类型](https://www.zybuluo.com/cxm-2016/note/563686)
- [配置 CMake](https://developer.android.com/studio/projects/configure-cmake?hl=zh-cn)