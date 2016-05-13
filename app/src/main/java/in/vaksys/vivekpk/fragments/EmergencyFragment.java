package in.vaksys.vivekpk.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import in.vaksys.vivekpk.R;

/**
 * Created by dell980 on 5/7/2016.
 */
public class EmergencyFragment extends Fragment {

    private Button btnContactOne, btnContactTwo;
    private LinearLayout linearLayoutOne, linearContactTwo;
    private ImageView deleteContactOne;
    private TextView contactTvOneName, contactTvTwoNumber;
    
    public static EmergencyFragment newInstance(int index) {
        EmergencyFragment fragment = new EmergencyFragment();
        Bundle b = new Bundle();
        b.putInt("index", index);
        fragment.setArguments(b);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_emergency, container, false);

        btnContactOne = (Button) rootView.findViewById(R.id.btn_contactOne);
        btnContactTwo = (Button) rootView.findViewById(R.id.btn_contactTwo);
        linearLayoutOne = (LinearLayout) rootView.findViewById(R.id.linear_contactOne);
        linearContactTwo = (LinearLayout) rootView.findViewById(R.id.linear_contactTwo);

        deleteContactOne = (ImageView) rootView.findViewById(R.id.img_deleteContact);

        contactTvOneName = (TextView) rootView.findViewById(R.id.tv_contactName);
        contactTvTwoNumber = (TextView) rootView.findViewById(R.id.tv_contactNumber);

        btnContactOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("emergencyFragment", "onClick: ");
                /*btnContactOne.setVisibility(View.GONE);
                linearLayoutOne.setVisibility(View.VISIBLE);*/
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(intent, 1);
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

        String phoneNo = null;
        String displayName = null;
        Uri uri = data.getData();
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();


        int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        phoneNo = cursor.getString(phoneIndex);
        contactTvTwoNumber.setText(phoneNo);

        int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        displayName = cursor.getString(nameIndex);
        contactTvOneName.setText(displayName);
        //int nameIndex1 = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI);
        btnContactOne.setVisibility(View.GONE);
        linearLayoutOne.setVisibility(View.VISIBLE);

        btnContactOne.setVisibility(View.GONE);
        linearLayoutOne.setVisibility(View.VISIBLE);

        Log.e("Activity", "onClick: " + phoneNo + "And : " + displayName);
    }
}
