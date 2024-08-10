package com.malenikajkat.classmate.ui.calendar;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;

public class CalendarFragment {
    private Context mContext;
    private ContentResolver mContentResolver;

    public CalendarFragment(Context context) {
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
    }

    public void manageCalendar() {
        // Получаем URI к таблице календарей
        Uri calendars = CalendarContract.Calendars.CONTENT_URI;

        // Выполняем запрос для получения всех календарей
        Cursor cursor = mContentResolver.query(calendars, null, null, null, null);

        if (cursor != null) {
            try {
                // Делаем что-то с курсором, если нужно
                while (cursor.moveToNext()) {
                    int nameIndex = cursor.getColumnIndex(CalendarContract.Calendars.NAME);
                    if (nameIndex >= 0) {
                        String calendarName = cursor.getString(nameIndex);
                        // Обработка данных календаря
                    }
                }
            } finally {
                cursor.close();
            }
        }

        // Создание нового события
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.TITLE, "Новое событие");
        values.put(CalendarContract.Events.EVENT_LOCATION, "Место проведения");
        values.put(CalendarContract.Events.DTSTART, System.currentTimeMillis());
        values.put(CalendarContract.Events.DTEND, System.currentTimeMillis() + 3600000); // 1 час
        values.put(CalendarContract.Events.HAS_ALARM, 1);
        values.put(CalendarContract.Events.ALL_DAY, 0);
        // Для создание события нужно указать ID календаря
        values.put(CalendarContract.Events.CALENDAR_ID, 1);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, "UTC");

        // Вставка нового события в календарь
        Uri eventUri = mContentResolver.insert(CalendarContract.Events.CONTENT_URI, values);
        long eventId = Long.parseLong(eventUri.getLastPathSegment());

        // Чтение всех событий
        Cursor eventsCursor = mContentResolver.query(CalendarContract.Events.CONTENT_URI, null, null, null, null);
        if (eventsCursor != null && eventsCursor.moveToFirst()) {
            try {
                do {
                    int titleIndex = eventsCursor.getColumnIndex(CalendarContract.Events.TITLE);
                    int locationIndex = eventsCursor.getColumnIndex(CalendarContract.Events.EVENT_LOCATION);
                    int startTimeIndex = eventsCursor.getColumnIndex(CalendarContract.Events.DTSTART);
                    int endTimeIndex = eventsCursor.getColumnIndex(CalendarContract.Events.DTEND);

                    // Проверка корректности индексов
                    if (titleIndex >= 0 && locationIndex >= 0 && startTimeIndex >= 0 && endTimeIndex >= 0) {
                        String title = eventsCursor.getString(titleIndex);
                        String location = eventsCursor.getString(locationIndex);
                        long startTime = eventsCursor.getLong(startTimeIndex);
                        long endTime = eventsCursor.getLong(endTimeIndex);
                        // Обработка результатов
                    }
                } while (eventsCursor.moveToNext());
            } finally {
                eventsCursor.close();
            }
        }

        // Обновление созданного события
        ContentValues updateValues = new ContentValues();
        updateValues.put(CalendarContract.Events.TITLE, "Обновленное событие");
        updateValues.put(CalendarContract.Events.EVENT_LOCATION, "Новое место проведения");

        int rowsUpdated = mContentResolver.update(
                CalendarContract.Events.CONTENT_URI,
                updateValues,
                CalendarContract.Events._ID + "=?",
                new String[]{String.valueOf(eventId)}
        );

        // Удаление созданного события
        int rowsDeleted = mContentResolver.delete(
                CalendarContract.Events.CONTENT_URI,
                CalendarContract.Events._ID + "=?",
                new String[]{String.valueOf(eventId)}
        );
    }
}