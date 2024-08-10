package com.malenikajkat.classmate.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.malenikajkat.classmate.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    // Переменная _binding объявлена nullable чтобы избежать утечки памяти
    private var _binding: FragmentHomeBinding? = null

    // К свойству binding можно безопасно обращаться только после onCreateView и до onDestroyView
    private val binding get() = _binding!!

    // Здесь создается и возвращается фрагментное представление
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Инициализация ViewModel
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        // Инициализация класса binding для текущего фрагмента
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Поиск TextView элемента через binding и подписка на изменения данных в ViewModel
        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        // Возвращаем корневой вид
        return root
    }

    // Очищаем binding при уничтожении вида, чтобы избежать утечки памяти
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}