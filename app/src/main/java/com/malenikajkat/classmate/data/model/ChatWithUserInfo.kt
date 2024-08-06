package com.malenikajkat.classmate.data.model

import com.malenikajkat.classmate.data.db.entity.Chat
import com.malenikajkat.classmate.data.db.entity.UserInfo

data class ChatWithUserInfo(
    var mChat: Chat,
    var mUserInfo: UserInfo
)
