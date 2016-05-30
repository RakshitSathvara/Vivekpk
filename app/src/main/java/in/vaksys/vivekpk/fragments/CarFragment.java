package in.vaksys.vivekpk.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.adapter.mySpinnerAdapterBrand;
import in.vaksys.vivekpk.adapter.mySpinnerAdapterModel;
import in.vaksys.vivekpk.dbPojo.VehicleModels;
import in.vaksys.vivekpk.extras.AppConfig;
import in.vaksys.vivekpk.extras.MyApplication;
import in.vaksys.vivekpk.extras.PercentLinearLayout;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarFragment extends Fragment {

    private static final String TAG = "carFragment";
    Spinner spSelectmake, spCarModel;
    @Bind(R.id.carDetailRecyclerView)
    RecyclerView carDetailRecyclerView;
    EditText etCarDetails;
    Button btnContinue;
    PercentLinearLayout percentEnterCarDetails;
    @Bind(R.id.btn_addVVehicle)
    Button btnAddVVehicle;
    @Bind(R.id.AddVehicleBtnLayout)
    LinearLayout AddVehicleBtnLayout;
    private Realm realm;
    MyApplication myApplication;
    private String modelSpinnItem;

    boolean btnvisible = true, detailLayout = false, recyclerview = false;
    private String CarSpinnItem;
    private int carPosi;
    private int makePosi;
    private String MakeSpinnItem;
    private String mCarDetail;

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

        myApplication = MyApplication.getInstance();


        spSelectmake = (Spinner) rootView.findViewById(R.id.sp_selectMake);
        spCarModel = (Spinner) rootView.findViewById(R.id.sp_selectModel);
        etCarDetails = (EditText) rootView.findViewById(R.id.et_carDetails);
        btnContinue = (Button) rootView.findViewById(R.id.btn_continue);
        percentEnterCarDetails = (PercentLinearLayout) rootView.findViewById(R.id.percentEnterCarDetails11);

        realm = Realm.getDefaultInstance();
        MyApplication.getInstance().createDialog(getActivity(), false);

//        List<String> selectBrand = new ArrayList<String>();
//        selectBrand.add("Select Brand");
//        selectBrand.add("Audi");
//        selectBrand.add("BMW");
//        selectBrand.add("Jaguar");
//
//        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, selectBrand);
//
//        // Drop down layout style - list view with radio button
//        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        // attaching data adapter to spinner
//        spSelectmake.setAdapter(dataAdapter1);
//        spSelectmake.setSelection(0);

        spSelectmake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView CarModelSpi = (TextView) view;
                MakeSpinnItem = CarModelSpi.getText().toString();
                makePosi = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        List<String> carModel = new ArrayList<String>();
//        carModel.add("Select Model");
//        carModel.add("A7");
//        carModel.add("X4");
//        carModel.add("A2");
//        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, carModel);
//
//        // Drop down layout style - list view with radio button
//        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        // attaching data adapter to spinner
//        spCarModel.setAdapter(dataAdapter2);
//        spCarModel.setSelection(0);
//
//        spCarModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                TextView CarModelSpi = (TextView) view;
//                CarSpinnItem = CarModelSpi.getText().toString();
//                carPosi = position;
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

      /*  btnAddVVehicle.setVisibility(View.VISIBLE);
        carDetailRecyclerView.setVisibility(View.VISIBLE);
        percentEnterCarDetails.setVisibility(View.INVISIBLE);
*/

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
//                SwitchUI();

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
                makePosi= position;
                String myid = ((TextView) view.findViewById(R.id.rowid)).getText().toString();
                Toast.makeText(getActivity(), "You have selected " + modelSpinnItem + " " + myid, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), "You have selected " + modelSpinnItem + " " + myid, Toast.LENGTH_SHORT).show();
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
        RegisterVehicle(mCarDetail, CarSpinnItem, MakeSpinnItem);
    }

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

    private boolean validateCarSpinner() {
        if (carPosi == 0) {
            return false;
        } else {
            return true;
        }
    }

    private boolean validateMakeSpinner() {
        if (makePosi == 0) {
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

    private void SwitchUI() {
        if (btnvisible) {
            btnAddVVehicle.setVisibility(View.INVISIBLE);
            percentEnterCarDetails.setVisibility(View.VISIBLE);
            btnvisible = false;
        } else {
            btnAddVVehicle.setVisibility(View.VISIBLE);
            percentEnterCarDetails.setVisibility(View.INVISIBLE);
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
}
