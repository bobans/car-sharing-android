package rs.elfak.bobans.carsharing.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;

import rs.elfak.bobans.carsharing.R;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public abstract class BaseDialog extends Dialog {

    protected Typeface fontRegular;
    protected Typeface fontMedium;
    protected Typeface fontBold;

    public BaseDialog(Context context) {
        super(context, R.style.AppTheme_Dialog);

        fontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
        fontMedium = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");
        fontBold = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Bold.ttf");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

}
