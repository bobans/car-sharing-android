package rs.elfak.bobans.carsharing.ui.adapters;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public abstract class RecyclerViewArrayAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private List<T> items;

    public RecyclerViewArrayAdapter() {
        this(null);
    }

    public RecyclerViewArrayAdapter(List<T> items) {
        this.items = new ArrayList<>();
        if (items != null && items.size() > 0) {
            this.items.addAll(items);
        }
    }

    public void setItems(List<T> items) {
        if (items != null) {
            this.items = items;
        } else {
            this.items.clear();
        }
        notifyDataSetChanged();
    }

    public void addItem(T item) {
        if (item != null) {
            this.items.add(item);
            notifyItemInserted(this.items.size() - 1);
        }
    }

    public void addItems(List<T> items) {
        if (items != null && items.size() > 0) {
            this.items.addAll(items);
            notifyItemRangeInserted(this.items.size() - items.size(), items.size());
        }
    }

    public T removeItem(int position) {
        if (position >= 0 && position < items.size()) {
            T item = items.remove(position);
            notifyItemRemoved(position);
            return item;
        }
        return null;
    }

    public T getItemAt(int position) {
        if (position >= 0 && position < items.size()) {
            return items.get(position);
        }
        return null;
    }

    public void clear() {
        this.items.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
