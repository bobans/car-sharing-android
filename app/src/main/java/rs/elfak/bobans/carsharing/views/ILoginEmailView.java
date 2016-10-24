package rs.elfak.bobans.carsharing.views;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public interface ILoginEmailView extends IBaseView<Object> {
    void showWrongCredentials();
    void showMain();
    void showCreateUser();
}
