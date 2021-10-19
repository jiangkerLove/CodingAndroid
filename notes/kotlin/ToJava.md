#### field

[NormalClass](../../language/src/main/java/jfp/study/language/kt/NormalClass.kt)

对于一般的成员变量，kotlin默认会生成对应的get和set方法。所以在对应的set方法中不能直接使用变量，因为这样走的是get方法，这样就会导致循环参数。在类的内部直接使用变量名称，也是走的get方法。



#### data class

> 构造函数中必须要有元素



[DataClass](../../language/src/main/java/jfp/study/language/kt/DataClass.kt)

比一般的class多component方法，copy方法，toString、hashCode和equals方法。



#### scaled class

[ScaledClass](../../language/src/main/java/jfp/study/language/kt/ScaledClass.kt)

静态内部类



#### 接口中的默认实现

类似于会默认一个静态的内部类，里面有默认的方法实现，如果没有重写默认调用的是静态方法。

```kotlin
interface Log {
    fun print() {}
}
public interface Log {
   void print();
   public static final class DefaultImpls {
      public static void print(@NotNull Log $this) {
      }
   }
}
```



