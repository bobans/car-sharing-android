package rs.elfak.bobans.carsharing.ui.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

public class GenericErrorDialog extends OneButtonDialog implements View.OnClickListener {

    @BindView(R.id.text_view_message) TextView tvMessage;
    @BindView(R.id.button_confirm) Button btnConfirm;

    private String message;

    public GenericErrorDialog(Context context, @NonNull String message, @Nullable OnButtonClickListener onButtonClickListener) {
        super(context, onButtonClickListener);

        this.message = message;
    }

    public GenericErrorDialog(Context context, @StringRes int message, @Nullable OnButtonClickListener onButtonClickListener) {
        super(context, onButtonClickListener);

        this.message = context.getString(message);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_generic_error);

        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        tvMessage.setTypeface(fontMedium);
        btnConfirm.setTypeface(fontMedium);

        tvMessage.setText(message);

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
