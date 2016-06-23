/**
 * @author Jenish Khanpara
 */

package in.vaksys.vivekpk.extras;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class PreferenceHelper {

    private final String PREFS_IS_CONFIGURE = "isConfigure";
    private SharedPreferences prefs;


    public PreferenceHelper(Context ctx) {
        prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static PreferenceHelper newInstance(Context ctx) {
        return new PreferenceHelper(ctx);
    }


    public boolean isConfigure() {
        return prefs.getBoolean(PREFS_IS_CONFIGURE, false);
    }

    public void setConfigure(boolean value) {
        prefs.edit().putBoolean(PREFS_IS_CONFIGURE, value).apply();
    }

    public void clearAllPrefs() {
        prefs.edit().clear().apply();
    }

    public void addValue(String apikey) {

        prefs.edit().putString("apikey", apikey).commit();
    }

    public String GetApikey() {

        String apikey = prefs.getString("apikey", "");

        return apikey;
    }

}