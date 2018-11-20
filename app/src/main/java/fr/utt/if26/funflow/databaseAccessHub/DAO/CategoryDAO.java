package fr.utt.if26.funflow.databaseAccessHub.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fr.utt.if26.funflow.Card;
import fr.utt.if26.funflow.Category;
import fr.utt.if26.funflow.databaseAccessHub.DatabaseHelper;
import fr.utt.if26.funflow.databaseAccessHub.exceptions.DAOAlreadyExistsException;

/**
 * DAO for Category object in SQLite format
 */
public class CategoryDAO extends SQLiteDAO<Category> {

    /**
     * DAO for Category object in SQLite format
     * @param dbReference SQLiteDatabase object
     */
    public CategoryDAO(SQLiteDatabase dbReference){
        super(dbReference);
    }

    /**
     * insert new category to SQLite database
     * @param obj Category object
     * @return row ID value (-1 if error)
     * @throws NullPointerException
     * @throws DAOAlreadyExistsException
     */
    @Override
    public long insert(Category obj) throws NullPointerException, DAOAlreadyExistsException {
        if (obj == null) throw new NullPointerException("Card object is null");
        if (fetchByName(obj.getName()) != null) throw new DAOAlreadyExistsException("The Category object with name " + obj.getName() + " already exists");
        if (fetchByID(obj.getId()) != null) throw new DAOAlreadyExistsException("The Category object with ID " + obj.getId() + " already exists");

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.CATEGORY_COL_NAME, obj.getName());
        values.put(DatabaseHelper.CATEGORY_COL_ICON, obj.getIcon());

        return dbReference.insert(DatabaseHelper.CATEGORY_TABLE, null, values);
    }

    /**
     * modifies attribute values of the category in the database
     * @param obj Category object
     * @return int state value
     * @throws NullPointerException
     */
    @Override
    public int update(Category obj) throws NullPointerException {
        if (obj == null) throw new NullPointerException("Category object is null");

        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.CATEGORY_COL_NAME, obj.getName());
        values.put(DatabaseHelper.CATEGORY_COL_ICON, obj.getIcon());

        return dbReference.update(DatabaseHelper.CATEGORY_TABLE, values, DatabaseHelper.CATEGORY_COL_ID + " = " + obj.getId(), null);
    }

    /**
     * removes a category from the database
     * @param id primary key of the Category you want to delete from the DB
     * @return int state value
     */
    @Override
    public int remove(int id) {

        CardDAO cardDAO = new CardDAO(dbReference);
        List<Card> listOfCards = cardDAO.fetchByFK(id);

        for (Card card : listOfCards){
            cardDAO.remove(card.getId());
        }

        return dbReference.delete(DatabaseHelper.CATEGORY_TABLE, DatabaseHelper.CATEGORY_COL_ID + " = " + id, null);
    }

    /**
     * fetch the category using name value
     * @param name name of the Category you want to fetch
     * @return category object
     */
    @Override
    public Category fetchByName(String name) {
        Cursor c = dbReference.query(DatabaseHelper.CATEGORY_TABLE,
                new String[]{
                        DatabaseHelper.CATEGORY_COL_ID,
                        DatabaseHelper.CATEGORY_COL_NAME,
                        DatabaseHelper.CATEGORY_COL_ICON
                },
                DatabaseHelper.CATEGORY_COL_NAME + " LIKE \"" + name + "\"",
                null, null, null, null);

        return cursorFetch(c);
    }

    /**
     * @Deprecated function. Do not use. By all means, it is the equivalent of Sonic Boom after all. Also go watch Buko No Piko because your lif is meaningless
     * use fetchByID or fetchByName instead
     * <blockquote>
     *     <pre>
     *         fetchByName()
     *     </pre>
     *     <pre>
     *         fetchByID
     *     </pre>
     * </blockquote>
     */
    @Deprecated
    public List<Category> fetchByFK(int fk) {
        return null;
    }

    /**
     * fetch Category object using ID
     * @param id primary key of the Category you want to fetch
     * @return category object
     */
    @Override
    public Category fetchByID(int id) {
        Cursor c = dbReference.query(DatabaseHelper.CATEGORY_TABLE,
                new String[]{
                        DatabaseHelper.CATEGORY_COL_ID,
                        DatabaseHelper.CATEGORY_COL_NAME,
                        DatabaseHelper.CATEGORY_COL_ICON
                },
                DatabaseHelper.CATEGORY_COL_ID + " = " + id,
                null, null, null, null);

        return cursorFetch(c);
    }

    /**
     * fetch the available Categories in the database
     * @return a list of Category object
     */
    @Override
    public List<Category> fetchListOfItems() {
        Cursor c = dbReference.query(DatabaseHelper.CATEGORY_TABLE,
                new String[]{
                        DatabaseHelper.CATEGORY_COL_ID,
                        DatabaseHelper.CATEGORY_COL_NAME,
                        DatabaseHelper.CATEGORY_COL_ICON
                },
                null, null, null, null, null);

        return cursorFetchList(c);
    }

    /**
     * queries the names of the categories
     * @return list of category names
     */
    @Override
    public List<String> fetchListOfItemsStr() {
        Cursor c = dbReference.query(DatabaseHelper.CATEGORY_TABLE,
                new String[]{DatabaseHelper.CATEGORY_COL_NAME},
                null, null, null, null, null);

        return cursorFetchListStr(c);
    }

    /**
     * converts cursor into Category object
     * @param c cursor generated from the DB query command
     * @return category object
     */
    @Override
    protected Category cursorFetch(Cursor c) {
        if (c.getCount() == 0) return null;

        c.moveToFirst();

        int numColID = c.getColumnIndex(DatabaseHelper.CATEGORY_COL_ID);
        int numColName = c.getColumnIndex(DatabaseHelper.CATEGORY_COL_NAME);
        int numColIcon = c.getColumnIndex(DatabaseHelper.CATEGORY_COL_ICON);

        Category category = new Category();
        category.setId(c.getInt(numColID));
        category.setName(c.getString(numColName));
        category.setIcon(c.getString(numColIcon));

        c.close();
        return category;
    }

    /**
     * converts cursor into a list of Category objects
     * @param c cursor generated from the DB query command
     * @return category object
     */
    @Override
    protected List<Category> cursorFetchList(Cursor c) {
        if (c.getCount() == 0) return new ArrayList<Category>();

        List<Category> categories = new ArrayList<Category>();

        while (c.moveToNext()){
            int numColID = c.getColumnIndex(DatabaseHelper.CATEGORY_COL_ID);
            int numColName = c.getColumnIndex(DatabaseHelper.CATEGORY_COL_NAME);
            int numColIcon = c.getColumnIndex(DatabaseHelper.CATEGORY_COL_ICON);

            categories.add(new Category(
                                    c.getInt(numColID),
                                    c.getString(numColName),
                                    c.getString(numColIcon)));

        }

        c.close();
        return categories;
    }

    /**
     * converts cursor into list of category names
     * @param c cursor generated from the DB query command
     * @return a list of category names
     */
    @Override
    protected List<String> cursorFetchListStr(Cursor c) {
        if (c.getCount() == 0) return new ArrayList<String>();

        List<String> categoriesStr = new ArrayList<String>();

        while (c.moveToNext()){
            int numColName = c.getColumnIndex(DatabaseHelper.CATEGORY_COL_NAME);
            categoriesStr.add(c.getString(numColName));
        }

        return categoriesStr;
    }
}
