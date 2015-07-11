package ninja.bryansills.retroscrobble;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
}
