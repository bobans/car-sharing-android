package rs.elfak.bobans.carsharing.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;

import rs.elfak.bobans.carsharing.R;
import rs.elfak.bobans.carsharing.interactors.ProfileInteractor;
import rs.elfak.bobans.carsharing.models.User;
import rs.elfak.bobans.carsharing.presenters.ProfilePresenter;
import rs.elfak.bobans.carsharing.views.IProfileView;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */
public class ProfileActivity extends BaseActivity<User, ProfileInteractor, IProfileView, ProfilePresenter> implements IProfileView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    @NonNull
    @Override
    public ProfilePresenter createPresenter() {
        return new ProfilePresenter();
    }

    @Override
    public void setData(User data) {

    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }

}
