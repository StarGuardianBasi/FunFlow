package fr.utt.if26.funflow.databaseAccessHub;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.util.List;

import fr.utt.if26.funflow.Card;
import fr.utt.if26.funflow.Category;
import fr.utt.if26.funflow.Task;
import fr.utt.if26.funflow.databaseAccessHub.DAO.CardDAO;
import fr.utt.if26.funflow.databaseAccessHub.DAO.CategoryDAO;
import fr.utt.if26.funflow.databaseAccessHub.DAO.TaskDAO;
import fr.utt.if26.funflow.databaseAccessHub.exceptions.DBControllerAlreadyOpenException;
import fr.utt.if26.funflow.databaseAccessHub.exceptions.DBControllerNotOpenException;

/**
 * Database controller of the application
 */
public class DBController {

    /**
     * enumeration of available object type of the database
     */
    public static enum DBObj{
        Card, Task, Category;
    }

    private static final String DBNAME = "funflow.db";
    private static final int VERSION = 1;

    private SQLiteDatabase db;

    private final DatabaseHelper model;
    private CardDAO cardDAO;
    private CategoryDAO categoryDAO;
    private TaskDAO taskDAO;


    /**
     * Database controller using basic DAO architecture
     * @param context context type variable
     */
    public DBController(Context context){
        model = new DatabaseHelper(context, DBNAME, null, VERSION);
    }

    /**
     * opens the dbController
     */
    public void open() throws DBControllerAlreadyOpenException {
        if (!db.isOpen()) {
            db = model.getWritableDatabase();
            categoryDAO = new CategoryDAO(db);
            taskDAO = new TaskDAO(db);
            cardDAO = new CardDAO(db);
        }

        else{
            throw new DBControllerAlreadyOpenException();
        }
    }

    /**
     * closes the dbController. what more do you want mate.
     */
    public void close(){
        if (db.isOpen()){
            db.close();
            cardDAO = null;
            taskDAO = null;
            categoryDAO = null;
        }
    }

    /**
     * get for the SQLiteDatabase
     * @return db
     */
    public SQLiteDatabase getDataBase(){
        return db;
    }

    /**
     * getter cardDAO
     * @return
     */
    public CardDAO CardDataBaseManipulation(){
        return cardDAO;
    }

    /**
     * getter categoryDAO
     * @return
     */
    public CategoryDAO CategoryDataBaseManipulation(){
        return categoryDAO;
    }

    /**
     * generic insert function. The parameter must be Card, Category or Task type
     * @param databaseObject object that can be either Card, Category or Task type, must not be null
     * @return row ID or -1 if error
     * @throws DBControllerNotOpenException
     * @throws ClassNotFoundException
     */
    public long insert(Object databaseObject) throws DBControllerNotOpenException, ClassNotFoundException{
        if (!db.isOpen()) throw new DBControllerNotOpenException();

        DBObj objType = DBObj.valueOf(databaseObject.getClass().getSimpleName());
        long state = -1l;

        switch (objType){

            case Card:
                Card card = (Card) databaseObject;
                state = cardDAO.insert(card);
                break;

            case Task:
                Task task = (Task) databaseObject;
                state = taskDAO.insert(task);
                break;

            case Category:
                Category category = (Category) databaseObject;
                state = categoryDAO.insert(category);
                break;

            default:
                throw new ClassNotFoundException("It must be either Card, Category or Task");
        }

        return state;
    }

    /**
     * insert a new card in the database
     * @param card Card object
     * @return row ID or -1 if error
     */
    public long insertCard(Card card){
        return cardDAO.insert(card);
    }

    /**
     * insert a new Task in the database
     * @param task Task object
     * @return row ID or -1 if error
     */
    public long insertTask(Task task){
        return taskDAO.insert(task);
    }

    /**
     * insert a new Category in the database
     * @param category Category object
     * @return row ID or -1 if error
     */
    public long insertCategory(Category category){
        return categoryDAO.insert(category);
    }

    /**
     * seamless update function. The parameter must be Card, Category or Task type
     * @param databaseObject object that can be either Card, Category or Task type, must not be null
     * @return a integer state variable
     * @throws DBControllerNotOpenException
     * @throws ClassNotFoundException
     */
    public int update(Object databaseObject) throws DBControllerNotOpenException, ClassNotFoundException{
        if (!db.isOpen()) throw new DBControllerNotOpenException();

        DBObj objType = DBObj.valueOf(databaseObject.getClass().getSimpleName());
        int state = -1;

        switch (objType){

            case Card:
                Card card = (Card) databaseObject;
                state = cardDAO.update(card);
                break;

            case Task:
                Task task = (Task) databaseObject;
                state = taskDAO.update(task);
                break;

            case Category:
                Category category = (Category) databaseObject;
                state = categoryDAO.update(category);
                break;

            default:
                throw new ClassNotFoundException("It must be either Card, Category or Task");
        }

        return state;
    }

    /**
     * update a new card in the database
     * @param card Card object
     * @return row ID or -1 if error
     */
    public long updateCard(Card card){
        return cardDAO.update(card);
    }

    /**
     * insert a new Task in the database
     * @param task Task object
     * @return row ID or -1 if error
     */
    public long updateTask(Task task){
        return taskDAO.update(task);
    }

    /**
     * insert a new Category in the database
     * @param category Category object
     * @return row ID or -1 if error
     */
    public long updateCategory(Category category){
        return categoryDAO.update(category);
    }

    /**
     * seamless remove function. The parameter must be Card, Category or Task type
     * @param objectType enum/DBObj type
     * @param id primary key of the concerned object
     * @return a integer state variable
     * @throws DBControllerNotOpenException
     * @throws ClassNotFoundException
     */
    public int remove(DBObj objectType, int id) throws DBControllerNotOpenException, ClassNotFoundException{
        if (!db.isOpen()) throw new DBControllerNotOpenException();
        int state = -1;

        switch (objectType){

            case Card:
                state = cardDAO.remove(id);
                break;

            case Task:
                state = taskDAO.remove(id);
                break;

            case Category:
                state = categoryDAO.remove(id);
                break;

            default:
                throw new ClassNotFoundException("It must be either Card, Category or Task");
        }

        return state;
    }

    /**
     * removes a Card using the name (unique string type in the database)
     * @param name name of the concerned card
     * @return int state variable
     */
    public int removeCardByName(String name){
        Card card = fetchCardByName(name);
        return cardDAO.remove(card.getId());
    }

    /**
     * removes a Category using the name (unique string type in the database)
     * @param name name of the concerned category
     * @return int state variable
     */
    public int removeCategoryByName(String name){
        Category category = fetchCategoryByName(name);
        return categoryDAO.remove(category.getId());
    }

    /**
     * fetch a category object using the name attribute
     * @param name name of the concerned category
     * @return category object
     */
    public Category fetchCategoryByName(String name){
        return categoryDAO.fetchByName(name);
    }

    /**
     * fetch a category object using the primary key attribute
     * @param id primary key of the concerned category
     * @return category object
     */
    public Category fetchCategoryByID(int id){
        return categoryDAO.fetchByID(id);
    }

    /**
     * fetch a list of categories available in the database
     * @return list of category objects
     */
    public List<Category> fetchListOfCategories(){
        return categoryDAO.fetchListOfItems();
    }

    /**
     * fetch a list of category names available in the database
     * @return list of category names string type
     */
    public List<String> fetchNamesOfCategories(){
        return categoryDAO.fetchListOfItemsStr();
    }

    /**
     * fetch a card object using the name attribute
     * @param name name of the concerned card
     * @return card object
     */
    public Card fetchCardByName(String name){
        return cardDAO.fetchByName(name);
    }

    /**
     * fetch a card object using the primary key attribute
     * @param id primary key of the concerned card
     * @return card object
     */
    public Card fetchCardByID(int id){
        return cardDAO.fetchByID(id);
    }

    /**
     * fetch a list of cards available in the database
     * @return list of card objects
     */
    public List<Card> fetchListOfCards(){
        return cardDAO.fetchListOfItems();
    }

    /**
     * fetch a list of card names available in the database
     * @return list of card names string type
     */
    public List<String> fetchNamesOfCards(){
        return cardDAO.fetchListOfItemsStr();
    }

    /**
     * fetch the list of tasks of a card using the foreign key
     * @param cardID foreign key (primary key of card object) of the concerned task
     * @return list of task objects
     */
    public List<Task> fetchListOfCardTask(int cardID){
        return taskDAO.fetchByFK(cardID);
    }
}
