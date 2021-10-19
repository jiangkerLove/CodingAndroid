package jfp.study.jetpack.room

import androidx.room.*

/**
 * 如果是作为实体对象来传入的参数，是不需要SQL语句
 * @Update 和 @Delete是根据数据id去执行操作的
 */
@Dao
interface UserDao {

    @Insert
    fun insertUser(user: User): Long

    @Update
    fun updateUser(newUser: User)

    @Query("select * from User")
    fun loadAllUser(): List<User>

    @Query("select * from User where age > :age")
    fun loadUserOlderThan(age: Int): List<User>

    @Delete
    fun deleteUser(user: User)

    @Query("delete from User where lastName = :lastName")
    fun deleteUserByLastName(lastName: String): Int
}