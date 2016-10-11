package rs.elfak.bobans.carsharing.ui.activities;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceActivity;

import rs.elfak.bobans.carsharing.R;
import rs.elfak.bobans.carsharing.interactors.AbstractInteractor;
import rs.elfak.bobans.carsharing.presenters.AbstractPresenter;
import rs.elfak.bobans.carsharing.ui.dialogs.ProgressDialog;
import rs.elfak.bobans.carsharing.views.IBaseView;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public abstract class BaseActivity<M, I extends AbstractInteractor, V extends IBaseView<M>, P extends AbstractPresenter<V, I>>
        extends MvpLceActivity<FrameLayout, M, V, P>
        implements IBaseView<M> {

    private ProgressDialog progressDialog;
    private int progressCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);

        errorView.setVisibility(View.GONE);
        contentView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressCount = 0;
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        // TODO show error popup
    }

    @Override
    public void showLoading(boolean pullToRefresh) {
        progressCount++;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!progressDialog.isShowing()) {
                    progressDialog.show();
                }
            }
        });
    }

    @Override
    public void showContent() {
        progressCount--;
        if (progressCount < 0) {
            progressCount = 0;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressDialog.isShowing() && progressCount == 0) {
                    progressDialog.dismiss();
                }
            }
        });
    }

    @Override
    public final void setContentView(@LayoutRes int layoutResID) {
        if (contentView == null) {
            throw new RuntimeException("Can't set content view before calling on create");
        }
        View view = LayoutInflater.from(getApplicationContext()).inflate(layoutResID, contentView, false);
        contentView.removeAllViews();
        contentView.addView(view);
    }

    @Override
    public final void setContentView(View view) {
        if (contentView == null) {
            throw new RuntimeException("Can't set content view before calling on create");
        }
        contentView.removeAllViews();
        contentView.addView(view);
    }

    @Override
    public final void setContentView(View view, ViewGroup.LayoutParams params) {
        if (contentView == null) {
            throw new RuntimeException("Can't set content view before calling on create");
        }
        contentView.removeAllViews();
        contentView.addView(view);
    }

}
