package in.vaksys.vivekpk.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.dbPojo.VehicleDetails;
import in.vaksys.vivekpk.dbPojo.VehicleModels;
import in.vaksys.vivekpk.extras.MyApplication;
import in.vaksys.vivekpk.extras.VolleyHelper;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by Harsh on 26-05-2016.
 */
public class CarRecyclerViewAdapter extends RecyclerView.Adapter<CarRecyclerViewAdapter.AdapterHolder> {

    private final Context context;
    private RealmResults<VehicleDetails> detailses = null;
    MyApplication myApplication;
    private Realm realm;
    private static final String TAG = "CarRecyclerViewAdapter";
    AdapterHolder viewHolder;
    private VehicleDetails details;
    Spinner brandSpinner, ModelSpinner;
    private int carPosi;
    private int makePosi;
    String myid;
    Dialog confirm;


    public CarRecyclerViewAdapter(Context context, RealmResults<VehicleDetails> detailses) {

        this.myApplication = MyApplication.getInstance();
        realm = Realm.getDefaultInstance();
        this.context = context;
        this.detailses = detailses;
        myApplication.createDialog((Activity) context, false);


    }

    public CarRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public AdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newvehicle, parent, false);
        viewHolder = new AdapterHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterHolder holder, final int position) {
        details = detailses.get(position);

        VehicleModels vehicleModels = realm.where(VehicleModels.class).equalTo("id", details.getVehicleModelID()).findFirst();

        holder.VehicleNumber.setText(details.getVehicleNo());
//        myApplication.showLog(TAG, details.getVehicleNo());
        holder.VehicleBrand.setText(vehicleModels.getManufacturerName());
        holder.VehicleModel.setText(vehicleModels.getModel());
        holder.VehicleCount.setText(new StringBuilder().append("Vehicle ").append(position + 1).toString());
        holder.VehicleIDHiddden.setText(String.valueOf(details.getVehicleId()));


        holder.EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm = new Dialog(context);
//                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                final View layout = inflater.inflate(R.layout.enter_car_details_edit, null, false);
                confirm.requestWindowFeature(Window.FEATURE_NO_TITLE);
                confirm.setContentView(R.layout.enter_car_details_edit);
                confirm.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Button btnEdit = (Button) confirm.findViewById(R.id.btn_car_detail_edit);
                Button btnDelt = (Button) confirm.findViewById(R.id.btn_car_detail_delete);

                final EditText VehicleNo = (EditText) confirm.findViewById(R.id.et_carDetails_edit);
                VehicleNo.setEnabled(false);

                brandSpinner = (Spinner) confirm.findViewById(R.id.sp_selectMake_edit);
                ModelSpinner = (Spinner) confirm.findViewById(R.id.sp_selectModel_edit);

                LodingBrand();
                LodingModel();
                VehicleNo.setText(holder.VehicleNumber.getText());
                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Submit(holder.VehicleIDHiddden.getText().toString());
                        confirm.dismiss();

                    }
                });
                btnDelt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                        alertDialogBuilder.setTitle("Sure Want to Delete this vehicle ??")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        DeleteVehicle((Activity) context, Integer.parseInt(holder.VehicleIDHiddden.getText().toString()));
                                        dialog.cancel();
                                        confirm.dismiss();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                });
                confirm.show();
            }

        });

    }


    @Override
    public int getItemCount() {
        return (null != detailses ? detailses.size() : 0);
    }

    public class AdapterHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_detailvehicleNumber_gen)
        TextView VehicleNumber;
        @Bind(R.id.tv_detailvehicleBrand_gen)
        TextView VehicleBrand;
        @Bind(R.id.tv_detailvehicleNumberModel_gen)
        TextView VehicleModel;
        @Bind(R.id.img_vehicelDetailEdit_gen)
        ImageView EditButton;
        @Bind(R.id.vehicleNumber)
        TextView VehicleCount;
        @Bind(R.id.vehcileIdHidden)
        TextView VehicleIDHiddden;


        AdapterHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

    private void Submit(String s) {
        if (!validateCarSpinner()) {
            Toast.makeText(context, "Select Brand to Continue..", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!validateMakeSpinner()) {
            Toast.makeText(context, "Select Model to Continue..", Toast.LENGTH_SHORT).show();
            return;
        }

        ProceedData(s);
    }

    private void ProceedData(final String s) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Sure Want to Delete this vehicle ??")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        DeleteVehicle((Activity) context, Integer.parseInt(s));
                        UpdateVehicle((Activity) context, Integer.parseInt(s), Integer.parseInt(myid), "", "", "", "", "");
                        dialog.cancel();
                        confirm.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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


    public void AddVehicle(Activity activity, final String type, final int modelid, final String vehicle_number) {
        VolleyHelper helper = new VolleyHelper(activity);
        helper.AddVehicle(type, modelid, vehicle_number);
//        notifyDataSetChanged();
        detailses.addChangeListener(new RealmChangeListener<RealmResults<VehicleDetails>>() {
            @Override
            public void onChange(RealmResults<VehicleDetails> element) {
                notifyDataSetChanged();
            }
        });
        /*details.addChangeListener(new RealmChangeListener() {
            @Override
            public void onChange(Object element) {
                notifyDataSetChanged();
            }
        });*/

    }

    public void UpdateVehicle(Activity activity, final int VehicleId, final int modelid, final String insuranceCompany, final String insurace_exp_date,
                              final String pollution_exp_date, final String service_exp_date, final String note) {
        VolleyHelper helper = new VolleyHelper(activity);
        helper.UpdateVehicle(VehicleId, modelid, insuranceCompany, insurace_exp_date, pollution_exp_date, service_exp_date, note);
        detailses.addChangeListener(new RealmChangeListener<RealmResults<VehicleDetails>>() {
            @Override
            public void onChange(RealmResults<VehicleDetails> element) {
                notifyDataSetChanged();
            }
        });
    }

    public void DeleteVehicle(Activity activity, final int VehicleId) {
        myApplication.showLog(TAG, String.valueOf(VehicleId));

        VolleyHelper helper = new VolleyHelper(activity);
        helper.DeleteVehicle(VehicleId);
//        temp();
        detailses.addChangeListener(new RealmChangeListener<RealmResults<VehicleDetails>>() {
            @Override
            public void onChange(RealmResults<VehicleDetails> element) {
                notifyDataSetChanged();
            }
        });
    }

    private void LodingModel() {

        // // TODO: 5/20/2016  add vishal
        RealmResults<VehicleModels> results = realm.where(VehicleModels.class).findAll();

        mySpinnerAdapterModel mySpinnerAdapterCity = new mySpinnerAdapterModel(context, results, "bike");
        ModelSpinner.setAdapter(mySpinnerAdapterCity);

        ModelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                makePosi = position;
                myid = ((TextView) view.findViewById(R.id.rowid)).getText().toString();
                Toast.makeText(context, "You have selected " + " " + myid, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(context, "You have selected Nothing ..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void LodingBrand() {

        RealmResults<VehicleModels> results = realm.where(VehicleModels.class).findAll();

        mySpinnerAdapterBrand mySpinnerAdapterCity = new mySpinnerAdapterBrand(context, results, "car");
        brandSpinner.setAdapter(mySpinnerAdapterCity);

        brandSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                carPosi = position;
                String myid = ((TextView) view.findViewById(R.id.rowid)).getText().toString();
                Toast.makeText(context, "You have selected " + " " + myid, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(context, "You have selected Nothing ..", Toast.LENGTH_SHORT).show();
            }
        });
    }

   /* public void temp() {
        RequestBody formBody = new FormBody.Builder()
                .add("id", "82")
                .build();

        Request request = new Request.Builder()
                .url(AppConfig.URL_TEMP + "/userVehicle")
                .delete(formBody)
                .addHeader("authorization", "99742a0bbcf11b9ac6b10e90b3a76f34")
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                myApplication.showLog(TAG, String.valueOf(response.code()));
                myApplication.showLog(TAG, String.valueOf(response.body().string()));

                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
            }
        });

    }*/
}
