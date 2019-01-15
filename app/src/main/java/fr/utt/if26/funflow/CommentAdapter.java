package fr.utt.if26.funflow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends ArrayAdapter<Task> {

    ArrayList<Task> tasks;
    Context c;
    ProgressBar pb;
    Button delete;
    Card card;
    ListView taskList;

    CommentAdapter(Context context, int resource, ArrayList<Task> objects, ProgressBar pb,Card card) {
        super(context, resource, objects);
        this.tasks = objects;
        this.c = context;
        this.pb=pb;
        this.card=card;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Task getItem(int i) {
        return tasks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = ((Activity) c).getLayoutInflater();
        final View v = inflater.inflate(R.layout.comment, viewGroup, false);
        final Task task = tasks.get(position);
        TextView tv = v.findViewById(R.id.TextComment);
        tv.setText(task.getDescription());
        final CheckBox cb = v.findViewById(R.id.checkBox);
        cb.setChecked(task.isDone());
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb.isChecked()){
                    task.setDone(true);
                }else {
                    task.setDone(false);
                }
                FunFlow.getController().TaskDataBaseManipulation().update(task);
                pb.setMax(FunFlow.getController().fetchListOfCardTask(card.getId()).size());
                int k = 0;
                for(Task tsk : FunFlow.getController().fetchListOfCardTask(card.getId())){
                    if(tsk.isDone())
                    {
                        k++;
                    }
                }
                pb.setProgress(k);
            }
        });
        delete = v.findViewById(R.id.DeleteCommentButton);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FunFlow.getController().TaskDataBaseManipulation().remove(task.getId());
                FunFlow.getController().TaskDataBaseManipulation().update(task);
                pb.setMax(FunFlow.getController().fetchListOfCardTask(card.getId()).size());
                int k = 0;
                for(Task tsk : FunFlow.getController().fetchListOfCardTask(card.getId())){
                    if(tsk.isDone())
                    {
                        k++;
                    }
                }
                pb.setProgress(k);
            }
        });
        return v;
    }
}
