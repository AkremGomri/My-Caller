package issat.akrem.myapplication.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserHelper extends SQLiteOpenHelper {
    public static final String table_name = "User";
    public static final String col_id = "Id";
    public static final String col_email = "email";
    public static final String col_password = "password";
    public static final String col_salt = "salt";

    String requete = "CREATE TABLE " + table_name + " (" + col_id + " Integer Primary Key AutoIncrement, " + col_email + " TEXT NOT NULL ," + col_password + " TEXT NOT NULL ," + col_salt + " TEXT NOT NULL )";

    public UserHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(requete);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + table_name);
        db.execSQL(requete);
    }
}
