package com.example.technologycollection.db

import android.content.Context
import android.database.Cursor
import android.util.Log
import androidx.room.Room
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

/**
 * 数据库操作工具类
 *
 * @author zqh 2019/11/20
 */
class LocalDataBaseLoadManager(private val context: Context) {

    private val db: AppDatabase
    private var mDabaseLoadCallBack: DabaseLoadCallBack? = null

    init {
        db = Room.databaseBuilder(context, AppDatabase::class.java, "database-name").build()
    }

    fun setLoadCallback(callBack: DabaseLoadCallBack) {
        mDabaseLoadCallBack = callBack
    }

//    /**
//     * 获取数据，格式是list
//     */
//    val users: Flowable<List<User>>
//        get() = db.userDao().getAllToList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

//    /**
//     * 获取数据，格式是Cursor
//     */
//    val userCursor:Flowable<Cursor>
//        get() = db.userDao().getALLToCursor().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    fun loadListDatas() {
       val result= db.userDao().getAllToList().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(object : Consumer<List<User>> {
                override fun accept(t: List<User>?) {
                    mDabaseLoadCallBack?.loadFinsh(t ?: listOf())
                }

            }, object : Consumer<Throwable> {
                override fun accept(t: Throwable?) {
                    mDabaseLoadCallBack?.loadFail()
                }

            })
    }


    /**
     * 添加数据
     */
    fun addData(uid: Int, firstName: String, lastName: String, age: Int) {
        val result = Completable.fromAction {
            val user = User( firstName = firstName, lastName = lastName, age = age)
            db.userDao().insertUser(user)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Action {
                override fun run() {
                    Log.i("", "LocalDataBaseLoadManager run")
                }
            }, object : Consumer<Throwable> {
                override fun accept(t: Throwable?) {
                    mDabaseLoadCallBack?.addResult(false)
                    Log.i("", "LocalDataBaseLoadManager addfail accept")
                }

            })
    }

    fun update(uid: Int, firstName: String, lastName: String, age: Int){
        val result = Completable.fromAction {
            val user = User( firstName = firstName, lastName = lastName, age = age)
            user.uid=uid
            db.userDao().update(user)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Action {
                override fun run() {
                    Log.i("", "LocalDataBaseLoadManager run")
                }
            }, object : Consumer<Throwable> {
                override fun accept(t: Throwable?) {
                    mDabaseLoadCallBack?.addResult(false)
                    Log.i("", "LocalDataBaseLoadManager addfail accept")
                }

            })
    }

    fun deleteData(user: User){
        val result = Completable.fromAction {
//            val user = User(uid = uid, firstName = firstName, lastName = lastName, age = age)
            db.userDao().delete(user)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Action {
                override fun run() {
                    Log.i("", "LocalDataBaseLoadManager run")
                }
            }, object : Consumer<Throwable> {
                override fun accept(t: Throwable?) {
                    mDabaseLoadCallBack?.deleteResult(false)
                    Log.i("", "LocalDataBaseLoadManager addfail accept")
                }

            })
    }

    fun destroy(){
        db.close()
    }


    interface DabaseLoadCallBack {
        fun loadFinsh(lists: List<User>)
        fun loadFinsh(cursor: Cursor)
        fun loadFail()
        fun addResult(result: Boolean)
        fun updateResult(result: Boolean)
        fun deleteResult(result: Boolean)
    }

}