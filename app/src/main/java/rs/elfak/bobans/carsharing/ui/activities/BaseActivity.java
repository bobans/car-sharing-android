package rs.elfak.bobans.carsharing.ui.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceActivity;

import retrofit2.adapter.rxjava.HttpException;
import rs.elfak.bobans.carsharing.R;
import rs.elfak.bobans.carsharing.api.ApiError;
import rs.elfak.bobans.carsharing.api.ApiManager;
import rs.elfak.bobans.carsharing.interactors.BaseInteractor;
import rs.elfak.bobans.carsharing.presenters.BasePresenter;
import rs.elfak.bobans.carsharing.ui.dialogs.GenericErrorDialog;
import rs.elfak.bobans.carsharing.ui.dialogs.ProgressDialog;
import rs.elfak.bobans.carsharing.views.IBaseView;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */
public abstract class BaseActivity<M, I extends BaseInteractor, V extends IBaseView<M>, P extends BasePresenter<V, I>>
        extends MvpLceActivity<FrameLayout, M, V, P>
        implements IBaseView<M> {

    private int onStartCount = 0;
    private ProgressDialog progressDialog;
    private int progressCount;

    protected Typeface fontRegular;
    protected Typeface fontMedium;
    protected Typeface fontBold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);

        onStartCount = 1;
        if (savedInstanceState == null) { // 1st time
            this.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
        } else { // already created so reverse animation
            onStartCount++;
        }

        fontRegular = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        fontMedium = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");
        fontBold = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");

        errorView.setVisibility(View.GONE);
        contentView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressCount = 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        getPresenter().onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (onStartCount > 1) {
            this.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
        } else if (onStartCount == 1) {
            onStartCount++;
        }
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        Log.e(getClass().getSimpleName(), e.getMessage(), e);
        if (e instanceof HttpException) {
            ApiError error = ApiManager.parseError(((HttpException) e).response());
            switch (error.getCode()) {
                case 401: { // unauthorized
                    startActivity(new Intent(this, LoginEmailActivity.class));
                    finish();
                    break;
                }

                default: {
                    GenericErrorDialog dialog = new GenericErrorDialog(this, R.string.server_error_generic, null);
                    dialog.setCancelable(false);
                    dialog.show();
                }
            }
        } else {
            GenericErrorDialog dialog = new GenericErrorDialog(this, R.string.server_error_generic, null);
            dialog.setCancelable(false);
            dialog.show();
        }
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
        View view = LayoutInflater.from(this).inflate(layoutResID, contentView, false);
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
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        // we don't use this
        return null;
    }

    @Override
    public final void setContentView(View view, ViewGroup.LayoutParams params) {
        if (contentView == null) {
            throw new RuntimeException("Can't set content view before calling on create");
        }
        contentView.removeAllViews();
        contentView.addView(view);
    }

    @Override
    public void showNoInternetConnection() {
        GenericErrorDialog dialog = new GenericErrorDialog(this, R.string.error_no_internet_connection, null);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void navigateToActivity(Class activityClass, Bundle extras) {
        Intent intent = new Intent(this, activityClass);
        if (extras != null) {
            intent.putExtras(extras);
        }
        startActivity(intent);
    }

    @Override
    public void navigateToActivityClearStack(Class activityClass, Bundle extras) {
        Intent intent = new Intent(this, activityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        if (extras != null) {
            intent.putExtras(extras);
        }
        startActivity(intent);
    }

    @Override
    public void navigateToActivityForResult(int requestCode, Class activityClass, Bundle extras) {
        Intent intent = new Intent(this, activityClass);
        if (extras != null) {
            intent.putExtras(extras);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void finishActivity() {
        finish();
    }

}
