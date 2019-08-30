package com.example.technologycollection.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import com.example.technologycollection.BaseCommonActivity
import com.example.technologycollection.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

/**
 * TextInputLayout
 * @author zqhcxy 2019/08/13
 */
class TextInPutLayoutActivity : BaseCommonActivity() {

    lateinit var input_textlayout_password:TextInputLayout
    lateinit var input_edit_password: TextInputEditText
    lateinit var loading_btn :Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_in_put_layout)
        initToolbar()
        setTitle("TextInPutLayout")

        initView()
        initData()
    }

    fun initView() {
        input_edit_password = findViewById(R.id.input_edit_password)
        loading_btn = findViewById(R.id.loading_btn)
        input_textlayout_password = findViewById(R.id.input_textlayout_password)

        input_edit_password.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                input_textlayout_password.isErrorEnabled=false
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })


        loading_btn.setOnClickListener {
            if(input_edit_password.text==null||input_edit_password.text.toString().length>20){
//                input_edit_password.setError("password error!!!")
                input_textlayout_password.error="password error!!!"
            }
        }
    }

    fun initData() {
        toolbar.navigationIcon = getDrawable(R.drawable.ic_navigate_before_white_36pt_3x)
        toolbar.setNavigationOnClickListener {
            finish()
        }

    }
}
