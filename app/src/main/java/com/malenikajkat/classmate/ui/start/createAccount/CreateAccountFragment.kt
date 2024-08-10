package com.malenikajkat.classmate.ui.start.createAccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.malenikajkat.classmate.data.EventObserver
import com.malenikajkat.classmate.R
import com.malenikajkat.classmate.databinding.FragmentCreateAccountBinding
import com.malenikajkat.classmate.ui.main.MainActivity
import com.malenikajkat.classmate.util.SharedPreferencesUtil
import com.malenikajkat.classmate.util.forceHideKeyboard
import com.malenikajkat.classmate.util.showSnackBar

// Определение фрагмента для создания аккаунта
class CreateAccountFragment : Fragment() {

    // Инициализация ViewModel для фрагмента
    private val viewModel by viewModels<CreateAccountViewModel>()
    private lateinit var viewDataBinding: FragmentCreateAccountBinding

    // Создание представления фрагмента
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Инфлейт макета и привязка ViewModel к датабиндингу
        viewDataBinding = FragmentCreateAccountBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }
        // Назначение lifecycleOwner для датабиндинга
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        // Установка флага, чтобы фрагмент имел меню опций
        setHasOptionsMenu(true)
        return viewDataBinding.root // Возвращаем корень макета
    }

    // Выполняется после создания активности
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Настраиваем наблюдателей для ViewModel
        setupObservers()
    }

    // Обработка выбора пунктов меню
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> { // Обработка нажатия на кнопку "назад" в Action Bar
                findNavController().popBackStack() // Возвращаемся к предыдущему фрагменту
                return true
            }
        }
        return super.onOptionsItemSelected(item) // Дефолтная обработка меню
    }

    // Настройка наблюдателей для LiveData из ViewModel
    private fun setupObservers() {
        viewModel.dataLoading.observe(viewLifecycleOwner,
            EventObserver { (activity as MainActivity).showGlobalProgressBar(it) })

        viewModel.snackBarText.observe(viewLifecycleOwner,
            EventObserver { text ->
                view?.showSnackBar(text)
                view?.forceHideKeyboard()
            })

        viewModel.isCreatedEvent.observe(viewLifecycleOwner, EventObserver {
            // Сохраняем ID пользователя в SharedPreferences
            SharedPreferencesUtil.saveUserID(requireContext(), it.uid)
            // Переходим к экрану чатов
            navigateToChats()
        })
    }

    // Навигация к экрану чатов
    private fun navigateToChats() {
        findNavController().navigate(R.id.action_createAccountFragment_to_navigation_chats)
    }
}