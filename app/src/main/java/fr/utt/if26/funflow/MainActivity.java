package fr.utt.if26.funflow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import java.util.ArrayList;
import java.util.List;

import fr.utt.if26.funflow.databaseAccessHub.DBController;
import fr.utt.if26.funflow.databaseAccessHub.exceptions.DAOAlreadyExistsException;
import fr.utt.if26.funflow.databaseAccessHub.exceptions.DBControllerAlreadyOpenException;
import fr.utt.if26.funflow.databaseAccessHub.exceptions.DBControllerNotOpenException;

public class MainActivity extends AppCompatActivity {

    GridView gridViewMain;
    private DBController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridViewMain = findViewById(R.id.MainGrid);
        ArrayList<Integer> list = new ArrayList<Integer>() {{
            add(R.drawable.sample_0);
            add(R.drawable.sample_1);
            add(R.drawable.sample_2);
        }};
        ImageAdapter imgAdpt  = new ImageAdapter(this,R.layout.category,list);
        gridViewMain.setAdapter(imgAdpt);
        gridViewMain.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Récupérer la clé permettant d'avoir les data
                Intent intent = new Intent(MainActivity.this, DataListActivity.class);
                intent.putExtra("category",view.toString());
                startActivity(intent);
            }
        });

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
