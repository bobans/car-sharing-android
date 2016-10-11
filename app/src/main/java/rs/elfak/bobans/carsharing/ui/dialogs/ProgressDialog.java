package rs.elfak.bobans.carsharing.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import rs.elfak.bobans.carsharing.R;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class ProgressDialog extends Dialog {

    public ProgressDialog(Context context) {
        super(context, R.style.AppTheme_Dialog_Transparent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.dialog_progress);
    }

}
