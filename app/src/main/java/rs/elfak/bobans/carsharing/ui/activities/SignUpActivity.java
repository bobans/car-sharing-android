package rs.elfak.bobans.carsharing.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import rs.elfak.bobans.carsharing.R;
import rs.elfak.bobans.carsharing.interactors.SignUpInteractor;
import rs.elfak.bobans.carsharing.presenters.SignUpPresenter;
import rs.elfak.bobans.carsharing.utils.ClearErrorTextWatcher;
import rs.elfak.bobans.carsharing.utils.Constants;
import rs.elfak.bobans.carsharing.views.ISignUpView;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class SignUpActivity extends BaseActivity<Object, SignUpInteractor, ISignUpView, SignUpPresenter> implements ISignUpView, View.OnClickListener {

    @BindView(R.id.text_input_username) TextInputLayout tiUsername;
    @BindView(R.id.edit_text_username) EditText etUsername;
    @BindView(R.id.text_input_password) TextInputLayout tiPassword;
    @BindView(R.id.edit_text_password) EditText etPassword;
    @BindView(R.id.button_sign_up) Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        etUsername.setTypeface(fontRegular);
        etPassword.setTypeface(fontRegular);
        btnSignUp.setTypeface(fontMedium);

        etUsername.addTextChangedListener(new ClearErrorTextWatcher(tiUsername));
        etPassword.addTextChangedListener(new ClearErrorTextWatcher(tiPassword));
        btnSignUp.setOnClickListener(this);
    }

    @NonNull
    @Override
    public SignUpPresenter createPresenter() {
        return new SignUpPresenter();
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
            case R.id.button_sign_up: {
                if (validate()) {
                    getPresenter().register(etUsername.getText().toString(), etPassword.getText().toString());
                }
                break;
            }
        }
    }

    private boolean validate() {
        boolean valid = true;
        if (etUsername.length() < Constants.USERNAME_MINIMUM_LENGTH) {
            tiUsername.setError(getString(R.string.error_minimum_length, Constants.USERNAME_MINIMUM_LENGTH));
            valid = false;
        }
        if (etUsername.getText().toString().contains(":")) {
            tiUsername.setError(getString(R.string.error_username_colon));
            valid = false;
        }
        if (etPassword.length() < Constants.PASSWORD_MINIMUM_LENGTH) {
            tiPassword.setError(getString(R.string.error_minimum_length, Constants.PASSWORD_MINIMUM_LENGTH));
            valid = false;
        }
        if (etPassword.getText().toString().contains(":")) {
            tiPassword.setError(getString(R.string.error_password_colon));
            valid = false;
        }
        return valid;
    }

    @Override
    public void showCreateUser() {
        // TODO show create user
        showContent();
    }

    @Override
    public void showAlreadyExists() {
        // TODO show already exists dialog
        Toast.makeText(this, "Already exists", Toast.LENGTH_SHORT).show();
    }
}
