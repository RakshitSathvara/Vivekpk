package in.vaksys.vivekpk.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.adapter.SetContactAdpter;
import in.vaksys.vivekpk.dbPojo.EmergencyContact;
import in.vaksys.vivekpk.extras.AppConfig;
import in.vaksys.vivekpk.extras.MyApplication;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by dell980 on 5/7/2016.
 */
public class EmergencyFragment extends Fragment {

    private static final String TAG = "EmergencyFragment";
    private Button btnContactOne, btnContactTwo;
    private LinearLayout linearLayoutOne, linearContactTwo;
    private ImageView deleteContactOne;
    private TextView contactTvOneName, contactTvTwoNumber, contactTvOneName2, contactTvTwoNumber2;
    String phoneNo = null;
    String displayName = null;
    MyApplication myApplication;
    private Realm realm;
    String no;
    int btn1 = 0;
    RealmResults<EmergencyContact> results;
    private RecyclerView EmergencyRecyclerview;
    SetContactAdpter setContactAdpter;

    public static EmergencyFragment newInstance(int index) {
        EmergencyFragment fragment = new EmergencyFragment();
        Bundle b = new Bundle();
        b.putInt("index", index);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        switchUI();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_emergency, container, false);

        myApplication = MyApplication.getInstance();
        realm = Realm.getDefaultInstance();

        btnContactOne = (Button) rootView.findViewById(R.id.btn_contactOne);
        btnContactTwo = (Button) rootView.findViewById(R.id.btn_contactTwo);
        linearLayoutOne = (LinearLayout) rootView.findViewById(R.id.linear_contactOne);
        linearContactTwo = (LinearLayout) rootView.findViewById(R.id.linear_contactTwo);

        deleteContactOne = (ImageView) rootView.findViewById(R.id.img_deleteContact);

        contactTvOneName = (TextView) rootView.findViewById(R.id.tv_contactName);
        contactTvTwoNumber = (TextView) rootView.findViewById(R.id.tv_contactNumber);

        contactTvOneName2 = (TextView) rootView.findViewById(R.id.tv_contactName2);
        contactTvTwoNumber2 = (TextView) rootView.findViewById(R.id.tv_contactNumber2);
        EmergencyRecyclerview = (RecyclerView) rootView.findViewById(R.id.EmargencyRecyclerView);


        EmergencyRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        //  LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        //EmergencyRecyclerview.setLayoutManager(manager);

        switchUI();

        btnContactOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("emergencyFragment", "onClick: ");
                /*btnContactOne.setVisibility(View.GONE);
                linearLayoutOne.setVisibility(View.VISIBLE);*/
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(intent, 1);
                btn1 = 1;

            }
        });

        btnContactTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("emergencyFragment", "onClick: ");
                /*btnContactOne.setVisibility(View.GONE);
                linearLayoutOne.setVisibility(View.VISIBLE);*/
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(intent, 1);
                //   AddEmergencyContact();

                btn1 = 2;

            }
        });

        deleteContactOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutOne.setVisibility(View.GONE);
                btnContactOne.setVisibility(View.VISIBLE);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        MyApplication.getInstance().showLog("respode  ", String.valueOf(resultCode));
        if (resultCode == -1) {

            Uri uri = data.getData();
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();

            int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            phoneNo = cursor.getString(phoneIndex);


            //  no = phoneNo.replaceAll("//s+/()[]-", "");

//            no =  phoneNo.replaceAll("\\s+-()[]]","");
//           String no1 = no.replace("()",no);
//            String no2 = no1.replaceAll("-",no1);
            //  no = phoneNo.replaceAll("[()\\\\s-]+", "");
            no = phoneNo.replaceAll("[()\\s-\\s+]", "");

            //   MyApplication.getInstance().showLog("Number formate",no2);
            MyApplication.getInstance().showLog("jkhdj", no);
            //    contactTvTwoNumber.setText(phoneNo);

            int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            displayName = cursor.getString(nameIndex);


            AddEmergencyContact();
            switchUI();
            //  contactTvOneName.setText(displayName);
            //int nameIndex1 = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI);
//            btnContactOne.setVisibility(View.GONE);
//            linearLayoutOne.setVisibility(View.VISIBLE);
//
//            btnContactOne.setVisibility(View.GONE);
//            linearLayoutOne.setVisibility(View.VISIBLE);

            Log.e("Activity", "onClick: " + phoneNo + "And : " + displayName);
        } else {
            Toast.makeText(getActivity(), "You Didn't Select Any Contact", Toast.LENGTH_SHORT).show();
        }
    }

    private void switchUI() {

        results = realm.where(EmergencyContact.class).findAll();

        myApplication.showLog(TAG, String.valueOf(results.size()));

        if (results.size() == 0) {

            EmergencyRecyclerview.setVisibility(View.GONE);

        } else if (results.size() == 1) {


            EmergencyRecyclerview.setVisibility(View.VISIBLE);

            btnContactOne.setVisibility(View.GONE);
//            linearLayoutOne.setVisibility(View.VISIBLE);
//            contactTvOneName.setText(realm.where(EmergencyContact.class).findFirst().getContactName());
//            contactTvTwoNumber.setText(realm.where(EmergencyContact.class).findFirst().getPhoneNumber());

        } else if (results.size() == 2) {

            EmergencyRecyclerview.setVisibility(View.VISIBLE);

            setContactAdpter = new SetContactAdpter(getActivity(), results);
            EmergencyRecyclerview.setAdapter(setContactAdpter);
            setContactAdpter.notifyDataSetChanged();


            btnContactOne.setVisibility(View.GONE);
            btnContactTwo.setVisibility(View.GONE);
//            contactTvOneName2.setText(realm.where(EmergencyContact.class).findFirst().getContactName());
//            contactTvTwoNumber2.setText(realm.where(EmergencyContact.class).findFirst().getPhoneNumber());
//            contactTvOneName.setText(realm.where(EmergencyContact.class).findFirst().getContactName());
//            contactTvTwoNumber.setText(realm.where(EmergencyContact.class).findFirst().getPhoneNumber());
//            btnContactTwo.setVisibility(View.GONE);
//            linearContactTwo.setVisibility(View.VISIBLE);
        }
    }

    private void AddEmergencyContact() {


        myApplication.DialogMessage("Add Contact...");
        myApplication.showDialog();


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_EMERGENY_CONTACT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    int id = jObj.getInt("id");

                    if (!error) {

                        Toast.makeText(getActivity(),
                               ""+ error, Toast.LENGTH_LONG).show();

                        saveContact(id, displayName, phoneNo);
                        switchUI();

                        myApplication.hideDialog();

                    } else {
                        Toast.makeText(getActivity(),
                                "No Add Contenct", Toast.LENGTH_LONG).show();
                        myApplication.hideDialog();
                    }


                } catch (Exception e) {

                    myApplication.hideDialog();

                    Toast.makeText(getActivity(),
                            "Error :", Toast.LENGTH_LONG).show();
                    myApplication.hideDialog();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                myApplication.hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", displayName);
                params.put("phone", no);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "52d8c0efea5039cd0d778db7521889cf");
                return headers;
            }
        };
        myApplication.addToRequestQueue(strReq);


    }

    private void saveContact(int id, String displayName, String phoneNo) {


        realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        EmergencyContact user = realm.createObject(EmergencyContact.class);
        user.setId(id);
        user.setContactName(displayName);
        user.setPhoneNumber(phoneNo);
        realm.commitTransaction();

        results = realm.where(EmergencyContact.class).findAll();

        setContactAdpter = new SetContactAdpter(getActivity(), results);
        EmergencyRecyclerview.setAdapter(setContactAdpter);
        setContactAdpter.notifyDataSetChanged();


    }

//    public static void DelEmgContact(final int id) {
//
//        MyApplication.getInstance().DialogMessage("Delet Contact...");
//        MyApplication.getInstance().showDialog();
//
//
//        StringRequest strReq = new StringRequest(Request.Method.DELETE,
//                AppConfig.URL_EMERGENY_CONTACT, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                MyApplication.getInstance().showLog(TAG, response);
//
//                try {
//
//                    JSONObject jObj = new JSONObject(response);
//                    boolean error = jObj.getBoolean("error");
//
//                    MyApplication.getInstance().showLog("eooro", String.valueOf(error));
//
//                } catch (Exception e) {
//
//
//                }
////                try {
////
////                    JSONObject jObj = new JSONObject(response);
////                    boolean error = jObj.getBoolean("error");
////
////                    String msg = jObj.getString("message");
////                    Log.e(TAG, "onResponse: " + jObj.toString());
////                    Log.e(TAG, "eoorrororo: " + error + id + msg);
////                    if (!error) {
////
////                        Toast.makeText(context,
////                                msg, Toast.LENGTH_LONG).show();
////
////                        myApplication.hideDialog();
////
////                    } else {
////                        Toast.makeText(context,
////                                "No Add Contenct", Toast.LENGTH_LONG).show();
////                        myApplication.hideDialog();
////                    }
////
////
////                } catch (Exception e) {
////
////                    myApplication.hideDialog();
////
////                    Toast.makeText(context,
////                            "Error :", Toast.LENGTH_LONG).show();
////                    myApplication.hideDialog();
////                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                MyApplication.getInstance().hideDialog();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("id", String.valueOf(id));
//
//                return params;
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Authorization", "52d8c0efea5039cd0d778db7521889cf");
//                return headers;
//            }
//        };
//        MyApplication.getInstance().addToRequestQueue(strReq);
//
////        MyApplication.getInstance().DialogMessage("Delet Contact...");
////        MyApplication.getInstance().showDialog();
//
////        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, AppConfig.URL_EMERGENY_CONTACT, new Response.Listener<JSONObject>() {
////            @Override
////            public void onResponse(JSONObject response) {
////
////                myApplication.showLog(TAG, response.toString());
////                MyApplication.getInstance().hideDialog();
////            }
////        }, new Response.ErrorListener() {
////            @Override
////            public void onErrorResponse(VolleyError error) {
////                MyApplication.getInstance().hideDialog();
////            }
////        }) {
////            @Override
////            protected Map<String, String> getParams() throws AuthFailureError {
////                Map<String, String> params = new HashMap<String, String>();
////                params.put("id", String.valueOf(id));
////                return params;
////            }
////
////            @Override
////            public Map<String, String> getHeaders() throws AuthFailureError {
////                HashMap<String, String> headers = new HashMap<String, String>();
////                headers.put("Authorization", "52d8c0efea5039cd0d778db7521889cf");
////                return headers;
////            }
////        };
////
////        myApplication.addToRequestQueue(request);
//    }


    public static void DelEmgContact(final int id) {


//        MyApplication.getInstance().DialogMessage("Del Contact...");
//        MyApplication.getInstance().showDialog();
//
//
//        StringRequest strReq = new StringRequest(Request.Method.DELETE,
//                AppConfig.URL_EMERGENY_CONTACT, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                try {
//
//                    JSONObject jObj = new JSONObject(response);
//                    boolean error = jObj.getBoolean("error");
//                    String msg = jObj.getString("message");
//                    Log.e(TAG, "onResponse: " + jObj.toString());
//                    Log.e(TAG, "eoorrororo: " + error + id + msg);
//                    if (!error) {
//
//
//                        Log.e(TAG, "onResponse:");
//                        //btn1++;
//
//
//                        MyApplication.getInstance().hideDialog();
//
//                    } else {
////                        Toast.makeText(getActivity(),
////                                "No Add Contenct", Toast.LENGTH_LONG).show();
//                        MyApplication.getInstance().hideDialog();
//                    }
//
//
//                } catch (Exception e) {
//
//                    MyApplication.getInstance().hideDialog();
//
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                MyApplication.getInstance().hideDialog();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("id", String.valueOf(45));
//
//                return params;
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Authorization", "52d8c0efea5039cd0d778db7521889cf");
//                return headers;
//            }
//        };
//        MyApplication.getInstance().addToRequestQueue(strReq);


        MyApplication.getInstance().DialogMessage("Delet Contact...");
        MyApplication.getInstance().showDialog();


        StringRequest request = new StringRequest(Request.Method.DELETE, AppConfig.URL_EMERGENY_CONTACT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    boolean error = jsonObject.getBoolean("error");

                    MyApplication.getInstance().showLog("dfiuyfude",""+error);

                }catch (Exception e){

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> par = new HashMap<>();
                par.put("id",""+id);

                return par;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String,String> header = new HashMap<>();
                header.put("Authorization", "52d8c0efea5039cd0d778db7521889cf");

                return header;
            }
        };

//        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, AppConfig.URL_EMERGENY_CONTACT,new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//
//                MyApplication.getInstance().showLog(TAG, response.toString());
//                MyApplication.getInstance().hideDialog();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                MyApplication.getInstance().hideDialog();
//            }
//        }) {
//
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Authorization", "52d8c0efea5039cd0d778db7521889cf");
//                return headers;
//            }
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("id", String.valueOf(id));
//                return params;
//            }
//        };

        MyApplication.getInstance().addToRequestQueue(request);

    }
}
