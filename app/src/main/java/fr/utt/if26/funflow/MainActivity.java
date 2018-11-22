package fr.utt.if26.funflow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import fr.utt.if26.funflow.databaseAccessHub.DBController;
import fr.utt.if26.funflow.databaseAccessHub.exceptions.DAOAlreadyExistsException;
import fr.utt.if26.funflow.databaseAccessHub.exceptions.DBControllerAlreadyOpenException;
import fr.utt.if26.funflow.databaseAccessHub.exceptions.DBControllerNotOpenException;

public class MainActivity extends AppCompatActivity {

    private DBController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.controller = new DBController(this);

        try{
            this.controller.open();
            Category movie = new Category(0, "Movie", "");
            Category series = new Category(0, "Series", "");
            Category book = new Category(0, "Book", "");
            Category manga = new Category(0, "Manga", "");
            Category anime = new Category(0, "Anime", "");
            Category videoGame = new Category(0, "Video Game", "");
            Category album = new Category(0, "Music Album", "");

            this.controller.insert(movie);
            this.controller.insert(series);
            this.controller.insert(book);
            this.controller.insert(manga);
            this.controller.insert(anime);
            this.controller.insert(videoGame);
            this.controller.insert(album);
        }

        catch (DBControllerAlreadyOpenException exception){
            exception.printStackTrace();
        }

        catch (DBControllerNotOpenException exception){
            exception.printStackTrace();
        }

        catch (ClassNotFoundException exception){
            exception.printStackTrace();
        }

        catch (DAOAlreadyExistsException exception){
            exception.printStackTrace();
        }
    }
}
