package fr.utt.if26.funflow;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CardAdapter extends ArrayAdapter<Card> {

    ArrayList<Card> cards;
    Context c;

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
        Card card = cards.get(position);
        TextView tv = v.findViewById(R.id.TextCard);
        //Récupérer dans la BDD l'image et la tv de la carte
        ImageView iv = v.findViewById(R.id.ImageCard);
        //Récupérer la bonne couleur
        v.setBackgroundColor(Color.RED);
        return v;
    }
}
