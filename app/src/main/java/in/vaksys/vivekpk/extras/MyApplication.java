package in.vaksys.vivekpk.extras;

/**
 * Created by dell980 on 5/3/2016.
 */

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.provider.Settings;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.enums.SnackbarType;
import com.nispok.snackbar.listeners.ActionClickListener;

import junit.framework.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import in.vaksys.vivekpk.activities.MainActivity;
import in.vaksys.vivekpk.dbPojo.Users;
import in.vaksys.vivekpk.dbPojo.VehicleModels;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MyApplication extends Application {
    public static final String TAG = MyApplication.class.getSimpleName();

    private static MyApplication mInstance;
    private RequestQueue mRequestQueue;
    private ProgressDialog pDialog;
    private Realm realm;

    public static Calendar c;
    Intent i;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    int value;
    private String mDate;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        RealmConfiguration configuration = new RealmConfiguration.Builder(this)
                .name("DaddysRoad1.realm")
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(configuration);

    }

    // common in volley singleton and analytics
    public static synchronized MyApplication getInstance() {
        return mInstance;
    }


    // for volley
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    // for multidex support
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public void ErrorSnackBar(final Activity activity) {
        Snackbar.with(activity)
                .type(SnackbarType.MULTI_LINE)
                .text("Check Internet Connection")
                .actionLabel("Done")
                .actionColor(Color.CYAN)
                .actionListener(new ActionClickListener() {
                    @Override
                    public void onActionClicked(Snackbar snackbar) {

                        try {
                            i = new Intent(activity, Class.forName(Settings.ACTION_WIRELESS_SETTINGS));
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                            activity.finish();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                    }
                })
                .duration(Snackbar.SnackbarDuration.LENGTH_LONG)
                .swipeToDismiss(false)
                .show(activity);
    }

    public void ExitSnackBar(Activity activity) {
        Snackbar.with(activity)
                .type(SnackbarType.MULTI_LINE)
                .text("Press BACK again to exit")
                .duration(Snackbar.SnackbarDuration.LENGTH_SHORT)
                .swipeToDismiss(true)
                .show(activity);
    }

    public void createDialog(Activity activity, boolean cancelable) {
        pDialog = new ProgressDialog(activity);
        pDialog.setCancelable(cancelable);
    }

    public void DialogMessage(String message) {
        pDialog.setMessage(message);
    }

    public void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    public void hideDialog() {
        //// TODO: 23-05-2016  errorr solve

        //show dialog
        if (pDialog.isShowing())
            pDialog.dismiss();

    }


    public void hideKeyboard(Activity context) {
        // Check if no view has focus:
        View view1 = context.getCurrentFocus();
        if (view1 != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);
        }
    }

    public void showLog(String TAG, String msg) {
        Log.e(TAG, msg);
    }

    public String ChanageDate(String CurrentDate, int days) {
//        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy", Locale.US);
//        Calendar c = Calendar.getInstance();
//        try {
//            c.setTime(sdf.parse(CurrentDate));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        c.add(Calendar.DATE, -days);  // number of days to add
//        sdf.format(c.getTime());  // dt is now the new date

//// TODO: 14/06/2016  this code for change date
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(CurrentDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, -days); /// -days mens 5 day befor reminder set
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date resultdate = new Date(c.getTimeInMillis());
        String dateInString = sdf.format(resultdate);
        System.out.println("String date:" + dateInString);
        //  myApplication.showLog("split date-->", String.valueOf(dateInString));


        return dateInString;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }


    public String getApikey() {

        PreferenceHelper preferenceHelper = new PreferenceHelper(this);
        String apikey = preferenceHelper.GetApikey();

        MyApplication.getInstance().showLog("keyyyyy---",apikey);

        return apikey;
    }
}