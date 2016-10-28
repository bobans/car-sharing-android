package rs.elfak.bobans.carsharing.ui.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import rs.elfak.bobans.carsharing.models.Make;
import rs.elfak.bobans.carsharing.models.Model;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class ModelsFontArrayAdapter extends FontArrayAdapter<Model> {

    public ModelsFontArrayAdapter(Context context, List<Model> objects, Typeface font) {
        super(context, android.R.layout.simple_spinner_item, objects, font);
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);

        Model item = getItem(position);
        if (item != null) {
            view.setText(item.getTitle());
        }

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView)  super.getDropDownView(position, convertView, parent);

        Model item = getItem(position);
        if (item != null) {
            view.setText(item.getTitle());
        }

        return view;
    }

}
