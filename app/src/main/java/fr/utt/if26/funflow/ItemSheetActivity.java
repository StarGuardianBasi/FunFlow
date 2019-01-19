package fr.utt.if26.funflow;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

import java.util.ArrayList;
import java.util.Calendar;

public class ItemSheetActivity extends AppCompatActivity {

    TextView desc;
    Button edit;
    TextView title;
    TextView author;
    TextView date;
    ImageView iv;
    ProgressBar pb;
    ListView taskList;
    Button newNote;
    EditText newComment;
    Button delete;
            final Activity c = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.loadActivity();
    }

    protected void loadActivity(){
        setContentView(R.layout.activity_item_sheet);
        final Card item = FunFlow.getController().fetchCardByName(getIntent().getStringExtra("flow"));
        desc = findViewById(R.id.TextDescSheet);
        desc.setMovementMethod(new ScrollingMovementMethod());
        desc.setText(item.getDescription());
        title = findViewById(R.id.TextTitleSheet);
        title.setText(item.getName());
        author = findViewById(R.id.TextAuthorSheet);
        author.setText(item.getAuthor());
        date = findViewById(R.id.TextDateSheet);
        Calendar cal = Calendar.getInstance();
        cal.setTime(item.getReleaseDate());
        date.setText(Integer.toString(cal.get(Calendar.DAY_OF_MONTH)) + "/" + Integer.toString(cal.get(Calendar.MONTH)) + "/" +Integer.toString(cal.get(Calendar.YEAR)-1900));
        iv = findViewById(R.id.ImageSheet);
        iv.setImageURI(Uri.parse(item.getImage()));
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setCropToPadding(false);
        iv.setLayoutParams(new LinearLayout.LayoutParams(450,450));
        SmileRating smileRating = findViewById(R.id.smile_rating);
        switch (item.getRating()) {
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
        pb = findViewById(R.id.progressBar);
        if(FunFlow.getController().fetchListOfCardTask(item.getId()).isEmpty()) {
            pb.setProgress(1);
            pb.setMax(1);
        } else
        {
            pb.setMax(FunFlow.getController().fetchListOfCardTask(item.getId()).size());
            int i = 0;
            for(Task task : FunFlow.getController().fetchListOfCardTask(item.getId())){
                if(task.isDone())
                {
                    i++;
                }
            }
            pb.setProgress(i);
        }
        smileRating.setIndicator(true);
        taskList = findViewById(R.id.CommentList);
        ArrayList<Task> list = new ArrayList<>(FunFlow.getController().fetchListOfCardTask(item.getId()));
        CommentAdapter Adpt  = new CommentAdapter(this,R.layout.category,list,pb,item);
        taskList.setAdapter(Adpt);
        edit = findViewById(R.id.ButtonEditSheet);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemSheetActivity.this,NewSheetActivity.class);
                intent.putExtra("item",item.getName());
                startActivityForResult(intent,0);
            }
        });
        newComment = findViewById(R.id.editText2);
        newNote = findViewById(R.id.AddNoteButton);
        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task task = new Task(item.getId(),newComment.getText().toString(), false);
                FunFlow.getController().insertTask(task);
                ArrayList<Task> list = new ArrayList<>(FunFlow.getController().fetchListOfCardTask(item.getId()));
                CommentAdapter Adpt  = new CommentAdapter(c,R.layout.category,list,pb,item);
                taskList.setAdapter(Adpt);
                newComment.setText("");
                pb.setMax(FunFlow.getController().fetchListOfCardTask(item.getId()).size());
                int i = 0;
                for(Task tsk : FunFlow.getController().fetchListOfCardTask(item.getId())){
                    if(tsk.isDone())
                    {
                        i++;
                    }
                }
                pb.setProgress(i);
            }
        });
        delete = findViewById(R.id.DeleteButton);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FunFlow.getController().removeCardByName(item.getName());
                finish();
            }
        });
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        this.loadActivity();
    }
}
