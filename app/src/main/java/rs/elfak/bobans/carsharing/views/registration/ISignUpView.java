package rs.elfak.bobans.carsharing.views.registration;

import rs.elfak.bobans.carsharing.views.IBaseView;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public interface ISignUpView extends IBaseView<Object> {
    void showCreateUser();
    void showAlreadyExists();
}
