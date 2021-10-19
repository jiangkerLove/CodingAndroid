- 内部是由LinkedHashMap来维护的一个accessOrder为true的由访问顺序排序的双链表。最近访问的放在最后。超过设置容量时移除最前端的元素。

- sizeOf方法在获取元素大小时会调用，默认大小为1。结合实际情况设置大小，这样才能保证缓存的内存是固定的，而与个数无关。

- trimToSize方法用于put操作后判断是否超过了设置的maxSize大小，若超过了就开始移除头部元素

- entryRemoved方法会在节点被移除时调用来通知外部，可以监听到缓存的移除。

- LruCache是线程安全的类。

- LruCache不允许空的key或value。