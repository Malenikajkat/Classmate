package com.malenikajkat.classmate.ui.chats

import androidx.lifecycle.*
import com.malenikajkat.classmate.data.Event
import com.malenikajkat.classmate.data.Result
import com.malenikajkat.classmate.data.db.entity.Chat
import com.malenikajkat.classmate.data.model.ChatWithUserInfo
import com.malenikajkat.classmate.data.db.entity.UserFriend
import com.malenikajkat.classmate.data.db.entity.UserInfo
import com.malenikajkat.classmate.data.db.remote.FirebaseReferenceValueObserver
import com.malenikajkat.classmate.data.db.repository.DatabaseRepository
import com.malenikajkat.classmate.ui.DefaultViewModel
import com.malenikajkat.classmate.util.addNewItem
import com.malenikajkat.classmate.util.convertTwoUserIDs
import com.malenikajkat.classmate.util.updateItemAt

class ChatsViewModel(val myUserID: String) : DefaultViewModel() {
    private val repository: DatabaseRepository = DatabaseRepository()
    private val firebaseReferenceObserverList = ArrayList<FirebaseReferenceValueObserver>()
    private val _updatedChatWithUserInfo = MutableLiveData<ChatWithUserInfo>()
    private val _selectedChat = MutableLiveData<Event<ChatWithUserInfo>>()

    val selectedChat: LiveData<Event<ChatWithUserInfo>> = _selectedChat
    val chatsList = MediatorLiveData<MutableList<ChatWithUserInfo>>()

    init {
        initListeners()
        loadFriends()
    }

    private fun initListeners() {
        chatsList.addSource(_updatedChatWithUserInfo) { newChat ->
            val chat = chatsList.value?.find { it.mChat.info.id == newChat.mChat.info.id }
            if (chat == null) {
                chatsList.addNewItem(newChat)
            } else {
                chatsList.updateItemAt(newChat, chatsList.value!!.indexOf(chat))
            }
        }
    }

    private fun loadFriends() {
        // загрузка друзей и последующая загрузка информации о пользователе
    }

    private fun loadUserInfo(userFriend: UserFriend) {
        // загрузка информации о пользователе и наблюдение за чатом
    }

    fun selectChatWithUserInfoPressed(chat: ChatWithUserInfo) {
        _selectedChat.value = Event(chat)
    }
}