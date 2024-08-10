package com.malenikajkat.classmate.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.malenikajkat.classmate.R
import com.malenikajkat.classmate.model.LectureModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_lesson.view.*

// Адаптер для отображения списка лекций с использованием RecyclerView
class LectureAdapter(val listener: (LectureModel) -> Unit) : RecyclerView.Adapter<LectureAdapter.LectureViewHolder>() {

    // Хранение текущего списка лекций
    var items: List<LectureModel> = listOf()

    // Создание ViewHolder для RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LectureViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val convertView = inflater.inflate(R.layout.item_lesson, parent, false)
        return LectureViewHolder(convertView)
    }

    // Обновление данных в адаптере с расчетом разницы между старым и новым списками
    fun updateData(data: List<LectureModel>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean =
                items[oldPos].Nom == data[newPos].Nom

            override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean =
                items[oldPos].hashCode() == data[newPos].hashCode()

            override fun getOldListSize(): Int = items.size

            override fun getNewListSize(): Int = data.size
        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
        items = data
        notifyDataSetChanged()
    }

    // Получение количества элементов в списке
    override fun getItemCount(): Int = items.size

    // Привязка данных к ViewHolder
    override fun onBindViewHolder(holder: LectureViewHolder, position: Int) =
        holder.bind(items[position], listener)

    // ViewHolder для отображения одной лекции
    inner class LectureViewHolder(convertView: View) : RecyclerView.ViewHolder(convertView), LayoutContainer {
        override val containerView: View?
            get() = itemView

        // Связывание данных лекции с элементами UI и установка слушателя нажатий
        fun bind(lecture: LectureModel, listener: (LectureModel) -> Unit) {
            itemView.item_lesson_data.text = lecture.Day
            val group = "${lecture.groupNav.courseNavigation!!.fullName} ${lecture.groupNav.grup}"
            itemView.item_lesson_group.text = group
            itemView.item_lesson_theme.text = lecture.themeNav.name
            itemView.setOnClickListener {
                listener.invoke(lecture)
            }
        }
    }
}