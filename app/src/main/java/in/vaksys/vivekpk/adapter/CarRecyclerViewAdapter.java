package in.vaksys.vivekpk.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

    //    Calendar newDate;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle, null);
        viewHolder = new AdapterHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterHolder holder, int position) {
        details = detailses.get(position);

        VehicleModels vehicleModels = realm.where(VehicleModels.class).equalTo("id", details.getVehicleModelID()).findFirst();

        holder.VehicleNumber.setText(details.getVehicleNo());
//        myApplication.showLog(TAG, details.getVehicleNo());
        holder.VehicleBrand.setText(vehicleModels.getManufacturerName());
        holder.VehicleModel.setText(vehicleModels.getModel());

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

        AdapterHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public void AddVehicle(Activity activity, final String type, final int modelid, final String vehicle_number) {
        VolleyHelper helper = new VolleyHelper(activity);
        helper.AddVehicle(type, modelid, vehicle_number);
//        notifyDataSetChanged();
        details.addChangeListener(new RealmChangeListener() {
            @Override
            public void onChange(Object element) {
                notifyDataSetChanged();
            }
        });

    }

    public void UpdateVehicle(Activity activity, final int VehicleId, final int modelid, final String insuranceCompany, final String insurace_exp_date,
                              final String pollution_exp_date, final String service_exp_date, final String note) {
        VolleyHelper helper = new VolleyHelper(activity);
        helper.UpdateVehicle(VehicleId, modelid, insuranceCompany, insurace_exp_date, pollution_exp_date, service_exp_date, note);
        notifyDataSetChanged();
    }

    public void DeleteVehicle(Activity activity, final int VehicleId) {
        VolleyHelper helper = new VolleyHelper(activity);
        helper.DeleteVehicle(VehicleId);
        notifyDataSetChanged();
    }
}
