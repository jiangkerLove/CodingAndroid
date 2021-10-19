// IBookManager.aidl
package jfp.study.ipc.aidl;

import jfp.study.ipc.aidl.Book;
import jfp.study.ipc.aidl.IOnNewBookArrivedListener;

// Declare any non-default types here with import statements

interface IBookManager {
    List<Book> getBookList();
    /**
     * 客户端的代理对象会读取客户端写入的值，但是不关心写入的结果
     * data.enforceInterface(descriptor);
     * Book _arg0;
     * if ((0!=data.readInt())) {
     *     _arg0 = Book.CREATOR.createFromParcel(data);
     * }else {
     *     _arg0 = null;
     * }
     * this.addBookIn(_arg0);
     * reply.writeNoException();
     */
    void addBookIn(in Book book);
    /**
     * 客户端只关心调用的结果，并不会去读取客户端传入的对象。
     * data.enforceInterface(descriptor);
     * Book _arg0 = new Book();
     * this.addBookOut(_arg0);
     * reply.writeNoException();
     * if ((_arg0!=null)) {
     *    reply.writeInt(1);
     *     _arg0.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
     * } else {
     *     reply.writeInt(0);
     * }
     */
    // void addBookOut(out Book book);
    /**
     * 双向都要
     * data.enforceInterface(descriptor);
     * Book _arg0;
     * if ((0!=data.readInt())) {
     *     _arg0 = Book.CREATOR.createFromParcel(data);
     * } else {
     *     _arg0 = null;
     * }
     * this.addBookInOut(_arg0);
     * reply.writeNoException();
     * if ((_arg0!=null)) {
     *    reply.writeInt(1);
     *    _arg0.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
     * } else {
     *    reply.writeInt(0);
     * }
     */
    // void addBookInOut(inout Book book);
    void registerListener(in IOnNewBookArrivedListener listener);
    void unRegisterListener(IOnNewBookArrivedListener listener);
}