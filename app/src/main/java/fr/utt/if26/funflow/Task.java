package fr.utt.if26.funflow;

public class Task {
    private int id;
    private int CardID;
    private String description;
    private boolean isDone;

    public Task(){}
    public Task(int id, int CardID, String description, boolean isDone) {
        this.id = id;
        this.CardID = CardID;
        this.description = description;
        this.isDone = isDone;
    }

    public Task(int CardID, String description, boolean isDone){
        this.CardID = CardID;
        this.description = description;
        this.isDone = isDone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getCardID() {
        return CardID;
    }

    public void setCardID(int CardID){
        this.CardID = CardID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
