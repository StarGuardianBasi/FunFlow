package fr.utt.if26.funflow.databaseAccessHub;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class    DatabaseHelper extends SQLiteOpenHelper {
    public static final String CARD_TABLE = "card_table";
    public static final String CATEGORY_TABLE = "categorie_table";
    public static final String TASK_TABLE = "task_table";

    //Card attributes in DB
    public static final String CARD_COL_ID = "ID";
    public static final String CARD_COL_NAME = "NAME";
    public static final String CARD_COL_DESCRIPTION = "DESCRIPTION";
    public static final String CARD_COL_IMAGE = "IMAGE";
    public static final String CARD_COL_RELEASE_DATE = "RELEASE_DATE";
    public static final String CARD_COL_AUTHOR = "AUTHOR";
    public static final String CARD_COL_CATEGORIE = "CATEGORIE";
    public static final String CARD_COL_RATING = "RATING";

    //Category attributes in DB
    public static final String CATEGORY_COL_ID = "ID";
    public static final String CATEGORY_COL_NAME = "NAME";
    public static final String CATEGORY_COL_ICON = "ICON";

    public static final String TASK_COL_ID = "ID";
    public static final String TASK_COL_CARDID = "CARDID";
    public static final String TASK_COL_DESCRIPTION = "DESCRIPTION";
    public static final String TASK_COL_ISDONE = "IS_DONE";

    //SQLite string command for create tables of the DB
    private static final String CREATE_CARD_DB = "CREATE TABLE IF NOT EXISTS " + CARD_TABLE + " (" +
            CARD_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CARD_COL_NAME + " TEXT NOT NULL UNIQUE, " +
            CARD_COL_DESCRIPTION + " TEXT, " +
            CARD_COL_IMAGE + " TEXT, " +
            CARD_COL_RELEASE_DATE + " TEXT, " +
            CARD_COL_AUTHOR + " TEXT NOT NULL, " +
            CARD_COL_CATEGORIE + " INTEGER, " +
            CARD_COL_RATING + " INTEGER, " +
            "FOREIGN KEY(" + CARD_COL_CATEGORIE + ") REFERENCES " + CATEGORY_TABLE + "(" + CATEGORY_COL_ID + "));";

    private static final String CREATE_CATEGORIE_DB = "CREATE TABLE IF NOT EXISTS " + CATEGORY_TABLE + " (" +
            CATEGORY_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CATEGORY_COL_NAME + " TEXT NOT NULL UNIQUE, " +
            CATEGORY_COL_ICON + " TEXT NOT NULL);";

    private static final String CREATE_TASK_DB = "CREATE TABLE IF NOT EXISTS " + TASK_TABLE + " (" +
            TASK_COL_ID + " INTEGER PRIMARY KEY, " +
            TASK_COL_CARDID + " INTEGER, " +
            TASK_COL_DESCRIPTION + " TEXT, " +
            TASK_COL_ISDONE + " INTEGER, " +
            "FOREIGN KEY (" + TASK_COL_CARDID + ") REFERENCES " + CARD_TABLE + "(" + CARD_COL_ID + "));";

    public DatabaseHelper(Context context, String dbName, SQLiteDatabase.CursorFactory factory, int version){
        super(context, dbName, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CARD_DB);
        db.execSQL(CREATE_CATEGORIE_DB);
        db.execSQL(CREATE_TASK_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + CARD_TABLE + ";");
        db.execSQL("DROP TABLE " + CATEGORY_TABLE + ";");
        db.execSQL("DROP TABLE " + TASK_TABLE + ";");

        onCreate(db);
    }
}
