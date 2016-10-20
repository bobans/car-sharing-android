package rs.elfak.bobans.carsharing.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import rs.elfak.bobans.carsharing.R;
import rs.elfak.bobans.carsharing.interactors.LoginEmailInteractor;
import rs.elfak.bobans.carsharing.presenters.LoginEmailPresenter;
import rs.elfak.bobans.carsharing.utils.ClearErrorTextWatcher;
import rs.elfak.bobans.carsharing.utils.SessionManager;
import rs.elfak.bobans.carsharing.views.ILoginEmailView;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class LoginEmailActivity extends BaseActivity<Object, LoginEmailInteractor, ILoginEmailView, LoginEmailPresenter> implements ILoginEmailView, View.OnClickListener {

    @BindView(R.id.text_input_username) TextInputLayout tiUsername;
    @BindView(R.id.edit_text_username) EditText etUsername;
    @BindView(R.id.text_input_password) TextInputLayout tiPassword;
    @BindView(R.id.edit_text_password) EditText etPassword;
    @BindView(R.id.button_login) Button btnLogin;
    @BindView(R.id.text_view_sign_up) TextView tvSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);

        ButterKnife.bind(this);

        SessionManager.getInstance().setToken(null);

        initView();
    }

    private void initView() {
        etUsername.setTypeface(fontRegular);
        etPassword.setTypeface(fontRegular);
        btnLogin.setTypeface(fontMedium);
        tvSignUp.setTypeface(fontRegular);

        etUsername.addTextChangedListener(new ClearErrorTextWatcher(tiUsername));
        etPassword.addTextChangedListener(new ClearErrorTextWatcher(tiPassword));
        btnLogin.setOnClickListener(this);

        String text = getString(R.string.label_sign_up);
        SpannableString signUpLabel = new SpannableString(text);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(LoginEmailActivity.this, SignUpActivity.class));
            }
        };
        String link = getString(R.string.link_sign_up);
        int start = text.indexOf(link);
        int end = start + link.length();
        signUpLabel.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvSignUp.setText(signUpLabel);
        tvSignUp.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @NonNull
    @Override
    public LoginEmailPresenter createPresenter() {
        return new LoginEmailPresenter();
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
            case R.id.button_login: {
                if (validate()) {
                    getPresenter().login(etUsername.getText().toString(), etPassword.getText().toString());
                }
                break;
            }
        }
    }

    private boolean validate() {
        boolean valid = true;
        if (etUsername.length() == 0) {
            tiUsername.setError(getString(R.string.error_no_username));
            valid = false;
        }
        if (etPassword.length() == 0) {
            tiPassword.setError(getString(R.string.error_no_password));
            valid = false;
        }
        return valid;
    }

    @Override
    public void showWrongCredentials() {
        // TODO show error dialog
        Toast.makeText(this, "Wrong credentials", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMain() {
        // TODO show main
        showContent();
    }

}
