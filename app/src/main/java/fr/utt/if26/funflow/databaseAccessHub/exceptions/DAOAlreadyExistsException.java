package fr.utt.if26.funflow.databaseAccessHub.exceptions;

public class DAOAlreadyExistsException extends RuntimeException {
    public DAOAlreadyExistsException(String msg){
        super(msg);
    }
}
