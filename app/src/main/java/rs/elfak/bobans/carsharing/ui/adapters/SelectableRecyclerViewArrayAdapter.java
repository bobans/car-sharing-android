package rs.elfak.bobans.carsharing.ui.adapters;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public abstract class SelectableRecyclerViewArrayAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerViewArrayAdapter<T, VH> {

    private int selection;

    public SelectableRecyclerViewArrayAdapter() {
        selection = -1;
    }

    public SelectableRecyclerViewArrayAdapter(List<T> items) {
        super(items);

        selection = -1;
    }

    public void setSelection(int selection) {
        if (this.selection == selection) {
            return;
        }

        int lastSelection = this.selection;
        this.selection = selection;
        if (lastSelection >= 0 && lastSelection < getItemCount()) {
            notifyItemChanged(lastSelection);
        }
        if (this.selection >= 0 && this.selection < getItemCount()) {
            notifyItemChanged(this.selection);
        }
    }

    public int getSelection() {
        return selection;
    }

}
