package rs.elfak.bobans.carsharing.views;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
public interface IResetPasswordView extends IBaseView<Object> {
    void showPasswordReset(String email);
}
