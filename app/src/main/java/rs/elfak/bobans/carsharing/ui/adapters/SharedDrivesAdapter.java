package rs.elfak.bobans.carsharing.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rs.elfak.bobans.carsharing.R;
import rs.elfak.bobans.carsharing.models.SharedDrive;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class SharedDrivesAdapter extends RecyclerViewArrayAdapter<SharedDrive, SharedDrivesAdapter.ViewHolder> {


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shared_drive, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SharedDrive drive = getItemAt(position);

        holder.tvFromTo.setText(holder.itemView.getContext().getString(R.string.from_to, drive.getDeparture(), drive.getDestination()));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_view_from_to) TextView tvFromTo;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

    }
}
