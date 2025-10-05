package com.example.firstbit;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * вспомогательный класс для работы с базой данных SQLite
 */
public class DbHelper extends SQLiteOpenHelper {

    /**
     * вонструктор для создания экземпляра DbHelper
     *
     * @param context Контекст приложения
     * @param factory Фабрика курсоров, используется для создания объектов Cursor.
     */
    public DbHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, "app", factory, 1);
    }

    /**
     * вызывается при создании базы данных
     *
     * @param db Объект базы данных SQLite
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE users (id INTEGER PRIMARY KEY, login TEXT, email TEXT, password TEXT)";
        db.execSQL(query);
    }

    /**
     * вызывается при обновлении версии базы данных
     * (удаляет существующую таблицу и создает новую)
     *
     * @param db         Объект базы данных SQLite
     * @param oldVersion Старая версия базы данных
     * @param newVersion Новая версия базы данных
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    /**
     * добавляет нового пользователя в базу данных
     *
     * @param user Объект пользователя, содержащий логин, электронную почту и пароль
     */
    public void addUser(User user) {
        ContentValues values = new ContentValues();
        values.put("login", user.getLogin());
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("users", null, values);

        db.close();
    }

    /**
     * проверяет, существует ли пользователь с указанным логином и паролем
     *
     * @param login    Логин пользователя
     * @param password Пароль пользователя
     * @return true, если пользователь существует, иначе false
     */
    public boolean getUser(String login, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor result = db.rawQuery("SELECT * FROM users WHERE login = ? AND password = ?", new String[]{login, password});
        return result.moveToFirst();
    }

    /**
     * проверяет, существует ли пользователь с указанным логином
     *
     * @param login Логин для проверки
     * @return true, если логин уже используется, иначе false
     */
    public boolean isLoginExists(String login) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE login = ?", new String[]{login});
        boolean exists = cursor.getCount() > 0;

        cursor.close();
        db.close();
        return exists;
    }

    /**
     * проверяет, существует ли пользователь с указанной электронной почтой
     *
     * @param email Электронная почта для проверки
     * @return true, если электронная почта уже используется, иначе false
     */
    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email = ?", new String[]{email});
        boolean exists = cursor.getCount() > 0;

        cursor.close();
        db.close();
        return exists;
    }
}

