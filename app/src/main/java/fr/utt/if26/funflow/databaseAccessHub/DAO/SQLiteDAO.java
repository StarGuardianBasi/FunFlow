package fr.utt.if26.funflow.databaseAccessHub.DAO;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * abstract class that allows to create DAO type object that will interact with the SQLite Helper
 * @param <T> any Class name belonging to the Database
 */
public abstract class SQLiteDAO<T> {

    protected SQLiteDatabase dbReference;

    /**
     * abstract class that allows to create DAO type object that will interact with the SQLite Helper
     * @param dbReference SQLiteDatabase object
     * @throws NullPointerException
     */
    protected SQLiteDAO(SQLiteDatabase dbReference) throws NullPointerException {
        if (dbReference == null) throw new NullPointerException("SQLiteDatabase object should be initialized");
        this.dbReference = dbReference;
    }

    abstract public long insert(T obj);
    abstract public int update(T obj);
    abstract public int remove(int id);

    abstract public T fetchByName(String name);
    abstract public List<T> fetchByFK(int fk);
    abstract public T fetchByID(int id);
    abstract public List<T> fetchListOfItems();
    abstract public List<String> fetchListOfItemsStr();

    abstract protected T cursorFetch(Cursor c);
    abstract protected List<T> cursorFetchList(Cursor c);
    abstract protected List<String> cursorFetchListStr(Cursor c);
}
