#### 示例代码
[SparseArray.kt](../../calculate/src/main/java/jfp/study/calculate/map/SparseArray.kt)

- SparseArray 内部使用双数组，分别存储 Key 和 Value，Key 是 int[]，用于查找 Value 对应的 Index，来定位数据在 Value 中的位置。
- 使用二分查找来定位 Key 数组中对应值的位置，所以 Key 数组是有序的。
- 使用数组就要面临删除数据时数据搬移的问题，所以引入了 DELETE 标记。

#### 使用场景

Int作为key的map，数据量小，但是增删频繁。

