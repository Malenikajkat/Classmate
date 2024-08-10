package com.malenikajkat.classmate.ui.users
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.malenikajkat.classmate.ui.users.UsersAdapter  // Импортируйте ваш адаптер
import com.malenikajkat.classmate.data.User  // Импортируйте вашу модель данных

// Создаем BindingAdapter для bind_users_list
@BindingAdapter("bind_users_list")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<User>?) {
    val adapter = recyclerView.adapter as UsersAdapter
    adapter.submitList(data)
}