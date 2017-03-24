package rs.elfak.bobans.carsharing.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import rs.elfak.bobans.carsharing.R;
import rs.elfak.bobans.carsharing.interactors.HomeInteractor;
import rs.elfak.bobans.carsharing.models.User;
import rs.elfak.bobans.carsharing.presenters.HomePresenter;
import rs.elfak.bobans.carsharing.ui.fragments.SharedDrivesFragment;
import rs.elfak.bobans.carsharing.utils.PictureUtils;
import rs.elfak.bobans.carsharing.utils.SessionManager;
import rs.elfak.bobans.carsharing.views.IHomeView;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class HomeActivity extends BaseActivity<Object, HomeInteractor, IHomeView, HomePresenter> implements IHomeView, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    public static final String EXTRA_SHOW_DRIVE = "EXTRA_SHOW_DRIVE";

    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @BindView(R.id.navigation_view) NavigationView navigationView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.content_home) FrameLayout homeContent;
    @BindView(R.id.image_view_logout) ImageView ivLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        initView();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_home, SharedDrivesFragment.newInstance())
                    .commit();
        }

        if (getIntent() != null && getIntent().hasExtra(EXTRA_SHOW_DRIVE)) {
            long driveId = getIntent().getLongExtra(EXTRA_SHOW_DRIVE, -1);
            getPresenter().loadDrive(driveId);
        }
    }

    private void initView() {
        populateHeader(navigationView.getHeaderView(0));
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getHeaderView(0).findViewById(R.id.navigation_view_header).setOnClickListener(this);

        ivLogout.setOnClickListener(this);
    }

    private void populateHeader(View headerView) {
        ImageView photo = (ImageView) headerView.findViewById(R.id.image_view_photo);
        TextView name = (TextView) headerView.findViewById(R.id.text_view_name);
        TextView email = (TextView) headerView.findViewById(R.id.text_view_email);

        name.setTypeface(fontMedium);
        email.setTypeface(fontRegular);

        User user = SessionManager.getInstance().getUser();
        if (user != null) {
            PictureUtils.loadImage(user.getPhotoUrl(), new CropCircleTransformation(this), R.drawable.ic_user_placeholder, photo);
            name.setText(user.getName());
            email.setText(user.getEmail());
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
            return;
        }
        super.onBackPressed();
    }

    @NonNull
    @Override
    public HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // TODO navigation item selected
        switch (item.getItemId()) {
            case R.id.action_drives: {
                replaceFragment(SharedDrivesFragment.newInstance());
                return true;
            }

            default: {
                return false;
            }
        }
    }

    private void replaceFragment(final Fragment fragment) {
        if (drawerLayout.isDrawerOpen(navigationView)) {
            drawerLayout.addDrawerListener(new CloseDrawerListener() {
                @Override
                public void onDrawerClosed(View drawerView) {
                    drawerLayout.removeDrawerListener(this);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_home, fragment)
                            .commit();
                }
            });
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_home);
            if (currentFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .remove(currentFragment)
                        .commit();
            }
            drawerLayout.closeDrawers();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_home, fragment)
                    .commit();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_view_logout: {
                getPresenter().unregisterFCM();
                SessionManager.getInstance().setToken(null);
                Intent intent = new Intent(this, LoginEmailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            }

            case R.id.navigation_view_header: {
                navigateToActivity(ProfileActivity.class, null);
                break;
            }
        }
    }

    private static abstract class CloseDrawerListener implements DrawerLayout.DrawerListener {
        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {}
        @Override
        public void onDrawerOpened(View drawerView) {}
        @Override
        public void onDrawerStateChanged(int newState) {}
    }

}
