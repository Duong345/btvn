package vn.edu.hust.studentman

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

  private val students = mutableListOf(
    StudentModel("Nguyen Van A", "12345123123123"),
    StudentModel("Le Van C", "67890"),
    StudentModel("Lê Hoàng Cường", "SV003")
    StudentModel("Trần Thị Bảo", "SV002"),
    StudentModel("Lê Hoàng Cường", "SV003")
    StudentModel("Nguyễn Văn An", "SV001"),
    StudentModel("Trần Thị Bảo", "SV002"),
    StudentModel("Lê Hoàng Cường", "SV003")
    StudentModel("Nguyễn Văn An", "SV001"),
    StudentModel("Trần Thị Bảo", "SV002"),
    StudentModel("Lê Hoàng Cường", "SV003")
  )
  private lateinit var listView: ListView
  private lateinit var adapter: ArrayAdapter<String>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    listView = findViewById(R.id.list_view_students)
    adapter = ArrayAdapter(
      this,
      android.R.layout.simple_list_item_1,
      students.map { "${it.studentName} (${it.studentId})" }
    )
    listView.adapter = adapter

    // Đăng ký context menu cho ListView
    registerForContextMenu(listView)

    // Xử lý sự kiện click vào một mục trong danh sách
    listView.setOnItemClickListener { _, _, position, _ ->
      editStudent(position)
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.main_menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.menu_add_new -> {
        startActivity(Intent(this, AddEditStudentActivity::class.java))
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  override fun onCreateContextMenu(
    menu: ContextMenu?,
    v: View?,
    menuInfo: ContextMenu.ContextMenuInfo?
  ) {
    super.onCreateContextMenu(menu, v, menuInfo)
    menuInflater.inflate(R.menu.context_menu, menu)
  }

  override fun onContextItemSelected(item: MenuItem): Boolean {
    val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
    val position = info.position

    return when (item.itemId) {
      R.id.menu_edit -> {
        editStudent(position)
        true
      }
      R.id.menu_remove -> {
        removeStudent(position)
        true
      }
      else -> super.onContextItemSelected(item)
    }
  }

  private fun editStudent(position: Int) {
    val intent = Intent(this, AddEditStudentActivity::class.java).apply {
      putExtra("name", students[position].studentName)
      putExtra("id", students[position].studentId)
      putExtra("position", position)
    }
    startActivity(intent)
  }

  private fun removeStudent(position: Int) {
    AlertDialog.Builder(this)
      .setMessage("Are you sure you want to delete this student?")
      .setPositiveButton("Yes") { _, _ ->
        students.removeAt(position)
        adapter.notifyDataSetChanged()
      }
      .setNegativeButton("No", null)
      .show()
  }
}
