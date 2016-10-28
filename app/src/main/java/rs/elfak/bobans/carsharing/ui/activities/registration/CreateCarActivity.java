package rs.elfak.bobans.carsharing.ui.activities.registration;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rs.elfak.bobans.carsharing.R;
import rs.elfak.bobans.carsharing.interactors.registration.CreateCarInteractor;
import rs.elfak.bobans.carsharing.models.Car;
import rs.elfak.bobans.carsharing.models.Make;
import rs.elfak.bobans.carsharing.models.Model;
import rs.elfak.bobans.carsharing.presenters.registration.CreateCarPresenter;
import rs.elfak.bobans.carsharing.ui.activities.BaseActivity;
import rs.elfak.bobans.carsharing.ui.adapters.MakesFontArrayAdapter;
import rs.elfak.bobans.carsharing.ui.adapters.ModelsFontArrayAdapter;
import rs.elfak.bobans.carsharing.utils.Constants;
import rs.elfak.bobans.carsharing.utils.textwatchers.ClearErrorTextWatcher;
import rs.elfak.bobans.carsharing.views.registration.ICreateCarView;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class CreateCarActivity extends BaseActivity<Object, CreateCarInteractor, ICreateCarView, CreateCarPresenter> implements ICreateCarView, AdapterView.OnItemSelectedListener, View.OnClickListener {
    private static final String TAG = CreateCarActivity.class.getSimpleName();

    public static final String EXTRA_CAR = "EXTRA_CAR";

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.text_view_make) TextView tvMake;
    @BindView(R.id.spinner_make) Spinner spMake;
    @BindView(R.id.text_view_model) TextView tvModel;
    @BindView(R.id.spinner_model) Spinner spModel;
    @BindView(R.id.text_input_year) TextInputLayout tiYear;
    @BindView(R.id.edit_text_year) EditText etYear;
    @BindView(R.id.text_input_plates) TextInputLayout tiPlates;
    @BindView(R.id.edit_text_plates) EditText etPlates;
    @BindView(R.id.button_create_car) Button btnCreate;

    private Car car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_car);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        car = getIntent().getParcelableExtra(EXTRA_CAR);

        initView();

        loadData(false);
    }

    private void initView() {
        tvMake.setTypeface(fontRegular);
        tvModel.setTypeface(fontRegular);
        tiYear.setTypeface(fontRegular);
        etYear.setTypeface(fontRegular);
        tiPlates.setTypeface(fontRegular);
        etPlates.setTypeface(fontRegular);
        btnCreate.setTypeface(fontRegular);

        if (car != null) {
            etYear.setText(String.valueOf(car.getYear()));
            etPlates.setText(car.getRegistrationPlates());

            btnCreate.setText(R.string.button_update);
        }

        etPlates.addTextChangedListener(new ClearErrorTextWatcher(tiPlates));
        etYear.addTextChangedListener(new ClearErrorTextWatcher(tiYear));

        spMake.setOnItemSelectedListener(this);

        btnCreate.setOnClickListener(this);
    }

    @NonNull
    @Override
    public CreateCarPresenter createPresenter() {
        return new CreateCarPresenter();
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void loadData(boolean pullToRefresh) {
        getPresenter().loadMakes();
    }

    @Override
    public void setMakes(List<Make> makes) {
        MakesFontArrayAdapter adapter = new MakesFontArrayAdapter(this, makes, fontRegular);
        adapter.setDropdownFont(fontRegular);
        spMake.setAdapter(adapter);
        if (car != null) {
            for (int i = 0; i < adapter.getCount(); i++) {
                if (adapter.getItem(i).getId() == car.getModel().getMake().getId()) {
                    spMake.setSelection(i);
                }
            }
        }
    }

    @Override
    public void setModels(List<Model> models) {
        ModelsFontArrayAdapter adapter = new ModelsFontArrayAdapter(this, models, fontRegular);
        adapter.setDropdownFont(fontRegular);
        spModel.setAdapter(adapter);
        if (car != null) {
            for (int i = 0; i < adapter.getCount(); i++) {
                if (adapter.getItem(i).getId() == car.getModel().getId()) {
                    spModel.setSelection(i, false);
                }
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner_make: {
                getPresenter().loadModels(((Make) spMake.getSelectedItem()).getId());
                break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_create_car: {
                if (validate()) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    Car car = createCar();
                    Intent data = new Intent();
                    data.putExtra(EXTRA_CAR, car);
                    setResult(RESULT_OK, data);
                    finish();
                }
                break;
            }
        }
    }

    private Car createCar() {
        Car car = new Car();
        car.setModel((Model) spModel.getSelectedItem());
        car.setYear(Integer.parseInt(etYear.getText().toString()));
        car.setRegistrationPlates(etPlates.getText().toString());
        return car;
    }

    private boolean validate() {
        boolean valid = true;
        if (etYear.length() == 0) {
            tiYear.setError(getString(R.string.error_no_car_year));
            valid = false;
        } else {
            try {
                int year = Integer.parseInt(etYear.getText().toString());
                if (year < Constants.MIN_CAR_YEAR || year > Constants.MAX_CAR_YEAR) {
                    tiYear.setError(getString(R.string.integer_range, Constants.MIN_CAR_YEAR, Constants.MAX_CAR_YEAR));
                    valid = false;
                }
            } catch (Exception e) {
                Log.w(TAG, e.getMessage(), e);
            }
        }
        if (etPlates.length() == 0) {
            tiPlates.setError(getString(R.string.error_no_plates));
            valid = false;
        } else if (!Constants.LICENSE_PLATES_REGEX.matcher(etPlates.getText().toString()).matches()) {
            tiPlates.setError(getString(R.string.error_invalid_plates));
            valid = false;
        }
        return valid;
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }
}
