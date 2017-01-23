package rs.elfak.bobans.carsharing.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DecimalFormat;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import rs.elfak.bobans.carsharing.R;
import rs.elfak.bobans.carsharing.interactors.ViewSharedDriveInteractor;
import rs.elfak.bobans.carsharing.models.DrivePreferences;
import rs.elfak.bobans.carsharing.models.DrivePrice;
import rs.elfak.bobans.carsharing.models.Passenger;
import rs.elfak.bobans.carsharing.models.PassengerDAO;
import rs.elfak.bobans.carsharing.models.SharedDrive;
import rs.elfak.bobans.carsharing.presenters.ViewSharedDrivePresenter;
import rs.elfak.bobans.carsharing.ui.adapters.PassengersAdapter;
import rs.elfak.bobans.carsharing.views.IViewSharedDriveView;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */
public class ViewSharedDriveActivity extends BaseActivity<SharedDrive, ViewSharedDriveInteractor, IViewSharedDriveView, ViewSharedDrivePresenter> implements IViewSharedDriveView, View.OnClickListener, PassengersAdapter.OnPassengerClickListener {

    public static final String EXTRA_SHARED_DRIVE = "EXTRA_SHARED_DRIVE";

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.text_input_departure) TextInputLayout tiDeparture;
    @BindView(R.id.edit_text_departure) EditText etDeparture;
    @BindView(R.id.text_input_destination) TextInputLayout tiDestination;
    @BindView(R.id.edit_text_destination) EditText etDestination;
    @BindView(R.id.text_input_car) TextInputLayout tiCar;
    @BindView(R.id.edit_text_car) EditText etCar;
    @BindView(R.id.text_input_seats) TextInputLayout tiSeats;
    @BindView(R.id.edit_text_seats) EditText etSeats;
    @BindView(R.id.text_view_preference_music) TextView tvPreferenceMusic;
    @BindView(R.id.text_view_preference_talk) TextView tvPreferenceTalk;
    @BindView(R.id.text_view_preference_pets) TextView tvPreferencePets;
    @BindView(R.id.text_view_preference_smoking) TextView tvPreferenceSmoking;
    @BindView(R.id.text_input_date) TextInputLayout tiDate;
    @BindView(R.id.edit_text_date) EditText etDate;
    @BindView(R.id.repetition_container) ViewGroup repetitionContainer;
    @BindView(R.id.text_view_repetition_monday) TextView tvRepetitionMonday;
    @BindView(R.id.text_view_repetition_tuesday) TextView tvRepetitionTuesday;
    @BindView(R.id.text_view_repetition_wednesday) TextView tvRepetitionWednesday;
    @BindView(R.id.text_view_repetition_thursday) TextView tvRepetitionThursday;
    @BindView(R.id.text_view_repetition_friday) TextView tvRepetitionFriday;
    @BindView(R.id.text_view_repetition_saturday) TextView tvRepetitionSaturday;
    @BindView(R.id.text_view_repetition_sunday) TextView tvRepetitionSunday;
    @BindView(R.id.text_input_departure_time) TextInputLayout tiDepartureTime;
    @BindView(R.id.edit_text_departure_time) EditText etDepartureTime;
    @BindView(R.id.text_input_arrival_time) TextInputLayout tiArrivalTime;
    @BindView(R.id.edit_text_arrival_time) EditText etArrivalTime;
    @BindView(R.id.text_input_price) TextInputLayout tiPrice;
    @BindView(R.id.edit_text_price) EditText etPrice;
    @BindView(R.id.text_input_price_type) TextInputLayout tiPriceType;
    @BindView(R.id.edit_text_price_type) EditText etPriceType;
    @BindView(R.id.recycler_view_passengers) RecyclerView recyclerViewPassengers;
    @BindView(R.id.button_request_ride) Button btnRequestRide;

    @BindColor(R.color.colorDivider) int colorDivider;

    private DateTimeFormatter datePrinter;
    private DateTimeFormatter timePrinter;
    private DecimalFormat pricePrinter;

    private boolean isOwner = false;
    private boolean isPassenger = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_shared_drive);

        ButterKnife.bind(this);

        datePrinter = DateTimeFormat.forPattern("MMM dd, YYYY");
        timePrinter = DateTimeFormat.forPattern("HH:mm");
        pricePrinter = (DecimalFormat) DecimalFormat.getInstance();
        pricePrinter.setMinimumFractionDigits(0);
        pricePrinter.setMaximumFractionDigits(1);

        initView();

        loadData(false);
    }

    private void initView() {
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setFonts();

        recyclerViewPassengers.setHasFixedSize(true);
        recyclerViewPassengers.setNestedScrollingEnabled(false);
        recyclerViewPassengers.setLayoutManager(new LinearLayoutManager(this));
        PassengersAdapter adapter = new PassengersAdapter();
        adapter.setOnClickListener(this);
        recyclerViewPassengers.setAdapter(adapter);

        btnRequestRide.setOnClickListener(this);
    }

    private void setFonts() {
        tiDeparture.setTypeface(fontRegular);
        etDeparture.setTypeface(fontMedium);
        tiDestination.setTypeface(fontRegular);
        etDestination.setTypeface(fontMedium);
        tiCar.setTypeface(fontRegular);
        etCar.setTypeface(fontMedium);
        tiSeats.setTypeface(fontRegular);
        etSeats.setTypeface(fontMedium);
        tvPreferenceMusic.setTypeface(fontMedium);
        tvPreferenceTalk.setTypeface(fontMedium);
        tvPreferencePets.setTypeface(fontMedium);
        tvPreferenceSmoking.setTypeface(fontMedium);
        tiDate.setTypeface(fontRegular);
        etDate.setTypeface(fontMedium);
        tiDepartureTime.setTypeface(fontRegular);
        etDepartureTime.setTypeface(fontMedium);
        tiArrivalTime.setTypeface(fontRegular);
        etArrivalTime.setTypeface(fontMedium);
        tiPrice.setTypeface(fontRegular);
        etPrice.setTypeface(fontMedium);
        tiPriceType.setTypeface(fontRegular);
        etPriceType.setTypeface(fontMedium);
        btnRequestRide.setTypeface(fontMedium);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isOwner) {
            getMenuInflater().inflate(R.menu.activity_view_shared_drive, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                setResult(RESULT_CANCELED);
                finish();
                return true;
            }

            case R.id.action_edit: {
                getPresenter().onEditClick();
                return true;
            }

            case R.id.action_delete: {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage(R.string.prompt_delete_shared_drive)
                        .setPositiveButton(R.string.prompt_answer_yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getPresenter().deleteSharedDrive();
                            }
                        })
                        .setNegativeButton(R.string.prompt_answer_no, null)
                        .show();
                return true;
            }

            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    @NonNull
    @Override
    public ViewSharedDrivePresenter createPresenter() {
        SharedDrive sharedDrive = null;
        if (getIntent() != null) {
            sharedDrive = getIntent().getParcelableExtra(EXTRA_SHARED_DRIVE);
        }
        return new ViewSharedDrivePresenter(sharedDrive);
    }

    @Override
    public void setData(SharedDrive data) {
        etDeparture.setText(data.getDeparture());
        etDestination.setText(data.getDestination());
        etCar.setText(getString(R.string.car,
                data.getCar().getModel().getMake().getTitle(),
                data.getCar().getModel().getTitle(),
                data.getCar().getYear()));
        etSeats.setText(String.valueOf(data.getSeats()));
        setPreference(tvPreferenceMusic, data.getPreferences().getMusic());
        setPreference(tvPreferenceTalk, data.getPreferences().getTalk());
        setPreference(tvPreferencePets, data.getPreferences().getPets());
        setPreference(tvPreferenceSmoking, data.getPreferences().getSmoking());
        etDate.setText(datePrinter.print(data.getTime().getDate()));
        if (data.getTime().isRepeat()) {
            repetitionContainer.setVisibility(View.VISIBLE);
            setRepetition(data.getTime().getRepeatDays());
        } else {
            repetitionContainer.setVisibility(View.GONE);
        }
        etDepartureTime.setText(timePrinter.print(data.getTime().getDepartureTime()));
        etArrivalTime.setText(timePrinter.print(data.getTime().getArrivalTime()));
        etPrice.setText(pricePrinter.format(data.getPrice().getPrice()));
        switch (data.getPrice().getType()) {
            case DrivePrice.PRICE_PER_PASSENGER: {
                etPriceType.setText(R.string.price_type_by_person);
                break;
            }

            case DrivePrice.PRICE_TOTAL: {
                etPriceType.setText(R.string.price_type_total);
                break;
            }
        }
        ((PassengersAdapter) recyclerViewPassengers.getAdapter()).setItems(data.getPassengers());
    }

    private void setRepetition(String repeatDays) {
        tvRepetitionMonday.setActivated(repeatDays.contains("M"));
        tvRepetitionTuesday.setActivated(repeatDays.contains("T"));
        tvRepetitionWednesday.setActivated(repeatDays.contains("W"));
        tvRepetitionThursday.setActivated(repeatDays.contains("R"));
        tvRepetitionFriday.setActivated(repeatDays.contains("F"));
        tvRepetitionSaturday.setActivated(repeatDays.contains("U"));
        tvRepetitionSunday.setActivated(repeatDays.contains("S"));
    }

    private void setPreference(View preference, int setting) {
        switch (setting) {
            case DrivePreferences.FLAG_POSITIVE: {
                preference.setActivated(true);
                preference.setSelected(true);
                break;
            }

            case DrivePreferences.FLAG_NEGATIVE: {
                preference.setActivated(false);
                preference.setSelected(true);
                break;
            }

            case DrivePreferences.FLAG_NEUTRAL: {
                preference.setActivated(false);
                preference.setSelected(false);
                break;
            }
        }
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        getPresenter().loadData();
    }

    @Override
    public void deleteSuccessful() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void setIsOwner(boolean isOwner) {
        this.isOwner = isOwner;
        invalidateOptionsMenu();
        recyclerViewPassengers.setVisibility(isOwner ? View.VISIBLE : View.GONE);
        btnRequestRide.setVisibility(isOwner ? View.GONE : View.VISIBLE);
    }

    @Override
    public void requestSuccessful() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void setIsPassenger(boolean isPassenger) {
        this.isPassenger = isPassenger;
        btnRequestRide.setText(isOwner || isPassenger ? R.string.button_cancel_ride_request : R.string.button_request_a_ride);
    }

    @Override
    public void requestCanceled() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void requestUpdated(int adapterPosition, int status) {
        setResult(RESULT_OK);
        ((PassengersAdapter) recyclerViewPassengers.getAdapter()).setPassengerStatus(adapterPosition, status);
        switch (status) {
            case PassengerDAO.STATUS_ACCEPTED: {
                etSeats.setText(String.valueOf(Integer.parseInt(etSeats.getText().toString()) - 1));
                break;
            }
            case PassengerDAO.STATUS_REJECTED: {
                etSeats.setText(String.valueOf(Integer.parseInt(etSeats.getText().toString()) + 1));
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1: {
                setResult(resultCode);
                if (resultCode == RESULT_OK) {
                    setData((SharedDrive) data.getParcelableExtra(CreateSharedDriveActivity.EXTRA_SHARED_DRIVE));
                }
                break;
            }

            default: {
                super.onActivityResult(requestCode, resultCode, data);
                break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_request_ride: {
                if (!isPassenger) {
                    getPresenter().requestARide();
                } else {
                    getPresenter().cancelRideRequest();
                }
                break;
            }
        }
    }

    @Override
    public void onPassengerClicked(int position, Passenger passenger) {
        // TODO show passenger info activity
        Toast.makeText(this, "Passenger " + passenger.getUser().getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPassengerAccepted(int position, Passenger passenger) {
        getPresenter().updateRideRequest(position, passenger.getId(), PassengerDAO.STATUS_ACCEPTED);
    }

    @Override
    public void onPassengerRejected(int position, Passenger passenger) {
        getPresenter().updateRideRequest(position, passenger.getId(), PassengerDAO.STATUS_REJECTED);
    }

}
