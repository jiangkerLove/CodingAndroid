package jfp.study.jetpack.room

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import jfp.study.jetpack.R
import jfp.study.jetpack.databinding.ActivityRoomBinding
import kotlin.concurrent.thread

class RoomActivity :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userDao = AppDatabase.getDatabase(this).userDao()
        val binding = DataBindingUtil.setContentView<ActivityRoomBinding>(this, R.layout.activity_room)
        binding.apply {
        val user1 = User("Tom","Brady",16)
        val user2 = User("Tom","Hanks",26)
            btnGetUser.setOnClickListener {
                thread {
                    user1.id = userDao.insertUser(user1)
                    user2.id = userDao.insertUser(user2)
                }
            }
            btnUpdateData.setOnClickListener {
                thread {
                    user1.age =42
                    userDao.updateUser(user1)
                }
            }
            btnDeleteData.setOnClickListener {
                thread {
                    userDao.deleteUserByLastName("Hanks")
                }
            }
            btnQueryData.setOnClickListener {
                thread {
                    for (user in userDao.loadAllUser()){
                        Log.d("Jetpack Tag", "onCreate: $user")
                    }
                }
            }
        }
    }
}