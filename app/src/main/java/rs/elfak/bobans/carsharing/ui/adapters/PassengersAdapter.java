package rs.elfak.bobans.carsharing.ui.adapters;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import rs.elfak.bobans.carsharing.R;
import rs.elfak.bobans.carsharing.models.Passenger;
import rs.elfak.bobans.carsharing.models.PassengerDAO;
import rs.elfak.bobans.carsharing.utils.PictureUtils;
import rs.elfak.bobans.carsharing.utils.SessionManager;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */
public class PassengersAdapter extends RecyclerViewArrayAdapter<Passenger, PassengersAdapter.ViewHolder> {

    private OnPassengerClickListener onClickListener;

    public void setOnClickListener(OnPassengerClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_passenger, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Passenger passenger = getItemAt(position);

        switch (passenger.getStatus()) {
            case PassengerDAO.STATUS_REQUESTED: {
                holder.status.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorDriveRequested));
                break;
            }
            case PassengerDAO.STATUS_ACCEPTED: {
                holder.status.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorDriveAccepted));
                break;
            }
            case PassengerDAO.STATUS_REJECTED: {
                holder.status.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorDriveRejected));
                break;
            }
        }
        PictureUtils.loadImage(passenger.getUser().getPhotoUrl(), new CropCircleTransformation(holder.itemView.getContext()), R.drawable.ic_user_placeholder, holder.photo);
        holder.name.setText(passenger.getUser().getName());
        switch (passenger.getStatus()) {
            case PassengerDAO.STATUS_REQUESTED: {
                holder.reject.setEnabled(true);
                holder.accept.setEnabled(true);
                break;
            }
            case PassengerDAO.STATUS_ACCEPTED: {
                holder.reject.setEnabled(true);
                holder.accept.setEnabled(false);
                break;
            }
            case PassengerDAO.STATUS_REJECTED: {
                holder.reject.setEnabled(false);
                holder.accept.setEnabled(true);
                break;
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onPassengerClicked(holder.getAdapterPosition(), getItemAt(holder.getAdapterPosition()));
                }
            }
        });

        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onPassengerRejected(holder.getAdapterPosition(), getItemAt(holder.getAdapterPosition()));
                }
            }
        });

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onPassengerAccepted(holder.getAdapterPosition(), getItemAt(holder.getAdapterPosition()));
                }
            }
        });
    }

    public void setPassengerStatus(int adapterPosition, int status) {
        if (adapterPosition >= 0 && adapterPosition < getItemCount()) {
            getItemAt(adapterPosition).setStatus(status);
            notifyItemChanged(adapterPosition);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.passenger_status) View status;
        @BindView(R.id.image_view_user_photo) ImageView photo;
        @BindView(R.id.text_view_name) TextView name;
        @BindView(R.id.image_button_reject) ImageButton reject;
        @BindView(R.id.image_button_accept) ImageButton accept;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

    }

    public interface OnPassengerClickListener {
        void onPassengerClicked(int position, Passenger passenger);
        void onPassengerAccepted(int position, Passenger passenger);
        void onPassengerRejected(int position, Passenger passenger);
    }

}
