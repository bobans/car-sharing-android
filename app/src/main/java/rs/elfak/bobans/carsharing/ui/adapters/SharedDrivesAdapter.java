package rs.elfak.bobans.carsharing.ui.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rs.elfak.bobans.carsharing.R;
import rs.elfak.bobans.carsharing.models.DrivePreferences;
import rs.elfak.bobans.carsharing.models.DrivePrice;
import rs.elfak.bobans.carsharing.models.SharedDrive;
import rs.elfak.bobans.carsharing.utils.CarSharingApplication;
import rs.elfak.bobans.carsharing.utils.DateTimeUtils;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class SharedDrivesAdapter extends RecyclerViewArrayAdapter<SharedDrive, SharedDrivesAdapter.ViewHolder> {

    private Typeface fontRegular;
    private Typeface fontMedium;
    private OnSharedDriveClickListener onSharedDriveClickListener;

    public SharedDrivesAdapter(Context context) {
        this(context, null);
    }

    public SharedDrivesAdapter(Context context, List<SharedDrive> items) {
        super(items);

        fontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
        fontMedium = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");
    }

    public void setOnSharedDriveClickListener(OnSharedDriveClickListener onSharedDriveClickListener) {
        this.onSharedDriveClickListener = onSharedDriveClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shared_drive, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        SharedDrive drive = getItemAt(position);

        setFonts(holder);

        holder.route.setText(holder.itemView.getContext().getString(R.string.route, drive.getDeparture(), drive.getDestination()));
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
        } else {
            holder.monday.setActivated(false);
            holder.tuesday.setActivated(false);
            holder.wednesday.setActivated(false);
            holder.thursday.setActivated(false);
            holder.friday.setActivated(false);
            holder.saturday.setActivated(false);
            holder.sunday.setActivated(false);
        }

        setPreferenceState(drive.getPreferences().getMusic(), holder.preferenceMusic);
        setPreferenceState(drive.getPreferences().getPets(), holder.preferencePets);
        setPreferenceState(drive.getPreferences().getSmoking(), holder.preferenceSmoking);
        setPreferenceState(drive.getPreferences().getTalk(), holder.preferenceTalk);

        switch (drive.getPrice().getType()) {
            case DrivePrice.PRICE_TOTAL: {
                holder.price.setText(CarSharingApplication.getInstance().getString(R.string.drive_price_total, drive.getPrice().getPrice()));
                break;
            }

            case DrivePrice.PRICE_PER_PASSENGER: {
                holder.price.setText(CarSharingApplication.getInstance().getString(R.string.drive_price_per_passenger, drive.getPrice().getPrice()));
                break;
            }
        }

        holder.seats.setText(String.valueOf(drive.getSeats() - drive.getPassengers().size()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSharedDriveClickListener != null) {
                    onSharedDriveClickListener.onSharedDriveClick(SharedDrivesAdapter.this, holder.getAdapterPosition(), getItemAt(holder.getAdapterPosition()));
                }
            }
        });
    }

    private void setPreferenceState(int preferenceState, View view) {
        switch (preferenceState) {
            case DrivePreferences.FLAG_POSITIVE: {
                view.setActivated(true);
                view.setSelected(true);
                break;
            }

            case DrivePreferences.FLAG_NEGATIVE: {
                view.setActivated(false);
                view.setSelected(true);
                break;
            }

            case DrivePreferences.FLAG_NEUTRAL: {
                view.setActivated(false);
                view.setSelected(false);
                break;
            }
        }
    }

    private void setFonts(ViewHolder holder) {
        holder.route.setTypeface(fontMedium);
        holder.date.setTypeface(fontRegular);
        holder.monday.setTypeface(fontMedium);
        holder.tuesday.setTypeface(fontMedium);
        holder.wednesday.setTypeface(fontMedium);
        holder.thursday.setTypeface(fontMedium);
        holder.friday.setTypeface(fontMedium);
        holder.saturday.setTypeface(fontMedium);
        holder.sunday.setTypeface(fontMedium);
        holder.seats.setTypeface(fontRegular);
    }

    public interface OnSharedDriveClickListener {
        void onSharedDriveClick(SharedDrivesAdapter adapter, int position, SharedDrive sharedDrive);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_view_user_photo) ImageView Photo;
        @BindView(R.id.text_view_route) TextView route;
        @BindView(R.id.image_view_preference_music) ImageView preferenceMusic;
        @BindView(R.id.image_view_preference_pets) ImageView preferencePets;
        @BindView(R.id.image_view_preference_smoking) ImageView preferenceSmoking;
        @BindView(R.id.image_view_preference_talk) ImageView preferenceTalk;
        @BindView(R.id.text_view_price) TextView price;
        @BindView(R.id.text_view_date) TextView date;
        @BindView(R.id.text_view_repetition_monday) TextView monday;
        @BindView(R.id.text_view_repetition_tuesday) TextView tuesday;
        @BindView(R.id.text_view_repetition_wednesday) TextView wednesday;
        @BindView(R.id.text_view_repetition_thursday) TextView thursday;
        @BindView(R.id.text_view_repetition_friday) TextView friday;
        @BindView(R.id.text_view_repetition_saturday) TextView saturday;
        @BindView(R.id.text_view_repetition_sunday) TextView sunday;
        @BindView(R.id.text_view_seats) TextView seats;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

    }
}
