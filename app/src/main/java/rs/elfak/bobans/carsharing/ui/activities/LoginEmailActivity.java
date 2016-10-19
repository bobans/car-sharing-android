package rs.elfak.bobans.carsharing.ui.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import rs.elfak.bobans.carsharing.views.ILoginEmailView;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class LoginEmailActivity extends BaseActivity<Object, LoginEmailInteractor, ILoginEmailView, LoginEmailPresenter> implements View.OnClickListener {

    @BindView(R.id.edit_text_username) EditText etUsername;
    @BindView(R.id.edit_text_password) EditText etPassword;
    @BindView(R.id.button_login) Button btnLogin;
    @BindView(R.id.text_view_sign_up) TextView tvSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        btnLogin.setOnClickListener(this);

        String text = getString(R.string.label_sign_up);
        SpannableString signUpLabel = new SpannableString(text);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // TODO sign up activity
                Toast.makeText(LoginEmailActivity.this, "Sign Up", Toast.LENGTH_SHORT).show();
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
                // TODO validate and login
                break;
            }
        }
    }
}
