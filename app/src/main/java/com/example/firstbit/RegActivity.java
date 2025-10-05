package com.example.firstbit;

import android.content.Intent;
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
 * активность для регистрации нового пользователя
 * (предоставляет пользовательский интерфейс для ввода логина, электронной почты и пароля,
 * регистрации и перехода к экрану авторизации)
 */
public class RegActivity extends AppCompatActivity {

    /**
     * вызывается при первом создании активности
     * (инициализирует пользовательский интерфейс, настраивает обработчики событий для кнопки регистрации
     * и ссылки на авторизацию, а также обрабатывает отступы для отображения от края до края)
     *
     * @param savedInstanceState Если активность перезапускается после предыдущего завершения,
     *                           этот Bundle содержит данные, которые она недавно предоставила в onSaveInstanceState(Bundle)
     *                           (в противном случае он равен null)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reg);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText userLogin = findViewById(R.id.user_login);
        EditText userEmail = findViewById(R.id.user_email);
        EditText userPassword = findViewById(R.id.user_password);
        Button button = findViewById(R.id.button_reg);
        TextView linkToAuth = findViewById(R.id.link_to_auth);

        DbHelper db = new DbHelper(RegActivity.this, null);

        linkToAuth.setOnClickListener(new View.OnClickListener() {
            /**
             * обрабатывает событие клика по ссылке для перехода к экрану авторизации
             *
             * @param v Виджет, по которому был выполнен клик.
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegActivity.this, AuthActivity.class);
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            /**
             * обрабатывает событие клика по кнопке регистрации
             * (проверяет введенные данные, регистрирует пользователя в базе данных, если данные уникальны,
             * и отображает соответствующие сообщения)
             *
             * @param v Виджет, по которому был выполнен клик.
             */
            @Override
            public void onClick(View v) {
                String login = userLogin.getText().toString().trim();
                String email = userEmail.getText().toString().trim();
                String password = userPassword.getText().toString().trim();

                if (login.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegActivity.this, "Не все поля заполенны", Toast.LENGTH_SHORT).show();
                }
                else if (db.isLoginExists(login) || db.isEmailExists(email)) {
                    Toast.makeText(RegActivity.this, "Такой пользователь уже существует", Toast.LENGTH_SHORT).show();
                }
                else {
                    User user = new User(login, email, password);

                    db.addUser(user);
                    Toast.makeText(RegActivity.this, "Пользователь " + login + " зарегистрирован", Toast.LENGTH_SHORT).show();

                    userLogin.getText().clear();
                    userEmail.getText().clear();
                    userPassword.getText().clear();

                }
            }
        });
    }
}