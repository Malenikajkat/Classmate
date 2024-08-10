package com.malenikajkat.classmate.ui.tools

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

// Класс ToolsFragment наследует Fragment, что позволяет использовать его как фрагмент в приложении
class ToolsFragment : Fragment() {

    // Метод onCreateView вызывается при создании представления фрагмента
    override fun onCreateView(
        inflater: LayoutInflater,           // Объект для раздувания макета
        container: ViewGroup?,              // Группа представлений, к которой будет присоединено представление фрагмента
        savedInstanceState: Bundle?         // Состояние фрагмента, если оно было сохранено перед уничтожением
    ): View? {
        // "Раздувание" макета из XML файла fragment_tools
        val root = inflater.inflate(R.layout.fragment_tools, container, false)

        // Найти TextView в раздуваемом макете по его идентификатору
        val textView: TextView = root.findViewById(R.id.text_tools)

        // Возвращаем корневое представление для отображения
        return root
    }
}