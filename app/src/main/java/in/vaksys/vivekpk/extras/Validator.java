package in.vaksys.vivekpk.extras;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by dell980 on 5/2/2016.
 */
public class Validator {

    private static final String TAG = "Vivekpk" + Validator.class.getSimpleName();

    public static boolean validatePhoneNo(String value, ValidationListener listener) {
        Log.i(TAG, "validating contact VALUE: " + value);

        if (TextUtils.isEmpty(value)) {
            listener.validationFailed("Contact Required.");
            return false;
        }
        if (value.length() > 10) {
            listener.validationFailed("Invalid contact.");
            return false;
        }
        return true;
    }

    public static boolean validatePassword(String value, ValidationListener listener) {
        Log.i(TAG, "validating password VALUE: " + value);

        if (TextUtils.isEmpty(value)) {
            listener.validationFailed("Password Required.");
            return false;
        }
        return true;
    }

    public static interface ValidationListener {
        public void validationFailed(String msg);
    }

}
