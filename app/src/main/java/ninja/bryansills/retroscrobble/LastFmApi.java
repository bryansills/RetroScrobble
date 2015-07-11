package ninja.bryansills.retroscrobble;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by bryan on 6/18/15.
 */
public interface LastFmApi {

    String USERNAME = "username";
    String PASSWORD = "password";
    String API_SIG = "api_sig";

    Map<String, String> AUTH_CONSTS = Util.buildMap(
            "format", "json",
            "method", "auth.getMobileSession",
            "api_key", BuildConfig.LAST_FM_API_KEY);

    @FormUrlEncoded
    @POST("/")
    void authenticate(@FieldMap Map constants,
                      @Field(USERNAME) String username,
                      @Field(PASSWORD) String password,
                      @Field(API_SIG) String apiSig,
                      Callback<String> callback);
}
