[toc]

## MediaType.parse(String)

### **Content-Type、MediaType是什么？**

> MediaType，即是Internet Media Type，互联网媒体类型，也叫做MIME类型，在Http协议消息头中，使用Content-Type来表示具体请求中的媒体类型信息。（也就是说MediaType在网络协议的消息头里面叫做Content-Type）它使用两部分的标识符来确定一个类型，是为了表明我们传的东西是什么类型。

#### 常见的媒体格式类型如下：

- text/html ： HTML格式
- text/plain ：纯文本格式
- text/xml ：  XML格式
- image/gif ：gif图片格式
- image/jpeg ：jpg图片格式 
- image/png：png图片格式

#### 以application开头的媒体格式类型

- application/xhtml+xml ：XHTML格式
- application/xml     ： XML数据格式
- application/atom+xml  ：Atom XML聚合格式    
- application/json    ： JSON数据格式
- application/pdf       ：pdf格式 
- application/msword  ： Word文档格式
- application/octet-stream ： 二进制流数据（如常见的文件下载、上传）
- application/x-www-form-urlencoded ： <form encType="">中默认的encType，form表单数据被编码为key/value格式发送到服务器（表单默认的提交数据的格式）

#### 文件上传类型

- multipart/form-data ： 需要在表单中进行文件上传时，就需要使用该格式

## 配置

依赖

```groovy
implementation "com.squareup.okhttp3:okhttp:4.9.1"
```

权限

```xml
<uses-permission android:name="android.permission.INTERNET"/>
```

## 使用

### 创建Client对象

```kotlin
val client = OkHttpClient()
```

### get请求

- 请求参数配置

  ```kotlin
  /**
    * http://localhost:8080/test/getParams?name=xxxx&age=18
    */
  val urlBuilder = HttpUrl.parse("${NetworkConfig.SHARE_BASE_URL}$url")!!.newBuilder()
  urlBuilder.addQueryParameter("name", name)
  urlBuilder.addQueryParameter("age", age.toString())
  val request = Request.Builder()
  	.url(urlBuilder.build())
  	.get()//默认请求
  	.build()
  ```

### Post请求

> 需要携带RequestBody参数

- RequestBody创建

  ```kotlin
  val jsonType = MediaType.parse("application/json; charset=utf-8")
  val requestBody = RequestBody.create(jsonType,jsonString)
  val request = Request.Builder()
      .url("${NetworkConfig.SHARE_BASE_URL}$url")
      .post(requestBody)
      .build()
  ```

- FromBody表单

  ```kotlin
  val requestBody = FormBody.Builder()
  	.add("name", name)
  	.add("age", age.toString())
  	.build()
  val request = Request.Builder()
  	.url("${NetworkConfig.SHARE_BASE_URL}$url")
  	.post(requestBody)
  	.build()
  ```

- MultipartBody文件上传

  
