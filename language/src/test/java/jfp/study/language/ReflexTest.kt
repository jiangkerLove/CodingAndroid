package jfp.study.language

import org.junit.Test

class ReflexTest {

    @MyAnnotation
    open class A(
        val name: String,
        val age: Int
    ) {
        open fun printSelf() {

        }

        private fun printA() {

        }
    }

    class B(
        name: String,
        age: Int
    ) : A(name, age) {

        companion object {
            const val staticName = "staticName"
        }

        private constructor(name: String) : this(name, 18)

        override fun printSelf() {
            super.printSelf()
        }

        private fun printB() {

        }
    }

    @Test
    fun get_class() {
        // 从对象中获取
        val b = B("jiangker", 19)
        val c1 = b.javaClass

        // 根据类名获取
        val c2 = Class.forName("java.lang.String")

        // class属性
        val c3 = String::class.java

    }

    @Test
    fun construct_test() {
        val typeB = B::class.java
        // 获取public的构造函数
        val constructors = typeB.constructors
        for (constructor in constructors) {
            // 获取参数集合
            constructor.parameterTypes
        }
        assert(constructors.size == 1)
        val constructor = typeB.getConstructor(String::class.java, Int::class.java)
        val b = constructor.newInstance("jiangker", 18)
        // 获取所有构造函数
        val declaredConstructors = typeB.declaredConstructors
    }

    @Test
    fun method_test() {
        val b = B("jiangker", 19)
        val c1 = b.javaClass
        //  获取所有public方法，包括其父类的public方法
        c1.methods
        //  获取所有访问权限的方法，但不包括父类的方法
        c1.declaredMethods
        // 获取指定名字的方法
        val method = c1.getDeclaredMethod("printSelf")
        // 调用方法，若是静态方法则传入空即可
        method.invoke(b)
    }

    @Test
    fun change_filed_test() {
        val b = B("jiangker", 19)
        val c1 = b.javaClass
        // 获取此类的public变量
        c1.fields
        // 获取此类的所有成员变量
        c1.declaredFields
        val filed = c1.superclass.getDeclaredField("name")
        // 设置不进行校验
        filed.isAccessible = true
        filed.set(b, "jiang")
        assert(b.name == "jiang")

        c1.getField("staticName").apply {
            isAccessible = true
            // 不能对static final字段进行修改
            set(null, "jiang")
        }
    }

    @Test
    fun getAnnotation() {
        val b = B("jiangker", 19)
        val c1 = b.javaClass
        // 类上的注解
        c1.annotations
        // 包括继承下来的注解
        c1.declaredAnnotations
        val annotation = c1.getDeclaredAnnotation(MyAnnotation::class.java)
        val valueArray: Array<String> = annotation.value
        val nameFiled = c1.getDeclaredField("name")
        // 获取成员变量的注解
        nameFiled.annotations
    }
}