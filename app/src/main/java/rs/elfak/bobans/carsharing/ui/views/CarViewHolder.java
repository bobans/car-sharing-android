package rs.elfak.bobans.carsharing.ui.views;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rs.elfak.bobans.carsharing.R;
import rs.elfak.bobans.carsharing.models.Car;
import rs.elfak.bobans.carsharing.models.CarDAO;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class CarViewHolder {

    private final View itemView;

    @BindView(R.id.text_view_make) TextView tvMake;
    @BindView(R.id.text_view_model) TextView tvModel;
    @BindView(R.id.text_view_plates) TextView tvPlates;
    @BindView(R.id.text_view_year) TextView tvYear;

    private Typeface fontRegular;
    private Typeface fontMedium;

    public CarViewHolder(LayoutInflater inflater, ViewGroup parent, CarDAO car) {
        itemView = inflater.inflate(R.layout.item_car, parent, false);

        fontRegular = Typeface.createFromAsset(inflater.getContext().getAssets(), "fonts/Roboto-Regular.ttf");
        fontMedium = Typeface.createFromAsset(inflater.getContext().getAssets(), "fonts/Roboto-Medium.ttf");

        ButterKnife.bind(this, itemView);

        bindView(car);
    }

    private void bindView(CarDAO car) {
        tvMake.setTypeface(fontMedium);
        tvModel.setTypeface(fontRegular);
        tvPlates.setTypeface(fontMedium);
        tvYear.setTypeface(fontRegular);

        if (car != null) {
            if (car.getModel() != null) {
                if (car.getModel().getMake() != null) {
                    tvMake.setText(car.getModel().getMake().getTitle());
                }
                tvModel.setText(car.getModel().getTitle());
            }
            tvPlates.setText(car.getRegistrationPlates());
            tvYear.setText(String.valueOf(car.getYear()));
        }
        itemView.setTag(car);
    }

    public void attachToView(ViewGroup container) {
        if (itemView.getParent() != null) {
            ((ViewGroup) itemView.getParent()).removeView(itemView);
        }

        container.addView(itemView);
    }

    public void attachToView(ViewGroup container, int index) {
        if (itemView.getParent() != null) {
            ((ViewGroup) itemView.getParent()).removeView(itemView);
        }

        container.addView(itemView, index);
    }

    public View getItemView() {
        return itemView;
    }

}
