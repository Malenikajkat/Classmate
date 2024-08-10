package com.malenikajkat.classmate.ui.lessons

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.malenikajkat.classmate.R
import com.malenikajkat.classmate.model.LectureModel
import com.malenikajkat.classmate.ui.adapters.LectureAdapter
import com.malenikajkat.classmate.ui.api.NetworkService
import kotlinx.android.synthetic.main.activity_lessons.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LessonListActivity : AppCompatActivity() {

    // Переменные для хранения информации о группе и преподавателе
    private var group: Int = 0
    private var teacher: Short = 0

    // Адаптер для отображения списка лекций
    private lateinit var lectureAdapter: LectureAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lessons)

        // Установка заголовка Action Bar и кнопки назад
        supportActionBar?.title = "Список"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Инициализация экрана, получение данных и загрузка списка лекций
        showProgressBar()
        getData()
        initViews()
        listOfLectures()
    }

    // Обработка нажатия кнопки назад в Action Bar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    // Получение данных из Intent
    private fun getData() {
        group = intent.getIntExtra("group", 0)
        teacher = intent.getShortExtra("teacher", 0)
        Toast.makeText(this, " $group $teacher", Toast.LENGTH_LONG).show()
    }

    // Инициализация RecyclerView и адаптера
    private fun initViews() {
        lectureAdapter = LectureAdapter { openActivity(it) }
        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)

        with(rec_lesson) {
            adapter = lectureAdapter
            layoutManager = LinearLayoutManager(this@LessonListActivity)
            addItemDecoration(divider)
        }
    }

    // Открытие активности для создания новой лекции
    private fun openActivity(lectureModel: LectureModel) {
        val intent = Intent(this, NewLessonActivity::class.java)
        intent.putExtra("lecture", lectureModel)
        startActivity(intent)
    }

    // Загрузка списка лекций с сервера
    private fun listOfLectures() {
        val settings = getSharedPreferences("Test", 0)
        val token = settings?.getString(getString(R.string.secret_token), "") ?: ""
        Log.e("Token", token)

        NetworkService(token)
            .lectureApi
            .getLectures(group, teacher)
            .enqueue(object : Callback<List<LectureModel>> {
                override fun onFailure(call: Call<List<LectureModel>>, t: Throwable) {
                    Log.e("Fail retrofit", t.localizedMessage ?: "Unexpected error")
                }

                override fun onResponse(
                    call: Call<List<LectureModel>>,
                    response: Response<List<LectureModel>>
                ) {
                    if (response.body()?.isEmpty() != false) {
                        text_if_empty.visibility = View.VISIBLE
                    } else {
                        text_if_empty.visibility = View.GONE
                        response.body()?.let { lectureAdapter.updateData(it) }
                    }
                    hideProgressBar()
                }
            })
    }

    // Показать индикатор загрузки
    private fun showProgressBar() {
        lesson_progress_bar.visibility = View.VISIBLE
    }

    // Спрятать индикатор загрузки
    private fun hideProgressBar() {
        lesson_progress_bar.visibility = View.GONE
    }
}
