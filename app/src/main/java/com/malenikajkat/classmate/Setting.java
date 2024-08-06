package com.malenikajkat.classmate;

import androidx.fragment.app.Fragment;

import java.util.Locale;

public class Setting extends Fragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Получаем ListView
        ListView settingsList = view.findViewById(R.id.settings_list);

        settingsList.setOnItemClickListener((parent, view1, position, id) -> {
            switch (position) {
                case 0: // Язык приложения
                    // Изменяем язык приложения на выбранный
                    Locale locale = new Locale("en"); // Английский язык
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getActivity().getApplicationContext().getResources().updateConfiguration(config, getActivity().getResources().getDisplayMetrics());
                    break;
                case 1: // Тема оформления
                    // Код для изменения темы оформления
                    changeTheme();
                    break;
                case 2: // Звуковые эффекты
                    // Код для включения/отключения звуковых эффектов
                    toggleSoundEffects();
                    break;
                case 3: // Уведомления
                    // Код для управления уведомлениями
                    toggleNotifications();
                    break;
            }
        });
    }

    private void toggleNotifications() {
        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            // Код для включения/отключения уведомлений
            notificationManager.cancelAll(); // Отключаю все уведомления
        }
    }

    private void toggleSoundEffects() {
        AudioManager audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL) {
                // Код для отключения звуковых эффектов
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
            } else {
                // Код для включения звуковых эффектов
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
            }
        }
    }

    private void changeTheme() {
        // Код для изменения темы оформления
        int themeId = R.style.LightTheme; // Заменить на нужный идентификатор темы
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getActivity().setTheme(themeId);
    }

}