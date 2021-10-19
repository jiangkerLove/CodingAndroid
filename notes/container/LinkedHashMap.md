> 主要是对HashMap的一个功能添加，增加对元素位置的维护。

- LinkedHashMap 是继承至HashMap的一个双向的链表，包含了指向链表头、尾的指针：head 和 tail，正是由于这个特性，所以LinkedHashMap 是有序的，而HashMap是无序的。

- LinkedHashMap 存储的Entey对象和HashMap的有不一样，因为是双向链表，所以肯定存在指向上个Node的指针 before以及指向下一个Node的指针 after，所以重写了HashMap生成Node的相关的方法，例如newNode、newTreeNode以及在其中把新节点连接到之前的尾节点上

- LinkedHashMap 还有一个特有的变量 accessOrder，他是布尔型变量，true 表示链表按访问顺序，false 表示按插入顺序

- afterNodeAccess方法把最近访问的元素移动到末尾，在get操作时会访问。以及当插入元素时，有相同key时，不会新建，而是替换对应的key然后调用此方法。

- afterNodeRemoval方法是HashMap中的方法，会在节点被移除时调用，LinkedHashMap用来去除掉链表的节点。