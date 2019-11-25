package com.example.technologycollection.activitys

import android.app.Application
import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.technologycollection.BaseCommonActivity
import com.example.technologycollection.R
import com.example.technologycollection.db.AppDatabase
import com.example.technologycollection.db.LocalDataBaseLoadManager
import com.example.technologycollection.db.User
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.room_item_layout.view.*

lateinit var mLocalDataBaseLoadManager: LocalDataBaseLoadManager

/**
 * 系统框架Room数据库操作
 * @author zqhcxy 2019/11/14
 */
class RoomActivity : BaseCommonActivity(), View.OnClickListener {



    private lateinit var mFirstNameEdit: EditText
    private lateinit var mLastNameEdit: EditText
    private lateinit var mAgeEdit: EditText
    private lateinit var mUidEdit: EditText
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mInsertButton: Button
    private lateinit var mUpdateButton: Button

    private lateinit var adapter: RoomAdapter


//    private lateinit var mLocalDataBaseLoadManager: LocalDataBaseLoadManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.room_activity)
        initToolbar()

        initView()
        initData()
    }

    private fun initView() {
        mFirstNameEdit = findViewById(R.id.room_first_name_ed)
        mLastNameEdit = findViewById(R.id.room_last_name_ed)
        mAgeEdit = findViewById(R.id.room_age_ed)
        mUidEdit = findViewById(R.id.room_uid_ed)
        mRecyclerView = findViewById(R.id.roo_recy)
        mInsertButton = findViewById(R.id.room_insert_btn)
        mUpdateButton = findViewById(R.id.room_update_btn)
    }

    private fun initData() {
        setTitle("Room")
        mLocalDataBaseLoadManager = LocalDataBaseLoadManager(this)
        mLocalDataBaseLoadManager.setLoadCallback(object :
            LocalDataBaseLoadManager.DabaseLoadCallBack {
            override fun loadFinsh(lists: List<User>) {
                Log.i("", "LocalDataBaseLoadManager loadFinsh")
                adapter.setData(lists)
            }

            override fun loadFinsh(cursor: Cursor) {
            }

            override fun loadFail() {
                Log.i("", "LocalDataBaseLoadManager loadFail")
            }

            override fun addResult(result: Boolean) {
                Log.i("", "LocalDataBaseLoadManager addResult：$result")
            }

            override fun updateResult(result: Boolean) {
                Log.i("", "LocalDataBaseLoadManager updateResult：$result")
            }

            override fun deleteResult(result: Boolean) {
                Log.i("", "LocalDataBaseLoadManager deleteResult：$result")
            }

        })
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = RoomAdapter(this, listOf())
        mRecyclerView.adapter = adapter
        mLocalDataBaseLoadManager.loadListDatas()


        mInsertButton.setOnClickListener(this)
        mUpdateButton.setOnClickListener(this)


    }


    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.room_insert_btn -> {
                val firstName = mFirstNameEdit.text.toString()
                val lastName = mLastNameEdit.text.toString()
                val age = mAgeEdit.text.toString()
                val uid = mUidEdit.text.toString()
                if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(
                        age
                    ) || TextUtils.isEmpty(uid)
                ) {
                    Toast.makeText(RoomActivity@ this, "请填写完整信息", Toast.LENGTH_LONG).show()
                    return
                }
                mLocalDataBaseLoadManager.addData(uid.toInt(), firstName, lastName, age.toInt())

            }
            R.id.room_update_btn -> {
                val firstName = mFirstNameEdit.text.toString()
                val lastName = mLastNameEdit.text.toString()
                val age = mAgeEdit.text.toString()
                val uid = mUidEdit.text.toString()
                if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(
                        age
                    ) || TextUtils.isEmpty(uid)
                ) {
                    Toast.makeText(RoomActivity@ this, "请填写完整信息", Toast.LENGTH_LONG).show()
                    return
                }
                mLocalDataBaseLoadManager.update(uid.toInt(), firstName, lastName, age.toInt())
            }
        }

    }

    class RoomAdapter : RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

        var mContext: Context
        var mLists: List<User>

        constructor(cnt: Context, lists: List<User>) {
            mContext = cnt
            mLists = lists
        }

        fun setData(lists: List<User>) {
            mLists = lists
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
            val view =
                LayoutInflater.from(mContext).inflate(R.layout.room_item_layout, parent, false)
            return RoomViewHolder(view)
        }

        override fun getItemCount(): Int {
            return mLists.size
        }

        override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
            val user = mLists.get(position)
            holder.itemView.apply {
                room_item_title_tv.setText(user.firstName)
                room_item_subtitle_tv.setText(user.lastName)
                room_item_ages_tv.setText(user.age.toString())
                room_item_delete_iv.setOnClickListener {
                    mLocalDataBaseLoadManager.deleteData(user)
                }
            }
        }


        class RoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    }

    override fun onDestroy() {
        super.onDestroy()
        mLocalDataBaseLoadManager.destroy()
    }


}