[泛型代码示例](../../language/src/test/java/jfp/study/language/GenericTest.java)

- List<?> 表示通配符，可以做接收，但是不能添加元素

- List<? super T> Put First 表示只能添加元素，父类集合可以像子类通配符转型。

- List<? extends T> Get First 表示只能取出元素的，子类集合可以像其赋值。

