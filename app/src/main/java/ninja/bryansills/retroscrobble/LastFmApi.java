package ninja.bryansills.retroscrobble;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by bryan on 6/18/15.
 */
public interface LastFmApi {

    @FormUrlEncoded
    @POST("/")
    void authenticate(@Field("format") String format,
                      @Field("method") String method,
                      @Field("username") String username,
                      @Field("password") String password,
                      @Field("api_key") String apiKey,
                      @Field("api_sig") String apiSig,
                      Callback<String> callback);
}
