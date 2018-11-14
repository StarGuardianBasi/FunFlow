package fr.utt.if26.funflow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FunFlowDBController {
    private static final String DBNAME = "funflow.db";
    private static final int VERSION = 1;

    private SQLiteDatabase db;
    private FunFlowModel model;

    public FunFlowDBController(Context context){
        model = new FunFlowModel(context, DBNAME, null, VERSION);
    }

    public void open(){
        if (!db.isOpen()) {
            db = model.getWritableDatabase();
        }
    }

    public void close(){
        if (db.isOpen()){
            db.close();
        }
    }

    public SQLiteDatabase getDataBase(){
        return db;
    }

    public long insertCategorie(Categorie categorie){
        ContentValues values = new ContentValues();
        values.put(FunFlowModel.CATEGORIE_COL_NAME, categorie.getName());
        values.put(FunFlowModel.CATEGORIE_COL_ICON, categorie.getIcon());

        return db.insert(FunFlowModel.CATEGORIE_TABLE, null, values);
    }

    public long insertCard(Card card){

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        ContentValues values = new ContentValues();
        values.put(FunFlowModel.CARD_COL_NAME, card.getName());
        values.put(FunFlowModel.CARD_COL_CATEGORIE, card.getCategorie().getId());
        values.put(FunFlowModel.CARD_COL_DESCRIPTION, card.getDescription());
        values.put(FunFlowModel.CARD_COL_IMAGE, card.getImage());
        values.put(FunFlowModel.CARD_COL_AUTHOR, card.getAuthor());
        values.put(FunFlowModel.CARD_COL_RELEASE_DATE, df.format(card.getReleaseDate()));
        values.put(FunFlowModel.CARD_COL_RATING, card.getRating());

        return db.insert(FunFlowModel.CARD_TABLE, null, values);
    }

    public long insertTask(Task task){
        ContentValues values = new ContentValues();
        values.put(FunFlowModel.TASK_COL_CARDID, task.getCardID());
        values.put(FunFlowModel.TASK_COL_DESCRIPTION, task.getDescription());
        values.put(FunFlowModel.TASK_COL_ISDONE, task.isDone());

        return db.insert(FunFlowModel.TASK_TABLE, null, values);
    }

    public int updateCategorie(Categorie categorie){
        ContentValues values = new ContentValues();

        values.put(FunFlowModel.CATEGORIE_COL_NAME, categorie.getName());
        values.put(FunFlowModel.CATEGORIE_COL_ICON, categorie.getIcon());

        return db.update(FunFlowModel.CATEGORIE_TABLE, values, FunFlowModel.CATEGORIE_COL_ID + " = " + categorie.getId(), null);
    }

    public int updateCard(Card card){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        ContentValues values = new ContentValues();
        values.put(FunFlowModel.CARD_COL_NAME, card.getName());
        values.put(FunFlowModel.CARD_COL_CATEGORIE, card.getCategorie().getId());
        values.put(FunFlowModel.CARD_COL_DESCRIPTION, card.getDescription());
        values.put(FunFlowModel.CARD_COL_IMAGE, card.getImage());
        values.put(FunFlowModel.CARD_COL_AUTHOR, card.getAuthor());
        values.put(FunFlowModel.CARD_COL_RELEASE_DATE, df.format(card.getReleaseDate()));
        values.put(FunFlowModel.CARD_COL_RATING, card.getRating());

        return db.update(FunFlowModel.CARD_TABLE, values, FunFlowModel.CARD_COL_ID + " = " + card.getId(), null);
    }

    public int updateTask(Task task){
        ContentValues values = new ContentValues();
        values.put(FunFlowModel.TASK_COL_CARDID, task.getCardID());
        values.put(FunFlowModel.TASK_COL_DESCRIPTION, task.getDescription());
        values.put(FunFlowModel.TASK_COL_ISDONE, task.isDone());

        return db.update(FunFlowModel.TASK_TABLE, values, FunFlowModel.TASK_COL_ID + " = " + task.getId(), null);
    }

    public int removeCategorie(int id){
        return db.delete(FunFlowModel.CATEGORIE_TABLE, FunFlowModel.CATEGORIE_COL_ID + " = " + id, null);
    }

    public int removeCard(int id){
        return db.delete(FunFlowModel.CARD_TABLE, FunFlowModel.CARD_COL_ID + " = " + id, null);
    }

    public int removeTask(int id){
        return db.delete(FunFlowModel.TASK_TABLE, FunFlowModel.TASK_COL_ID + " = " + id, null);
    }

    //A faire à la maison, terminer le DB controller + le App controller

    public Categorie getCategory(String name){
        Cursor c = db.query(FunFlowModel.CATEGORIE_TABLE,
                            new String[]{
                                         FunFlowModel.CATEGORIE_COL_ID,
                                         FunFlowModel.CATEGORIE_COL_NAME,
                                         FunFlowModel.CATEGORIE_COL_ICON
                            },
                    FunFlowModel.CATEGORIE_COL_NAME + " LIKE \"" + name + "\"",
                 null, null, null, null);

        return cursorCategorie(c);
    }

    public Card getCard(String name){
        Cursor c = db.query(FunFlowModel.CARD_TABLE,
                            new String[]{
                                    FunFlowModel.CARD_COL_ID,
                                    FunFlowModel.CARD_COL_NAME,
                                    FunFlowModel.CARD_COL_DESCRIPTION,
                                    FunFlowModel.CARD_COL_IMAGE,
                                    FunFlowModel.CARD_COL_RELEASE_DATE,
                                    FunFlowModel.CARD_COL_AUTHOR,
                                    FunFlowModel.CARD_COL_CATEGORIE,
                                    FunFlowModel.CARD_COL_RATING
                            },
                            FunFlowModel.CARD_COL_NAME + " LIKE \"" + name + "\"",
                            null, null, null, null);

        return cursorCard(c);
    }

    public List<Task> getTasks(int CardId){
        Cursor c = db.query(FunFlowModel.TASK_TABLE,
                            new String[]{
                                FunFlowModel.TASK_COL_ID,
                                FunFlowModel.TASK_COL_CARDID,
                                FunFlowModel.TASK_COL_DESCRIPTION,
                                FunFlowModel.TASK_COL_ISDONE
                            },
                            FunFlowModel.TASK_COL_CARDID + " = " + CardId,
                            null, null, null, null);

        return cursorTasks(c);
    }

    public Categorie getCategorieByID(int id){
        Cursor c = db.query(FunFlowModel.CATEGORIE_TABLE,
                new String[]{
                        FunFlowModel.CATEGORIE_COL_ID,
                        FunFlowModel.CATEGORIE_COL_NAME,
                        FunFlowModel.CATEGORIE_COL_ICON
                },
                FunFlowModel.CATEGORIE_COL_ID + " = " + id,
                null, null, null, null);

        return cursorCategorie(c);
    }

    public Card getCardByID(int id){
        Cursor c = db.query(FunFlowModel.CARD_TABLE,
                new String[]{
                        FunFlowModel.CARD_COL_ID,
                        FunFlowModel.CARD_COL_NAME,
                        FunFlowModel.CARD_COL_DESCRIPTION,
                        FunFlowModel.CARD_COL_IMAGE,
                        FunFlowModel.CARD_COL_RELEASE_DATE,
                        FunFlowModel.CARD_COL_AUTHOR,
                        FunFlowModel.CARD_COL_CATEGORIE,
                        FunFlowModel.CARD_COL_RATING
                },
                FunFlowModel.CARD_COL_NAME + " = " + id,
                null, null, null, null);

        return cursorCard(c);
    }

    public Task getTaskByID(int id){
        Cursor c = db.query(FunFlowModel.TASK_TABLE,
                new String[]{
                        FunFlowModel.TASK_COL_ID,
                        FunFlowModel.TASK_COL_CARDID,
                        FunFlowModel.TASK_COL_DESCRIPTION,
                        FunFlowModel.TASK_COL_ISDONE
                },
                FunFlowModel.TASK_COL_ID + " = " + id,
                null, null, null, null);

        return cursorTask(c);
    }

    private Categorie cursorCategorie(Cursor c){
        if (c.getCount() == 0) return null;

        c.moveToFirst();

        Categorie categorie = new Categorie();
        categorie.setId(c.getInt(FunFlowModel.CATEGORIE_NUM_COL_ID));
        categorie.setName(c.getString(FunFlowModel.CATEGORIE_NUM_COL_NAME));
        categorie.setIcon(c.getString(FunFlowModel.CATEGORIE_NUM_COL_ICON));

        c.close();
        return categorie;
    }

    private Card cursorCard(Cursor c){
        if (c.getCount() == 0) return null;

        c.moveToFirst();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date date;

        try {
            date = df.parse(c.getString(FunFlowModel.CARD_NUM_COL_RELEASE_DATE));
        }

        catch (ParseException e){
            System.out.println(e.getMessage());
            date = null;
        }

        Card card = new Card();
        card.setId(c.getInt(FunFlowModel.CARD_NUM_COL_ID));
        card.setName(c.getString(FunFlowModel.CARD_NUM_COL_NAME));
        card.setDescription(c.getString(FunFlowModel.CARD_NUM_COL_DESCRIPTION));
        card.setImage(c.getString(FunFlowModel.CARD_NUM_COL_IMAGE));
        card.setReleaseDate(date);
        card.setAuthor(c.getString(FunFlowModel.CARD_NUM_COL_AUTHOR));
        card.setCategorie(getCategorieByID(c.getInt(FunFlowModel.CARD_NUM_COL_CATEGORIE)));
        card.setTasks(getTasks(card.getId()));
        card.updateProgression();
        card.setRating(c.getInt(FunFlowModel.CARD_NUM_COL_RATING));

        c.close();

        return card;
    }

    private List<Task> cursorTasks(Cursor c){
        if (c.getCount() == 0) return new ArrayList<Task>();
        List<Task> tasks = new ArrayList<Task>();

        while (c.moveToNext()){
            tasks.add(
                    new Task(
                            c.getInt(FunFlowModel.TASK_NUM_COL_ID),
                            c.getInt(FunFlowModel.TASK_NUM_COL_CARDID),
                            c.getString(FunFlowModel.TASK_NUM_COL_DESCRIPTION),
                            (c.getInt(FunFlowModel.TASK_NUM_COL_ISDONE) == 1)? true : false));
        }

        c.close();
        return tasks;
    }

    private Task cursorTask(Cursor c){
        if (c.getCount() == 0) return null;

        c.moveToFirst();

        Task task = new Task();
        task.setId(c.getInt(FunFlowModel.TASK_NUM_COL_ID));
        task.setCardID(c.getInt(FunFlowModel.TASK_NUM_COL_CARDID));
        task.setDescription(c.getString(FunFlowModel.TASK_NUM_COL_DESCRIPTION));
        task.setDone((c.getInt(FunFlowModel.TASK_NUM_COL_ISDONE) == 1)? true : false);

        c.close();
        return task;
    }
}
