package in.vaksys.vivekpk.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.dbPojo.EmergencyContact;
import in.vaksys.vivekpk.extras.AppConfig;
import in.vaksys.vivekpk.extras.MyApplication;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by patel on 30-05-2016.
 */
public class SetContactAdpter extends RecyclerView.Adapter<SetContactAdpter.AdapterHolder> {
    private static final String TAG = "SetContactAdpter";
    private Context context;
    private Realm realm;
    private RealmResults<EmergencyContact> data;
    AdapterHolder viewHolder;
    MyApplication myApplication;

    public SetContactAdpter(Context context, RealmResults<EmergencyContact> data) {

        realm = Realm.getDefaultInstance();
        this.context = context;
        this.data = data;
        this.myApplication = MyApplication.getInstance();
    }

    @Override
    public AdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_contact_number, null);
        viewHolder = new AdapterHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterHolder holder, int position) {

        EmergencyContact emergencyContact = data.get(position);


        holder.tv_contactName.setText(emergencyContact.getContactName());
        holder.tv_contactNumber.setText(emergencyContact.getPhoneNumber());

        final int id = emergencyContact.getId();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyApplication.getInstance().showLog("id", String.valueOf(id));
                DelEmgContact(id);
            }
        });


    }

    private void DelEmgContact(final int id) {

        MyApplication.getInstance().DialogMessage("Delet Contact...");
        MyApplication.getInstance().showDialog();

       final StringRequest request = new StringRequest(Request.Method.DELETE, AppConfig.URL_EMERGENY_CONTACT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams()  {
                Map<String, String> stringMap = new HashMap<>();
                stringMap.put("id", "22");

                return stringMap;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("Authorization", "52d8c0efea5039cd0d778db7521889cf");

                return hashMap;
            }
        };

        MyApplication.getInstance().addToRequestQueue(request);



    }


    @Override
    public int getItemCount() {
        return (null != data ? data.size() : 0);
    }

    public class AdapterHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_contactName)
        TextView tv_contactName;

        @Bind(R.id.tv_contactNumber)
        TextView tv_contactNumber;


        AdapterHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
