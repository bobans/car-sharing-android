package rs.elfak.bobans.carsharing.ui.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import rs.elfak.bobans.carsharing.utils.ViewUtils;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class FontArrayAdapter<T> extends ArrayAdapter<T> {

    private Typeface font;
    private Typeface dropdownFont;

    public FontArrayAdapter(Context context, int resource, T[] objects, Typeface font) {
        super(context, resource, objects);

        this.font = font;
    }

    public FontArrayAdapter(Context context, int resource, List<T> objects, Typeface font) {
        super(context, resource, objects);

        this.font = font;
    }

    public void setDropdownFont(Typeface dropdownFont) {
        this.dropdownFont = dropdownFont;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        if (font != null) {
            ViewUtils.setFont(view, font);
        }

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);

        if (dropdownFont != null) {
            ViewUtils.setFont(view, dropdownFont);
        }

        return view;
    }

}
