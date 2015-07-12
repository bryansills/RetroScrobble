package ninja.bryansills.retroscrobble;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import ninja.bryansills.retroscrobble.model.Session;

/**
 * Created by bryan on 7/11/15.
 */
public class PreferenceManager {

    private static final String PREFS_NAME = "prefs";
    private static final String USER = "user";

    public static Session getSession(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String userJson = preferences.getString(USER, "");

        if (!userJson.isEmpty()) {
            Gson gson = new Gson();
            return gson.fromJson(userJson, Session.class);
        } else {
            return null;
        }
    }

    public static void putSession(Context context, Session session) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();

        editor.putString(USER, gson.toJson(session));

        editor.commit();
    }
}
