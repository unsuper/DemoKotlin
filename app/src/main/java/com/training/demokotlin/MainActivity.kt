package com.training.demokotlin

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.training.demokotlin.adapter.StudentListAdapter
import com.training.demokotlin.databinding.ActivityMainBinding
import com.training.demokotlin.entity.Student
import com.training.demokotlin.repository.StudentViewModel
import com.training.demokotlin.repository.StudentViewModelFactory

class MainActivity : AppCompatActivity() {

    private var isUpdate: Boolean = false
    private var student: Student? = null
    private val TAG: String = "MainActivity"
    private var studentId: Int? = null

    private val studentViewModel: StudentViewModel by viewModels {
        StudentViewModelFactory((application as StudentApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

        btnOnPress(binding)

        //setup adapter và gọi cái vụ click item. Khi click vô đây thì
        //đối tượng đc click sẽ bay hết lên Edittext, isUpdate = true. Nhân lúc còn nóng
        //hãy chỉnh sửa và click cái nút duy nhất để update đối tượng.
        val adapter = StudentListAdapter(studentViewModel) { student ->
            studentId = student.id
            binding.edtName.setText(student.name)
            binding.edtClass.setText(student.classname)
            binding.edtPhone.setText(student.phone)
            binding.btnAdd.text = "Sửa"
            isUpdate = true
        }

        binding.listView.adapter = adapter
        binding.listView.layoutManager = LinearLayoutManager(this)

        studentViewModel.allStudents.observe(this) { students ->
            // update adapter sau khi có thay đổi
            students.let { adapter.submitList(it) }
        }


        setContentView(binding.root)
    }

    //Sau khi click vào cái nút duy nhất
    //Nếu isUpdate = true thì sẽ cập nhật lại đối tượng, và isUpdate = false thì sẽ thêm đối tượng mới
    private fun btnOnPress(binding: ActivityMainBinding) {
        binding.btnAdd.setOnClickListener {
            if (!isUpdate) {
                dataFromEdt(binding)
                student?.let { it1 -> studentViewModel.insertStudent(it1) }
            }else{
                dataFromEdt(binding)
                student?.let { it1 ->
                    val result = studentViewModel.updateStudent(it1)
                    Log.d(TAG, "addStudent: $result === $it1")
                    isUpdate = false
                    binding.btnAdd.text = "Thêm"
                }
            }
        }
    }

    //Nếu Edittext ko rỗng thì new 1 student sẵn sàng để add hoặc update
    private fun dataFromEdt(binding: ActivityMainBinding) {
         when {
            binding.edtName.text.toString().isEmpty() ||  binding.edtClass.text.toString()
                .isEmpty() ||  binding.edtPhone.text.toString().isEmpty() -> {
                Toast.makeText(
                    this,
                    "Nhập đủ mới thêm đc nhó",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> {
                 student = Student(
                     studentId,
                     binding.edtName.text.toString(),
                     binding.edtClass.text.toString(),
                     binding.edtPhone.text.toString(),
                )
            }
        }
    }

}