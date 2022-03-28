package com.example.student_management

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.student_management.database.DB_Student
import com.example.student_management.model.Student_Entity
import kotlinx.android.synthetic.main.activity_fragment2.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class edit_fragment(
) : AppCompatActivity(),CoroutineScope {


    private var noteDB: DB_Student ?= null

    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setTitle("Chỉnh sửa thông tin sinh viên");
        setContentView(R.layout.edit_fragment_activity)
        mJob = Job()
        noteDB = DB_Student.getDatabase(this)
//      var notes = emptyList<Student_Entity>()

        val id:Int = Integer.parseInt(intent.extras?.get("student_id").toString())
        val name: String = intent.extras?.get("student_name").toString()
        val grade: String = intent.extras?.get("student_grade").toString()
        val phone: String = intent.extras?.get("student_phone").toString()
        val email: String = intent.extras?.get("student_email").toString()
//        var strUser: String? = intent.getStringExtra("student_name") // 2
        edt_name.setText(name)
        edt_grade.setText(grade)
        edt_phone.setText(phone)
        edt_email.setText(email)
        btn_update.setOnClickListener{
            launch {
                val strname: String = edt_name.text.toString()
                val strgrade: String = edt_grade.text.toString()
                val strphone: String = edt_phone.text.toString()
                val stremail: String = edt_email.text.toString()
                if (strname =="" || strgrade == "" || strphone == "" || stremail == "") {
                    Toast.makeText(this@edit_fragment,"Bạn phải nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show()
                } else {
                    noteDB?.studentDao()?.update(Student_Entity(id = id,name=strname,grade = strgrade, phone = strphone, email = stremail))
                    Toast.makeText(this@edit_fragment,"Cập Nhật Thành Công",Toast.LENGTH_SHORT).show()
                    val intent = Intent()
                    setResult(RESULT_OK,intent)
                    finish()
                }

            }
        }
        cancel_button.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }


}