## 外部储存
可以在手机中直接查看的目录，目录为Android/data/<package name>/xxx 下的系列文件
> 传入的参数不要以“/”开始

```kotlin
//主要对应Android/data/<package name>/files/下的文件目录,传入空则为根目录
val externalFilesDir = getExternalFilesDir("plugin/app-debug.apk")
//path: /storage/emulated/0/Android/data/com.jiangker.testapplication/files/plugin/app-debug.apk
Log.i(TAG, "path: ${externalFilesDir?.path}")
//name: app-debug.apk
Log.i(TAG, "name: ${externalFilesDir?.name}")
//absolutePath: /storage/emulated/0/Android/data/com.jiangker.testapplication/files/plugin/app-debug.apk
Log.i(TAG, "absolutePath: ${externalFilesDir?.absolutePath}")
//canonicalPath: /storage/emulated/0/Android/data/com.jiangker.testapplication/files/plugin/app-debug.apk
Log.i(TAG, "canonicalPath: ${externalFilesDir?.canonicalPath}")
```

另外还有缓存目录
```kotlin
//主要对应Android/data/<package name>/cache/下的文件目录
val externalCacheDir = externalCacheDir
```

## 内部储存
只能在as中看到的目录，对应在/data/data/<package name>/ 下的系列文件
> 对应的sp目录也是在这个下，对应/data/data/<package name>/shared_prefs/

- getDir(String name, int mode)方法

```kotlin
//调用这个方法会在根目录下生成一个文件夹，在对应的名字前会加上app_,现在模式只有这一个可以使用了
val dir = getDir("pulgin", Context.MODE_PRIVATE)
//path: /data/user/0/com.jiangker.testapplication/app_pulgin
Log.i(TAG, "path: ${dir?.path}")
//name: app_pulgin
Log.i(TAG, "name: ${dir?.name}")
//absolutePath: /data/user/0/com.jiangker.testapplication/app_pulgin
Log.i(TAG, "absolutePath: ${dir?.absolutePath}")
//canonicalPath: /data/data/com.jiangker.testapplication/app_pulgin
Log.i(TAG, "canonicalPath: ${dir?.canonicalPath}")
```
- getCacheDir()方法


```kotlin
//直接获取其下的cache目录
val dir = cacheDir
//path: /data/user/0/com.jiangker.testapplication/cache
Log.i(TAG, "path: ${dir?.path}")
//name: cache
Log.i(TAG, "name: ${dir?.name}")
//absolutePath: /data/user/0/com.jiangker.testapplication/cache
Log.i(TAG, "absolutePath: ${dir?.absolutePath}")
//canonicalPath: /data/data/com.jiangker.testapplication/cache
Log.i(TAG, "canonicalPath: ${dir?.canonicalPath}")
```
- getFilesDir()方法

与cache类似，对应文件夹files
```kotlin
//直接获取其下的files目录
val dir = filesDir
//path: /data/user/0/com.jiangker.testapplication/files
Log.i(TAG, "path: ${dir?.path}")
//name: files
Log.i(TAG, "name: ${dir?.name}")
//absolutePath: /data/user/0/com.jiangker.testapplication/files
Log.i(TAG, "absolutePath: ${dir?.absolutePath}")
//canonicalPath: /data/data/com.jiangker.testapplication/files
Log.i(TAG, "canonicalPath: ${dir?.canonicalPath}")
```

- getCodeCacheDir()方法

与cache类似，对应文件夹code_cache

```kotlin
//直接获取其下的code_cache目录
val dir = codeCacheDir
//path: /data/user/0/com.jiangker.testapplication/code_cache
Log.i(TAG, "path: ${dir?.path}")
//name: code_cache
Log.i(TAG, "name: ${dir?.name}")
//absolutePath: /data/user/0/com.jiangker.testapplication/code_cache
Log.i(TAG, "absolutePath: ${dir?.absolutePath}")
//canonicalPath: /data/data/com.jiangker.testapplication/code_cache
Log.i(TAG, "canonicalPath: ${dir?.canonicalPath}")
```
- getFileStreamPath(String name)方法

对应的files中的文件路径
```kotlin
//对应files中存放了一个app-debug.apk文件
val fileStreamPath = getFileStreamPath("app-debug.apk")
//path: /data/user/0/com.jiangker.testapplication/files/app-debug.apk
Log.i(TAG, "path: ${fileStreamPath?.path}")
//name: app-debug.apk
Log.i(TAG, "name: ${fileStreamPath?.name}")
//absolutePath: /data/user/0/com.jiangker.testapplication/files/app-debug.apk
Log.i(TAG, "absolutePath: ${fileStreamPath?.absolutePath}")
//canonicalPath: /data/data/com.jiangker.testapplication/files/app-debug.apk
Log.i(TAG, "canonicalPath: ${fileStreamPath?.canonicalPath}")
```
