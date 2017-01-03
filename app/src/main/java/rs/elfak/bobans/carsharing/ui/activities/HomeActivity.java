package rs.elfak.bobans.carsharing.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
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

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.content_description_open_drawer, R.string.content_description_close_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        initView();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_home, SharedDrivesFragment.newInstance())
                    .commit();
        }
    }

    private void initView() {
        populateHeader(navigationView.getHeaderView(0));
        navigationView.setNavigationItemSelectedListener(this);

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
        if (drawerLayout.isDrawerOpen(navigationView)) {
            drawerLayout.closeDrawers();
        }
        switch (item.getItemId()) {
            case R.id.action_drives: {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_home, SharedDrivesFragment.newInstance())
                        .commit();
                return true;
            }

            default: {
                return false;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_view_logout: {
                SessionManager.getInstance().setToken(null);
                Intent intent = new Intent(this, LoginEmailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            }
        }
    }
}
