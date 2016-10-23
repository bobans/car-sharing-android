package rs.elfak.bobans.carsharing.ui.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rs.elfak.bobans.carsharing.R;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class InfoDialog extends OneButtonDialog implements View.OnClickListener {

    @BindView(R.id.text_view_message) TextView tvMessage;
    @BindView(R.id.button_confirm) Button btnConfirm;

    private String message;
    private String buttonText;

    public InfoDialog(Context context, String message, @Nullable String buttonText, @Nullable OnButtonClickListener onButtonClickListener) {
        super(context, onButtonClickListener);

        this.message = message;
        this.buttonText = buttonText;
    }

    public InfoDialog(Context context, @StringRes int message, @StringRes int buttonText, @Nullable OnButtonClickListener onButtonClickListener) {
        super(context, onButtonClickListener);

        this.message = context.getString(message);
        this.buttonText = context.getString(buttonText);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_info);

        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        tvMessage.setTypeface(fontMedium);
        btnConfirm.setTypeface(fontMedium);

        tvMessage.setText(message);
        if (buttonText != null) {
            btnConfirm.setText(buttonText);
        }

        btnConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_confirm) {
            dismiss();
            notifyButtonClicked(this, v);
        }
    }
}
