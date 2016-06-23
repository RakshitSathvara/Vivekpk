package in.vaksys.vivekpk.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.activities.NotificationActivity;
import in.vaksys.vivekpk.dbPojo.EmergencyContact;
import in.vaksys.vivekpk.extras.AppConfig;
import in.vaksys.vivekpk.extras.MyApplication;
import in.vaksys.vivekpk.fragments.EmergencyFragment;
import in.vaksys.vivekpk.model.notificationdata;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import okhttp3.OkHttpClient;

/**
 * Created by patel on 30-05-2016.
 */
public class ReminderAdpter extends RecyclerView.Adapter<ReminderAdpter.AdapterHolder> {
    private static final String TAG = "SetContactAdpter";
    private Context context;

    AdapterHolder viewHolder;
    MyApplication myApplication;

    String type,vehicalno,nofidate;

    List<notificationdata> list ;



    public ReminderAdpter(Context context, String type, String vehicalno, String nofidate) {

        this.context = context;
        this.type = type;
        this.vehicalno = vehicalno;
        this.nofidate = nofidate;
        this.myApplication = MyApplication.getInstance();
    }

    public ReminderAdpter(Context context, List<notificationdata> list) {

        this.context = context;
        this.list = list;

    }


    @Override
    public AdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_reminder_data, parent, false);
        viewHolder = new AdapterHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterHolder holder, int position) {

//        notificationdata s = list.get(position);
//
//
//
//        holder.remindertype.setText(s.getRemndertype());
//        holder.vehicalNumber.setText(s.getVehecalno());
//        holder.vehicalexpdate.setText(s.getExpdate());

        holder.remindertype.setText(type);
        holder.vehicalNumber.setText(vehicalno);
        holder.vehicalexpdate.setText(nofidate);



    }





    @Override
    public int getItemCount() {
        return (null != type ? type.length() : 0);
    }

    public class AdapterHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_reminder_typr)
        TextView remindertype;

        @Bind(R.id.tv_reminder_vehicalNumber)
        TextView vehicalNumber;

        @Bind(R.id.tv_reminder_expdate)
        TextView vehicalexpdate;


        AdapterHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }





}
