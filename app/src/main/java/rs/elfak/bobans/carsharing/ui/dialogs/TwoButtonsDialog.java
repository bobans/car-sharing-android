package rs.elfak.bobans.carsharing.ui.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rs.elfak.bobans.carsharing.R;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class TwoButtonsDialog extends BaseDialog implements View.OnClickListener {

    @BindView(R.id.image_view_icon) ImageView ivIcon;
    @BindView(R.id.text_view_message) TextView tvMessage;
    @BindView(R.id.button_positive) Button btnPositive;
    @BindView(R.id.button_negative) Button btnNegative;

    private @DrawableRes int icon;
    private String message;
    private String positiveButtonText;
    private String negativeButtonText;
    private OnClickListener onButtonClickListener;

    public TwoButtonsDialog(Context context, @DrawableRes int icon, @StringRes int message, @StringRes int positiveButtonText, @StringRes int negativeButtonText, @Nullable OnClickListener onButtonClickListener) {
        super(context);

        this.icon = icon;
        this.message = context.getString(message);
        this.positiveButtonText = context.getString(positiveButtonText);
        this.negativeButtonText = context.getString(negativeButtonText);
        this.onButtonClickListener = onButtonClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_two_buttons);

        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        tvMessage.setTypeface(fontMedium);
        btnPositive.setTypeface(fontMedium);
        btnNegative.setTypeface(fontMedium);

        ivIcon.setImageResource(icon);
        tvMessage.setText(message);
        if (positiveButtonText != null) {
            btnPositive.setText(positiveButtonText);
        }
        if (negativeButtonText != null) {
            btnNegative.setText(negativeButtonText);
        }

        btnPositive.setOnClickListener(this);
        btnNegative.setOnClickListener(this);
    }

    protected void notifyPositiveButtonClicked(DialogInterface dialogInterface, View view) {
        if (onButtonClickListener != null) {
            onButtonClickListener.onPositiveButtonClick(dialogInterface, view);
        }
    }

    protected void notifyNegativeButtonClicked(DialogInterface dialogInterface, View view) {
        if (onButtonClickListener != null) {
            onButtonClickListener.onNegativeButtonClick(dialogInterface, view);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_positive: {
                dismiss();
                notifyPositiveButtonClicked(this, v);
                break;
            }

            case R.id.button_negative: {
                dismiss();
                notifyNegativeButtonClicked(this, v);
                break;
            }
        }
    }

    public interface OnClickListener {
        void onPositiveButtonClick(DialogInterface dialogInterface, View view);
        void onNegativeButtonClick(DialogInterface dialogInterface, View view);
    }

}
