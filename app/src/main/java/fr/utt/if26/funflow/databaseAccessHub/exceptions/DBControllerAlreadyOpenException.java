package fr.utt.if26.funflow.databaseAccessHub.exceptions;

public class DBControllerAlreadyOpenException extends Exception {
    /**
     * Exception that notifies if the DB controller is already open when the user attempts an open() action
     */
    public DBControllerAlreadyOpenException(){
        super("You should close the DBController before opening a new one");
    }
}
