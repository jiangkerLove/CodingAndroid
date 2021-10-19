#### 示例代码

[ArrayList.kt](../../calculate/src/main/java/jfp/study/calculate/list/ArrayList.kt)

- 如果初始化时没有指定大小，在第一次添加元素时会扩充到10或者刚好容纳所需大小的最大值。

- 扩容比例一般为1.5倍。

- 不带有自动缩容，有一个trimToSize方法使list变为一个满容量的list。

- modCount保证迭代过程中无元素的增加或删除。

- subList之后，父列表改变，子列表再改变则会发生错误，因为modCount不一致。[fun sublist_test](../../calculate/src/test/jfp/study/calculate/ArrayListTest.kt)

- 有一个最大size为int最大值减8，若1.5倍扩充大于此值，而期望容量小于此值，则取最大size值。若期望值大于此值则选取为int最大值。

- 序列化时，只序列化size个长度，不写入后面为空的部分

