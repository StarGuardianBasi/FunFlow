package fr.utt.if26.funflow;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import fr.utt.if26.funflow.databaseAccessHub.DBController;
import fr.utt.if26.funflow.databaseAccessHub.exceptions.DBControllerAlreadyOpenException;

public class DataListActivity extends AppCompatActivity {

    ListView listView;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_list);
        listView = findViewById(R.id.CardList);
        ArrayList<Card> Cards = (ArrayList<Card>) FunFlow.getController().CardDataBaseManipulation().fetchByFK(getIntent().getIntExtra("category", 0));
        List<Category> CatAll = FunFlow.getController().fetchListOfCategories();

        for(Category cat : CatAll) {
            System.out.println(cat.getName());
        }

            for(Card c : Cards){
            System.out.println(c.getName());
        }
        List<Card> CardsAll = FunFlow.getController().fetchListOfCards();
        for(Card c : CardsAll){
            System.out.println(c.getName());
            System.out.println(c.getCategory().getName());
        }
        CardAdapter CA = new CardAdapter(DataListActivity.this, R.layout.card, Cards);
        listView.setAdapter(CA);
        button = findViewById(R.id.AddButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DataListActivity.this,NewSheetActivity.class);
                startActivity(intent);
            }
        });
    }
}
