package rs.elfak.bobans.carsharing.ui.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public abstract class OneButtonDialog extends BaseDialog {

    private OnButtonClickListener onButtonClickListener;

    public OneButtonDialog(Context context, @Nullable OnButtonClickListener onButtonClickListener) {
        super(context);

        this.onButtonClickListener = onButtonClickListener;
    }

    protected void notifyButtonClicked(DialogInterface dialogInterface, View view) {
        if (onButtonClickListener != null) {
            onButtonClickListener.onButtonClick(dialogInterface, view);
        }
    }

    public interface OnButtonClickListener {
        void onButtonClick(DialogInterface dialogInterface, View view);
    }

}
