package rs.elfak.bobans.carsharing.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import rs.elfak.bobans.carsharing.R;
import rs.elfak.bobans.carsharing.interactors.ViewSharedDriveInteractor;
import rs.elfak.bobans.carsharing.models.SharedDrive;
import rs.elfak.bobans.carsharing.presenters.ViewSharedDrivePresenter;
import rs.elfak.bobans.carsharing.views.IViewSharedDriveView;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */
public class ViewSharedDriveActivity extends BaseActivity<SharedDrive, ViewSharedDriveInteractor, IViewSharedDriveView, ViewSharedDrivePresenter> {

    public static final String EXTRA_SHARED_DRIVE = "EXTRA_SHARED_DRIVE";

    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_shared_drive);

        ButterKnife.bind(this);

        initView();

        loadData(false);
    }

    private void initView() {
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setFonts();
    }

    private void setFonts() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_view_shared_drive, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                setResult(RESULT_CANCELED);
                finish();
                return true;
            }

            case R.id.action_edit: {
                // TODO on edit click
                return true;
            }

            case R.id.action_delete: {
                // TODO on delete click
                return true;
            }

            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    @NonNull
    @Override
    public ViewSharedDrivePresenter createPresenter() {
        SharedDrive sharedDrive = null;
        if (getIntent() != null) {
            sharedDrive = getIntent().getParcelableExtra(EXTRA_SHARED_DRIVE);
        }
        return new ViewSharedDrivePresenter(sharedDrive);
    }

    @Override
    public void setData(SharedDrive data) {

    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }

}
