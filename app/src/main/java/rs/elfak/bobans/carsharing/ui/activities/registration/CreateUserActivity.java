package rs.elfak.bobans.carsharing.ui.activities.registration;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;

import butterknife.BindView;
import butterknife.ButterKnife;
import rs.elfak.bobans.carsharing.R;
import rs.elfak.bobans.carsharing.interactors.registration.CreateUserInteractor;
import rs.elfak.bobans.carsharing.models.User;
import rs.elfak.bobans.carsharing.presenters.registration.CreateUserPresenter;
import rs.elfak.bobans.carsharing.ui.activities.BaseActivity;
import rs.elfak.bobans.carsharing.ui.activities.LoginEmailActivity;
import rs.elfak.bobans.carsharing.ui.adapters.FontArrayAdapter;
import rs.elfak.bobans.carsharing.ui.dialogs.TwoButtonsDialog;
import rs.elfak.bobans.carsharing.utils.ClearErrorTextWatcher;
import rs.elfak.bobans.carsharing.utils.Constants;
import rs.elfak.bobans.carsharing.utils.DateTimeUtils;
import rs.elfak.bobans.carsharing.views.registration.ICreateUserView;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class CreateUserActivity extends BaseActivity<Object, CreateUserInteractor, ICreateUserView, CreateUserPresenter> implements ICreateUserView, View.OnClickListener, View.OnFocusChangeListener, AdapterView.OnItemSelectedListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.text_input_name) TextInputLayout tiName;
    @BindView(R.id.edit_text_name) EditText etName;
    @BindView(R.id.text_input_email) TextInputLayout tiEmail;
    @BindView(R.id.edit_text_email) EditText etEmail;
    @BindView(R.id.text_input_city) TextInputLayout tiCity;
    @BindView(R.id.edit_text_city) EditText etCity;
    @BindView(R.id.text_input_birth_date) TextInputLayout tiBirthDate;
    @BindView(R.id.edit_text_birth_date) EditText etBirthDate;
    @BindView(R.id.text_view_user_type) TextView tvUserType;
    @BindView(R.id.spinner_user_type) Spinner spUserType;
    @BindView(R.id.driver_container) View driverContainer;
    @BindView(R.id.text_input_driver_license) TextInputLayout tiDriverLicense;
    @BindView(R.id.edit_text_driver_license) EditText etDriverLicense;
    @BindView(R.id.text_view_cars) TextView tvCars;
    @BindView(R.id.image_view_add_car) ImageView ivAddCar;
    @BindView(R.id.cars_container) ViewGroup carsContainer;
    @BindView(R.id.button_create_user) Button btnCreateUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        initView();
    }

    private void initView() {
        tiName.setTypeface(fontRegular);
        etName.setTypeface(fontRegular);
        tiEmail.setTypeface(fontRegular);
        etEmail.setTypeface(fontRegular);
        tiCity.setTypeface(fontRegular);
        etCity.setTypeface(fontRegular);
        tiBirthDate.setTypeface(fontRegular);
        etBirthDate.setTypeface(fontRegular);
        tvUserType.setTypeface(fontRegular);
        tiDriverLicense.setTypeface(fontRegular);
        etDriverLicense.setTypeface(fontRegular);
        tvCars.setTypeface(fontRegular);

        etName.addTextChangedListener(new ClearErrorTextWatcher(tiName));
        etEmail.addTextChangedListener(new ClearErrorTextWatcher(tiEmail));
        etCity.addTextChangedListener(new ClearErrorTextWatcher(tiCity));
        etDriverLicense.addTextChangedListener(new ClearErrorTextWatcher(tiDriverLicense));

        etBirthDate.setOnFocusChangeListener(this);
        etDriverLicense.setOnFocusChangeListener(this);
        ivAddCar.setOnClickListener(this);
        btnCreateUser.setOnClickListener(this);

        spUserType.setOnItemSelectedListener(this);

        initUserTypes();
    }

    private void initUserTypes() {
        String[] userTypes = getResources().getStringArray(R.array.user_types);
        FontArrayAdapter<String> adapter = new FontArrayAdapter<>(this, android.R.layout.simple_spinner_item, userTypes, fontRegular);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropdownFont(fontRegular);
        spUserType.setAdapter(adapter);
    }

    @NonNull
    @Override
    public CreateUserPresenter createPresenter() {
        return new CreateUserPresenter();
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void loadData(boolean pullToRefresh) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_create_user: {
                if (validate()) {
                    getPresenter().createUser(createUser());
                }
                break;
            }

            case R.id.image_view_add_car: {
                // TODO add car activity
                Toast.makeText(this, "Add car", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    private User createUser() {
        User user = new User();
        user.setName(etName.getText().toString());
        user.setEmail(etEmail.getText().toString());
        user.setCity(etCity.getText().toString());
        user.setBirthDate((DateTime) etBirthDate.getTag());
        user.setDriverLicenseDate((DateTime) etDriverLicense.getTag());
        user.setUserType(spUserType.getSelectedItemPosition() + 1);
        return user;
    }

    private boolean validate() {
        boolean valid = true;
        if (etName.length() == 0) {
            tiName.setError(getString(R.string.error_no_name));
            valid = false;
        }
        if (etEmail.length() == 0) {
            tiEmail.setError(getString(R.string.error_no_email));
            valid = false;
        } else if (!Constants.VALID_EMAIL_ADDRESS_REGEX.matcher(etEmail.getText().toString()).matches()) {
            tiEmail.setError(getString(R.string.error_invalid_email));
            valid = false;
        }
        if (etCity.length() == 0) {
            tiCity.setError(getString(R.string.error_no_city));
            valid = false;
        }
        if (etBirthDate.length() == 0) {
            tiBirthDate.setError(getString(R.string.error_no_birth_date));
            valid = false;
        }
        if (spUserType.getSelectedItemPosition() == 1) {    // driver
            if (etDriverLicense.length() == 0) {
                tiDriverLicense.setError(getString(R.string.error_no_driver_license));
                valid = false;
            }
        }
        return valid;
    }

    @Override
    public void showUserAlreadyExists() {
        TwoButtonsDialog dialog = new TwoButtonsDialog(this, android.R.drawable.ic_dialog_alert, R.string.warning_user_already_exists, R.string.button_to_login, R.string.back, new TwoButtonsDialog.OnClickListener() {
            @Override
            public void onPositiveButtonClick(DialogInterface dialogInterface, View view) {
                startActivity(new Intent(CreateUserActivity.this, LoginEmailActivity.class));
                finish();
            }

            @Override
            public void onNegativeButtonClick(DialogInterface dialogInterface, View view) {

            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void showMain() {
        // TODO show main
        showContent();
        Toast.makeText(this, "User created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.edit_text_birth_date: {
                if (hasFocus) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    DateTime dateTime = (DateTime) etBirthDate.getTag();
                    if (dateTime == null) {
                        dateTime = DateTime.now();
                    }
                    DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {
                            DateTime picked = new DateTime(year, month + 1, dayOfMonth, 0, 0);
                            etBirthDate.setTag(picked);
                            etBirthDate.setText(DateTimeUtils.printLongDate(picked));
                        }
                    }, dateTime.getYear(), dateTime.getMonthOfYear() - 1, dateTime.getDayOfMonth());
                    datePickerDialog.setCancelable(false);
                    datePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            etName.requestFocus();
                        }
                    });
                    datePickerDialog.show();
                }
                break;
            }

            case R.id.edit_text_driver_license: {
                if (hasFocus) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    DateTime dateTime = (DateTime) etDriverLicense.getTag();
                    if (dateTime == null) {
                        dateTime = DateTime.now();
                    }
                    DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {
                            DateTime picked = new DateTime(year, month + 1, dayOfMonth, 0, 0);
                            etDriverLicense.setTag(picked);
                            etDriverLicense.setText(DateTimeUtils.printLongDate(picked));
                        }
                    }, dateTime.getYear(), dateTime.getMonthOfYear() - 1, dateTime.getDayOfMonth());
                    datePickerDialog.setCancelable(false);
                    datePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            etName.requestFocus();
                        }
                    });
                    datePickerDialog.show();
                }
                break;
            }

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0: {
                driverContainer.setVisibility(View.GONE);
                break;
            }

            case 1: {
                driverContainer.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
