package fr.utt.if26.funflow;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FunFlowModel extends SQLiteOpenHelper {
    public static final String CARD_TABLE = "card_table";
    public static final String CATEGORIE_TABLE = "categorie_table";
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

    //Categorie attributes in DB
    public static final String CATEGORIE_COL_ID = "ID";
    public static final String CATEGORIE_COL_NAME = "NAME";
    public static final String CATEGORIE_COL_ICON = "ICON";

    public static final String TASK_COL_ID = "ID";
    public static final String TASK_COL_CARDID = "CARDID";
    public static final String TASK_COL_DESCRIPTION = "DESCRIPTION";
    public static final String TASK_COL_ISDONE = "IS_DONE";

    //Column position for each Table in the DB
    public static final int CARD_NUM_COL_ID = 0;
    public static final int CARD_NUM_COL_NAME = 1;
    public static final int CARD_NUM_COL_DESCRIPTION = 2;
    public static final int CARD_NUM_COL_IMAGE = 3;
    public static final int CARD_NUM_COL_RELEASE_DATE = 4;
    public static final int CARD_NUM_COL_AUTHOR = 5;
    public static final int CARD_NUM_COL_CATEGORIE = 6;
    public static final int CARD_NUM_COL_RATING = 7;

    public static final int CATEGORIE_NUM_COL_ID = 0;
    public static final int CATEGORIE_NUM_COL_NAME = 1;
    public static final int CATEGORIE_NUM_COL_ICON = 2;

    public static final int TASK_NUM_COL_ID = 0;
    public static final int TASK_NUM_COL_CARDID = 1;
    public static final int TASK_NUM_COL_DESCRIPTION = 2;
    public static final int TASK_NUM_COL_ISDONE = 3;

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
            "FOREIGN KEY(" + CARD_COL_CATEGORIE + ") REFERENCES " + CATEGORIE_TABLE + "(" + CATEGORIE_COL_ID + "));";

    private static final String CREATE_CATEGORIE_DB = "CREATE TABLE IF NOT EXISTS " + CATEGORIE_TABLE + " (" +
            CATEGORIE_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CATEGORIE_COL_NAME + " TEXT NOT NULL UNIQUE, " +
            CATEGORIE_COL_ICON + " TEXT NOT NULL);";

    private static final String CREATE_TASK_DB = "CREATE TABLE IF NOT EXISTS " + TASK_TABLE + " (" +
            TASK_COL_ID + " INTEGER PRIMARY KEY, " +
            TASK_COL_CARDID + " INTEGER, " +
            TASK_COL_DESCRIPTION + " TEXT, " +
            TASK_COL_ISDONE + " INTEGER, " +
            "FOREIGN KEY (" + TASK_COL_CARDID + ") REFERENCES " + CARD_TABLE + "(" + CARD_COL_ID + "));";

    public FunFlowModel(Context context, String dbName, SQLiteDatabase.CursorFactory factory, int version){
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
        db.execSQL("DROP TABLE " + CATEGORIE_TABLE + ";");
        db.execSQL("DROP TABLE " + TASK_TABLE + ";");

        onCreate(db);
    }
}
