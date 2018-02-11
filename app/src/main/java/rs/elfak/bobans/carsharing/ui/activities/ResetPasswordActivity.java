package rs.elfak.bobans.carsharing.ui.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import rs.elfak.bobans.carsharing.R;
import rs.elfak.bobans.carsharing.interactors.ResetPasswordInteractor;
import rs.elfak.bobans.carsharing.presenters.ResetPasswordPresenter;
import rs.elfak.bobans.carsharing.ui.dialogs.InfoDialog;
import rs.elfak.bobans.carsharing.ui.dialogs.OneButtonDialog;
import rs.elfak.bobans.carsharing.utils.Constants;
import rs.elfak.bobans.carsharing.utils.textwatchers.ClearErrorTextWatcher;
import rs.elfak.bobans.carsharing.views.IResetPasswordView;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
public class ResetPasswordActivity extends BaseActivity<Object, ResetPasswordInteractor, IResetPasswordView, ResetPasswordPresenter> implements IResetPasswordView, View.OnClickListener {

    @BindView(R.id.text_input_email) TextInputLayout tiEmail;
    @BindView(R.id.edit_text_email) EditText etEmail;
    @BindView(R.id.button_reset_password) Button btnResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        tiEmail.setTypeface(fontRegular);
        etEmail.setTypeface(fontRegular);
        btnResetPassword.setTypeface(fontMedium);

        etEmail.addTextChangedListener(new ClearErrorTextWatcher(tiEmail));
        btnResetPassword.setOnClickListener(this);
    }

    @NonNull
    @Override
    public ResetPasswordPresenter createPresenter() {
        return new ResetPasswordPresenter();
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_reset_password: {
                if (validate()) {
                    getPresenter().resetPassword(etEmail.getText().toString());
                }
                break;
            }
        }
    }

    private boolean validate() {
        boolean valid = true;
        if (etEmail.length() == 0) {
            tiEmail.setError(getString(R.string.error_no_email));
            valid = false;
        } else if (!Constants.VALID_EMAIL_ADDRESS_REGEX.matcher(etEmail.getText().toString()).matches()) {
            tiEmail.setError(getString(R.string.error_invalid_email));
            valid = false;
        }
        return valid;
    }

    @Override
    public void showPasswordReset(String email) {
        InfoDialog dialog = new InfoDialog(this, getString(R.string.info_password_reset_requested, email), getString(R.string.ok), new OneButtonDialog.OnButtonClickListener() {
            @Override
            public void onButtonClick(DialogInterface dialogInterface, View view) {
                finish();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

}
