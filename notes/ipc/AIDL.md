# 1 默认支持的数据类型

- Java中的八种基本数据类型，包括 byte，short，int，long，float，double，boolean，char。
- String类型和CharSequence类型。
- List类型：只支持ArrayList，里面的每个元素都必须要被AIDL支持；
- Map类型：只支持HashMap，里面的每个元素都必须要被AIDL支持，包括key和value；
- Parcelable：所有实现了Parcelable接口的对象；
- AIDL：所有的AIDL接口本身也可以在AIDL文件中使用

> 其中自定义的Parcelable对象和AIDL对象必须要显示的import进来

# 2 in out inout的区别

AIDL中的定向 tag 表示了在跨进程通信中数据的流向，其中in表示数据只能由客户端流向服务端，out表示数据只能由服务端流向客户端，而 inout 则表示数据可在服务端与客户端之间双向流通。其中，数据流向是针对在客户端中的那个传入方法的对象而言的。in 为定向 tag 的话表现为服务端将会接收到一个那个对象的完整数据，但是客户端的那个对象不会因为服务端对传参的修改而发生变动；out 的话表现为服务端将会接收到那个对象的参数为空的对象，但是在服务端对接收到的空对象有任何修改之后客户端将会同步变动；inout 为定向 tag 的情况下，服务端将会接收到客户端传来对象的完整信息，并且客户端将会同步服务端对该对象的任何变动。

# 3 使用示例

### 3.1 AIDL文件的定义

aidl文件路径下
```java
//Book.aidl
package com.jiangker.testitem.lib;

parcelable Book;
```

对应java文件路径下
```java
//Book.java
package com.jiangker.testitem.lib

import android.os.Parcel
import android.os.Parcelable

 class Book(
    var name: String?,
    var price: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }
}
```

AIDL接口定义
```java
//IBookManager.aidl
package com.jiangker.testitem.lib;

import com.jiangker.testitem.lib.Book;

interface IBookManager{
    List<Book> getBookList();
    void addBook(in Book book);
}
```

### 3.2 Server端

```kotlin
class MainService : Service() {

    private val bookLists = ArrayList<Book>()

    private val manager = object : IBookManager.Stub() {
        override fun addBook(book: Book) {
            bookLists.add(book)
        }

        override fun getBookList(): MutableList<Book> {
            return bookLists
        }
    }

    override fun onBind(intent: Intent) = manager
}
```

### 3.3 Client端

```kotlin
class MainActivity : AppCompatActivity() {

    private var bookService: IBookManager? = null

    private val connection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            bookService = IBookManager.Stub.asInterface(service)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        //绑定服务
        bindService(Intent(this,MainService::class.java),connection,Context.BIND_AUTO_CREATE)
        binding.apply {
            btnInsert.setOnClickListener 
                val book = Book("jiangker ${SystemClock.uptimeMillis()}", 528)
                bookService?.addBook(book)
                Log.i(TAG, "addBook $book - ${book.hashCode()}")
            }
            btnQuery.setOnClickListener {
                bookService?.bookList?.forEachIndexed {index,book ->
                    Log.i(TAG, "list index: $index : - : $book - ${book.hashCode()}")
                }
            }
        }
    }
}
```

## 对应的Java文件
```java
public interface IBookManager extends IInterface
{
    /** server端继承的抽象类*/
    public static abstract class Stub extends Binder implements IBookManager
    {
        //binder的唯一标识
        private static final String DESCRIPTOR = "com.jiangker.testitem.lib.IBookManager";

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }
        /**
         * 判断IBinder对象是否和自己处于同一进程，如果是就直接使用，否则把服务端的IBinder包装为一个客户端所需的AIDL接口的Proxy代理对象
         */
        public static IBookManager asInterface(IBinder obj) {
            if ((obj==null)) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (((iin!=null)&&(iin instanceof IBookManager))) {
                return ((IBookManager)iin);
            }
            return new IBookManager.Stub.Proxy(obj);
        }
        //返回当前Binder对象
        @Override
        public IBinder asBinder() {
            return this;
        }
        /**
         * 接收客户端传递过来的数据。运行在服务端中的Binder线程池中，当客户端发起请求时，远程请求会通过系统底层封装后交由此方法来处理
         */
        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            String descriptor = DESCRIPTOR;
            switch (code)
            {
                case INTERFACE_TRANSACTION:
                {
                    reply.writeString(descriptor);
                    return true;
                }
                case TRANSACTION_getBookList:
                {
                    data.enforceInterface(descriptor);
                    List<Book> _result = this.getBookList();
                    reply.writeNoException();
                    reply.writeTypedList(_result);
                    return true;
                }
                case TRANSACTION_addBook:
                {
                    data.enforceInterface(descriptor);
                    Book _arg0;
                    if ((0!=data.readInt())) {
                        _arg0 = Book.CREATOR.createFromParcel(data);
                    }
                    else {
                        _arg0 = null;
                    }
                    this.addBook(_arg0);
                    reply.writeNoException();
                    return true;
                }
                case TRANSACTION_registerListener:
                {
                    data.enforceInterface(descriptor);
                    IOnNewBookArrivedListener _arg0;
                    _arg0 = IOnNewBookArrivedListener.Stub.asInterface(data.readStrongBinder());
                    this.registerListener(_arg0);
                    reply.writeNoException();
                    return true;
                }
                case TRANSACTION_unRegisterListener:
                {
                    data.enforceInterface(descriptor);
                    IOnNewBookArrivedListener _arg0;
                    _arg0 = IOnNewBookArrivedListener.Stub.asInterface(data.readStrongBinder());
                    this.unRegisterListener(_arg0);
                    reply.writeNoException();
                    return true;
                }
                default:
                {
                    return super.onTransact(code, data, reply, flags);
                }
            }
        }
        private static class Proxy implements IBookManager
        {
            private IBinder mRemote;
            Proxy(IBinder remote)
            {
                mRemote = remote;
            }
            @Override 
            public IBinder asBinder() {
                return mRemote;
            }
            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }
            //运行在客户端，会把该方法的信息参数打包发给server端，等待方法返回
            @Override 
            public List<Book> getBookList() throws RemoteException {
                //把要传递的数据写入_data
                Parcel _data = Parcel.obtain();
                //把要接收的函数返回值写入_reply
                Parcel _reply = Parcel.obtain();
                List<Book> _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    //最后使用transact方法，就可以把数据传递给Binder的server端了
                    boolean _status = mRemote.transact(Stub.TRANSACTION_getBookList, _data, _reply, 0);
                    if (!_status && getDefaultImpl() != null) {
                        return getDefaultImpl().getBookList();
                    }
                    _reply.readException();
                    _result = _reply.createTypedArrayList(Book.CREATOR);
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
            @Override public void addBook(Book book) throws RemoteException
            {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    if ((book!=null)) {
                        _data.writeInt(1);
                        book.writeToParcel(_data, 0);
                    }
                    else {
                        _data.writeInt(0);
                    }
                    boolean _status = mRemote.transact(Stub.TRANSACTION_addBook, _data, _reply, 0);
                    if (!_status && getDefaultImpl() != null) {
                        getDefaultImpl().addBook(book);
                        return;
                    }
                    _reply.readException();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
            @Override public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException
            {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
                    boolean _status = mRemote.transact(Stub.TRANSACTION_registerListener, _data, _reply, 0);
                    if (!_status && getDefaultImpl() != null) {
                        getDefaultImpl().registerListener(listener);
                        return;
                    }
                    _reply.readException();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
            @Override public void unRegisterListener(IOnNewBookArrivedListener listener) throws RemoteException
            {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
                    boolean _status = mRemote.transact(Stub.TRANSACTION_unRegisterListener, _data, _reply, 0);
                    if (!_status && getDefaultImpl() != null) {
                        getDefaultImpl().unRegisterListener(listener);
                        return;
                    }
                    _reply.readException();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
            public static IBookManager sDefaultImpl;
        }
        static final int TRANSACTION_getBookList = (IBinder.FIRST_CALL_TRANSACTION + 0);
        static final int TRANSACTION_addBook = (IBinder.FIRST_CALL_TRANSACTION + 1);
        static final int TRANSACTION_registerListener = (IBinder.FIRST_CALL_TRANSACTION + 2);
        static final int TRANSACTION_unRegisterListener = (IBinder.FIRST_CALL_TRANSACTION + 3);
        public static boolean setDefaultImpl(IBookManager impl) {
            if (Stub.Proxy.sDefaultImpl == null && impl != null) {
                Stub.Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }
        public static IBookManager getDefaultImpl() {
            return Stub.Proxy.sDefaultImpl;
        }
    }
    public List<Book> getBookList() throws RemoteException;
    public void addBook(Book book) throws RemoteException;
    public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException;
    public void unRegisterListener(IOnNewBookArrivedListener listener) throws RemoteException;
}
```
## 死亡代理
binder提供两个方法，通过linkToDeath就可以给Binder设置一个死亡代理，在Binder死亡时通知我们，这个触发是在客户端的Binder线程池里面的，不可以进行UI操作

```java
//定义死亡代理
private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
    @Override
    public void binderDied() {
        if (bookServer == null) return;
        bookServer.asBinder().unlinkToDeath(mDeathRecipient,0);
        bookServer = null;
//            binderServer();
    }
};
//设置绑定
private ServiceConnection connection = new ServiceConnection{
    @Override
    public void onServiceDisConnected(ComponentName name){}
    
    @Override
    public void onServiceConnected(ComponentName name,IBinder service){
        service.linkToDeath(mDeathRecipient,0)
    }
}
```
也可以在onServiceDisConnected中进行重连操作，这个是主线程中触发的

## 权限校验
在服务端定义权限
```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.sourcecode">
    <permission android:name="com.jinagker.permission.Test"
        android:protectionLevel="normal"/>
</manifest>
```
可以在两个地方进行拦截
1. onBind方法处（使用Context类的`checkCallingOrSelfPermission(String permission)`方法）
    ```kotlin
    override fun onBind(intent: Intent): IBinder? {
        val result =
            checkCallingOrSelfPermission("com.jinagker.permission.Test")
        if (result == PackageManager.PERMISSION_DENIED) return null
        return messageManager.binder
    }
    ```
    使用，需要申明权限
    
    ```xml
    <uses-permission android:name="com.jinagker.permission.Test"/>
    ```
2. 在服务端的onTransact中进行验证（重写服务端的onTransact方法，校验失败返回false）
   
    ```kotlin
    val server = object : ISaveSp.Stub() {
        override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
            val result =
                checkCallingOrSelfPermission("com.jinagker.permission.Test")
            if (result == PackageManager.PERMISSION_DENIED) return false
            return super.onTransact(code, data, reply, flags)
        }
    }
    ```

3. 直接在服务端属性处增加`android:poermission`，没有申明的`onServiceConnected`不会有返回