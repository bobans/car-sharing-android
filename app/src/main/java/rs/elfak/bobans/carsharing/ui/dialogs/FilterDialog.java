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
import rs.elfak.bobans.carsharing.models.FilterDAO;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
public class FilterDialog extends BaseDialog implements View.OnClickListener {

    @BindView(R.id.text_view_title) TextView tvTitle;
    @BindView(R.id.text_input_departure) TextInputLayout tiDeparture;
    @BindView(R.id.edit_text_departure) EditText etDeparture;
    @BindView(R.id.text_input_destination) TextInputLayout tiDestination;
    @BindView(R.id.edit_text_destination) EditText etDestination;
    @BindView(R.id.button_confirm) Button btnConfirm;
    @BindView(R.id.button_clear) Button btnClear;

    private String start;
    private String stop;

    private OnFilterChangedListener listener;

    public FilterDialog(@NonNull Context context, String start, String stop, @Nullable OnFilterChangedListener listener) {
        super(context);

        this.start = start;
        this.stop = stop;

        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_filter);

        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        tvTitle.setTypeface(fontBold);
        tiDeparture.setTypeface(fontRegular);
        etDeparture.setTypeface(fontMedium);
        tiDestination.setTypeface(fontRegular);
        etDestination.setTypeface(fontMedium);
        btnConfirm.setTypeface(fontMedium);
        btnClear.setTypeface(fontMedium);

        etDeparture.setText(start);
        etDestination.setText(stop);

        btnConfirm.setOnClickListener(this);
        btnClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_confirm: {
                if (checkInput()) {
                    dismiss();
                    FilterDAO filter = new FilterDAO(etDeparture.getText().toString().trim(), etDestination.getText().toString().trim());
                    if (listener != null) {
                        listener.onFilterChanged(filter);
                    }
                }
                break;
            }
            case R.id.button_clear: {
                dismiss();
                if (listener != null) {
                    listener.onFilterChanged(null);
                }
                break;
            }
        }
    }

    private boolean checkInput() {
        if (etDeparture.length() == 0) {
            Toast.makeText(getContext(), R.string.error_shared_drive_departure, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etDestination.length() == 0) {
            Toast.makeText(getContext(), R.string.error_shared_drive_destination, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public interface OnFilterChangedListener {
        void onFilterChanged(@Nullable FilterDAO filter);
    }

}
