package rs.elfak.bobans.carsharing.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rs.elfak.bobans.carsharing.R;
import rs.elfak.bobans.carsharing.interactors.GiveFeedbackInteractor;
import rs.elfak.bobans.carsharing.models.Car;
import rs.elfak.bobans.carsharing.models.Passenger;
import rs.elfak.bobans.carsharing.models.SharedDrive;
import rs.elfak.bobans.carsharing.models.User;
import rs.elfak.bobans.carsharing.presenters.GiveFeedbackPresenter;
import rs.elfak.bobans.carsharing.ui.adapters.FeedbackPassengersAdapter;
import rs.elfak.bobans.carsharing.utils.PictureUtils;
import rs.elfak.bobans.carsharing.views.IGiveFeedbackView;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */
public class GiveFeedbackActivity extends BaseActivity<Object, GiveFeedbackInteractor, IGiveFeedbackView, GiveFeedbackPresenter> implements IGiveFeedbackView, View.OnClickListener, FeedbackPassengersAdapter.OnPassengerClickListener {

    public static final String EXTRA_SHARED_DRIVE = "EXTRA_SHARED_DRIVE";

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.driver_container) LinearLayout driverContainer;
    @BindView(R.id.text_view_driver_label) TextView tvDriverLabel;
    @BindView(R.id.text_view_passengers_label) TextView tvPassengersLabel;
    @BindView(R.id.recycler_view_passengers) RecyclerView recyclerViewPassengers;
    @BindView(R.id.item_driver) View driverItem;
    @BindView(R.id.image_view_driver_photo) ImageView ivDriverPhoto;
    @BindView(R.id.text_view_driver_name) TextView tvDriverName;
    @BindView(R.id.text_view_car) TextView tvCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_feedback);

        ButterKnife.bind(this);

        initView();

        loadData(false);
    }

    private void initView() {
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setFonts();

        recyclerViewPassengers.setHasFixedSize(true);
        recyclerViewPassengers.setLayoutFrozen(true);
        recyclerViewPassengers.setNestedScrollingEnabled(false);
        recyclerViewPassengers.setLayoutManager(new LinearLayoutManager(this));
        FeedbackPassengersAdapter adapter = new FeedbackPassengersAdapter(this);
        adapter.setOnPassengerClickListener(this);
        recyclerViewPassengers.setAdapter(adapter);

        driverItem.setOnClickListener(this);
    }

    private void setFonts() {
        // TODO set fonts
        tvDriverLabel.setTypeface(fontBold);
        tvPassengersLabel.setTypeface(fontBold);
        tvDriverName.setTypeface(fontMedium);
        tvCar.setTypeface(fontRegular);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                setResult(RESULT_CANCELED);
                finish();
                return true;
            }

            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    @NonNull
    @Override
    public GiveFeedbackPresenter createPresenter() {
        SharedDrive sharedDrive = null;
        if (getIntent() != null) {
            sharedDrive = getIntent().getParcelableExtra(EXTRA_SHARED_DRIVE);
        }
        return new GiveFeedbackPresenter(sharedDrive);
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void loadData(boolean pullToRefresh) {
        getPresenter().loadData();
    }

    @Override
    public void setOwner(boolean isOwner, User user, Car car) {
        if (isOwner) {
            driverContainer.setVisibility(View.GONE);
        } else {
            driverContainer.setVisibility(View.VISIBLE);
            PictureUtils.loadImage(user.getPhotoUrl(), R.drawable.ic_user_placeholder, ivDriverPhoto);
            tvDriverName.setText(user.getName());
            tvCar.setText(getString(R.string.car,
                    car.getModel().getMake().getTitle(),
                    car.getModel().getTitle(),
                    car.getYear()));
        }
    }

    @Override
    public void setPassengers(List<Passenger> passengers) {
        ((FeedbackPassengersAdapter) recyclerViewPassengers.getAdapter()).setItems(passengers);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_driver: {
                // TODO review driver
                Toast.makeText(this, "Driver", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    @Override
    public void onPassengerClick(FeedbackPassengersAdapter adapter, int position, Passenger passenger) {
        // TODO review passenger
        Toast.makeText(this, "Passenger " + passenger.getUser().getName(), Toast.LENGTH_SHORT).show();
    }

}
