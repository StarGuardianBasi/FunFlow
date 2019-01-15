package fr.utt.if26.funflow.databaseAccessHub.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.utt.if26.funflow.Card;
import fr.utt.if26.funflow.databaseAccessHub.DatabaseHelper;
import fr.utt.if26.funflow.databaseAccessHub.exceptions.DAOAlreadyExistsException;

/**
 * DAO for Card object in SQLite format
 */
public class CardDAO extends SQLiteDAO<Card> {

    private final CategoryDAO categoryDAO;
    private final TaskDAO taskDAO;
    private final String dateFormatSyntax;

    /**
     * DAO for Card object in SQLite format
     * @param dbReference SQLiteDatabase object
     */
    public CardDAO(SQLiteDatabase dbReference){
        super(dbReference);
        this.categoryDAO = new CategoryDAO(dbReference);
        this.taskDAO = new TaskDAO(dbReference);
        dateFormatSyntax = "yyyy-MM-dd HH:mm:ss.SSS";
    }

    /**
     * insert new card to SQLite database
     * @param obj Card object
     * @return row ID value (-1 if error)
     * @throws NullPointerException
     * @throws DAOAlreadyExistsException
     */
    @Override
    public long insert(Card obj) throws NullPointerException, DAOAlreadyExistsException {

        if (obj == null) throw new NullPointerException("card object is not initialized");
        if (fetchByName(obj.getName()) != null) throw new DAOAlreadyExistsException("The Card object with name " + obj.getName() + " already exists");
        if (fetchByID(obj.getId()) != null) throw new DAOAlreadyExistsException("The Card object with ID " + obj.getId() + " already exists");

        DateFormat df = new SimpleDateFormat(dateFormatSyntax);

        if (obj.getReleaseDate() == null){
            obj.setReleaseDate(new Date());
        }

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.CARD_COL_NAME, obj.getName());
        values.put(DatabaseHelper.CARD_COL_CATEGORIE, obj.getCategory().getId());
        values.put(DatabaseHelper.CARD_COL_DESCRIPTION, obj.getDescription());
        values.put(DatabaseHelper.CARD_COL_IMAGE, obj.getImage());
        values.put(DatabaseHelper.CARD_COL_AUTHOR, obj.getAuthor());
        values.put(DatabaseHelper.CARD_COL_RELEASE_DATE, df.format(obj.getReleaseDate()));
        values.put(DatabaseHelper.CARD_COL_RATING, obj.getRating());

        return dbReference.insert(DatabaseHelper.CARD_TABLE, null, values);
    }

    /**
     * modifies attribute values of the card in the database
     * @param obj Card object
     * @return a int state variable
     * @throws NullPointerException
     */
    @Override
    public int update(Card obj) throws NullPointerException {

        if (obj == null) throw new NullPointerException("card object is not initialized");

        DateFormat df = new SimpleDateFormat(dateFormatSyntax);

        if (obj.getReleaseDate() == null){
            obj.setReleaseDate(new Date());
        }

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.CARD_COL_NAME, obj.getName());
        values.put(DatabaseHelper.CARD_COL_CATEGORIE, obj.getCategory().getId());
        values.put(DatabaseHelper.CARD_COL_DESCRIPTION, obj.getDescription());
        values.put(DatabaseHelper.CARD_COL_IMAGE, obj.getImage());
        values.put(DatabaseHelper.CARD_COL_AUTHOR, obj.getAuthor());
        values.put(DatabaseHelper.CARD_COL_RELEASE_DATE, df.format(obj.getReleaseDate()));
        values.put(DatabaseHelper.CARD_COL_RATING, obj.getRating());

        return dbReference.update(DatabaseHelper.CARD_TABLE, values, DatabaseHelper.CARD_COL_ID + " = " + obj.getId(), null);
    }

    /**
     * removes a card and its tasks from the database
     * @param id primary key of the card object you want to erase
     * @return a int state variable
     */
    @Override
    public int remove(int id) {
        int deleteChildrenTasks = dbReference.delete(DatabaseHelper.TASK_TABLE, DatabaseHelper.TASK_COL_CARDID + " = " + id, null);
        int deleteCard = dbReference.delete(DatabaseHelper.CARD_TABLE, DatabaseHelper.CARD_COL_ID + " = " + id, null);

        return deleteChildrenTasks + deleteCard;
    }

    /**
     * fetch the card using name value
     * @param name String name of the card you want to fetch in the DB
     * @return a card object
     */
    @Override
    public Card fetchByName(String name) {
        Cursor c = dbReference.query(DatabaseHelper.CARD_TABLE,
                new String[]{
                        DatabaseHelper.CARD_COL_ID,
                        DatabaseHelper.CARD_COL_NAME,
                        DatabaseHelper.CARD_COL_DESCRIPTION,
                        DatabaseHelper.CARD_COL_IMAGE,
                        DatabaseHelper.CARD_COL_RELEASE_DATE,
                        DatabaseHelper.CARD_COL_AUTHOR,
                        DatabaseHelper.CARD_COL_CATEGORIE,
                        DatabaseHelper.CARD_COL_RATING
                },
                DatabaseHelper.CARD_COL_NAME + " LIKE \"" + name + "\"",
                null, null, null, null);

        return cursorFetch(c);
    }

    /**
     * fetches a list of card objects using the category primary key
     * @param fk Category primary key linked with the card you want to fetch
     * @return a list of card objects
     */
    public List<Card> fetchByFK(int fk) {
        Cursor c = dbReference.query(DatabaseHelper.CARD_TABLE,
                new String[]{
                        DatabaseHelper.CARD_COL_ID,
                        DatabaseHelper.CARD_COL_NAME,
                        DatabaseHelper.CARD_COL_DESCRIPTION,
                        DatabaseHelper.CARD_COL_IMAGE,
                        DatabaseHelper.CARD_COL_RELEASE_DATE,
                        DatabaseHelper.CARD_COL_AUTHOR,
                        DatabaseHelper.CARD_COL_CATEGORIE,
                        DatabaseHelper.CARD_COL_RATING
                },
                DatabaseHelper.CARD_COL_CATEGORIE + " = " + fk,
                null, null, null, null);

        return cursorFetchList(c);
    }

    /**
     * fetch Card object using ID
     * @param id primary key of the Card object you want to fetch
     * @return a Card object
     */
    @Override
    public Card fetchByID(int id) {
        Cursor c = dbReference.query(DatabaseHelper.CARD_TABLE,
                new String[]{
                        DatabaseHelper.CARD_COL_ID,
                        DatabaseHelper.CARD_COL_NAME,
                        DatabaseHelper.CARD_COL_DESCRIPTION,
                        DatabaseHelper.CARD_COL_IMAGE,
                        DatabaseHelper.CARD_COL_RELEASE_DATE,
                        DatabaseHelper.CARD_COL_AUTHOR,
                        DatabaseHelper.CARD_COL_CATEGORIE,
                        DatabaseHelper.CARD_COL_RATING
                },
                DatabaseHelper.CARD_COL_ID + " = " + id,
                null, null, null, null);

        return cursorFetch(c);
    }

    /**
     * fetch the available Categories in the database
     * @return a list of card objects
     */
    @Override
    public List<Card> fetchListOfItems(){
        Cursor c = dbReference.query(DatabaseHelper.CARD_TABLE,
                new String[]{
                        DatabaseHelper.CARD_COL_ID,
                        DatabaseHelper.CARD_COL_NAME,
                        DatabaseHelper.CARD_COL_DESCRIPTION,
                        DatabaseHelper.CARD_COL_IMAGE,
                        DatabaseHelper.CARD_COL_RELEASE_DATE,
                        DatabaseHelper.CARD_COL_AUTHOR,
                        DatabaseHelper.CARD_COL_CATEGORIE,
                        DatabaseHelper.CARD_COL_RATING
                },
                null, null, null, null, null);

        return cursorFetchList(c);
    }

    /**
     * queries the names of the cards
     * @return list of card names
     */
    @Override
    public List<String> fetchListOfItemsStr() {
        Cursor c = dbReference.query(DatabaseHelper.CARD_TABLE,
                new String[]{DatabaseHelper.CARD_COL_NAME},
                null, null, null, null, null);

        return cursorFetchListStr(c);
    }

    /**
     * converts cursor into Card object
     * @param c cursor generated from the DB query command
     * @return a Card object
     */
    @Override
    protected Card cursorFetch(Cursor c) {
        if (c.getCount() == 0) return null;
        
        //first element of the cursor
        c.moveToFirst();

        DateFormat df = new SimpleDateFormat(dateFormatSyntax);
        Date date;

        try {
            int numColReleaseDate = c.getColumnIndex(DatabaseHelper.CARD_COL_RELEASE_DATE);
            date = df.parse(c.getString(numColReleaseDate));
        }

        catch (ParseException e){
            System.out.println(e.getMessage());
            date = null;
        }

        int numColID = c.getColumnIndex(DatabaseHelper.CARD_COL_ID);
        int numColName = c.getColumnIndex(DatabaseHelper.CARD_COL_NAME);
        int numColDescription = c.getColumnIndex(DatabaseHelper.CARD_COL_DESCRIPTION);
        int numColImage = c.getColumnIndex(DatabaseHelper.CARD_COL_IMAGE);
        int numColAuthor = c.getColumnIndex(DatabaseHelper.CARD_COL_AUTHOR);
        int numColCategory = c.getColumnIndex(DatabaseHelper.CARD_COL_CATEGORIE);
        int numColRating = c.getColumnIndex(DatabaseHelper.CARD_COL_RATING);

        Card card = new Card();
        card.setId(c.getInt(numColID));
        card.setName(c.getString(numColName));
        card.setDescription(c.getString(numColDescription));
        card.setImage(c.getString(numColImage));
        card.setReleaseDate(date);
        card.setAuthor(c.getString(numColAuthor));
        card.setCategory(categoryDAO.fetchByID(c.getInt(numColCategory)));
        card.setTasks(taskDAO.fetchByFK(card.getId()));
        card.updateProgression();
        card.setRating(c.getInt(numColRating));

        c.close();
        return card;
    }

    /**
     * converts cursor into a list of Category objects
     * @param c cursor generated from the DB query command
     * @return a list of Card objects
     */
    @Override
    protected List<Card> cursorFetchList(Cursor c) {
        if (c.getCount() == 0) return new ArrayList<Card>();

        List<Card> cards = new ArrayList<Card>();

        while (c.moveToNext()){
            DateFormat df = new SimpleDateFormat(dateFormatSyntax);
            Date date;

            try {
                int numColReleaseDate = c.getColumnIndex(DatabaseHelper.CARD_COL_RELEASE_DATE);
                date = df.parse(c.getString(numColReleaseDate));
            }

            catch (ParseException e){
                System.out.println(e.getMessage());
                date = null;
            }

            int numColID = c.getColumnIndex(DatabaseHelper.CARD_COL_ID);
            int numColName = c.getColumnIndex(DatabaseHelper.CARD_COL_NAME);
            int numColDescription = c.getColumnIndex(DatabaseHelper.CARD_COL_DESCRIPTION);
            int numColImage = c.getColumnIndex(DatabaseHelper.CARD_COL_IMAGE);
            int numColAuthor = c.getColumnIndex(DatabaseHelper.CARD_COL_AUTHOR);
            int numColCategory = c.getColumnIndex(DatabaseHelper.CARD_COL_CATEGORIE);
            int numColRating = c.getColumnIndex(DatabaseHelper.CARD_COL_RATING);

            cards.add(
                    new Card(
                            c.getInt(numColID),
                            c.getString(numColName),
                            c.getString(numColDescription),
                            c.getString(numColImage),
                            date,
                            c.getString(numColAuthor),
                            c.getInt(numColRating),
                            categoryDAO.fetchByID(c.getInt(numColCategory)),
                            taskDAO.fetchByFK(c.getInt(numColID))
                    )
            );

            cards.get(cards.size()-1).updateProgression();
        }

        c.close();
        return cards;
    }

    /**
     * converts cursor into a list of card names
     * @param c cursor generated from the DB query command
     * @return list of card names
     */
    @Override
    protected List<String> cursorFetchListStr(Cursor c) {
        if (c.getCount() == 0) return new ArrayList<String>();

        List<String> cardsStr = new ArrayList<String>();

        while (c.moveToNext()){
            int numColName = c.getColumnIndex(DatabaseHelper.CARD_COL_NAME);
            cardsStr.add(c.getString(numColName));
        }

        return cardsStr;
    }
}
