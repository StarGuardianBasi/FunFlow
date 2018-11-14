package fr.utt.if26.funflow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GridView gridViewMain;
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
    }
}
