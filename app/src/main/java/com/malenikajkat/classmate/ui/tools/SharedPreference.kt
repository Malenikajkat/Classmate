package com.malenikajkat.classmate.ui.tools

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(val context: Context) {

    // Имя файла SharedPreferences
    private val PREFS_NAME = "kotlincodes"

    // Инициализация SharedPreferences
    private val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    // Сохранение строки в SharedPreferences
    fun save(KEY_NAME: String, text: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_NAME, text)
        editor.commit() // Используем commit() для синхронного сохранения
    }

    // Сохранение целого числа в SharedPreferences
    fun save(KEY_NAME: String, value: Int) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putInt(KEY_NAME, value)
        editor.commit() // Используем commit() для синхронного сохранения
    }

    // Сохранение булевого значения в SharedPreferences
    fun save(KEY_NAME: String, status: Boolean) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putBoolean(KEY_NAME, status)
        editor.commit() // Используем commit() для синхронного сохранения
    }

    // Получение строки из SharedPreferences
    fun getValueString(KEY_NAME: String): String? {
        return sharedPref.getString(KEY_NAME, null) // Возвращаем строку или null, если ключ не найден
    }

    // Получение целого числа из SharedPreferences
    fun getValueInt(KEY_NAME: String): Int {
        return sharedPref.getInt(KEY_NAME, 0) // Возвращаем целое число или 0, если ключ не найден
    }

    // Получение булевого значения из SharedPreferences
    fun getValueBoolean(KEY_NAME: String, defaultValue: Boolean): Boolean {
        return sharedPref.getBoolean(KEY_NAME, defaultValue) // Возвращаем булево значение или defaultValue, если ключ не найден
    }

    // Очистка всех записей в SharedPreferences
    fun clearSharedPreference() {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.clear()
        editor.commit() // Используем commit() для синхронного удаления всех записей
    }

    // Удаление конкретного значения из SharedPreferences
    fun removeValue(KEY_NAME: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove(KEY_NAME)
        editor.commit() // Используем commit() для синхронного удаления записи
    }
}
