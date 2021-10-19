## Binder的通信过程
1. server在SM容器中注册、
2. Client若要调用Server的add方法，就需要先获取server对象，但是SM不会把真正的server对象返回给Client，而是把Server的一个代理对象Proxy返回给Client。
3. Client调用Proxy的add方法，ServiceManager会帮它去调用Server的add方法，并把结果返回给Client