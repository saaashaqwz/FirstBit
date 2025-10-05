package com.example.firstbit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * основная активность приложения
 * (предоставляет пользовательский интерфейс для перехода к экранам регистрации или авторизации)
 */
public class MainActivity extends AppCompatActivity {

    /**
     * вызывается при первом создании активности
     * (инициализирует пользовательский интерфейс, настраивает обработчики событий для кнопок
     * и обрабатывает отступы для отображения от края до края)
     *
     * @param savedInstanceState Если активность перезапускается после предыдущего завершения,
     *                           этот Bundle содержит данные, которые она недавно предоставила в onSaveInstanceState(Bundle)
     *                           (в противном случае он равен null)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button buttonLogin = findViewById(R.id.button_login);
        Button buttonJoin = findViewById(R.id.button_join);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            /**
             * обрабатывает событие клика по кнопке для перехода к экрану регистрации
             *
             * @param v Виджет, по которому был выполнен клик
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegActivity.class);
                startActivity(intent);
            }
        });

        buttonJoin.setOnClickListener(new View.OnClickListener() {
            /**
             * обрабатывает событие клика по кнопке для перехода к экрану авторизации
             *
             * @param v Виджет, по которому был выполнен клик
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AuthActivity.class);
                startActivity(intent);
            }
        });
    }
}