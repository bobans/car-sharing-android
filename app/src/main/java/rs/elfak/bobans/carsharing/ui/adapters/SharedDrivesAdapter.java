package rs.elfak.bobans.carsharing.ui.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

public class SharedDrivesAdapter extends SelectableRecyclerViewArrayAdapter<SharedDrive, SharedDrivesAdapter.ViewHolder> {

    private Typeface fontRegular;
    private Typeface fontMedium;

    public SharedDrivesAdapter(Context context) {
        super();

        fontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
        fontMedium = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");
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

        if (position == getSelection()) {
            holder.expand.setVisibility(View.VISIBLE);
        } else {
            holder.expand.setVisibility(View.GONE);
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

        if (drive.getStops().size() > 0) {
            boolean first = true;
            holder.stops.setText("");
            for (String stop : drive.getStops()) {
                if (first) {
                    first = false;
                } else {
                    holder.stops.append(", ");
                }
                holder.stops.append(stop);
            }
        } else {
            holder.stops.setText(R.string.text_no_stops);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position == getSelection()) {
                    setSelection(-1);
                } else {
                    setSelection(position);
                }
            }
        });
    }

    private void setPreferenceState(int preferenceState, View view) {
        switch (preferenceState) {
            case DrivePreferences.FLAG_POSITIVE: {
                view.setActivated(true);
                view.setEnabled(true);
                break;
            }

            case DrivePreferences.FLAG_NEGATIVE: {
                view.setActivated(false);
                view.setEnabled(false);
                break;
            }

            case DrivePreferences.FLAG_NEUTRAL: {
                view.setActivated(false);
                view.setEnabled(true);
                break;
            }
        }
    }

    private void setFonts(ViewHolder holder) {
        holder.route.setTypeface(fontMedium);
        holder.name.setTypeface(fontRegular);
        holder.car.setTypeface(fontRegular);
        holder.date.setTypeface(fontRegular);
        holder.monday.setTypeface(fontMedium);
        holder.tuesday.setTypeface(fontMedium);
        holder.wednesday.setTypeface(fontMedium);
        holder.thursday.setTypeface(fontMedium);
        holder.friday.setTypeface(fontMedium);
        holder.saturday.setTypeface(fontMedium);
        holder.sunday.setTypeface(fontMedium);
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
        @BindView(R.id.expand_container) ViewGroup expand;
        @BindView(R.id.text_view_name) TextView name;
        @BindView(R.id.text_view_car) TextView car;
        @BindView(R.id.text_view_stops) TextView stops;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

    }
}
