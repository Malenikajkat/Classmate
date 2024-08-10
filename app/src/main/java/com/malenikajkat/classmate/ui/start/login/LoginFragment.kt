package com.malenikajkat.classmate.ui.start.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.malenikajkat.classmate.R
import com.malenikajkat.classmate.databinding.FragmentLoginBinding
import com.malenikajkat.classmate.data.EventObserver
import com.malenikajkat.classmate.util.showSnackBar
import com.malenikajkat.classmate.ui.main.MainActivity
import com.malenikajkat.classmate.util.SharedPreferencesUtil
import com.malenikajkat.classmate.util.forceHideKeyboard

class LoginFragment : Fragment() {

    // Создаем ViewModel для LoginFragment
    private val viewModel by viewModels<LoginViewModel>()

    // Переменная для binding объектов пользователя
    private lateinit var viewDataBinding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Инициализируем viewDataBinding с fragment_login.xml
        viewDataBinding = FragmentLoginBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }
        // Устанавливаем lifecycleOwner для возможности наблюдения LiveData
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner

        // Указываем, что этот фрагмент имеет собственное меню
        setHasOptionsMenu(true)

        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Настраиваем наблюдатели для LiveData
        setupObservers()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // Обработка нажатий кнопки "назад" в action bar
            android.R.id.home -> {
                findNavController().popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupObservers() {
        // Наблюдаем за состоянием загрузки данных. Показ/скрытие глобального ProgressBar
        viewModel.dataLoading.observe(viewLifecycleOwner,
            EventObserver { isLoading ->
                (activity as MainActivity).showGlobalProgressBar(isLoading)
            })

        // Наблюдаем за сообщениями для отображения в Snackbar
        viewModel.snackBarText.observe(viewLifecycleOwner,
            EventObserver { message ->
                view?.showSnackBar(message)
                view?.forceHideKeyboard()
            })

        // Наблюдаем за событием успешного входа
        viewModel.isLoggedInEvent.observe(viewLifecycleOwner, EventObserver { user ->
            // Сохраняем ID пользователя в SharedPreferences
            SharedPreferencesUtil.saveUserID(requireContext(), user.uid)
            // Переходим к экрану чатов
            navigateToChats()
        })
    }

    private fun navigateToChats() {
        // Навигация к экрану чатов
        findNavController().navigate(R.id.action_loginFragment_to_navigation_chats)
    }
}
