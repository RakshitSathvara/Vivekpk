package in.vaksys.vivekpk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.dbPojo.InsuranceCompanies;
import io.realm.RealmResults;

/**
 * Created by Harsh on 18-02-2016.
 */
public class mySpinnerAdapterInsurance extends BaseAdapter {

    private static final String TAG = "mySpinnerAdapterBrand";
    private static LayoutInflater inflater = null;
    private final Context context;
    //    private final List<ModelPojo.ResultEntity> cityEntityList;
    private final RealmResults<InsuranceCompanies> insuranceCompanies;
    String mType;

    public mySpinnerAdapterInsurance(Context context, RealmResults<InsuranceCompanies> insuranceCompanies, String type) {
        this.context = context;
//        this.cityEntityList = cityEntityList;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mType = type;
        this.insuranceCompanies = insuranceCompanies;
    }

    @Override
    public int getCount() {
        return (null != insuranceCompanies ? insuranceCompanies.size() : 0);
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
        InsuranceCompanies insuranceCompanies1 = insuranceCompanies.get(position);

        if (convertView == null) {
            row = inflater.inflate(R.layout.spinner_layout_model, null);
            holder = new Holder(row);
            row.setTag(holder);
        } else {
            row = convertView;
            holder = (Holder) row.getTag();
        }

        holder.mytext.setText(insuranceCompanies1.getInsuranceName());
        holder.idtext.setText(String.valueOf(insuranceCompanies1.getInsuranceId()));

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
