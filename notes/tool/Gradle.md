## 1 创建gradle文件
在project的build.gradle同级目录下创建config.gradle

```groovy
ext {

    //测试版和上线版控制
    isRelease = false

    androidID = [compileSdkVersion: 29,
                 buildToolsVersion: "29.0.3",
                 applicationId    : "jfp.study.coding_android",
                 minSdkVersion    : 21,
                 targetSdkVersion : 29,
                 versionCode      : 1,
                 versionName      : "1.0",
    ]

    dependenciesID = [
            retrofit      : 'com.squareup.retrofit2:retrofit:2.6.1',
            converter_gson: 'com.squareup.retrofit2:converter-gson:2.6.1'
    ]

    url = [
            debug  : "http:www.debug.com",
            release: "http:www.release.com"
    ]
}
```
## 2 引入到build.gradle中
在project的build.gradle文件的根目录下添加

```groovy
apply from : 'config.gradle'
```
## 3 模块build.gradle中的使用

```groovy
if (isRelease) {
    apply plugin: 'com.android.library'
} else {
    apply plugin: 'com.android.application'
}
apply plugin: 'kotlin-android'
apply plugin: 'kotlin-android-extensions'
apply plugin: 'kotlin-kapt'

def androidId = rootProject.ext.androidID

android {
    compileSdkVersion androidId.compileSdkVersion
    buildToolsVersion androidId.buildToolsVersion


    defaultConfig {
        if (!isRelease) {
            // appId = applicationId + flavorDimensions.applicationIdSuffix + buildTypes.applicationIdSuffix
            applicationId androidId.applicationId
        }
        minSdkVersion androidId.minSdkVersion
        targetSdkVersion androidId.targetSdkVersion
        versionCode androidId.versionCode
        versionName androidId.versionName

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"

        //给Java代码暴露release标记，通过BuildConfig文件获取
        buildConfigField("boolean", "isDebug", String.valueOf(isRelease))
    }

    buildTypes {
        //不同版本设置不同的url
        debug {
            applicationIdSuffix ".debug"
            buildConfigField("String", "urlService", "\"${url.debug}\"")
        }
        release {
            minifyEnabled false
            buildConfigField("String", "urlService", "\"${url.release}\"")
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    
    // 变种维度
    flavorDimensions "version", "type"
    productFlavors {
        demo {
            dimension "version"
            // 修改此版本的appId
            applicationIdSuffix ".demo"
            versionNameSuffix "-demo"
        }
        full {
            dimension "version"
            applicationIdSuffix ".full"
            versionNameSuffix "-full"
        }
        free {
            dimension "type"
        }
        noFree{
            dimension "type"
        }
    }

    //配置资源路径，方便测试环境，打包不集成到正式环境
    sourceSets {
        //main包下
        main {
            if (isRelease) {
                //java包下
                java {
                    //在release时，屏蔽掉debug目录下的文件
                    exclude '**/debug/**'
                }
            }
        }
    }
}

dependencies {
    implementation fileTree(dir: "libs", include: ["*.jar"])
    dependenciesID.each { k, v -> implementation v }
}
```
## 4 生成BuildConfig.java文件

```java
public final class BuildConfig {
  public static final boolean DEBUG = Boolean.parseBoolean("true");
  public static final String APPLICATION_ID = "com.jiangker.testapplication";
  public static final String BUILD_TYPE = "debug";
  public static final int VERSION_CODE = 1;
  public static final String VERSION_NAME = "1.0";
  // Fields from build type: debug
  public static final String urlService = "http:www.debug.com";
  // Fields from default config.
  public static final boolean isDebug = false;
}
```

## 5 参考文档

 [配置构建  | Android 开发者  | Android Developers](https://developer.android.com/studio/build?hl=zh-cn)

