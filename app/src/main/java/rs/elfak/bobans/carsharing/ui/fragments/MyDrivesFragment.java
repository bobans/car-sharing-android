package rs.elfak.bobans.carsharing.ui.fragments;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rs.elfak.bobans.carsharing.R;
import rs.elfak.bobans.carsharing.interactors.MyDrivesInteractor;
import rs.elfak.bobans.carsharing.models.SharedDrive;
import rs.elfak.bobans.carsharing.models.User;
import rs.elfak.bobans.carsharing.presenters.MyDrivesPresenter;
import rs.elfak.bobans.carsharing.ui.adapters.SharedDrivesAdapter;
import rs.elfak.bobans.carsharing.utils.EndlessRecyclerOnScrollListener;
import rs.elfak.bobans.carsharing.utils.SessionManager;
import rs.elfak.bobans.carsharing.views.IMyDrivesView;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */
public class MyDrivesFragment extends BaseFragment<List<SharedDrive>, MyDrivesInteractor, IMyDrivesView, MyDrivesPresenter> implements IMyDrivesView, SwipeRefreshLayout.OnRefreshListener, SharedDrivesAdapter.OnSharedDriveClickListener, View.OnClickListener {

    @BindView(R.id.swipe_refresh_shared_drives) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view_shared_drives) RecyclerView recyclerView;
    @BindView(R.id.no_drives_container) ViewGroup noDrivesContainer;
    @BindView(R.id.text_view_no_shared_drives) TextView tvNoDrives;
    @BindView(R.id.button_create_drive) Button btnCreateDrive;
    @BindView(R.id.floating_action_button) FloatingActionButton floatingActionButton;

    public static MyDrivesFragment newInstance() {
        return new MyDrivesFragment();
    }

    private Unbinder unbinder;

    @Override
    protected int getTitleResId() {
        return R.string.title_fragment_my_drives;
    }

    @NonNull
    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_my_drives, container, false);
    }

    @Override
    protected void onViewCreated(View view) {
        unbinder = ButterKnife.bind(this, view);

        initView();

        loadData(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unbinder.unbind();
    }

    @NonNull
    @Override
    public MyDrivesPresenter createPresenter() {
        return new MyDrivesPresenter();
    }

    @Override
    public void setData(List<SharedDrive> data) {
        ((SharedDrivesAdapter) recyclerView.getAdapter()).addItems(data);
        if (recyclerView.getAdapter().getItemCount() == 0) {
            noDrivesContainer.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            floatingActionButton.hide();
        } else {
            noDrivesContainer.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            floatingActionButton.show();
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        getPresenter().loadMyDrives(pullToRefresh);
    }

    private void initView() {
        setFonts();
        initRecyclerView();
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);
        if (SessionManager.getInstance().getUser() != null && SessionManager.getInstance().getUser().getUserType() != User.TYPE_DRIVER) {
            btnCreateDrive.setVisibility(View.GONE);
        }
        btnCreateDrive.setOnClickListener(this);
        floatingActionButton.setOnClickListener(this);
    }

    private void setFonts() {
        tvNoDrives.setTypeface(fontRegular);
        btnCreateDrive.setTypeface(fontMedium);
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        SharedDrivesAdapter adapter = new SharedDrivesAdapter(getContext());
        adapter.setOnSharedDriveClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                getPresenter().loadMyDrives(current_page);
            }
        });
    }

    @Override
    public void showLoading(boolean pullToRefresh) {
        if (!pullToRefresh) {
            super.showLoading(false);
        } else {
            swipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void showContent() {
        super.showContent();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        ((SharedDrivesAdapter) recyclerView.getAdapter()).clear();
        loadData(true);
    }

    @Override
    public void onSharedDriveClick(SharedDrivesAdapter adapter, int position, SharedDrive sharedDrive) {
        getPresenter().onSharedDriveClick(sharedDrive);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_create_drive: {
                getPresenter().onCreateDriveClick();
                break;
            }

            case R.id.floating_action_button: {
                getPresenter().onCreateDriveClick();
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1: {
                ((SharedDrivesAdapter) recyclerView.getAdapter()).clear();
                loadData(true);
                break;
            }

            default: {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

}
