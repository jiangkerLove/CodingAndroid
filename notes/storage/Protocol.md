# 项目配置
#### 项目build.gradle

```groovy
buildscript {
    dependencies {
        //添加
        classpath 'com.google.protobuf:protobuf-gradle-plugin:0.8.8'
    }
}
```

#### 模块build.gradle

```groovy
plugins {
    id 'com.google.protobuf'
}
android {
    sourceSets{
        main{
            //设置proto和生成的java文件路径
            proto {
                srcDir 'src/mian/proto'
                include '**/*.proto'
            }
            java {
                srcDir 'src/main/java'
            }
        }
    }
}
protobuf {
    //配置protoc编译器
    protoc {
        artifact = 'com.google.protobuf:protoc:3.8.0'
    }
    //这里配置生成目录，编译后会在build的目录下生成对应的java文件
    generateProtoTasks {
        all().each { task ->
            task.builtins {
                remove java
            }
            task.builtins {
                java {}
            }
        }
    }
}
dependencies {
    api "com.google.protobuf:protobuf-java:3.8.0"
}
```

#### proto文件格式

```protobuf
//指定版本为3，否则默认为2
syntax = "proto3";

package com.jiangker.testitem.proto;

//option 为可选字段
//生成文件路径
option java_package = "com.jiangker.testitem.datastore.protobuf";
//生成的文件名
option java_outer_classname = "PersonProto";

message Person{
  // 格式：字段类型 + 字段名称 + 字段编号
  string name = 1;
}
```
**字段编号一旦开始使用就不能够再改变**

**注意** ：在范围 \[1, 15\] 之间的字段编号在编码的时候会占用一个字节，包括字段编号和字段类型，在范围 \[16, 2047\] 之间的字段编号占用两个字节，因此，应该为频繁出现的消息字段保留 \[1, 15\] 之间的字段编号，**一定要为将来频繁出现的元素留出一些空间**。

#### 字段类型


|proto Type| Notes                      | Java Type|
|----------|----------------------------|----------|
|double    |                            |double    |
|float     |                            |float     |
|int32     |使用变长编码，如果字段是负值，效率很低，使用 sint32 代替|int |
|int64     |使用变长编码。如果字段是负值，效率很低，使用 sint64 代替|long|
|uint32    |使用变长编码                |int       |
|uint64    |使用变长编码                |long      |
|sint32    |使用变长编码，如果是负值比普通的 int32 更高效|int |
|sint64    |使用变长编码，如果是负值比普通的 int64 更高效|long|
|fixed32   |使用变长编码。符号整型。负值的编码效率高于常规的 int32 类型。|int|
|bool      |                            |boolean   |
|string    |包含 UTF-8 和 ASCII 编码的字符串，长度不能超过 2^32 。|String   |
|bytes     |可包含任意的字节序列但长度不能超过 2^32 。|ByteString|


#### 默认值
当解析消息时，若消息编码中没有包含某个元素，则相应的会使用该字段的默认值。默认值依据类型而不同：

- 字符串类型，空字符串
- 字节类型，空字节
- 布尔类型，false
- 数值类型，0
- 枚举类型，第一个枚举元素
- 被 repeated 修饰字段，默认值是一个大小为 0 的空 List

#### 枚举

```kotlin
enum Type{
   MAN = 0;
}
```
- 必须有一个 0 值，才可以作为数值类型的默认值。
- 0 值常量必须作为第一个元素，是为了与 proto2 的语义兼容就是第一个元素作为默认值。

