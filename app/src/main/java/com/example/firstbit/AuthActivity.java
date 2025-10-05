package com.example.firstbit;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * активность для аутентификации пользователя
 * (предоставляет пользовательский интерфейс для ввода логина и пароля, аутентификации и перехода к экрану регистрации)
 */
public class AuthActivity extends AppCompatActivity {

    /**
     * вызывается при первом создании активности
     * (инициализирует пользовательский интерфейс, настраивает обработчики событий для кнопки аутентификации
     * и ссылки на регистрацию, а также обрабатывает отступы для отображения от края до края)
     *
     * @param savedInstanceState Если активность перезапускается после предыдущего завершения,
     *                           этот Bundle содержит данные, которые она недавно предоставила в onSaveInstanceState(Bundle)
     *                           (в противном случае он равен null)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_auth);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText userLogin = findViewById(R.id.user_login_auth);
        EditText userPassword = findViewById(R.id.user_password_auth);
        Button button = findViewById(R.id.button_auth);
        TextView linkToReg = findViewById(R.id.link_to_reg);

        linkToReg.setOnClickListener(new View.OnClickListener() {
            /**
             * обрабатывает событие клика по ссылке для перехода к экрану регистрации
             *
             * @param v Виджет, по которому был выполнен клик
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AuthActivity.this, RegActivity.class);
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            /**
             * обрабатывает событие клика по кнопке аутентификации
             * (проверяет введенные логин и пароль, выполняет аутентификацию через базу данных
             * и отображает соответствующие сообщения)
             *
             * @param v Виджет, по которому был выполнен клик
             */
            @Override
            public void onClick(View v) {
                String login = userLogin.getText().toString().trim();
                String password = userPassword.getText().toString().trim();

                if (login.isEmpty() || password.isEmpty()) {
                    Toast.makeText(AuthActivity.this, "Не все поля заполенны", Toast.LENGTH_SHORT).show();
                }
                else {
                    DbHelper db = new DbHelper(AuthActivity.this, null);
                    boolean isAuth = db.getUser(login, password);

                    if (isAuth) {
                        Toast.makeText(AuthActivity.this, "Пользователь " + login + " авторизован", Toast.LENGTH_SHORT).show();
                        userLogin.getText().clear();
                        userPassword.getText().clear();
                    }
                    else {
                        Toast.makeText(AuthActivity.this, "Пользователь " + login + " НЕ авторизован", Toast.LENGTH_SHORT).show();
                        Toast.makeText(AuthActivity.this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}