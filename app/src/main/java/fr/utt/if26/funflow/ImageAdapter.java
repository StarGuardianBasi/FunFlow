package fr.utt.if26.funflow;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImageAdapter extends ArrayAdapter<Integer> {

    private Context c;
    private Integer[] images = {
                R.drawable.video_games3x, R.drawable.movie3x,
                R.drawable.tv_series3x, R.drawable.music3x,
                R.drawable.read3x, R.drawable.japan_culture3x,
                R.drawable.travel3x, R.drawable.others3x,
    };

    ImageAdapter(Context context, int resource, ArrayList<Integer> objects) {
        super(context, resource, objects);
        this.c = context;
    }
    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Integer getItem(int i) {
        return images[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = ((Activity) c).getLayoutInflater();
        View v = inflater.inflate(R.layout.category, parent, false);
        ImageView iv = v.findViewById(R.id.imageButton);
        iv.setLayoutParams(new RelativeLayout.LayoutParams(300,300));
        iv.setCropToPadding(false);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setPadding(12,12,12,12);
        iv.setImageResource(images[position]);
        return v;
    }
}
