package in.vaksys.vivekpk.fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.adapter.CarBikeRecyclerViewAdapter;
import in.vaksys.vivekpk.adapter.mySpinnerAdapterBrand;
import in.vaksys.vivekpk.adapter.mySpinnerAdapterModel;
import in.vaksys.vivekpk.dbPojo.VehicleDetails;
import in.vaksys.vivekpk.dbPojo.VehicleModels;
import in.vaksys.vivekpk.extras.AppConfig;
import in.vaksys.vivekpk.extras.MyApplication;
import in.vaksys.vivekpk.model.ClaimMessage;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarFragment extends Fragment {

    private static final String TAG = "carFragment";
    Spinner spSelectmake, spCarModel;
    @Bind(R.id.carRecycle)
    RecyclerView carDetailRecyclerView;
    EditText etCarDetails;
    Button btnContinue;
    @Bind(R.id.btn_addVVehicle)
    Button btnAddVVehicle;
    @Bind(R.id.AddVehicleBtnLayout)
    LinearLayout AddVehicleBtnLayout;
    @Bind(R.id.add_car_detail)
    View addCarView;

    private Realm realm;
    MyApplication myApplication;
    private String modelSpinnItem;

    private int carPosi;
    private int makePosi;
    private String mCarDetail;
    private RealmResults<VehicleDetails> results;
    private CarBikeRecyclerViewAdapter carAdapter;
    String myid;


    public static CarFragment newInstance(int index) {
        CarFragment fragment = new CarFragment();
        Bundle b = new Bundle();
        b.putInt("index", index);
        fragment.setArguments(b);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_car, container, false);
        ButterKnife.bind(this, rootView);


        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        carDetailRecyclerView.setLayoutManager(manager);


        addCarView.setVisibility(View.GONE);
        carDetailRecyclerView.setVisibility(View.GONE);
        AddVehicleBtnLayout.setVisibility(View.GONE);


        myApplication = MyApplication.getInstance();


        spSelectmake = (Spinner) rootView.findViewById(R.id.sp_selectMake);
        spCarModel = (Spinner) rootView.findViewById(R.id.sp_selectModel);
        etCarDetails = (EditText) rootView.findViewById(R.id.et_carDetails);
        btnContinue = (Button) rootView.findViewById(R.id.btn_continue);


        realm = Realm.getDefaultInstance();
        MyApplication.getInstance().createDialog(getActivity(), false);
        results = realm.where(VehicleDetails.class).equalTo("type", "car").findAll();
        myApplication.showLog(TAG, "count : " + results.size());
        SetCarDetailsList("");


        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
//                SwitchUI();

            }
        });

        btnAddVVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCarView.setVisibility(View.VISIBLE);
                carDetailRecyclerView.setVisibility(View.GONE);
                AddVehicleBtnLayout.setVisibility(View.GONE);
            }
        });
        LodingBrand();
        LodingModel();

        return rootView;
    }

    private void LodingModel() {

        // // TODO: 5/20/2016  add vishal
        RealmResults<VehicleModels> results = realm.where(VehicleModels.class).findAll();

        mySpinnerAdapterModel mySpinnerAdapterCity = new mySpinnerAdapterModel(getActivity(), results, "bike");
        spCarModel.setAdapter(mySpinnerAdapterCity);

        spCarModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                modelSpinnItem = ((TextView) view.findViewById(R.id.rowText)).getText().toString();
                makePosi = position;
                myid = ((TextView) view.findViewById(R.id.rowid)).getText().toString();
             //   Toast.makeText(getActivity(), "You have selected " + modelSpinnItem + " " + myid, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(), "You have selected Nothing ..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void LodingBrand() {

        RealmResults<VehicleModels> results = realm.where(VehicleModels.class).findAll();

        mySpinnerAdapterBrand mySpinnerAdapterCity = new mySpinnerAdapterBrand(getActivity(), results, "car");
        spSelectmake.setAdapter(mySpinnerAdapterCity);

        spSelectmake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                carPosi = position;
                modelSpinnItem = ((TextView) view.findViewById(R.id.rowText)).getText().toString();
                String myid = ((TextView) view.findViewById(R.id.rowid)).getText().toString();
               // Toast.makeText(getActivity(), "You have selected " + modelSpinnItem + " " + myid, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(), "You have selected Nothing ..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void submitForm() {

        if (!validateRegNumber()) {
            return;
        }
        if (!validateMakeSpinner()) {
            return;
        }
        if (!validateCarSpinner()) {
            return;
        }
        getData();
    }

    private void getData() {
        mCarDetail = etCarDetails.getText().toString();
        carAdapter.AddVehicle(getActivity(), "car", Integer.parseInt(myid), mCarDetail);
        carDetailRecyclerView.setVisibility(View.VISIBLE);
        AddVehicleBtnLayout.setVisibility(View.VISIBLE);
        addCarView.setVisibility(View.GONE);
        //    results = realm.where(VehicleDetails.class).equalTo("type", "car").findAll();
//        carAdapter = new CarBikeRecyclerViewAdapter(getActivity(), results);
//        carDetailRecyclerView.setAdapter(carAdapter);
//        carAdapter.notifyDataSetChanged();
//        RegisterVehicle(mCarDetail, CarSpinnItem, MakeSpinnItem);
//        carAdapter.notifyDataSetChanged();
    }

    private boolean validateCarSpinner() {
        if (carPosi == 0) {
            Toast.makeText(getActivity(), "Select Brand and Model", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateMakeSpinner() {
        if (makePosi == 0) {

            Toast.makeText(getActivity(), "Select Model and Brand", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateRegNumber() {
        if (etCarDetails.getText().toString().trim().isEmpty()) {
            etCarDetails.setError(getString(R.string.err_msg_number));
            requestFocus(etCarDetails);
            return false;
        } else {
            return true;
        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void confirmDialog(final String carNo, String model) {
        final Dialog confirm = new Dialog(getActivity());
        confirm.requestWindowFeature(Window.FEATURE_NO_TITLE);
        confirm.setContentView(R.layout.claim_this_vehicle);

        Button btnCancel = (Button) confirm.findViewById(R.id.btn_claming_this_vehicle_cancel);
        Button btnClaim = (Button) confirm.findViewById(R.id.btn_claming_this_vehicle_claim);
        TextView VehicleNo = (TextView) confirm.findViewById(R.id.tv_claimNumber);
        TextView VehicleModel = (TextView) confirm.findViewById(R.id.tv_claimCarModel);

        VehicleNo.setText(carNo);
        VehicleModel.setText(model);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm.dismiss();
            }
        });
        btnClaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClaimVehicle(carNo);
                confirm.dismiss();
            }
        });
        confirm.show();
    }

    private void ClaimVehicle(final String vehicle_number) {

        String tag_string_req = "req_claim_vehicle";

        myApplication.DialogMessage("Claiming Vehicle...");
        myApplication.showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_CLAIM_VEHICLE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                myApplication.hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    Log.e(TAG, "onResponse: " + jObj.toString());

                    if (!error) {
                        Toast.makeText(getActivity(), jObj.getString("message") + "... ", Toast.LENGTH_LONG).show();

                    } else {
                        // Error in login. Get the error message
                        myApplication.hideDialog();
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(getActivity(),
                                "Error :" + errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    myApplication.hideDialog();
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Login Error: " + error.getMessage());
                myApplication.ErrorSnackBar(getActivity());
                myApplication.hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("vehicleNo", vehicle_number);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "52d8c0efea5039cd0d778db7521889cf");
                return headers;

            }
        };
        // Adding request to request queue
        myApplication.addToRequestQueue(strReq, tag_string_req);
    }


    private void SetCarDetailsList(String ab) {

//        carDetailRecyclerView.setHasFixedSize(true);

        results = realm.where(VehicleDetails.class).equalTo("type", "car").findAll();
        if (results.size() > 0) {
            carDetailRecyclerView.setVisibility(View.VISIBLE);
            AddVehicleBtnLayout.setVisibility(View.VISIBLE);
            carAdapter = new CarBikeRecyclerViewAdapter(getActivity(), results);
            carDetailRecyclerView.setHasFixedSize(true);
            carDetailRecyclerView.setItemAnimator(new DefaultItemAnimator());
            carDetailRecyclerView.setNestedScrollingEnabled(false);
            carDetailRecyclerView.setAdapter(carAdapter);


        } else {
            addCarView.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe
    public void onEvent(ClaimMessage messageCar) {
        Log.e("car datata", messageCar.getCarNo() + " " + messageCar.getModel());
        String aa = realm.where(VehicleModels.class).equalTo("id", messageCar.getModel()).findFirst().getManufacturerName();
        myApplication.showLog(TAG, aa);
//        Toast.makeText(getActivity(), messageCar.getModel(), Toast.LENGTH_SHORT).show();
        confirmDialog(messageCar.getCarNo(), aa);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
/*

    private void RegisterVehicle(String mCarDetail, final String carSpinnItem, final String makeSpinnItem) {
        String tag_string_req = "req_add_model";

        MyApplication.getInstance().DialogMessage("Adding Model...");
        MyApplication.getInstance().showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_ADD_USER_VEHICLE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                MyApplication.getInstance().hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    Log.e(TAG, "onResponse: " + jObj.toString());

                    // Check for error node in json
                    if (!error) {
                        Toast.makeText(getActivity(),
                                "Login Successfull... ", Toast.LENGTH_LONG).show();

                        // parsing the user profile information
                        JSONObject profileObj = jObj.getJSONObject("result");

                        String fname = profileObj.getString("firstName");
                        String lname = profileObj.getString("lastName");
                        String email = profileObj.getString("email");
                        String apikey = profileObj.getString("apiKey");
                        int status = profileObj.getInt("status");
                        String phone = profileObj.getString("phone");
                        String createdAt = profileObj.getString("createdAt");
                        String updatedAt = profileObj.getString("updatedAt");

                        Log.e(TAG, "" + fname + " " + lname + " " + email + " " + apikey
                                + " " + status + " " + phone + " " + createdAt + " " + updatedAt);

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(getActivity(),
                                "Error :" + errorMsg, Toast.LENGTH_LONG).show();
                        return;
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Login Error: " + error.getMessage());
                MyApplication.getInstance().ErrorSnackBar(getActivity());
                MyApplication.getInstance().hideDialog();
                return;
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("manifactororName", carSpinnItem);
                params.put("model", makeSpinnItem);
                params.put("type", "car");

                return params;
            }
        };
        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);

    }
*/


