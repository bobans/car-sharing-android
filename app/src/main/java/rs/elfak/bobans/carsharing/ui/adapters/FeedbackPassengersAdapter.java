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
import rs.elfak.bobans.carsharing.models.Passenger;
import rs.elfak.bobans.carsharing.utils.PictureUtils;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */
public class FeedbackPassengersAdapter extends RecyclerViewArrayAdapter<Passenger, FeedbackPassengersAdapter.ViewHolder> {

    private Typeface fontMedium;
    private OnPassengerClickListener onPassengerClickListener;

    public FeedbackPassengersAdapter(Context context) {
        this(context, null);
    }

    public FeedbackPassengersAdapter(Context context, List<Passenger> passengers) {
        super(passengers);

        fontMedium = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");
    }

    public void setOnPassengerClickListener(OnPassengerClickListener onPassengerClickListener) {
        this.onPassengerClickListener = onPassengerClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feedback_passenger, parent, false));
        holder.name.setTypeface(fontMedium);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Passenger passenger = getItemAt(position);

        holder.divider.setVisibility(position == 0 ? View.GONE : View.VISIBLE);

        PictureUtils.loadImage(passenger.getUser().getPhotoUrl(), R.drawable.ic_user_placeholder, holder.photo);
        holder.name.setText(passenger.getUser().getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPassengerClickListener != null) {
                    onPassengerClickListener.onPassengerClick(FeedbackPassengersAdapter.this, holder.getAdapterPosition(), getItemAt(holder.getAdapterPosition()));
                }
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.divider) View divider;
        @BindView(R.id.image_view_user_photo) ImageView photo;
        @BindView(R.id.text_view_user_name) TextView name;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

    }

    public interface OnPassengerClickListener {
        void onPassengerClick(FeedbackPassengersAdapter adapter, int position, Passenger passenger);
    }

}
