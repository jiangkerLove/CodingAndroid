package jfp.study.ipc.aidl

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.databinding.DataBindingUtil
import jfp.study.ipc.R
import jfp.study.ipc.aidl.Book.CREATOR.TAG
import jfp.study.ipc.databinding.ActivityBookBinding

class BookActivity : AppCompatActivity() {

    var bookManager : IBookManager?= null

    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            bookManager = IBookManager.Stub.asInterface(service)
            bookManager?.registerListener(object : IOnNewBookArrivedListener.Stub() {
                override fun onNewBookArrived(newBook: Book) {
                    Log.i(TAG, "onNewBookArrived: $newBook")
                }
            })
        }

        override fun onServiceDisconnected(name: ComponentName?) {

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityBookBinding>(this, R.layout.activity_book)
        bindService(
            Intent(this, BookManagerService::class.java),
            mConnection,
            Context.BIND_AUTO_CREATE
        )
        binding.apply {
            btnAddBook.setOnClickListener {
                val bookSource = Book(19, "jiangker")
                bookManager?.addBookIn(bookSource)
                Log.i(TAG, "onCreate: $bookSource")
            }
            btnGetBookList.setOnClickListener {
                Log.i(TAG, "onCreate: ${bookManager?.bookList.toString()}")
            }
        }
    }
}