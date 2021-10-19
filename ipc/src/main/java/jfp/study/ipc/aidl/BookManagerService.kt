package jfp.study.ipc.aidl

import android.app.Service
import android.content.Intent
import android.os.RemoteCallbackList
import android.os.RemoteException
import java.util.concurrent.CopyOnWriteArrayList

class BookManagerService : Service() {

    private val books = CopyOnWriteArrayList<Book>()

    private val listeners = RemoteCallbackList<IOnNewBookArrivedListener>()

    override fun onBind(intent: Intent) = object : IBookManager.Stub() {

        override fun getBookList(): MutableList<Book> {
            return books
        }

        override fun addBookIn(book: Book?) {
            val count = listeners.beginBroadcast()
            repeat(count) {
                val listener = listeners.getBroadcastItem(it)
                try {
                    listener.onNewBookArrived(book)
                } catch (e: RemoteException) {
                }
            }
            listeners.finishBroadcast()
            book?.bookName = "newName"
            books.add(book)
        }

        override fun registerListener(listener: IOnNewBookArrivedListener?) {
            listeners.register(listener)
        }

        override fun unRegisterListener(listener: IOnNewBookArrivedListener?) {
            listeners.unregister(listener)
        }

    }
}