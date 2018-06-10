package rs.elfak.bobans.carsharing.ui.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rs.elfak.bobans.carsharing.R;
import rs.elfak.bobans.carsharing.interactors.CreateSharedDriveInteractor;
import rs.elfak.bobans.carsharing.models.Car;
import rs.elfak.bobans.carsharing.models.DrivePreferences;
import rs.elfak.bobans.carsharing.models.DrivePreferencesDAO;
import rs.elfak.bobans.carsharing.models.DrivePrice;
import rs.elfak.bobans.carsharing.models.DrivePriceDAO;
import rs.elfak.bobans.carsharing.models.DriveTimeDAO;
import rs.elfak.bobans.carsharing.models.SharedDrive;
import rs.elfak.bobans.carsharing.models.SharedDriveDAO;
import rs.elfak.bobans.carsharing.presenters.CreateSharedDrivePresenter;
import rs.elfak.bobans.carsharing.ui.adapters.FontArrayAdapter;
import rs.elfak.bobans.carsharing.ui.dialogs.AddStopDialog;
import rs.elfak.bobans.carsharing.utils.SessionManager;
import rs.elfak.bobans.carsharing.views.ICreateSharedDriveView;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class CreateSharedDriveActivity extends BaseActivity<SharedDrive, CreateSharedDriveInteractor, ICreateSharedDriveView, CreateSharedDrivePresenter> implements ICreateSharedDriveView, View.OnClickListener, CompoundButton.OnCheckedChangeListener, AddStopDialog.OnCityEnteredListener {

    public static final String EXTRA_SHARED_DRIVE = "EXTRA_SHARED_DRIVE";

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.text_input_departure) TextInputLayout tiDeparture;
    @BindView(R.id.edit_text_departure) EditText etDeparture;
    @BindView(R.id.text_input_destination) TextInputLayout tiDestination;
    @BindView(R.id.edit_text_destination) EditText etDestination;
    @BindView(R.id.text_view_stops_label) TextView tvStopsLabel;
    @BindView(R.id.image_view_add_stop) ImageView ivAddStop;
    @BindView(R.id.stops_container) ViewGroup stopsContainer;
    @BindView(R.id.text_view_car_label) TextView tvCarLabel;
    @BindView(R.id.spinner_car) Spinner spCar;
    @BindView(R.id.text_input_seats) TextInputLayout tiSeats;
    @BindView(R.id.edit_text_seats) EditText etSeats;
    @BindView(R.id.text_view_preference_music) TextView tvPreferenceMusic;
    @BindView(R.id.text_view_preference_talk) TextView tvPreferenceTalk;
    @BindView(R.id.text_view_preference_pets) TextView tvPreferencePets;
    @BindView(R.id.text_view_preference_smoking) TextView tvPreferenceSmoking;
    @BindView(R.id.text_input_date) TextInputLayout tiDate;
    @BindView(R.id.edit_text_date) EditText etDate;
    @BindView(R.id.checkbox_repeat) CheckBox cbRepeat;
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
    @BindView(R.id.text_view_price_type_label) TextView tvPriceTypeLabel;
    @BindView(R.id.spinner_price_type) Spinner spPriceType;

    private DateTimeFormatter datePrinter;
    private DateTimeFormatter timePrinter;
    private DecimalFormat pricePrinter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_shared_drive);

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
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear_white);

        setFonts();

        String[] priceTypes = getResources().getStringArray(R.array.price_types);
        FontArrayAdapter<String> priceTypeAdapter = new FontArrayAdapter<>(this, R.layout.spinner_item, priceTypes, fontMedium);
        priceTypeAdapter.setDropdownFont(fontMedium);
        priceTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPriceType.setAdapter(priceTypeAdapter);

        ivAddStop.setOnClickListener(this);
        tvPreferenceMusic.setOnClickListener(this);
        tvPreferenceTalk.setOnClickListener(this);
        tvPreferencePets.setOnClickListener(this);
        tvPreferenceSmoking.setOnClickListener(this);
        etDate.setOnClickListener(this);
        cbRepeat.setOnCheckedChangeListener(this);
        tvRepetitionMonday.setOnClickListener(new RepetitionOnClickListener());
        tvRepetitionTuesday.setOnClickListener(new RepetitionOnClickListener());
        tvRepetitionWednesday.setOnClickListener(new RepetitionOnClickListener());
        tvRepetitionThursday.setOnClickListener(new RepetitionOnClickListener());
        tvRepetitionFriday.setOnClickListener(new RepetitionOnClickListener());
        tvRepetitionSaturday.setOnClickListener(new RepetitionOnClickListener());
        tvRepetitionSunday.setOnClickListener(new RepetitionOnClickListener());
        etDepartureTime.setOnClickListener(this);
        etArrivalTime.setOnClickListener(this);
    }

    private void setFonts() {
        tiDeparture.setTypeface(fontRegular);
        etDeparture.setTypeface(fontMedium);
        tiDestination.setTypeface(fontRegular);
        etDestination.setTypeface(fontMedium);
        tvStopsLabel.setTypeface(fontRegular);
        tvCarLabel.setTypeface(fontRegular);
        tiSeats.setTypeface(fontRegular);
        etSeats.setTypeface(fontMedium);
        tvPreferenceMusic.setTypeface(fontMedium);
        tvPreferenceTalk.setTypeface(fontMedium);
        tvPreferencePets.setTypeface(fontMedium);
        tvPreferenceSmoking.setTypeface(fontMedium);
        tiDate.setTypeface(fontRegular);
        etDate.setTypeface(fontMedium);
        cbRepeat.setTypeface(fontMedium);
        tiDepartureTime.setTypeface(fontRegular);
        etDepartureTime.setTypeface(fontMedium);
        tiArrivalTime.setTypeface(fontRegular);
        etArrivalTime.setTypeface(fontMedium);
        tiPrice.setTypeface(fontRegular);
        etPrice.setTypeface(fontMedium);
        tvPriceTypeLabel.setTypeface(fontRegular);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_create_shared_drive, menu);
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

            case R.id.action_save: {
                if (checkInput()) {
                    SharedDriveDAO sharedDrive = createSharedDriveDAO();
                    getPresenter().onDoneClicked(sharedDrive);
                }
                return true;
            }

            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    @NonNull
    private SharedDriveDAO createSharedDriveDAO() {
        SharedDriveDAO sharedDrive = new SharedDriveDAO();
        sharedDrive.setDeparture(etDeparture.getText().toString().trim());
        sharedDrive.setDestination(etDestination.getText().toString().trim());
        sharedDrive.setStops(new ArrayList<String>());
        for (int i = 0; i < stopsContainer.getChildCount(); i++) {
            sharedDrive.addStop(((TextView) stopsContainer.getChildAt(i).findViewById(R.id.text_view_city)).getText().toString().trim());
        }
        sharedDrive.setCar((Car) spCar.getSelectedItem());
        sharedDrive.setSeats(Integer.parseInt(etSeats.getText().toString().trim()));
        DrivePreferencesDAO drivePreferences = new DrivePreferencesDAO();
        drivePreferences.setMusic(getDrivePreference(tvPreferenceMusic));
        drivePreferences.setTalk(getDrivePreference(tvPreferenceTalk));
        drivePreferences.setPets(getDrivePreference(tvPreferencePets));
        drivePreferences.setSmoking(getDrivePreference(tvPreferenceSmoking));
        sharedDrive.setPreferences(drivePreferences);
        DriveTimeDAO driveTime = new DriveTimeDAO();
        driveTime.setDate((DateTime) etDate.getTag());
        driveTime.setRepeat(cbRepeat.isChecked());
        if (cbRepeat.isChecked()) {
            driveTime.setRepeatDays(createRepeatDays());
        }
        driveTime.setDepartureTime((DateTime) etDepartureTime.getTag());
        driveTime.setArrivalTime((DateTime) etArrivalTime.getTag());
        sharedDrive.setTime(driveTime);
        DrivePriceDAO drivePrice = new DrivePriceDAO();
        switch (spPriceType.getSelectedItemPosition()) {
            case 0: {
                drivePrice.setType(DrivePrice.PRICE_PER_PASSENGER);
                break;
            }

            case 1: {
                drivePrice.setType(DrivePrice.PRICE_TOTAL);
                break;
            }
        }
        drivePrice.setPrice(Double.parseDouble(etPrice.getText().toString().trim()));
        sharedDrive.setPrice(drivePrice);
        sharedDrive.setUser(SessionManager.getInstance().getUser());
        return sharedDrive;
    }

    private String createRepeatDays() {
        String repeatString = "";
        if (tvRepetitionMonday.isActivated()) {
            repeatString += "M";
        }
        if (tvRepetitionTuesday.isActivated()) {
            repeatString += "T";
        }
        if (tvRepetitionWednesday.isActivated()) {
            repeatString += "W";
        }
        if (tvRepetitionThursday.isActivated()) {
            repeatString += "R";
        }
        if (tvRepetitionFriday.isActivated()) {
            repeatString += "F";
        }
        if (tvRepetitionSaturday.isActivated()) {
            repeatString += "U";
        }
        if (tvRepetitionSunday.isActivated()) {
            repeatString += "S";
        }
        return repeatString;
    }

    private int getDrivePreference(View preference) {
        if (preference.isActivated() && preference.isSelected()) {
            return DrivePreferences.FLAG_POSITIVE;
        }
        if (!preference.isActivated() && preference.isSelected()) {
            return DrivePreferences.FLAG_NEGATIVE;
        }
        return DrivePreferences.FLAG_NEUTRAL;
    }

    private boolean checkInput() {
        if (etDeparture.length() == 0) {
            showLightError(getString(R.string.error_shared_drive_departure));
            return false;
        }
        if (etDestination.length() == 0) {
            showLightError(getString(R.string.error_shared_drive_destination));
            return false;
        }
        if (etSeats.length() == 0) {
            showLightError(getString(R.string.error_shared_drive_seats));
            return false;
        }
        if (etDate.length() == 0) {
            showLightError(getString(R.string.error_shared_drive_date));
            return false;
        }
        if (etDepartureTime.length() == 0) {
            showLightError(getString(R.string.error_shared_drive_departure_time));
            return false;
        }
        if (etArrivalTime.length() == 0) {
            showLightError(getString(R.string.error_shared_drive_arrival_time));
            return false;
        }
        if (etPrice.length() == 0) {
            showLightError(getString(R.string.error_shared_drive_price));
            return false;
        }
        return true;
    }

    @NonNull
    @Override
    public CreateSharedDrivePresenter createPresenter() {
        SharedDrive sharedDrive = null;
        if (getIntent() != null) {
            sharedDrive = getIntent().getParcelableExtra(EXTRA_SHARED_DRIVE);
        }
        return new CreateSharedDrivePresenter(sharedDrive);
    }

    @Override
    public void setData(SharedDrive data) {
        etDeparture.setText(data.getDeparture());
        etDestination.setText(data.getDestination());
        etSeats.setText(String.valueOf(data.getSeats()));
        if (data.getStops() != null) {
            for (String city : data.getStops()) {
                addStop(city);
            }
        }
        setPreference(tvPreferenceMusic, data.getPreferences().getMusic());
        setPreference(tvPreferenceTalk, data.getPreferences().getTalk());
        setPreference(tvPreferencePets, data.getPreferences().getPets());
        setPreference(tvPreferenceSmoking, data.getPreferences().getSmoking());
        etDate.setText(datePrinter.print(data.getTime().getDate()));
        etDate.setTag(data.getTime().getDate());
        etDepartureTime.setText(timePrinter.print(data.getTime().getDepartureTime()));
        etDepartureTime.setTag(data.getTime().getDepartureTime());
        etArrivalTime.setText(timePrinter.print(data.getTime().getArrivalTime()));
        etArrivalTime.setTag(data.getTime().getArrivalTime());
        etPrice.setText(pricePrinter.format(data.getPrice().getPrice()));
        switch (data.getPrice().getType()) {
            case DrivePrice.PRICE_PER_PASSENGER: {
                spPriceType.setSelection(0);
                break;
            }

            case DrivePrice.PRICE_TOTAL: {
                spPriceType.setSelection(1);
                break;
            }
        }
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
        getPresenter().loadCars(pullToRefresh);
        getPresenter().loadSharedDrive();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_view_add_stop: {
                new AddStopDialog(this, this).show();
                break;
            }

            case R.id.text_view_preference_music: {
                showPreferenceStatusPicker(tvPreferenceMusic);
                break;
            }

            case R.id.text_view_preference_talk: {
                showPreferenceStatusPicker(tvPreferenceTalk);
                break;
            }

            case R.id.text_view_preference_pets: {
                showPreferenceStatusPicker(tvPreferencePets);
                break;
            }

            case R.id.text_view_preference_smoking: {
                showPreferenceStatusPicker(tvPreferenceSmoking);
                break;
            }

            case R.id.edit_text_date: {
                getPresenter().onDateClicked((DateTime) etDate.getTag());
                break;
            }

            case R.id.edit_text_departure_time: {
                getPresenter().onDepartureTimeClicked((DateTime) etDepartureTime.getTag());
                break;
            }

            case R.id.edit_text_arrival_time: {
                getPresenter().onArrivalTimeClicked((DateTime) etArrivalTime.getTag());
                break;
            }
        }
    }

    private void showPreferenceStatusPicker(final TextView view) {
        PopupMenu menu = new PopupMenu(this, view);
        menu.inflate(R.menu.preference_status);
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_preference_status_positive: {
                        view.setActivated(true);
                        view.setSelected(true);
                        return true;
                    }

                    case R.id.action_preference_status_neutral: {
                        view.setActivated(false);
                        view.setSelected(false);
                        return true;
                    }

                    case R.id.action_preference_status_negative: {
                        view.setActivated(false);
                        view.setSelected(true);
                        return true;
                    }

                    default: {
                        return false;
                    }
                }
            }
        });
        menu.show();
    }

    @Override
    public void showDatePicker(final int requestCode, DateTime initialDate) {
        DatePickerDialog datePicker = new DatePickerDialog(this, R.style.AppTheme_Dialog_DateTimePicker, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                getPresenter().onDatePicked(requestCode, new DateTime(year, month + 1, dayOfMonth, 0, 0));
            }
        }, initialDate.getYear(), initialDate.getMonthOfYear() - 1, initialDate.getDayOfMonth());
        datePicker.setCancelable(false);
        datePicker.show();
    }

    @Override
    public void setDate(DateTime date) {
        etDate.setText(datePrinter.print(date));
        etDate.setTag(date);
    }

    @Override
    public void showTimePicker(final int requestCode, DateTime initialTime) {
        TimePickerDialog timePicker = new TimePickerDialog(this, R.style.AppTheme_Dialog_DateTimePicker, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                getPresenter().onTimePicked(requestCode, hourOfDay, minute);
            }
        }, initialTime.getHourOfDay(), initialTime.getMinuteOfHour(), true);
        timePicker.setCancelable(false);
        timePicker.show();
    }

    @Override
    public void setDepartureTime(DateTime time) {
        etDepartureTime.setText(timePrinter.print(time));
        etDepartureTime.setTag(time);
    }

    @Override
    public void setArrivalTime(DateTime time) {
        etArrivalTime.setText(timePrinter.print(time));
        etArrivalTime.setTag(time);
    }

    @Override
    public void setCars(List<Car> cars, Car selected) {
        FontArrayAdapter<Car> adapter = new FontArrayAdapter<Car>(this, R.layout.spinner_item, cars, fontMedium) {
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getView(position, convertView, parent);

                Car car = getItem(position);
                //noinspection ConstantConditions
                view.setText(getString(R.string.car,
                        car.getModel().getMake().getTitle(),
                        car.getModel().getTitle(),
                        car.getYear()));

                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getDropDownView(position, convertView, parent);


                Car car = getItem(position);
                //noinspection ConstantConditions
                view.setText(getString(R.string.car,
                        car.getModel().getMake().getTitle(),
                        car.getModel().getTitle(),
                        car.getYear()));

                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropdownFont(fontMedium);
        spCar.setAdapter(adapter);
        if (selected != null) {
            for (int i = 0; i < spCar.getAdapter().getCount(); i++) {
                if (selected.getId() == ((Car) spCar.getAdapter().getItem(i)).getId()) {
                    spCar.setSelection(i);
                }
            }
        }
    }

    @Override
    public void sharedDriveCreated() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void sharedDriveUpdated(SharedDrive sharedDrive) {
        Intent data = new Intent();
        data.putExtra(EXTRA_SHARED_DRIVE, sharedDrive);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.checkbox_repeat: {
                if (isChecked) {
                    repetitionContainer.setVisibility(View.VISIBLE);
                } else {
                    repetitionContainer.setVisibility(View.GONE);
                }
                break;
            }
        }
    }

    @Override
    public void onCityEntered(@NonNull String city) {
        addStop(city);
    }

    private void addStop(@NonNull String city) {
        View item = getLayoutInflater().inflate(R.layout.item_drive_stop, stopsContainer, false);

        TextView tvCity = (TextView) item.findViewById(R.id.text_view_city);
        ImageView ivRemove = (ImageView) item.findViewById(R.id.image_view_remove);

        tvCity.setTypeface(fontMedium);
        tvCity.setText(city);

        ivRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopsContainer.removeView((View) view.getParent());
            }
        });

        stopsContainer.addView(item);
    }

    private static class RepetitionOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            v.setActivated(!v.isActivated());
        }
    }

}
