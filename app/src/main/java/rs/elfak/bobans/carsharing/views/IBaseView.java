package rs.elfak.bobans.carsharing.views;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public interface IBaseView<M> extends MvpLceView<M> {
    void showNoInternetConnection();
}
