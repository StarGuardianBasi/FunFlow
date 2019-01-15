package fr.utt.if26.funflow;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CardAdapter extends ArrayAdapter<Card> {

    ArrayList<Card> cards;
    final Context c;

    CardAdapter(Context context, int resource, ArrayList<Card> objects) {
        super(context, resource, objects);
        this.cards = objects;
        this.c = context;
    }

    @Override
    public int getCount() {
        return cards.size();
    }

    @Override
    public Card getItem(int i) {
        return cards.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = ((Activity) c).getLayoutInflater();
        View v = inflater.inflate(R.layout.card, viewGroup, false);
        final Card card = cards.get(position);
        TextView tv = v.findViewById(R.id.TextCard);
        tv.setText(card.getName());
        ImageView iv = v.findViewById(R.id.ImageCard);
        iv.setImageURI(Uri.parse(card.getImage()));
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setCropToPadding(false);
        iv.setLayoutParams(new ConstraintLayout.LayoutParams(300,300));
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ItemSheetActivity.class);
                intent.putExtra("flow",card.getName());
                c.startActivity(intent);
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ItemSheetActivity.class);
                intent.putExtra("flow",card.getName());
                c.startActivity(intent);
            }
        });
        return v;
    }
}
