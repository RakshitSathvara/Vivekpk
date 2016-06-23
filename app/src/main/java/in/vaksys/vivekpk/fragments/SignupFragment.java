package in.vaksys.vivekpk.fragments;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.enums.SnackbarType;
import com.nispok.snackbar.listeners.ActionClickListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.activities.VerifyOtpActivity;
import in.vaksys.vivekpk.adapter.ListViewAdapter;
import in.vaksys.vivekpk.extras.AdapterCallback;
import in.vaksys.vivekpk.extras.AppConfig;
import in.vaksys.vivekpk.extras.MyApplication;
import in.vaksys.vivekpk.extras.ShowHidePasswordEditText;
import in.vaksys.vivekpk.pojo.Coutrycode;

/**
 * Created by dell980 on 5/2/2016.
 */
public class SignupFragment extends Fragment implements AdapterCallback {

    private static final String TAG = "harsh";
    @Bind(R.id.et_firstName)
    EditText etFirstName;
    @Bind(R.id.et_lasttName)
    EditText etLasttName;
    @Bind(R.id.et_emailId)
    EditText etEmailId;
    @Bind(R.id.et_code_signup)
    EditText etCode;
    @Bind(R.id.et_contactNo)
    EditText etContactNo;
    @Bind(R.id.et_password)
    ShowHidePasswordEditText etPassword;
    @Bind(R.id.btn_continue_signup)
    Button btnContinue;
    Dialog dialog;
    ListView list;
    ListViewAdapter adapter;
    EditText editsearch;
    String[] code;
    String[] countryName;
    ArrayList<Coutrycode> arraylist = new ArrayList<Coutrycode>();
    String gmail, mFname, mLname, mEmail, mCode, mContactNo, mPassword;
    private ProgressDialog pDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_signup, container, false);
        ButterKnife.bind(this, rootView);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);


        code = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        countryName = new String[]{"China", "India", "United States",
                "Indonesia", "Brazil", "Pakistan", "Nigeria", "Bangladesh",
                "Russia", "Japan"};

        etCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  ShowAlertDialogWithListview();
            }
        });



        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                submitForm();
            }
        });

        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    MyApplication.getInstance().showLog(TAG,"Enter pressed");
                    hideKeyboard();
                    submitForm();
                }
                return false;
            }
        });


     /*   getEmailid();
        getUsername();*/

        return rootView;
    }

    private void confirmDialog() {
        final Dialog confirm = new Dialog(getActivity());
        confirm.requestWindowFeature(Window.FEATURE_NO_TITLE);
        confirm.setContentView(R.layout.confirm_dialog);

        Button btnEdit = (Button) confirm.findViewById(R.id.et_context_edit);
        Button btnSend = (Button) confirm.findViewById(R.id.et_context_send);

        TextView number = (TextView) confirm.findViewById(R.id.tv_phoneNo);

        number.setText(etContactNo.getText().toString());

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm.dismiss();
                requestFocus(etContactNo);
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm.dismiss();
                getData();
            }
        });
        confirm.show();
    }

    private void getData() {
        mFname = etFirstName.getText().toString();
        mLname = etLasttName.getText().toString();
        mEmail = etEmailId.getText().toString();
        mCode = etCode.getText().toString();
        mContactNo = etContactNo.getText().toString();
        mPassword = etPassword.getText().toString();

        signUp(mFname, mLname, mEmail, mCode, mContactNo, mPassword);

    }

    private void signUp(final String mFname, final String mLname, final String mEmail, String mCode, final String mContactNo, final String mPassword) {
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_SIGNUP, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    Log.e(TAG, "onResponse: " + jObj.toString());

                    // Check for error node in json
                    if (!error) {
                        Log.e(TAG, "onResponse: " + jObj.toString());
                        String successMessage = jObj.getString("message");
                        Toast.makeText(getActivity(),
                                successMessage, Toast.LENGTH_LONG).show();

                        SharedPreferences sharedPreferences = MyApplication.getInstance().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("mobile", mContactNo);
                        editor.apply();
                        startActivity(new Intent(getActivity(), VerifyOtpActivity.class));
                        getActivity().finish();


                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(getActivity(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Login Error: " + error.getMessage());
                ErrorSnackBar();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("firstName", mFname);
                params.put("lastName", mLname);
                params.put("email", mEmail);
                params.put("password", mPassword);
                params.put("phone", mContactNo);

                return params;
            }
        };
        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    private void hideKeyboard() {
        // Check if no view has focus:
        View view1 = getActivity().getCurrentFocus();
        if (view1 != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);
        }
    }

    private void ErrorSnackBar() {
        Snackbar.with(getActivity())
                .type(SnackbarType.MULTI_LINE)
                .text("Check Internet Connection")
                .actionLabel("Done")
                .actionColor(Color.CYAN)
                .actionListener(new ActionClickListener() {
                    @Override
                    public void onActionClicked(Snackbar snackbar) {
                        startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                    }
                })
                .duration(Snackbar.SnackbarDuration.LENGTH_INDEFINITE)
                .swipeToDismiss(false)
                .show(getActivity());
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void getEmailid() {
        gmail = null;
        Account[] accounts = AccountManager.get(getActivity()).getAccountsByType("com.google");
        gmail = accounts[0].name.trim().toLowerCase();
        etEmailId.setText(gmail);
//        Log.e(TAG, "onCreateView: " + gmail);
    }

    public String getUsername() {
        AccountManager manager = AccountManager.get(MyApplication.getInstance());
        Account[] accounts = manager.getAccountsByType("com.google");
        List<String> possibleEmails = new LinkedList<String>();

        for (Account account : accounts) {
            possibleEmails.add(account.name);
        }
        if (!possibleEmails.isEmpty() && possibleEmails.get(0) != null) {
            String email = possibleEmails.get(0);
            String[] parts = email.split("@");
            if (parts.length > 0 && parts[0] != null) {
//                Log.e(TAG, "getUsername: " + parts[0]);
                etFirstName.setText(parts[0]);
                return parts[0];
            } else
                return null;
        } else
            return null;
    }

    public void ShowAlertDialogWithListview() {
        dialog = new Dialog(getActivity());
        dialog.setTitle("Choose country Code");
        dialog.setContentView(R.layout.country_list);

        list = (ListView) dialog.findViewById(R.id.list);

        for (int i = 0; i < countryName.length; i++) {
            Coutrycode wp = new Coutrycode(code[i], countryName[i]);
            // Binds all strings into an array
            arraylist.add(wp);
        }

        // Pass results to ListViewAdapter Class
        adapter = new ListViewAdapter(this, arraylist);


        // Binds the Adapter to the ListView
        list.setAdapter(adapter);

        // Locate the EditText in listview_main.xml
        editsearch = (EditText) dialog.findViewById(R.id.et_search);

        // Capture Text in EditText
        editsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                etCode.setText(parent.getSelectedItem().toString());
            }
        });
        dialog.show();
    }

    private void submitForm() {
        if (!validateFirstName()) {
            return;
        }
        if (!validateLastName()) {
            return;
        }
        if (!validateEmail()) {
            return;
        }
//        if (!validateCode()) {
//            return;
//        }
        if (!validateNumber()) {
            return;
        }
        if (!validatePassword()) {
            return;
        }
        confirmDialog();

        /*getData();
        uploadData();*/
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

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private boolean validateFirstName() {
        if (etFirstName.getText().toString().trim().isEmpty()) {
            etFirstName.setError(getString(R.string.err_msg_first_name));
            requestFocus(etFirstName);
            return false;
        } else {
            return true;
        }
    }

    private boolean validateLastName() {
        if (etLasttName.getText().toString().trim().isEmpty()) {
            etLasttName.setError(getString(R.string.err_msg_last_name));
            requestFocus(etLasttName);
            return false;
        } else {
            return true;
        }
    }

    private boolean validateEmail() {
        String email = etEmailId.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            etEmailId.setError(getString(R.string.err_msg_email));
            requestFocus(etEmailId);
            return false;
        } else {
            return true;
        }
    }

    private boolean validateCode() {

        if (etCode.getText().toString().trim().isEmpty()) {
            etCode.setError(getString(R.string.err_msg_code));
            requestFocus(etCode);
            return false;
        } else {
            return true;
        }
    }

    private boolean validateNumber() {
        if (etContactNo.getText().toString().trim().isEmpty()) {
            etContactNo.setError(getString(R.string.err_msg_number));
            requestFocus(etContactNo);
            return false;
        }
        if (etContactNo.length() != 10) {
            etContactNo.setError(getString(R.string.err_msg_valid_number));
            requestFocus(etContactNo);
            return false;
        } else {
            return true;
        }
    }

    private boolean validatePassword() {
        if (etPassword.getText().toString().trim().isEmpty()) {
            etPassword.setError(getString(R.string.err_msg_password));
            requestFocus(etPassword);
            return false;
        }
        if (etPassword.length() < 6) {
            etPassword.setError(getString(R.string.err_valid_password));
            requestFocus(etPassword);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onMethodCallback() {
        dialog.dismiss();
        SharedPreferences sharedPreferences = MyApplication.getInstance().getSharedPreferences("harsh", Context.MODE_PRIVATE);
        //Toast.makeText(MyApplication.getInstance(), "yeyyyyy" + sharedPreferences.getString("value", "fuck"), Toast.LENGTH_LONG).show();
        etCode.setText(sharedPreferences.getString("value", ""));
    }
}
