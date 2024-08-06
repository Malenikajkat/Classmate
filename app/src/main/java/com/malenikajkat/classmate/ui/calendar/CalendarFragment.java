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

    // Используем ContentResolver для работы с календарем
    Uri calendars = CalendarContract.Calendars.CONTENT_URI;
    Cursor cursor = mContentResolver.query(calendars, null, null, null, null);

    // Создание нового события
    ContentValues values = new ContentValues();
    values.put(CalendarContract.Events.TITLE, "Новое событие");
    values.put(CalendarContract.Events.EVENT_LOCATION, "Место проведения");
    values.put(CalendarContract.Events.DTSTART, System.currentTimeMillis());
    values.put(CalendarContract.Events.DTEND, System.currentTimeMillis() + 3600000); // 1 час
    values.put(CalendarContract.Events.HAS_ALARM, 1);
    values.put(CalendarContract.Events.ALL_DAY, 0);
    long eventId = mContentResolver.insert(CalendarContract.Events.CONTENT_URI, values);

    // Чтение событий
    Cursor eventsCursor = mContentResolver.query(CalendarContract.Events.CONTENT_URI, null, null, null, null);
    if (eventsCursor != null && eventsCursor.moveToFirst()) {
        do {
            String title = eventsCursor.getString(eventsCursor.getColumnIndex(CalendarContract.Events.TITLE));
            String location = eventsCursor.getString(eventsCursor.getColumnIndex(CalendarContract.Events.EVENT_LOCATION));
            long startTime = eventsCursor.getLong(eventsCursor.getColumnIndex(CalendarContract.Events.DTSTART));
            long endTime = eventsCursor.getLong(eventsCursor.getColumnIndex(CalendarContract.Events.DTEND));
            // Обработка результатов
        } while (eventsCursor.moveToNext());
    }
    eventsCursor.close();

    // Обновление события
    ContentValues updateValues = new ContentValues();
    updateValues.put(CalendarContract.Events.TITLE, "Обновленное событие");
    updateValues.put(CalendarContract.Events.EVENT_LOCATION, "Новое место проведения");
    int rowsUpdated = mContentResolver.update(CalendarContract.Events.CONTENT_URI, updateValues, "_id=?", new String[]{String.valueOf(eventId)});

    // Удаление события
    int rowsDeleted = mContentResolver.delete(CalendarContract.Events.CONTENT_URI, "_id=?", new String[]{String.valueOf(eventId)});

    // Закрываем курсор
    cursor.close();
}