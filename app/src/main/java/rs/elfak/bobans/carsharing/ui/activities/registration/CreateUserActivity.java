package rs.elfak.bobans.carsharing.ui.activities.registration;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rs.elfak.bobans.carsharing.R;
import rs.elfak.bobans.carsharing.interactors.registration.CreateUserInteractor;
import rs.elfak.bobans.carsharing.presenters.registration.CreateUserPresenter;
import rs.elfak.bobans.carsharing.ui.activities.BaseActivity;
import rs.elfak.bobans.carsharing.utils.ClearErrorTextWatcher;
import rs.elfak.bobans.carsharing.views.registration.ICreateUserView;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class CreateUserActivity extends BaseActivity<Object, CreateUserInteractor, ICreateUserView, CreateUserPresenter> implements ICreateUserView, View.OnClickListener {

    @BindView(R.id.text_input_name) TextInputLayout tiName;
    @BindView(R.id.edit_text_name) EditText etName;
    @BindView(R.id.text_input_email) TextInputLayout tiEmail;
    @BindView(R.id.edit_text_email) EditText etEmail;
    @BindView(R.id.text_view_city) TextView tvCity;
    @BindView(R.id.spinner_city) Spinner spCity;
    @BindView(R.id.text_view_user_type) TextView tvUserType;
    @BindView(R.id.spinner_user_type) Spinner spUserType;
    @BindView(R.id.button_create_user) Button btnCreateUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        tiName.setTypeface(fontRegular);
        etName.setTypeface(fontRegular);
        tiEmail.setTypeface(fontRegular);
        etEmail.setTypeface(fontRegular);
        tvCity.setTypeface(fontRegular);
        tvUserType.setTypeface(fontRegular);

        etName.addTextChangedListener(new ClearErrorTextWatcher(tiName));
        etEmail.addTextChangedListener(new ClearErrorTextWatcher(tiEmail));

        btnCreateUser.setOnClickListener(this);
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
                // TODO create user
                break;
            }
        }
    }
}
