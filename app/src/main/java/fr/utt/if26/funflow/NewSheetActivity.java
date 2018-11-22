package fr.utt.if26.funflow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NewSheetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sheet);
        Intent intent = getIntent();
        if(intent.getStringExtra("item")!=null)
        {
            //TODO Initialisation des valeurs de la page
        }
    }
}
