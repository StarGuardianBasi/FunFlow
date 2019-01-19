package fr.utt.if26.funflow;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NewSheetActivity extends AppCompatActivity {

    static final int RESULT_LOAD_IMAGE = 1;

    EditText title;
    EditText author;
    Spinner cat;
    SmileRating sm;
    EditText desc;
    ImageView iv;
    EditText day;
    EditText month;
    EditText year;
    Button aB;
    Button iB;
    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_sheet);
        final Intent intent = getIntent();
        this.c = this;
        title = findViewById(R.id.NewSheetTitleEntry);
        author = findViewById(R.id.NewSheetAuthorEntry);
        cat = findViewById(R.id.spinner);
        year = findViewById(R.id.editYear);
        month = findViewById(R.id.editMonth);
        day = findViewById(R.id.editDay);
        sm = findViewById(R.id.smile_rating);
        desc = findViewById(R.id.NewSheetDescEntry);
        iv = findViewById(R.id.NewSheetImageView);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cat.setAdapter(adapter);
        if(intent.getStringExtra("item")!=null)
        {
            Card card = FunFlow.getController().fetchCardByName(intent.getStringExtra("item"));
            title.setText(card.getName());
            title.setFocusable(false);
            author.setText(card.getAuthor());
            switch(card.getCategory().getName()) {
                case "Video Games":
                    cat.setSelection(0);
                    break;
                case "Movies":
                    cat.setSelection(1);
                    break;
                case "Tv Series":
                    cat.setSelection(2);
                    break;
                case "Music":
                    cat.setSelection(3);
                    break;
                case "Books":
                    cat.setSelection(4);
                    break;
                case "Japanese Culture":
                    cat.setSelection(5);
                    break;
                case "Travel and Culture":
                    cat.setSelection(6);
                    break;
                case "Others":
                    cat.setSelection(7);
                    break;
            }
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(card.getReleaseDate().getTime());
            year.setText(Integer.toString(cal.get(Calendar.YEAR) - 1900));
            month.setText(Integer.toString(cal.get(Calendar.MONTH)));
            day.setText(Integer.toString(cal.get(Calendar.DAY_OF_MONTH)));
            final SmileRating smileRating = findViewById(R.id.smile_rating);
            switch (card.getRating()) {
                case 1:
                    smileRating.setSelectedSmile(BaseRating.TERRIBLE);
                    break;
                case 2:
                    smileRating.setSelectedSmile(BaseRating.BAD);
                    break;
                case 3:
                    smileRating.setSelectedSmile(BaseRating.OKAY);
                    break;
                case 4:
                    smileRating.setSelectedSmile(BaseRating.GOOD);
                    break;
                case 5:
                    smileRating.setSelectedSmile(BaseRating.GREAT);
                    break;
                default:
                    smileRating.setSelectedSmile(BaseRating.NONE);
                    break;
            }
            desc.setText(card.getDescription());
            iv.setImageURI(Uri.parse(card.getImage()));
            iv.setTag(card.getImage());
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setCropToPadding(false);
            iv.setLayoutParams(new LinearLayout.LayoutParams(450,450));
        }
        iB = findViewById(R.id.NewSheetButtonChooseImage);
        iB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
        aB = findViewById(R.id.NewSheetButtonAdd);
        aB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isDigitsOnly(day.getText())||TextUtils.isDigitsOnly(day.getText())||TextUtils.isDigitsOnly(day.getText())) {
                    String category = "Tv Series";
                    switch (cat.getSelectedItemPosition()) {
                        case 0:
                            category = "Video Games";
                            break;
                        case 1:
                            category = "Movies";
                            break;
                        case 2:
                            category = "Tv Series";
                            break;
                        case 3:
                            category = "Music";
                            break;
                        case 4:
                            category = "Books";
                            break;
                        case 5:
                            category = "Japanese Culture";
                            break;
                        case 6:
                            category = "Travel and Culture";
                            break;
                        case 7:
                            category = "Others";
                            break;
                        default:
                            break;
                    }
                    if (intent.getStringExtra("item") != null) {
                        Card card = FunFlow.getController().fetchCardByName(intent.getStringExtra("item"));
                        Card c = new Card(card.getId(), title.getText().toString(), desc.getText().toString(), iv.getTag().toString(), new Date(Integer.parseInt(year.getText().toString()), Integer.parseInt(month.getText().toString()), Integer.parseInt(day.getText().toString())), author.getText().toString(), sm.getRating(), FunFlow.getController().fetchCategoryByName(category));
                        FunFlow.getController().updateCard(c);
                    } else {
                        Card c = new Card(title.getText().toString(), desc.getText().toString(), iv.getTag().toString(), new Date(Integer.parseInt(year.getText().toString()), Integer.parseInt(month.getText().toString()), Integer.parseInt(day.getText().toString())), author.getText().toString(), sm.getRating(), FunFlow.getController().fetchCategoryByName(category));
                        FunFlow.getController().insertCard(c);
                    }
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                } else
                {
                    Toast.makeText(c,"Wrong Date", Toast.LENGTH_LONG);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            System.out.println(data.getData());
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            iv.setImageURI(Uri.parse(picturePath));
            iv.setTag(picturePath);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setCropToPadding(false);
            iv.setLayoutParams(new LinearLayout.LayoutParams(450,450));
        }
    }
}
