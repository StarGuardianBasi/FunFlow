package fr.utt.if26.funflow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Card {
    private int id;
    private String name;
    private String description;
    private String image;
    private Date releaseDate;
    private double progression; //from 0.0 to 1.0

    private String author;
    private int rating;
    private Categorie categorie;
    private List<Task> tasks;

    public Card(){
        tasks = new ArrayList<Task>();
    }

    public Card(int id,
                String name,
                String description,
                String image,
                Date releaseDate,
                String author,
                int rating,
                Categorie categorie,
                List<Task> tasks){

        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.releaseDate = releaseDate;
        this.author = author;
        this.rating = rating;
        this.categorie = categorie;
        this.tasks = tasks;
    }

    public Card(int id,
                String name,
                String description,
                String image,
                Date releaseDate,
                String author,
                int rating,
                Categorie categorie){

        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.releaseDate = releaseDate;
        this.author = author;
        this.rating = rating;
        this.categorie = categorie;
        this.tasks = new ArrayList<Task>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public String getAuthor() {
        return author;
    }

    public int getRating() {
        return rating;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public double getProgression(){
        return progression;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) { this.tasks.add(task); }

    public void updateProgression(){
        int nbTasks = tasks.size();
        int nbTasksDone = 0;

        for (Task task : tasks){
            nbTasksDone += (task.isDone())? 1 : 0;
        }

        progression = (float)(nbTasksDone / (float)nbTasks);
    }
}
