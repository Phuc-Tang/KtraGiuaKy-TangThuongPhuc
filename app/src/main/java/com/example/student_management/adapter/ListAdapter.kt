package com.example.student_management.adapter

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.student_management.R
import com.example.student_management.database.DB_Student
import com.example.student_management.edit_fragment
import com.example.student_management.model.Student_Entity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.reflect.Method


class ListAdapter
internal constructor(val context: Context, val noteDB: DB_Student)

    :RecyclerView.Adapter<ListAdapter.ItemViewHolder>() {

  private  val MY_REREQUEST_CODE:Int =10

    private var notes = emptyList<Student_Entity>()

    private val job = Job()
  fun setNote(notes: List<Student_Entity>) {
        this.notes = notes
        notifyDataSetChanged()
    }
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.rs_name)
        val textView1: TextView = view.findViewById(R.id.rs_grade)
        val textView2: TextView = view.findViewById(R.id.rs_phone)
        val textView3: TextView = view.findViewById(R.id.rs_email)
        val imageView : ImageView = view.findViewById(R.id.imv_menu_option)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.item_fragment_activity, parent, false)
        return ItemViewHolder(adapterLayout)

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentNote = notes[position]

        holder.textView.text =context.getString(R.string.name,currentNote.name.toString())
        holder.textView1.text = context.getString(R.string.grade,currentNote.grade.toString())
        holder.textView2.text = context.getString(R.string.phone,currentNote.phone.toString())
        holder.textView3.text = context.getString(R.string.email,currentNote.email.toString())



        holder.imageView.setOnClickListener {
            val popupMenu = PopupMenu(context,it)
            popupMenu.setOnMenuItemClickListener { Item ->
                when (Item.itemId) {
                    R.id.item1 -> {
                        Toast.makeText(context, "Đã ấn vào sửa", Toast.LENGTH_SHORT).show()
                        uiScope.launch {
//
               val intent = Intent(context,edit_fragment::class.java)
                val bundle = Bundle()
                bundle.putSerializable("student_id",currentNote.id)
                bundle.putSerializable("student_name",currentNote.name)
                bundle.putSerializable("student_grade",currentNote.grade)
                bundle.putSerializable("student_phone",currentNote.phone)
                bundle.putSerializable("student_email",currentNote.email)
                intent.putExtras(bundle)
                startActivity(context,intent,bundle)
//                startActivityForResult(fragment2(),intent,MY_REREQUEST_CODE,bundle)

            }
                       return@setOnMenuItemClickListener true

                    }
                    R.id.item2 -> {
                        MaterialAlertDialogBuilder(context,
                            R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog)
                            .setIcon(R.drawable.ic_baseline_warning_24)
                            .setTitle("Thông Báo")
                            .setMessage("Bạn có chắc chắn muốn xoá sinh viên này")
                            .setNegativeButton("Huỷ") { dialog, which ->
                                // Respond to negative button press
                                return@setNegativeButton
                            }
                            .setPositiveButton("Đồng ý") { dialog, whichs ->
                                uiScope.launch {
                                    noteDB?.studentDao()?.delete(currentNote)
                                    notes = noteDB?.studentDao()?.GetAll()

                                    Toast.makeText(context, "Xoá Thành Công", Toast.LENGTH_SHORT).show()
                                    notifyDataSetChanged()
                                }
                            }
                            .show()
                        return@setOnMenuItemClickListener true


                    }
                    else -> false
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                popupMenu.setForceShowIcon(true)
            } else {
                try {
                    val fields = popupMenu.javaClass.declaredFields
                    for (field in fields) {
                        if ("mPopup" == field.name) {
                            field.isAccessible = true
                            val menuPopupHelper = field[popupMenu]
                            val classPopupHelper =
                                Class.forName(menuPopupHelper.javaClass.name)
                            val setForceIcons: Method = classPopupHelper.getMethod(
                                "setForceShowIcon",
                                Boolean::class.javaPrimitiveType
                            )
                            setForceIcons.invoke(menuPopupHelper, true)
                            break
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            popupMenu.inflate(R.menu.menu_fragment_activity)
            popupMenu.show()

        }

    }




    override fun getItemCount(): Int {
      return notes.size
    }

}