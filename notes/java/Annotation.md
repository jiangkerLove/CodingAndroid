[自定义注解代码示例](../../language/src/main/java/jfp/study/language/MyAnnotation.java)

[使用示例](../../language/src/test/java/jfp/study/language/ReflexTest.kt)

### 1、@Documented:

用于标记在生成javadoc时是否将注解包含进去，可以看到这个注解和@Override一样，注解中空空如也，什么东西都没有。

### 2、@Target

用于定义注解可以在什么地方使用，默认可以在任何地方使用，也可以指定使用的范围，开发中将注解用。

| 字段                        | 作用               |
| --------------------------- | ------------------ |
| ElementType.TYPE            | 类、接口或enum声明 |
| ElementType.FIELD           | 域(属性)声明       |
| ElementType.METHOD          | 方法声明           |
| ElementType.PARAMETER       | 参数声明           |
| ElementType.CONSTRUCTOR     | 构造方法声明       |
| ElementType.LOCAL_VARIABLE  | 局部变量声明       |
| ElementType.ANNOTATION_TYPE | 注释类型声明       |
| ElementType.PACKAGE         | 包声明             |

### 3、@Inherited

允许子类继承父类中的注解，可以通过反射获取到父类的注解

### 4、@Constraint

用于校验属性值是否合法

### 5、@Retention

注解的声明周期，用于定义注解的存活阶段，可以存活在源码级别、编译级别(字节码级别)、运行时级别。

| 类型                    | 作用                                                         |
| ----------------------- | ------------------------------------------------------------ |
| RetentionPolicy.SOURCE  | 源码级别，注解只存在源码中，一般用于和编译器交互，用于检测代码。如@Override, @SuppressWarings。 |
| RetentionPolicy.CLASS   | 字节码级别，注解存在于源码和字节码文件中，主要用于编译时生成额外的文件，如XML，Java文件等，但运行时无法获得。 默认注解 |
| RetentionPolicy.RUNTIME | 运行时级别，注解存在于源码、字节码、java虚拟机中，主要用于运行时，可以使用反射获取相关的信息。 |

