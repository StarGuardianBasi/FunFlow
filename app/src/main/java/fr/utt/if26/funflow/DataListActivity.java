package fr.utt.if26.funflow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DataListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_list);

        Intent intent = getIntent();
        //récupérer la data correspondante
        intent.getStringExtra("category");
        //créer la liste de modules à afficher et set l'adaptateur
    }
}
