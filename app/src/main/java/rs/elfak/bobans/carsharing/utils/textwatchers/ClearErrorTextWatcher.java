package rs.elfak.bobans.carsharing.utils.textwatchers;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class ClearErrorTextWatcher implements TextWatcher {

    private TextInputLayout textInputLayout;
    private EditText editText;

    public ClearErrorTextWatcher(EditText editText) {
        this.textInputLayout = null;
        this.editText = editText;
    }

    public ClearErrorTextWatcher(TextInputLayout textInputLayout) {
        this.textInputLayout = textInputLayout;
        this.editText = null;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (textInputLayout != null) {
            textInputLayout.setError(null);
            textInputLayout.requestLayout();
        } else if (editText != null){
            editText.setError(null);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
