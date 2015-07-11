package ninja.bryansills.retroscrobble;

import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by bryan on 7/10/15.
 */
public class Util {

    private static final String TAG = Util.class.getName();

    public static String getMD5Sum(String message){
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hashedBytes = digest.digest(message.getBytes("UTF-8"));

            StringBuilder stringBuilder = new StringBuilder();
            for (byte arrayByte : hashedBytes) {
                stringBuilder.append(Integer.toString((arrayByte & 0xff) + 0x100, 16)
                        .substring(1));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Log.d(TAG, ex.getMessage());
            return "md5fail:(";
        }
    }

    public static String generateLastFmApiSig(Map<String, String> constants, String... strings) {
        if (strings.length % 2 != 0) {
            return ":(";
        }

        Map<String, String> params = constants;

        for (int ii = 0; ii < strings.length; ii+=2) {
            params.put(strings[ii], strings[ii + 1]);
        }

        String[] keys = params.keySet().toArray(new String[params.size()]);
        Arrays.sort(keys);

        StringBuilder stringBuilder = new StringBuilder();
        for (String key : keys) {
            if (!key.equals("format")) {
                stringBuilder.append(key);
                stringBuilder.append(params.get(key));
            }
        }
        stringBuilder.append(BuildConfig.LAST_FM_SECRET);

        return getMD5Sum(stringBuilder.toString());
    }

    public static Map<String, String> buildMap(String... strings) {
        // EVEN NUMBER OF PARAMTERS ONLY. DON'T FUCK THIS UP.
        if (strings.length % 2 != 0) {
            return Collections.EMPTY_MAP;
        }

        Map<String, String> result = new HashMap<>();
        for (int ii = 0; ii < strings.length; ii+=2) {
            result.put(strings[ii], strings[ii + 1]);
        }

        return result;
    }
}
