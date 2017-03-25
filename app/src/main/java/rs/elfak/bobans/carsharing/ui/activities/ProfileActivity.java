package rs.elfak.bobans.carsharing.ui.activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rs.elfak.bobans.carsharing.R;
import rs.elfak.bobans.carsharing.interactors.ProfileInteractor;
import rs.elfak.bobans.carsharing.models.Car;
import rs.elfak.bobans.carsharing.models.CarDAO;
import rs.elfak.bobans.carsharing.models.User;
import rs.elfak.bobans.carsharing.models.UserDAO;
import rs.elfak.bobans.carsharing.presenters.ProfilePresenter;
import rs.elfak.bobans.carsharing.ui.activities.registration.CreateCarActivity;
import rs.elfak.bobans.carsharing.ui.adapters.FontArrayAdapter;
import rs.elfak.bobans.carsharing.ui.views.CarViewHolder;
import rs.elfak.bobans.carsharing.utils.Constants;
import rs.elfak.bobans.carsharing.utils.DateTimeUtils;
import rs.elfak.bobans.carsharing.utils.textwatchers.ClearErrorTextWatcher;
import rs.elfak.bobans.carsharing.views.IProfileView;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */
public class ProfileActivity extends BaseActivity<User, ProfileInteractor, IProfileView, ProfilePresenter> implements IProfileView, View.OnFocusChangeListener, View.OnClickListener, View.OnLongClickListener, AdapterView.OnItemSelectedListener {

    private static final int REQUEST_CREATE_CAR = 1;
    private static final int REQUEST_EDIT_CAR = 2;
    private static final int REQUEST_PICK_PHOTO = 3;

    private static final int REQUEST_PERMISSION_STORAGE = 101;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.image_view_user_photo) ImageView ivPhoto;
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

    private MenuItem menuItemEdit;
    private MenuItem menuItemSave;

    private boolean editable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setEditable(false);

        initView();

        loadData(false);
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
        ivPhoto.setOnClickListener(this);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_profile, menu);
        menuItemEdit = menu.findItem(R.id.action_edit);
        menuItemSave = menu.findItem(R.id.action_save);
        menuItemSave.setVisible(false);
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
                menuItemEdit.setVisible(false);
                menuItemSave.setVisible(true);

                setEditable(true);
                return true;
            }

            case R.id.action_save: {
                menuItemEdit.setVisible(true);
                menuItemSave.setVisible(false);

                setEditable(false);

                if (validate()) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(etName.getWindowToken(), 0);

                    getPresenter().updateUser(createUser());
                }
                return true;
            }

            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    private UserDAO createUser() {
        UserDAO user = new UserDAO();
        user.setName(etName.getText().toString());
        user.setEmail(etEmail.getText().toString());
        user.setCity(etCity.getText().toString());
        user.setBirthDate((DateTime) etBirthDate.getTag());
        user.setUserType(spUserType.getSelectedItemPosition() + 1);
        if (user.getUserType() == User.TYPE_DRIVER) {
            user.setDriverLicenseDate((DateTime) etDriverLicense.getTag());
            for (int i=0; i<carsContainer.getChildCount(); i++) {
                user.addCar((CarDAO) carsContainer.getChildAt(i).getTag());
            }
        } else {
            user.setDriverLicenseDate(null);
            user.setCars(new ArrayList<CarDAO>());
        }
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

    @NonNull
    @Override
    public ProfilePresenter createPresenter() {
        return new ProfilePresenter();
    }

    @Override
    public void setData(User data) {
        etName.setText(data.getName());
        etEmail.setText(data.getEmail());
        etCity.setText(data.getCity());
        etBirthDate.setTag(data.getBirthDate());
        etBirthDate.setText(DateTimeUtils.printLongDate(data.getBirthDate()));
        if (data.getUserType() == User.TYPE_DRIVER) {
            spUserType.setSelection(1);
            etDriverLicense.setTag(data.getDriverLicenseDate());
            etDriverLicense.setText(DateTimeUtils.printLongDate(data.getDriverLicenseDate()));
            for (CarDAO car : data.getCars()) {
                CarViewHolder holder = new CarViewHolder(LayoutInflater.from(this), carsContainer, car);
                holder.attachToView(carsContainer);
                if (editable) {
                    registerForContextMenu(holder.getItemView());
                }
            }
        }
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        getPresenter().getMyProfile();
    }

    private void setEditable(boolean editable) {
        this.editable = editable;

        etName.setEnabled(editable);
        etName.setFocusable(editable);
        etName.setFocusableInTouchMode(editable);
        etEmail.setEnabled(editable);
        etEmail.setFocusable(editable);
        etEmail.setFocusableInTouchMode(editable);
        etCity.setEnabled(editable);
        etCity.setFocusable(editable);
        etCity.setFocusableInTouchMode(editable);
        etBirthDate.setEnabled(editable);
        etBirthDate.setFocusable(editable);
        etBirthDate.setFocusableInTouchMode(editable);
        spUserType.setEnabled(editable);
        etDriverLicense.setEnabled(editable);
        etDriverLicense.setFocusable(editable);
        etDriverLicense.setFocusableInTouchMode(editable);
        ivAddCar.setVisibility(editable ? View.VISIBLE : View.GONE);

        for (int i=0; i<carsContainer.getChildCount(); i++) {
            if (editable) {
                registerForContextMenu(carsContainer.getChildAt(i));
            } else {
                unregisterForContextMenu(carsContainer.getChildAt(i));
            }
        }
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
                    DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.AppTheme_Dialog_Alert, new DatePickerDialog.OnDateSetListener() {
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
                    DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.AppTheme_Dialog_Alert, new DatePickerDialog.OnDateSetListener() {
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_view_user_photo: {
                int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_STORAGE);
                } else {
                    pickPhoto();
                }
                break;
            }

            case R.id.image_view_add_car: {
                startActivityForResult(new Intent(this, CreateCarActivity.class), REQUEST_CREATE_CAR);
                break;
            }
        }
    }

    private void pickPhoto() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        Intent chooserIntent = Intent.createChooser(pickIntent, getString(R.string.label_select_image));
        startActivityForResult(chooserIntent, REQUEST_PICK_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CREATE_CAR: {
                if (resultCode == RESULT_OK) {
                    CarDAO car = data.getParcelableExtra(CreateCarActivity.EXTRA_CAR);
                    CarViewHolder holder = new CarViewHolder(LayoutInflater.from(this), carsContainer, car);
                    holder.attachToView(carsContainer);
                    if (editable) {
                        registerForContextMenu(holder.getItemView());
                    }
                }
                break;
            }

            case REQUEST_EDIT_CAR: {
                if (resultCode == RESULT_OK) {
                    Car car = data.getParcelableExtra(CreateCarActivity.EXTRA_CAR);
                    CarViewHolder holder = new CarViewHolder(LayoutInflater.from(this), carsContainer, car);
                    View editing = (View) carsContainer.getTag();
                    for (int i=0; i<carsContainer.getChildCount(); i++) {
                        if (editing.equals(carsContainer.getChildAt(i))) {
                            carsContainer.removeViewAt(i);
                            holder.attachToView(carsContainer, i);
                            carsContainer.setTag(null);
                        }
                    }
                    registerForContextMenu(holder.getItemView());
                    holder.getItemView().setOnLongClickListener(this);
                }
                break;
            }

            case REQUEST_PICK_PHOTO: {
                if (resultCode == RESULT_OK) {
                    Log.i(ProfileActivity.class.getSimpleName(), "Picked photo: " + data);
                }
                break;
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.car_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit: {
                View view = (View) carsContainer.getTag();
                Car car = (Car) view.getTag();
                Intent intent = new Intent(this, CreateCarActivity.class);
                intent.putExtra(CreateCarActivity.EXTRA_CAR, car);
                startActivityForResult(intent, REQUEST_EDIT_CAR);
                return true;
            }

            case R.id.action_remove: {
                View view = (View) carsContainer.getTag();
                carsContainer.removeView(view);
                carsContainer.setTag(null);
                return true;
            }

            default: {
                return super.onContextItemSelected(item);
            }
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (editable) {
            carsContainer.setTag(v);
            v.showContextMenu();
            return true;
        }
        return false;
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickPhoto();
                }
                break;
            }
        }
    }

}
