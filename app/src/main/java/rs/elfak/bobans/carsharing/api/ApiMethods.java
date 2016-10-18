package rs.elfak.bobans.carsharing.api;

import retrofit2.http.Header;
import retrofit2.http.POST;
import rs.elfak.bobans.carsharing.models.Token;
import rx.Observable;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public interface ApiMethods {

    @POST(ApiConstants.LOGIN)
    Observable<Token> login(@Header("Authorization") String authorization);

}
