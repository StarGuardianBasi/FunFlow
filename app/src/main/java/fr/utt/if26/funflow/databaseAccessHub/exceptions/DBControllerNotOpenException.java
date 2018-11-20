package fr.utt.if26.funflow.databaseAccessHub.exceptions;

public class DBControllerNotOpenException extends Exception {
    /**
     * Exception that notifies if a DB controller is not open when the user wants to use an action on it
     */
    public DBControllerNotOpenException(){
        super("You must open the DBController before anything else! BAKA!");
    }
}
