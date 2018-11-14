package fr.utt.if26.funflow;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

import java.util.ArrayList;

public class ImageAdapter extends ArrayAdapter<Integer> {

    private Context c;
    private ArrayList<Integer> images;

    ImageAdapter(Context context, int resource, ArrayList<Integer> objects) {
        super(context, resource, objects);
        this.images = objects;
        this.c = context;
    }
    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Integer getItem(int i) {
        return images.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) c).getLayoutInflater();
        View v = inflater.inflate(R.layout.category, parent, false);
        ImageButton ib = v.findViewById(R.id.imageButton);
        //set l'image correspondant au bouton
        return v;
    }
}
