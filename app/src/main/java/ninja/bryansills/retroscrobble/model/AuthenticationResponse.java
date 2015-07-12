package ninja.bryansills.retroscrobble.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bryan on 7/11/15.
 */
public class AuthenticationResponse {

    @SerializedName("error")
    private int error;

    @SerializedName("session")
    private Session session;

    public Session getSession() {
        return session;
    }

    public int getError() {
        return error;
    }

}
