package ninja.bryansills.retroscrobble;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by bryan on 6/18/15.
 */
public interface LastFmApi {

    @FormUrlEncoded
    @POST("/")
    void authenticate(@Field("method") String method,
                      @Field("format") String format,
                      @Field("api_key") String apiKey,
                      Callback<String> callback);
}
