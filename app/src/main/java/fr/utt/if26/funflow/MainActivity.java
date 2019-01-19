package fr.utt.if26.funflow;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import fr.utt.if26.funflow.databaseAccessHub.DBController;
import fr.utt.if26.funflow.databaseAccessHub.exceptions.DAOAlreadyExistsException;
import fr.utt.if26.funflow.databaseAccessHub.exceptions.DBControllerAlreadyOpenException;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    public static ArrayList<Integer> catId = new ArrayList<Integer>();
    int RC_READ_AND_WRITE_PERMISSIONS = 1;
    GridView gridViewMain;
    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EasyPermissions.requestPermissions(this, "Access for storage", RC_READ_AND_WRITE_PERMISSIONS, galleryPermissions);
        FunFlow.setController(new DBController(this));
        try{
            FunFlow.getController().open();
            Category movie = new Category(1, "Movies", "");
            Category series = new Category(2, "TV Series", "");
            Category book = new Category(4, "Books", "");
            Category manga = new Category(5, "Japanese Culture", "");
            Category videoGame = new Category(0, "Video Games", "");
            Category album = new Category(3, "Music", "");
            Category culture = new Category(6,"Travel and culture","");
            Category others = new Category(7,"Others","");

            FunFlow.getController().getDataBase().execSQL("delete from " + "card_table");
            //FunFlow.getController().getDataBase().execSQL("delete from " + "categorie_table");
            FunFlow.getController().getDataBase().execSQL("delete from " + "task_table");
            FunFlow.getController().insertCategory(videoGame);
            FunFlow.getController().insertCategory(movie);
            FunFlow.getController().insertCategory(series);
            FunFlow.getController().insertCategory(album);
            FunFlow.getController().insertCategory(book);
            FunFlow.getController().insertCategory(manga);
            FunFlow.getController().insertCategory(culture);
            FunFlow.getController().insertCategory(others);

            /*Card moviecard = new Card("Saw","Un film d'horreur très sanglant.", Uri.parse("android.resource://fr.utt.if26.funflow/" + R.drawable.saw).toString(),new Date(2005,3,5),"James Wan",2,FunFlow.getController().fetchCategoryByName("Movies"));
            Card seriescard = new Card("Dr Who","Un voyageur immortel qui sauve le monde quotidiennement.",Uri.parse("android.resource://fr.utt.if26.funflow/" + R.drawable.drwho).toString(),new Date(2005,3,5),"James Wan",2,FunFlow.getController().fetchCategoryByName("TV Series"));
            Card bookcard = new Card("Neogicia","Un film d'horreur très sanglant.",Uri.parse("android.resource://fr.utt.if26.funflow/" + R.drawable.neogicia).toString(),new Date(2005,3,5),"James Wan",2,FunFlow.getController().fetchCategoryByName("Books"));
            Card videoGamecard = new Card("Fire Emblem : Path of Radiance","Un film d'horreur très sanglant.",Uri.parse("android.resource://fr.utt.if26.funflow/" + R.drawable.por).toString(),new Date(2005,3,5),"James Wan",2,FunFlow.getController().fetchCategoryByName("Video Games"));
            Card albumcard = new Card("Origins","Un film d'horreur très sanglant.",Uri.parse("android.resource://fr.utt.if26.funflow/" + R.drawable.origins).toString(),new Date(2005,3,5),"James Wan",2,FunFlow.getController().fetchCategoryByName("Music"));
            Card mangacard = new Card("Re:Zero","Un film d'horreur très sanglant.",Uri.parse("android.resource://fr.utt.if26.funflow/" + R.drawable.rezero).toString(),new Date(2005,3,5),"James Wan",2,FunFlow.getController().fetchCategoryByName("Japanese Culture"));
            Card culturecard = new Card("Kyoto Castle","Un film d'horreur très sanglant.",Uri.parse("android.resource://fr.utt.if26.funflow/" + R.drawable.kyoto).toString(),new Date(2005,3,5),"James Wan",2,FunFlow.getController().fetchCategoryByName("Travel and culture"));
            Card othercard = new Card("Figurine Ashe du championnat","Une figurine célébrant le mondial de 2018.",Uri.parse("android.resource://fr.utt.if26.funflow/" + R.drawable.ashe).toString(),new Date(2018,10,13),"Riot Games",5,FunFlow.getController().fetchCategoryByName("Others"));


            FunFlow.getController().insertCard(moviecard);
            FunFlow.getController().insertCard(seriescard);
            FunFlow.getController().insertCard(bookcard);
            FunFlow.getController().insertCard(videoGamecard);
            FunFlow.getController().insertCard(albumcard);
            FunFlow.getController().insertCard(mangacard);
            FunFlow.getController().insertCard(culturecard);
            FunFlow.getController().insertCard(othercard);*/
            }
        catch (DAOAlreadyExistsException exception){
            exception.printStackTrace();
        } catch (DBControllerAlreadyOpenException e) {
            e.printStackTrace();
        }
        List<Category> CatAll = FunFlow.getController().fetchListOfCategories();
        for(Category cat : CatAll) {
            catId.add((Integer) cat.getId());
        }
        gridViewMain = findViewById(R.id.MainGrid);
        ArrayList<Integer> list = new ArrayList<>();
        ImageAdapter imgAdpt  = new ImageAdapter(this,R.layout.category,list);
        gridViewMain.setAdapter(imgAdpt);
        gridViewMain.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DataListActivity.class);
                intent.putExtra("category",catId.get(i));
                startActivity(intent);
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    public void onPermissionsDenied(int requestCode, List<String> list) {
        finish();
    }
}
