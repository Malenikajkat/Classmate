package com.malenikajkat.classmate.ui.chat

import androidx.lifecycle.*
import com.malenikajkat.classmate.data.db.entity.Chat
import com.malenikajkat.classmate.data.db.entity.Message
import com.malenikajkat.classmate.data.db.entity.UserInfo
import com.malenikajkat.classmate.data.db.remote.FirebaseReferenceChildObserver
import com.malenikajkat.classmate.data.db.remote.FirebaseReferenceValueObserver
import com.malenikajkat.classmate.data.db.repository.DatabaseRepository
import com.malenikajkat.classmate.ui.DefaultViewModel
import com.malenikajkat.classmate.data.Result
import com.malenikajkat.classmate.util.addNewItem

// Фабрика для создания экземпляра ChatViewModel
class ChatViewModelFactory(private val myUserID: String, private val otherUserID: String, private val chatID: String) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChatViewModel(myUserID, otherUserID, chatID) as T
    }
}

// Основной ViewModel для чата
class ChatViewModel(private val myUserID: String, private val otherUserID: String, private val chatID: String) : DefaultViewModel() {

    // Репозиторий для доступа к базе данных
    private val dbRepository: DatabaseRepository = DatabaseRepository()

    // LiveData для информации о другом пользователе и для добавленных сообщений
    private val _otherUser: MutableLiveData<UserInfo> = MutableLiveData()
    private val _addedMessage = MutableLiveData<Message>()

    // Обсерверы для Firebase
    private val fbRefMessagesChildObserver = FirebaseReferenceChildObserver()
    private val fbRefUserInfoObserver = FirebaseReferenceValueObserver()

    // MediatorLiveData для списка сообщений и LiveData для текста нового сообщения
    val messagesList = MediatorLiveData<MutableList<Message>>()
    val newMessageText = MutableLiveData<String?>()
    val otherUser: LiveData<UserInfo> = _otherUser

    init {
        setupChat()
        checkAndUpdateLastMessageSeen()
    }

    override fun onCleared() {
        super.onCleared()
        // Очищаем обсерверы при очищении ViewModel
        fbRefMessagesChildObserver.clear()
        fbRefUserInfoObserver.clear()
    }

    // Проверка и обновление статуса последнего сообщения
    private fun checkAndUpdateLastMessageSeen() {
        dbRepository.loadChat(chatID) { result: Result<Chat> ->
            if (result is Result.Success && result.data != null) {
                result.data.lastMessage.let {
                    if (!it.seen && it.senderID != myUserID) {
                        it.seen = true
                        dbRepository.updateChatLastMessage(chatID, it)
                    }
                }
            }
        }
    }

    // Настройка чата, загрузка информации о другом пользователе
    private fun setupChat() {
        dbRepository.loadAndObserveUserInfo(otherUserID, fbRefUserInfoObserver) { result: Result<UserInfo> ->
            onResult(_otherUser, result)
            if (result is Result.Success && !fbRefMessagesChildObserver.isObserving()) {
                loadAndObserveNewMessages()
            }
        }
    }

    // Загрузка и наблюдение за новыми сообщениями
    private fun loadAndObserveNewMessages() {
        messagesList.addSource(_addedMessage) { messagesList.addNewItem(it) }

        dbRepository.loadAndObserveMessagesAdded(
            chatID,
            fbRefMessagesChildObserver
        ) { result: Result<Message> ->
            onResult(_addedMessage, result)
        }
    }

    // Отправка нового сообщения
    fun sendMessagePressed() {
        if (!newMessageText.value.isNullOrBlank()) {
            val newMsg = Message(myUserID, newMessageText.value!!)
            dbRepository.updateNewMessage(chatID, newMsg)
            dbRepository.updateChatLastMessage(chatID, newMsg)
            newMessageText.value = null
        }
    }
}