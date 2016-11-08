package rs.elfak.bobans.carsharing.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rs.elfak.bobans.carsharing.R;
import rs.elfak.bobans.carsharing.models.SharedDrive;
import rs.elfak.bobans.carsharing.utils.DateTimeUtils;

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

        holder.route.setText(holder.itemView.getContext().getString(R.string.route, drive.getDeparture(), drive.getDestination()));
        holder.name.setText(drive.getUser().getName());
        holder.car.setText(holder.itemView.getContext().getString(R.string.car, drive.getCar().getModel().getMake().getTitle(), drive.getCar().getModel().getTitle(), drive.getCar().getYear()));
        holder.date.setText(DateTimeUtils.printMediumDateTime(drive.getTime().getDepartureTime()));
        if (drive.getTime().isRepeat()) {
            String repeatDays = drive.getTime().getRepeatDays();
            holder.monday.setActivated(repeatDays.contains("M"));
            holder.tuesday.setActivated(repeatDays.contains("T"));
            holder.wednesday.setActivated(repeatDays.contains("W"));
            holder.thursday.setActivated(repeatDays.contains("R"));
            holder.friday.setActivated(repeatDays.contains("F"));
            holder.saturday.setActivated(repeatDays.contains("U"));
            holder.sunday.setActivated(repeatDays.contains("S"));
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_view_user_photo) ImageView Photo;
        @BindView(R.id.text_view_route) TextView route;
        @BindView(R.id.text_view_name) TextView name;
        @BindView(R.id.text_view_car) TextView car;
        @BindView(R.id.text_view_date) TextView date;
        @BindView(R.id.text_view_repetition_monday) TextView monday;
        @BindView(R.id.text_view_repetition_tuesday) TextView tuesday;
        @BindView(R.id.text_view_repetition_wednesday) TextView wednesday;
        @BindView(R.id.text_view_repetition_thursday) TextView thursday;
        @BindView(R.id.text_view_repetition_friday) TextView friday;
        @BindView(R.id.text_view_repetition_saturday) TextView saturday;
        @BindView(R.id.text_view_repetition_sunday) TextView sunday;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

    }
}
