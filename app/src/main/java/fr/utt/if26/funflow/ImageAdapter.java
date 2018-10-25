package fr.utt.if26.funflow;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private Integer[] images = {
            R.drawable.sample_0, R.drawable.sample_1, R.drawable.sample_2,R.drawable.sample_3,R.drawable.sample_4,R.drawable.sample_5, R.drawable.sample_6, R.drawable.sample_7
    };

    ImageAdapter (Context c){
        mContext = c;
    }
    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ImageView imageView;
        if (view == null)
        {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(170,170));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8,8,8,8);
        }
        else {
            imageView = (ImageView) view;
        }

        imageView.setImageResource(images[position]);
        return imageView;
    }
}
