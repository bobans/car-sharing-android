package rs.elfak.bobans.carsharing.ui.activities;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceActivity;

import rs.elfak.bobans.carsharing.R;
import rs.elfak.bobans.carsharing.interactors.BaseInteractor;
import rs.elfak.bobans.carsharing.presenters.BasePresenter;
import rs.elfak.bobans.carsharing.views.IBaseView;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public abstract class BaseActivity<M, I extends BaseInteractor, V extends IBaseView<M>, P extends BasePresenter<V, I>>
        extends MvpLceActivity<FrameLayout, M, V, P>
        implements IBaseView<M> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        errorView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
        contentView.setVisibility(View.GONE);
    }

    @Override
    public void showLoading(boolean pullToRefresh) {
        errorView.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
        contentView.setVisibility(View.GONE);
    }

    @Override
    public void showContent() {
        errorView.setVisibility(View.GONE);
        contentView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
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
