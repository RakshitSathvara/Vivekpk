package in.vaksys.vivekpk.adapter;

/**
 * Created by dell980 on 5/10/2016.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.extras.AdapterCallback;
import in.vaksys.vivekpk.extras.MyApplication;
import in.vaksys.vivekpk.pojo.Coutrycode;

public class ListViewAdapter extends BaseAdapter {

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<Coutrycode> worldpopulationlist = null;

    private ArrayList<Coutrycode> arraylist;
    private AdapterCallback mAdapterCallback;

    public ListViewAdapter(Fragment fragment,
                           List<Coutrycode> worldpopulationlist) {
        mContext = MyApplication.getInstance();
        this.worldpopulationlist = worldpopulationlist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Coutrycode>();
        this.arraylist.addAll(worldpopulationlist);
        try {
            this.mAdapterCallback = ((AdapterCallback) fragment);
        } catch (ClassCastException e) {
            throw new ClassCastException("Fragment must implement AdapterCallback.");
        }
    }

    @Override
    public int getCount() {
        return worldpopulationlist.size();
    }

    @Override
    public Coutrycode getItem(int position) {
        return worldpopulationlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.list_row, null);
            // Locate the TextViews in listview_item.xml
            holder.code = (TextView) view.findViewById(R.id.code);
            holder.countryName = (TextView) view.findViewById(R.id.countryName);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.code.setText(worldpopulationlist.get(position).getCode());
        holder.countryName.setText(worldpopulationlist.get(position).getCountryName());
        // Listen for ListView Item Click
        view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                //Toast.makeText(mContext, worldpopulationlist.get(position).getCode(), Toast.LENGTH_SHORT).show();

                SharedPreferences sharedPreferences = MyApplication.getInstance().getSharedPreferences("harsh", Context.MODE_PRIVATE);

                SharedPreferences.Editor edit = sharedPreferences.edit();

                edit.putString("value", worldpopulationlist.get(position).getCode());
                edit.apply();

                mAdapterCallback.onMethodCallback();

            }
        });

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        worldpopulationlist.clear();
        if (charText.length() == 0) {
            worldpopulationlist.addAll(arraylist);
        } else {
            for (Coutrycode wp : arraylist) {
                if (wp.getCountryName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    worldpopulationlist.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder {
        TextView code;
        TextView countryName;
    }

}