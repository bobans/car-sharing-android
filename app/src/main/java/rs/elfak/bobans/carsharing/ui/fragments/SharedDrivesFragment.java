package rs.elfak.bobans.carsharing.ui.fragments;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rs.elfak.bobans.carsharing.R;
import rs.elfak.bobans.carsharing.interactors.SharedDrivesInteractor;
import rs.elfak.bobans.carsharing.models.SharedDrive;
import rs.elfak.bobans.carsharing.models.User;
import rs.elfak.bobans.carsharing.presenters.SharedDrivesPresenter;
import rs.elfak.bobans.carsharing.ui.adapters.SharedDrivesAdapter;
import rs.elfak.bobans.carsharing.utils.EndlessRecyclerOnScrollListener;
import rs.elfak.bobans.carsharing.utils.SessionManager;
import rs.elfak.bobans.carsharing.views.ISharedDrivesView;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class SharedDrivesFragment extends BaseFragment<List<SharedDrive>, SharedDrivesInteractor, ISharedDrivesView, SharedDrivesPresenter> implements ISharedDrivesView, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    @BindView(R.id.swipe_refresh_shared_drives) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.filter_container) ViewGroup filterContainer;
    @BindView(R.id.image_view_filter) ImageView ivFilter;
    @BindView(R.id.recycler_view_shared_drives) RecyclerView recyclerView;
    @BindView(R.id.no_drives_container) ViewGroup noDrivesContainer;
    @BindView(R.id.text_view_no_shared_drives) TextView tvNoDrives;
    @BindView(R.id.button_create_drive) Button btnCreateDrive;

    public static SharedDrivesFragment newInstance() {
        return new SharedDrivesFragment();
    }

    private Unbinder unbinder;

    @NonNull
    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_shared_drives, container, false);
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
    public SharedDrivesPresenter createPresenter() {
        return new SharedDrivesPresenter();
    }

    @Override
    public void setData(List<SharedDrive> data) {
        ((SharedDrivesAdapter) recyclerView.getAdapter()).addItems(data);
        if (recyclerView.getAdapter().getItemCount() == 0) {
            noDrivesContainer.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            filterContainer.setVisibility(View.GONE);
        } else {
            noDrivesContainer.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            filterContainer.setVisibility(View.VISIBLE);
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        getPresenter().loadSharedDrives(pullToRefresh);
    }

    private void initView() {
        setFonts();
        initRecyclerView();
        ivFilter.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        if (SessionManager.getInstance().getUser() != null && SessionManager.getInstance().getUser().getUserType() != User.TYPE_DRIVER) {
            btnCreateDrive.setVisibility(View.GONE);
        }
    }

    private void setFonts() {
        tvNoDrives.setTypeface(fontRegular);
        btnCreateDrive.setTypeface(fontMedium);
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new SharedDrivesAdapter(getContext()));
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                getPresenter().loadSharedDrives(current_page);
            }
        });
    }

    @Override
    public void showLoading(boolean pullToRefresh) {
        if (!pullToRefresh) {
            super.showLoading(pullToRefresh);
        } else {
            swipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void onRefresh() {
        ((SharedDrivesAdapter) recyclerView.getAdapter()).clear();
        getPresenter().loadSharedDrives(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_view_filter: {
                // TODO filter click
                Toast.makeText(getContext(), "Filter", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
}
