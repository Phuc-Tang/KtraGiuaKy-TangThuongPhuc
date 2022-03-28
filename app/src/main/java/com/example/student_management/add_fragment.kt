package com.example.student_management


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.student_management.database.DB_Student
import com.example.student_management.model.Student_Entity
import kotlinx.android.synthetic.main.activity_first_fragment.*
import kotlinx.android.synthetic.main.activity_first_fragment.edt_grade
import kotlinx.android.synthetic.main.activity_first_fragment.edt_name
import kotlinx.android.synthetic.main.activity_first_fragment.edt_phone
import kotlinx.android.synthetic.main.activity_first_fragment.edt_email
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class add_fragment : AppCompatActivity(), CoroutineScope {
    private var noteDB: DB_Student ?= null

    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_fragment_activity)
        supportActionBar?.setTitle("Thêm Sinh viên")
        mJob = Job()
        noteDB = DB_Student.getDatabase(this)
material_timepicker_cancel_button.setOnClickListener {

        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

        btn_add.setOnClickListener {
            launch {
                val strTitle: String = edt_name.text.toString()
                val strContent: String = edt_grade.text.toString()
                val strContent1: String = edt_phone.text.toString()
                val strContent2: String = edt_email.text.toString()
                if (strTitle =="" && strContent == "" && strContent1 == "" && strContent2 == "") {
                    Toast.makeText(this@add_fragment,"Bạn phải nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show()

                } else if (strTitle =="" || strContent == "" || strContent1 == "" || strContent2 == "") {
                   Toast.makeText(this@add_fragment,"Bạn phải nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show()

                } else {
                    noteDB?.studentDao()?.insert(Student_Entity(name = strTitle, grade = strContent, phone = strContent1, email = strContent2))
                    Toast.makeText(this@add_fragment,"Thêm Thành Công",Toast.LENGTH_SHORT).show()

                    finish()
                }

        }
}

}
}