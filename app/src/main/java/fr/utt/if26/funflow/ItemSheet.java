package fr.utt.if26.funflow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ItemSheet extends AppCompatActivity {

    TextView desc;
    Button edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_sheet);

        desc = findViewById(R.id.TextDescSheet);
        desc.setMovementMethod(new ScrollingMovementMethod());
        edit = findViewById(R.id.ButtonEditSheet);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemSheet.this,NewSheetActivity.class);
                intent.putExtra("item","string");
                startActivity(intent);
            }
        });
    }
}
