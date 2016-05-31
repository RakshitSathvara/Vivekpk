package in.vaksys.vivekpk.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.vaksys.vivekpk.R;

public class MyAccountActivity extends AppCompatActivity {

    @Bind(R.id.et_firstName)
    EditText etFirstName;
    @Bind(R.id.et_lastName)
    EditText etLastName;
    @Bind(R.id.et_emailId)
    EditText etEmailId;
    @Bind(R.id.et_mobileNumber)
    EditText etMobileNumber;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.btn_myaccount_cancel)
    Button btnMyaccountCancel;
    @Bind(R.id.btn_myaccount_save)
    Button btnMyaccountSave;
    @Bind(R.id.btn_myaccount_logout)
    Button btnMyaccountLogout;
    @Bind(R.id.btn_myaccount_deactiveAccount)
    Button btnMyaccountDeactiveAccount;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account_details);
        ButterKnife.bind(this);

        toolbar = (Toolbar) findViewById(R.id.myAccountToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


    }

    @OnClick({R.id.btn_myaccount_cancel, R.id.btn_myaccount_save, R.id.btn_myaccount_logout, R.id.btn_myaccount_deactiveAccount})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_myaccount_cancel:
                break;
            case R.id.btn_myaccount_save:
                submitForm();
                break;
            case R.id.btn_myaccount_logout:
                break;
            case R.id.btn_myaccount_deactiveAccount:
                break;
        }
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
        if (!validateNumber()) {
            return;
        }
        if (!validatePassword()) {
            return;
        }
        confirmDialog();
    }

    private void confirmDialog() {
        UpdateInfo();
    }

    private void UpdateInfo() {

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
        if (etLastName.getText().toString().trim().isEmpty()) {
            etLastName.setError(getString(R.string.err_msg_last_name));
            requestFocus(etLastName);
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

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean validateNumber() {
        if (etMobileNumber.getText().toString().trim().isEmpty()) {
            etMobileNumber.setError(getString(R.string.err_msg_number));
            requestFocus(etMobileNumber);
            return false;
        }
        if (etMobileNumber.length() != 10) {
            etMobileNumber.setError(getString(R.string.err_msg_valid_number));
            requestFocus(etMobileNumber);
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
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
