package ninja.bryansills.retroscrobble.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bryan on 7/12/15.
 */
public class Session {

    @SerializedName("name")
    private String username;

    @SerializedName("key")
    private String sessionKey;

    public String getUsername() {
        return username;
    }

    public String getSessionKey() {
        return sessionKey;
    }
}
