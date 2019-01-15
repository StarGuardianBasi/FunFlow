package fr.utt.if26.funflow.databaseAccessHub.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fr.utt.if26.funflow.Task;
import fr.utt.if26.funflow.databaseAccessHub.DatabaseHelper;
import fr.utt.if26.funflow.databaseAccessHub.exceptions.DAOAlreadyExistsException;

/**
 * DAO for Task object in SQLite format
 */
public class TaskDAO extends SQLiteDAO<Task> {

    /**
     * DAO for Task object in SQLite format
     * @param dbReference SQLiteDatabase object
     */
    public TaskDAO(SQLiteDatabase dbReference){
        super(dbReference);
    }

    /**
     * inserts a Task in the SQLite database
     * @param obj Task object
     * @return row ID value (-1 if error)
     * @throws NullPointerException
     */
    @Override
    public long insert(Task obj) throws NullPointerException{
        if (obj == null) throw new NullPointerException("Task object is null");

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.TASK_COL_CARDID, obj.getCardID());
        values.put(DatabaseHelper.TASK_COL_DESCRIPTION, obj.getDescription());
        values.put(DatabaseHelper.TASK_COL_ISDONE, obj.isDone());

        return dbReference.insert(DatabaseHelper.TASK_TABLE, null, values);
    }

    /**
     * updates a Task in the SQLite database
     * @param obj Task object
     * @return int state variable
     * @throws NullPointerException
     * @throws DAOAlreadyExistsException
     */
    @Override
    public int update(Task obj) throws NullPointerException, DAOAlreadyExistsException{
        if (obj == null) throw  new NullPointerException("Task object is null");

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.TASK_COL_CARDID, obj.getCardID());
        values.put(DatabaseHelper.TASK_COL_DESCRIPTION, obj.getDescription());
        values.put(DatabaseHelper.TASK_COL_ISDONE, obj.isDone());

        return dbReference.update(DatabaseHelper.TASK_TABLE, values, DatabaseHelper.TASK_COL_ID + " = " + obj.getId(), null);
    }

    @Override
    public int remove(int id) {
        return dbReference.delete(DatabaseHelper.TASK_TABLE, DatabaseHelper.TASK_COL_ID + " = " + id, null);
    }

    /**
     * @Deprecated do not use or else, you are officially winner of the Darwin arwards
     * Use fetchByID or fetchByFK instead
     *    <blockquote>
     *      <pre>
     *          fetchByID()
     *      </pre>
     *      <pre>
     *          fetchByFK()
     *      </pre>
     *    </blockquote>
     *
     */
    @Deprecated
    public Task fetchByName(String name) {
        return null;
    }

    /**
     * Fetch a list of tasks that is referenced to a Card using the foreign key
     * @param fk primary key of Card linked to the Task you want to fetch
     * @return a list of Task objects
     */
    @Override
    public List<Task> fetchByFK(int fk) {
        Cursor c = dbReference.query(DatabaseHelper.TASK_TABLE,
                new String[]{
                        DatabaseHelper.TASK_COL_ID,
                        DatabaseHelper.TASK_COL_CARDID,
                        DatabaseHelper.TASK_COL_DESCRIPTION,
                        DatabaseHelper.TASK_COL_ISDONE
                },
                DatabaseHelper.TASK_COL_CARDID + " = " + fk,
                null, null, null, null);

        return cursorFetchList(c);
    }

    /**
     * fetch the Task object for a specific ID
     * @param id primary key of Task
     * @return a Task object
     */
    @Override
    public Task fetchByID(int id) {
        Cursor c = dbReference.query(DatabaseHelper.TASK_TABLE,
                new String[]{
                        DatabaseHelper.TASK_COL_ID,
                        DatabaseHelper.TASK_COL_CARDID,
                        DatabaseHelper.TASK_COL_DESCRIPTION,
                        DatabaseHelper.TASK_COL_ISDONE
                },
                DatabaseHelper.TASK_COL_ID + " = " + id,
                null, null, null, null);

        return cursorFetch(c);
    }

    /**
     * @deprecated do not use if your soul is not ready to face the end of time
     * Use fetchByID or fetchByFK instead
     * <blockquote>
     *     <pre>
     *         fetchByID()
     *     </pre>
     *     <pre>
     *         fetchByFK()
     *     </pre>
     * </blockquote>
     * @return null
     */
    @Deprecated
    public List<Task> fetchListOfItems(){
        return null;
    }

    /**
     * Allows to retrieve string only items
     * @return a list of string items (here the description of the task)
     */
    @Override
    public List<String> fetchListOfItemsStr(){
        Cursor c = dbReference.query(DatabaseHelper.TASK_TABLE,
                new String[]{
                      DatabaseHelper.TASK_COL_DESCRIPTION
                },
                null, null, null, null, null
                );

        return cursorFetchListStr(c);
    }

    /**
     * converts a cursor into a Task object
     * @param c cursor generated from the DB query command
     * @return a Task object
     */
    @Override
    protected Task cursorFetch(Cursor c) {
        if (c.getCount() == 0) return null;

        int numColID = c.getColumnIndex(DatabaseHelper.TASK_COL_ID);
        int numColCardID = c.getColumnIndex(DatabaseHelper.TASK_COL_CARDID);
        int numColDescription = c.getColumnIndex(DatabaseHelper.TASK_COL_DESCRIPTION);
        int numColIsDone = c.getColumnIndex(DatabaseHelper.TASK_COL_ISDONE);

        c.moveToFirst();

        Task task = new Task();
        task.setId(c.getInt(numColID));
        task.setCardID(c.getInt(numColCardID));
        task.setDescription(c.getString(numColDescription));
        task.setDone(c.getInt(numColIsDone) == 1);

        c.close();
        return task;
    }

    /**
     * converts a cursor into a list of Task objects
     * @param c cursor generated from the DB query command
     * @return a list of task objects
     */
    @Override
    protected List<Task> cursorFetchList(Cursor c) {
        if (c.getCount() == 0) return new ArrayList<Task>();
        List<Task> tasks = new ArrayList<Task>();

        while (c.moveToNext()){
            int numColID = c.getColumnIndex(DatabaseHelper.TASK_COL_ID);
            int numColCardID = c.getColumnIndex(DatabaseHelper.TASK_COL_CARDID);
            int numColDescription = c.getColumnIndex(DatabaseHelper.TASK_COL_DESCRIPTION);
            int numColIsDone = c.getColumnIndex(DatabaseHelper.TASK_COL_ISDONE);

            tasks.add(
                    new Task(
                            c.getInt(numColID),
                            c.getInt(numColCardID),
                            c.getString(numColDescription),
                            c.getInt(numColIsDone) == 1));
        }

        c.close();
        return tasks;
    }

    /**
     * converts cursor to list of string items
     * @param c cursor generated from the DB query command
     * @return a list of string items
     */
    @Override
    protected List<String> cursorFetchListStr(Cursor c) {
        if (c.getCount() == 0) return new ArrayList<String>();
        List<String> tasksStr = new ArrayList<String>();

        while (c.moveToNext()){
            int numColDescription = c.getColumnIndex(DatabaseHelper.TASK_COL_DESCRIPTION);
            tasksStr.add(c.getString(numColDescription));
        }

        return tasksStr;
    }
}
