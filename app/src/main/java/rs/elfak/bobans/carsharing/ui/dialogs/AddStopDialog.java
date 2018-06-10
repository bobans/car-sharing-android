package rs.elfak.bobans.carsharing.ui.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import rs.elfak.bobans.carsharing.R;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
public class AddStopDialog extends BaseDialog implements View.OnClickListener {

    @BindView(R.id.text_view_title) TextView tvTitle;
    @BindView(R.id.text_input_city) TextInputLayout tiCity;
    @BindView(R.id.edit_text_city) EditText etCity;
    @BindView(R.id.button_confirm) Button btnConfirm;

    private OnCityEnteredListener listener;

    public AddStopDialog(Context context, @Nullable OnCityEnteredListener listener) {
        super(context);

        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_stop);

        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        tvTitle.setTypeface(fontBold);
        tiCity.setTypeface(fontRegular);
        etCity.setTypeface(fontMedium);
        btnConfirm.setTypeface(fontMedium);

        btnConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_confirm: {
                if (checkInput()) {
                    dismiss();
                    if (listener != null) {
                        listener.onCityEntered(etCity.getText().toString());
                    }
                }
                break;
            }
        }
    }

    private boolean checkInput() {
        if (etCity.length() == 0) {
            Toast.makeText(getContext(), R.string.error_no_city, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public interface OnCityEnteredListener {
        void onCityEntered(@NonNull String city);
    }

}
