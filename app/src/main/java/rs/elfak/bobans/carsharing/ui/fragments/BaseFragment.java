package rs.elfak.bobans.carsharing.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceFragment;

import rs.elfak.bobans.carsharing.R;
import rs.elfak.bobans.carsharing.interactors.AbstractInteractor;
import rs.elfak.bobans.carsharing.presenters.AbstractPresenter;
import rs.elfak.bobans.carsharing.ui.activities.BaseActivity;
import rs.elfak.bobans.carsharing.views.IBaseView;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public abstract class BaseFragment<M, I extends AbstractInteractor, V extends IBaseView<M>, P extends AbstractPresenter<V, I>>
        extends MvpLceFragment<FrameLayout, M, V, P>
        implements IBaseView<M> {

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    @Override
    public final void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View content = onCreateView(getLayoutInflater(savedInstanceState), contentView);
        contentView.removeAllViews();
        contentView.addView(content);
        onViewCreated(contentView);

        errorView.setVisibility(View.GONE);
        contentView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
    }

    @NonNull
    protected abstract View onCreateView(LayoutInflater inflater, ViewGroup container);

    protected abstract void onViewCreated(View view);

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showError(e, pullToRefresh);
        }
    }

    @Override
    public void showLoading(boolean pullToRefresh) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showLoading(pullToRefresh);
        }
    }

    @Override
    public void showContent() {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showContent();
        }
    }

}
