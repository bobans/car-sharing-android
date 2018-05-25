package rs.elfak.bobans.carsharing.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceFragment;

import rs.elfak.bobans.carsharing.R;
import rs.elfak.bobans.carsharing.interactors.BaseInteractor;
import rs.elfak.bobans.carsharing.presenters.BasePresenter;
import rs.elfak.bobans.carsharing.ui.activities.BaseActivity;
import rs.elfak.bobans.carsharing.views.IBaseView;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public abstract class BaseFragment<M, I extends BaseInteractor, V extends IBaseView<M>, P extends BasePresenter<V, I>>
        extends MvpLceFragment<FrameLayout, M, V, P>
        implements IBaseView<M> {

    protected Typeface fontRegular;
    protected Typeface fontMedium;
    protected Typeface fontBold;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fontRegular = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf");
        fontMedium = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Medium.ttf");
        fontBold = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Bold.ttf");
    }

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    @Override
    public final void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View content = onCreateView(LayoutInflater.from(getContext()), contentView);
        contentView.removeAllViews();
        contentView.addView(content);
        onViewCreated(contentView);

        errorView.setVisibility(View.GONE);
        contentView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        getPresenter().onDestroy();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        activity.setTitle(getTitleResId());
    }

    protected abstract @StringRes int getTitleResId();

    @NonNull
    protected abstract View onCreateView(LayoutInflater inflater, ViewGroup container);

    protected abstract void onViewCreated(View view);

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showError(e, pullToRefresh);
        }
    }

    @Override
    public void showLoading(boolean pullToRefresh) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showLoading(pullToRefresh);
        }
    }

    @Override
    public void showContent() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showContent();
        }
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }

    @Override
    public void showNoInternetConnection() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showNoInternetConnection();
        }
    }

    @Override
    public void navigateToActivity(Class activityClass, Bundle extras) {
        Intent intent = new Intent(getContext(), activityClass);
        if (extras != null) {
            intent.putExtras(extras);
        }
        startActivity(intent);
    }

    @Override
    public void navigateToActivityClearStack(Class activityClass, Bundle extras) {
        Intent intent = new Intent(getContext(), activityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        if (extras != null) {
            intent.putExtras(extras);
        }
        startActivity(intent);
    }

    @Override
    public void navigateToActivityForResult(int requestCode, Class activityClass, Bundle extras) {
        Intent intent = new Intent(getContext(), activityClass);
        if (extras != null) {
            intent.putExtras(extras);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void finishActivity() {
        if (getActivity() instanceof IBaseView) {
            ((IBaseView) getActivity()).finishActivity();
        }
    }
}
