package in.vaksys.vivekpk.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.pinball83.maskededittext.MaskedEditText;

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
import in.vaksys.vivekpk.model.SearchMessage;
import io.realm.Realm;
import io.realm.RealmResults;


public class BikeFragment extends Fragment {

    private static final String TAG = "BikeFragment";
    Spinner spSelectmake, spCarModel;
    @Bind(R.id.bikeRecycle)
    RecyclerView carDetailRecyclerView;
    MaskedEditText etCarDetails;
    private Button btnContinue;
    @Bind(R.id.btn_addVVehiclebike)
    Button btnAddVVehicle;
    @Bind(R.id.AddVehicleBtnLayoutbike)
    LinearLayout AddVehicleBtnLayout;
    @Bind(R.id.add_car_detail_bike)
    View addCarView;

    private Realm realm;
    MyApplication myApplication;
    private String modelSpinnItem;
    private String SerachData = null;

    private int brandposition;
    private int modelpostion;
    private String mCarDetail;
    private RealmResults<VehicleDetails> results;
    private CarBikeRecyclerViewAdapter carAdapter;
    String myid;
    private TextView txt_car_bike_replace;
    private TextView txt_car_bike_type_replace;


    public static BikeFragment newInstance(int index) {
        BikeFragment fragment = new BikeFragment();
        Bundle b = new Bundle();
        b.putInt("index", index);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_bike, container, false);
        ButterKnife.bind(this, rootView);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        carDetailRecyclerView.setLayoutManager(manager);


        addCarView.setVisibility(View.GONE);
        carDetailRecyclerView.setVisibility(View.GONE);
        AddVehicleBtnLayout.setVisibility(View.GONE);


        myApplication = MyApplication.getInstance();

        txt_car_bike_replace = (TextView) rootView.findViewById(R.id.txt_car_bike_replace);
        txt_car_bike_type_replace = (TextView) rootView.findViewById(R.id.txt_car_bike_type_replace);

        spSelectmake = (Spinner) rootView.findViewById(R.id.sp_selectMake);
        spCarModel = (Spinner) rootView.findViewById(R.id.sp_selectModel);
        etCarDetails = (MaskedEditText) rootView.findViewById(R.id.et_carDetails);
        btnContinue = (Button) rootView.findViewById(R.id.btn_continue);

        txt_car_bike_replace.setText("Enter Bike Details");
        txt_car_bike_type_replace.setText("Type 2 Wheeler");

        realm = Realm.getDefaultInstance();
        MyApplication.getInstance().createDialog(getActivity(), false);
        results = realm.where(VehicleDetails.class).equalTo("type", "bike").findAll();
        myApplication.showLog(TAG, "count : " + results.size());
        SetCarDetailsList();


        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
//                SwitchUI();

            }
        });

        etCarDetails.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    myApplication.showLog(TAG, "Enter pressed");
                    submitForm();
                }
                return false;
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
        LodingModel("select Brand");

        return rootView;
    }

    private void LodingModel(String spv) {


        RealmResults<VehicleModels> results = realm.where(VehicleModels.class).equalTo("manufacturerName", spv).findAll();

//        for (VehicleModels s : results) {
//
//            myApplication.showLog("releted Model List", "" + s);
//        }

        mySpinnerAdapterModel mySpinnerAdapterCity = new mySpinnerAdapterModel(getActivity(), results, "bike");
        spCarModel.setAdapter(mySpinnerAdapterCity);

        spCarModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                modelSpinnItem = ((TextView) view.findViewById(R.id.rowText)).getText().toString();
                modelpostion = position;
                myid = ((TextView) view.findViewById(R.id.rowid)).getText().toString();
                MyApplication.getInstance().showLog("myidddddd", "" + myid);
                //    Toast.makeText(getActivity(), "You have selected " + modelSpinnItem + " " + myid, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(), "You have selected Nothing ..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void LodingBrand() {

        RealmResults<VehicleModels> results = realm.where(VehicleModels.class).findAll();

//        for (VehicleModels s : results) {
//
//            myApplication.showLog("all lis", "" +   s.getManufacturerName());
//        }

        RealmResults<VehicleModels> vehicleModels = results.distinct("manufacturerName");

//        for (VehicleModels ss : vehicleModels) {
//            ss.getManufacturerName();
//            myApplication.showLog("all list distics--->", "" + ss.getManufacturerName());
//        }

        mySpinnerAdapterBrand mySpinnerAdapterCity = new mySpinnerAdapterBrand(getActivity(), vehicleModels, "bike");
        spSelectmake.setAdapter(mySpinnerAdapterCity);

        spSelectmake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                brandposition = position;
                modelSpinnItem = ((TextView) view.findViewById(R.id.rowText)).getText().toString();
                String myid = ((TextView) view.findViewById(R.id.rowid)).getText().toString();
                MyApplication.getInstance().showLog("myidddddd", "" + myid);
                LodingModel(modelSpinnItem);
//                Toast.makeText(getActivity(), "You have selected " + modelSpinnItem + " " + myid, Toast.LENGTH_SHORT).show();
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

        MyApplication.getInstance().showLog("brand value",mCarDetail);

        carAdapter.AddVehicle(getActivity(), "bike", Integer.parseInt(myid), mCarDetail, brandposition, modelpostion);
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
        if (modelSpinnItem.equalsIgnoreCase("Select Model")) {
            Toast.makeText(getActivity(), "Select Brand and Model", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateMakeSpinner() {
        if (modelSpinnItem.equalsIgnoreCase("Select Brand")) {
            Toast.makeText(getActivity(), "Select Brand and Model", Toast.LENGTH_SHORT).show();
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

        final String apikey = myApplication.getApikey();

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
                headers.put("Authorization", apikey);
                return headers;

            }
        };
        // Adding request to request queue
        myApplication.addToRequestQueue(strReq, tag_string_req);
    }


    private void SetCarDetailsList() {

//        carDetailRecyclerView.setHasFixedSize(true);

        results = realm.where(VehicleDetails.class).equalTo("type", "bike").findAll();
        carAdapter = new CarBikeRecyclerViewAdapter(getActivity(), results);
        carDetailRecyclerView.setHasFixedSize(true);
        carDetailRecyclerView.setItemAnimator(new DefaultItemAnimator());
        carDetailRecyclerView.setNestedScrollingEnabled(false);
        carDetailRecyclerView.setAdapter(carAdapter);

        if (results.size() > 0) {
            carDetailRecyclerView.setVisibility(View.VISIBLE);
            AddVehicleBtnLayout.setVisibility(View.VISIBLE);
        } else {
            addCarView.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe
    public void onEvent(SearchMessage messageCar) {
        Log.e("SerchData", messageCar.getMsg());

        SerachData = messageCar.getMsg();

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
