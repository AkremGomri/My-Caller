package issat.akrem.myapplication;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class UserHelper extends SQLiteOpenHelper {

    public static final String table_user="ContactUsers";
    public static final String col_id="ID";
    public static final String col_nom="Nom";
    public static final String col_prenom="Prenom";
    public static final String col_numero="numero";

    String requete="CREATE TABLE "+table_user+" ("+col_id+" Integer Primary Key AutoIncrement, "+col_nom+" TEXT NOT NULL, "+col_prenom+" TEXT NOT NULL, "+col_numero+" TEXT NOT NULL)";

    public UserHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Lors de louverture de la base pour la première fois
        db.execSQL(requete);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE "+table_user);
        onCreate(db);
    }
}

