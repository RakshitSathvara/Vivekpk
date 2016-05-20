package in.vaksys.vivekpk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.dbPojo.VehicleModels;
import io.realm.RealmResults;

/**
 * Created by Harsh on 18-02-2016.
 */
public class mySpinnerAdapterModel extends BaseAdapter {

    private final Context context;
    //    private final List<ModelPojo.ResultEntity> cityEntityList;
    private final RealmResults<VehicleModels> vehicleModelses;
    private static LayoutInflater inflater = null;
    String mType;
    private static final String TAG = "mySpinnerAdapterModel";

    public mySpinnerAdapterModel(Context context, RealmResults<VehicleModels> vehicleModelses, String type) {
        this.context = context;
//        this.cityEntityList = cityEntityList;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mType = type;
        this.vehicleModelses = vehicleModelses;
    }

    @Override
    public int getCount() {
        return (null != vehicleModelses ? vehicleModelses.size() : 0);
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        final View row;
        VehicleModels vehicleModels = vehicleModelses.get(position);


//        final ModelPojo.ResultEntity item = cityEntityList.get(position);

        if (convertView == null) {
            row = inflater.inflate(R.layout.spinner_layout_model, null);
            holder = new Holder(row);
            row.setTag(holder);
        } else {
            row = convertView;
            holder = (Holder) row.getTag();
        }
//        Log.e(TAG, "getView: " + item.getType());

        holder.mytext.setText(vehicleModels.getManufacturerName());


        if (mType.equalsIgnoreCase("car") || mType.equalsIgnoreCase("")) {
//            Log.e(TAG, "getView:  car  " + item.getType());
            if (vehicleModels.getType().equalsIgnoreCase("car")) {
//                Log.e(TAG, "getView:  car 1111 " + item.getType());

                holder.mytext.setText(vehicleModels.getManufacturerName());
                holder.idtext.setText(String.valueOf(vehicleModels.getId()));
            }
        }
        if (mType.equalsIgnoreCase("bike") ) {
//            Log.e(TAG, "getView:  bike" + item.getType());
            if (vehicleModels.getType().equalsIgnoreCase("car")) {      // here i have to change bike when real api comes . // TODO: 18-05-2016
//                Log.e(TAG, "getView:  bike 1111 " + item.getType());

                holder.mytext.setText(vehicleModels.getManufacturerName());
                holder.idtext.setText(String.valueOf(vehicleModels.getId()));
            }
        }
        return row;

    }

    public class Holder {
        TextView mytext, idtext;

        Holder(View v) {
            mytext = (TextView) v.findViewById(R.id.rowText);
            idtext = (TextView) v.findViewById(R.id.rowid);
        }

    }
}
