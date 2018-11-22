package fr.utt.if26.funflow.databaseAccessHub.exceptions;

public class DAOAlreadyExistsException extends RuntimeException {
    /**
     * exception that launches when a item in the database already exists or other similar actions in the DAO
     */
    public DAOAlreadyExistsException(String msg){
        super(msg);
    }
}
