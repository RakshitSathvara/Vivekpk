package in.vaksys.vivekpk.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import in.vaksys.vivekpk.R;

public class RefferalCodeActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private ImageView arrowOne, arrowTwo;
    View one, two, three, four, five, six, seven, eight, nine, ten, aa, bb, cc, dd, ee;
    LinearLayout helpClick, myAccountClick, registeringVehicleClick, emergencyContactClick, setReminderClick,
            documentListClick, insuranceReminderClick, reminderEmissionClick, reminderServiceClick,
            docDrivingLicenseClick, docInsuranseClick, docEmissionClick, docBillsClick, registerVehicleOneClick,
            regVehicleAlreadyRegister, regClaimVehicle, regDeleteVehicle, emergencyContact, emergencyAddContact,
            emergencyNotReceiving, reminderOne, reminderTwo, reminderThree, reminderFour, reminderFive, reminderSix,
            emissionOne, emissionTwo, emissionThree, emissionFour, emissionFive, serviceOne, serviceTwo, serviceThree,
            servicefour, serviceFive, servicesix, drivingOne, drivingTwo, drivingThree, drivingFour, drivingFive,
            drivingSix, doc_insurance_one, doc_insurance_two, doc_insurance_three, doc_insurance_four, doc_insurance_five,
            doc_insurance_six, doc_emission_one, doc_emission_two, doc_emission_three, doc_emission_four, doc_emission_five,
            doc_emission_six, doc_bill_one, doc_bill_two, doc_bill_three, doc_bill_four, doc_bill_five,
            doc_bill_six, documentOne, documentTwo, documentThree, documentFour, documentFive, documentSix;
    TextView txtOne, txtTwo, txtZero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refferal_code);

        toolbar = (Toolbar) findViewById(R.id.refferalToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        arrowOne = (ImageView) findViewById(R.id.arrowOne);
        arrowTwo = (ImageView) findViewById(R.id.arrowTwo);

        helpClick = (LinearLayout) findViewById(R.id.linear_help_helpCenter);
        myAccountClick = (LinearLayout) findViewById(R.id.linear_myAccount);
        registeringVehicleClick = (LinearLayout) findViewById(R.id.linear_RegisteringVehicle);
        emergencyContactClick = (LinearLayout) findViewById(R.id.linear_EmergencyContact);
        setReminderClick = (LinearLayout) findViewById(R.id.linear_setReminder);
        insuranceReminderClick = (LinearLayout) findViewById(R.id.linear_reminder_insurance);
        reminderEmissionClick = (LinearLayout) findViewById(R.id.linear_reminder_emission);
        reminderServiceClick = (LinearLayout) findViewById(R.id.linear_reminder_service);
        documentListClick = (LinearLayout) findViewById(R.id.liner_documentList);
        docDrivingLicenseClick = (LinearLayout) findViewById(R.id.linear_doc_drivingLicense);
        docInsuranseClick = (LinearLayout) findViewById(R.id.linear_doc_insuranse);
        docEmissionClick = (LinearLayout) findViewById(R.id.linear_doc_emission);
        docBillsClick = (LinearLayout) findViewById(R.id.linear_doc_bills);


        registerVehicleOneClick = (LinearLayout) findViewById(R.id.linear_reg_registerVehicle);
        regVehicleAlreadyRegister = (LinearLayout) findViewById(R.id.linear_reg_vehicleAlreadyRegister);
        regClaimVehicle = (LinearLayout) findViewById(R.id.linear_reg_claimVehicle);
        regDeleteVehicle = (LinearLayout) findViewById(R.id.linear_reg_deleteVehicle);
        emergencyContact = (LinearLayout) findViewById(R.id.linear_emergency_contact);
        emergencyAddContact = (LinearLayout) findViewById(R.id.linear_emergency_addContact);
        emergencyNotReceiving = (LinearLayout) findViewById(R.id.linear_emergency_notReceiving);
        reminderOne = (LinearLayout) findViewById(R.id.reminder_one);
        reminderTwo = (LinearLayout) findViewById(R.id.reminder_two);
        reminderThree = (LinearLayout) findViewById(R.id.reminder_three);
        reminderFour = (LinearLayout) findViewById(R.id.reminder_four);
        reminderFive = (LinearLayout) findViewById(R.id.reminder_five);
        reminderSix = (LinearLayout) findViewById(R.id.reminder_six);

        emissionOne = (LinearLayout) findViewById(R.id.emission_one);
        emissionTwo = (LinearLayout) findViewById(R.id.emission_two);
        emissionThree = (LinearLayout) findViewById(R.id.emission_three);
        emissionFour = (LinearLayout) findViewById(R.id.emission_four);
        emissionFive = (LinearLayout) findViewById(R.id.emission_five);

        serviceOne = (LinearLayout) findViewById(R.id.service_one);
        serviceTwo = (LinearLayout) findViewById(R.id.service_two);
        serviceThree = (LinearLayout) findViewById(R.id.service_three);
        servicefour = (LinearLayout) findViewById(R.id.service_four);
        serviceFive = (LinearLayout) findViewById(R.id.service_five);
        servicesix = (LinearLayout) findViewById(R.id.service_six);

        drivingOne = (LinearLayout) findViewById(R.id.driving_one);
        drivingTwo = (LinearLayout) findViewById(R.id.driving_two);
        drivingThree = (LinearLayout) findViewById(R.id.driving_three);
        drivingFour = (LinearLayout) findViewById(R.id.driving_four);
        drivingFive = (LinearLayout) findViewById(R.id.driving_five);
        drivingSix = (LinearLayout) findViewById(R.id.driving_six);

        doc_insurance_one = (LinearLayout) findViewById(R.id.doc_insurance_one);
        doc_insurance_two = (LinearLayout) findViewById(R.id.doc_insurance_two);
        doc_insurance_three = (LinearLayout) findViewById(R.id.doc_insurance_three);
        doc_insurance_four = (LinearLayout) findViewById(R.id.doc_insurance_four);
        doc_insurance_five = (LinearLayout) findViewById(R.id.doc_insurance_five);
        doc_insurance_six = (LinearLayout) findViewById(R.id.doc_insurance_six);

        doc_emission_one = (LinearLayout) findViewById(R.id.doc_emission_one);
        doc_emission_two = (LinearLayout) findViewById(R.id.doc_emission_two);
        doc_emission_three = (LinearLayout) findViewById(R.id.doc_emission_three);
        doc_emission_four = (LinearLayout) findViewById(R.id.doc_emission_four);
        doc_emission_five = (LinearLayout) findViewById(R.id.doc_emission_five);
        doc_emission_six = (LinearLayout) findViewById(R.id.doc_emission_six);

        doc_bill_one = (LinearLayout) findViewById(R.id.doc_bill_one);
        doc_bill_two = (LinearLayout) findViewById(R.id.doc_bill_two);
        doc_bill_three = (LinearLayout) findViewById(R.id.doc_bill_three);
        doc_bill_four = (LinearLayout) findViewById(R.id.doc_bill_four);
        doc_bill_five = (LinearLayout) findViewById(R.id.doc_bill_five);
        doc_bill_six = (LinearLayout) findViewById(R.id.doc_bill_six);

        documentOne = (LinearLayout) findViewById(R.id.document_one);
        documentTwo = (LinearLayout) findViewById(R.id.document_two);
        documentThree = (LinearLayout) findViewById(R.id.document_three);
        documentFour = (LinearLayout) findViewById(R.id.document_four);
        documentFive = (LinearLayout) findViewById(R.id.document_five);
        documentSix = (LinearLayout) findViewById(R.id.document_six);

        one = findViewById(R.id.helpCenter);
        two = findViewById(R.id.myAccountView);
        three = findViewById(R.id.help_register_vehicle);
        four = findViewById(R.id.help_emergency_contact);
        five = findViewById(R.id.help_set_reminder);
        six = findViewById(R.id.help_document_list);
        seven = findViewById(R.id.troubleshooting);
        eight = findViewById(R.id.insurance_reminder);
        nine = findViewById(R.id.emission_reminder);
        ten = findViewById(R.id.service_reminder);
        aa = findViewById(R.id.accountClick);
        bb = findViewById(R.id.driving_License);
        cc = findViewById(R.id.document_list_insurance);
        dd = findViewById(R.id.document_list_emission);
        ee = findViewById(R.id.document_list_bills);
        txtOne = (TextView) findViewById(R.id.pos_one);
        txtTwo = (TextView) findViewById(R.id.pos_two);
        txtZero = (TextView) findViewById(R.id.txtZero);

        two.setVisibility(View.GONE);
        three.setVisibility(View.GONE);
        four.setVisibility(View.GONE);
        five.setVisibility(View.GONE);
        six.setVisibility(View.GONE);
        seven.setVisibility(View.GONE);
        eight.setVisibility(View.GONE);
        nine.setVisibility(View.GONE);
        ten.setVisibility(View.GONE);
        aa.setVisibility(View.GONE);
        bb.setVisibility(View.GONE);
        cc.setVisibility(View.GONE);
        dd.setVisibility(View.GONE);
        ee.setVisibility(View.GONE);
        txtOne.setText("");
        txtTwo.setText("");
        txtZero.setText("Help Center");
        arrowOne.setVisibility(View.GONE);
        arrowTwo.setVisibility(View.GONE);


        helpClick.setOnClickListener(this);
        myAccountClick.setOnClickListener(this);
        registeringVehicleClick.setOnClickListener(this);
        emergencyContactClick.setOnClickListener(this);
        setReminderClick.setOnClickListener(this);
        documentListClick.setOnClickListener(this);
        insuranceReminderClick.setOnClickListener(this);
        reminderEmissionClick.setOnClickListener(this);
        reminderServiceClick.setOnClickListener(this);
        docDrivingLicenseClick.setOnClickListener(this);
        docInsuranseClick.setOnClickListener(this);
        docEmissionClick.setOnClickListener(this);
        docBillsClick.setOnClickListener(this);
        registerVehicleOneClick.setOnClickListener(this);
        regVehicleAlreadyRegister.setOnClickListener(this);
        regClaimVehicle.setOnClickListener(this);
        regDeleteVehicle.setOnClickListener(this);
        emergencyContact.setOnClickListener(this);
        emergencyAddContact.setOnClickListener(this);
        emergencyNotReceiving.setOnClickListener(this);
        reminderOne.setOnClickListener(this);
        reminderTwo.setOnClickListener(this);
        reminderThree.setOnClickListener(this);
        reminderFour.setOnClickListener(this);
        reminderFive.setOnClickListener(this);
        reminderSix.setOnClickListener(this);
        emissionOne.setOnClickListener(this);
        emissionTwo.setOnClickListener(this);
        emissionThree.setOnClickListener(this);
        emissionFour.setOnClickListener(this);
        emissionFive.setOnClickListener(this);
        serviceOne.setOnClickListener(this);
        serviceTwo.setOnClickListener(this);
        serviceThree.setOnClickListener(this);
        servicefour.setOnClickListener(this);
        serviceFive.setOnClickListener(this);
        servicesix.setOnClickListener(this);
        drivingOne.setOnClickListener(this);
        drivingTwo.setOnClickListener(this);
        drivingThree.setOnClickListener(this);
        drivingFour.setOnClickListener(this);
        drivingFive.setOnClickListener(this);
        drivingSix.setOnClickListener(this);
        doc_insurance_one.setOnClickListener(this);
        doc_insurance_two.setOnClickListener(this);
        doc_insurance_three.setOnClickListener(this);
        doc_insurance_four.setOnClickListener(this);
        doc_insurance_five.setOnClickListener(this);
        doc_insurance_six.setOnClickListener(this);
        doc_emission_one.setOnClickListener(this);
        doc_emission_two.setOnClickListener(this);
        doc_emission_three.setOnClickListener(this);
        doc_emission_four.setOnClickListener(this);
        doc_emission_five.setOnClickListener(this);
        doc_emission_six.setOnClickListener(this);
        doc_bill_one.setOnClickListener(this);
        doc_bill_two.setOnClickListener(this);
        doc_bill_three.setOnClickListener(this);
        doc_bill_four.setOnClickListener(this);
        doc_bill_five.setOnClickListener(this);
        doc_bill_six.setOnClickListener(this);
        documentOne.setOnClickListener(this);
        documentTwo.setOnClickListener(this);
        documentThree.setOnClickListener(this);
        documentFour.setOnClickListener(this);
        documentFive.setOnClickListener(this);
        documentSix.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(RefferalCodeActivity.this, HomeActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear_help_helpCenter:
                one.setVisibility(View.GONE);
                two.setVisibility(View.VISIBLE);
                three.setVisibility(View.GONE);
                four.setVisibility(View.GONE);
                five.setVisibility(View.GONE);
                six.setVisibility(View.GONE);
                seven.setVisibility(View.GONE);
                eight.setVisibility(View.GONE);
                nine.setVisibility(View.GONE);
                ten.setVisibility(View.GONE);
                aa.setVisibility(View.GONE);
                bb.setVisibility(View.GONE);
                cc.setVisibility(View.GONE);
                dd.setVisibility(View.GONE);
                ee.setVisibility(View.GONE);
                txtOne.setText("");
                txtTwo.setText("");
                break;

            case R.id.linear_myAccount:
                one.setVisibility(View.GONE);
                two.setVisibility(View.GONE);
                three.setVisibility(View.GONE);
                four.setVisibility(View.GONE);
                five.setVisibility(View.GONE);
                six.setVisibility(View.GONE);
                seven.setVisibility(View.GONE);
                eight.setVisibility(View.GONE);
                nine.setVisibility(View.GONE);
                ten.setVisibility(View.GONE);
                aa.setVisibility(View.VISIBLE);
                bb.setVisibility(View.GONE);
                cc.setVisibility(View.GONE);
                dd.setVisibility(View.GONE);
                ee.setVisibility(View.GONE);
                txtOne.setText("My Account");
                txtTwo.setText("");
                arrowOne.setVisibility(View.VISIBLE);
                arrowTwo.setVisibility(View.GONE);

                txtZero.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        one.setVisibility(View.VISIBLE);
                        two.setVisibility(View.GONE);
                        three.setVisibility(View.GONE);
                        four.setVisibility(View.GONE);
                        five.setVisibility(View.GONE);
                        six.setVisibility(View.GONE);
                        seven.setVisibility(View.GONE);
                        eight.setVisibility(View.GONE);
                        nine.setVisibility(View.GONE);
                        ten.setVisibility(View.GONE);
                        aa.setVisibility(View.GONE);
                        bb.setVisibility(View.GONE);
                        cc.setVisibility(View.GONE);
                        dd.setVisibility(View.GONE);
                        ee.setVisibility(View.GONE);
                        txtOne.setText("");
                        txtTwo.setText("");
                        arrowOne.setVisibility(View.GONE);
                        arrowTwo.setVisibility(View.GONE);
                    }
                });
                break;

            case R.id.linear_RegisteringVehicle:
                one.setVisibility(View.GONE);
                two.setVisibility(View.GONE);
                three.setVisibility(View.VISIBLE);
                four.setVisibility(View.GONE);
                five.setVisibility(View.GONE);
                six.setVisibility(View.GONE);
                seven.setVisibility(View.GONE);
                eight.setVisibility(View.GONE);
                nine.setVisibility(View.GONE);
                ten.setVisibility(View.GONE);
                aa.setVisibility(View.GONE);
                bb.setVisibility(View.GONE);
                cc.setVisibility(View.GONE);
                dd.setVisibility(View.GONE);
                ee.setVisibility(View.GONE);
                txtOne.setText("Register Vehicle");
                txtTwo.setText("");
                arrowOne.setVisibility(View.VISIBLE);
                arrowTwo.setVisibility(View.GONE);

                txtZero.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        one.setVisibility(View.VISIBLE);
                        two.setVisibility(View.GONE);
                        three.setVisibility(View.GONE);
                        four.setVisibility(View.GONE);
                        five.setVisibility(View.GONE);
                        six.setVisibility(View.GONE);
                        seven.setVisibility(View.GONE);
                        eight.setVisibility(View.GONE);
                        nine.setVisibility(View.GONE);
                        ten.setVisibility(View.GONE);
                        aa.setVisibility(View.GONE);
                        bb.setVisibility(View.GONE);
                        cc.setVisibility(View.GONE);
                        dd.setVisibility(View.GONE);
                        ee.setVisibility(View.GONE);
                        txtOne.setText("");
                        txtTwo.setText("");
                        arrowOne.setVisibility(View.GONE);
                        arrowTwo.setVisibility(View.GONE);
                    }
                });
                break;

            case R.id.linear_EmergencyContact:
                one.setVisibility(View.GONE);
                two.setVisibility(View.GONE);
                three.setVisibility(View.GONE);
                four.setVisibility(View.VISIBLE);
                five.setVisibility(View.GONE);
                six.setVisibility(View.GONE);
                seven.setVisibility(View.GONE);
                eight.setVisibility(View.GONE);
                nine.setVisibility(View.GONE);
                ten.setVisibility(View.GONE);
                aa.setVisibility(View.GONE);
                bb.setVisibility(View.GONE);
                cc.setVisibility(View.GONE);
                dd.setVisibility(View.GONE);
                ee.setVisibility(View.GONE);
                txtOne.setText("Emergency Contact");
                txtTwo.setText("");
                arrowOne.setVisibility(View.VISIBLE);
                arrowTwo.setVisibility(View.GONE);

                txtZero.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        one.setVisibility(View.VISIBLE);
                        two.setVisibility(View.GONE);
                        three.setVisibility(View.GONE);
                        four.setVisibility(View.GONE);
                        five.setVisibility(View.GONE);
                        six.setVisibility(View.GONE);
                        seven.setVisibility(View.GONE);
                        eight.setVisibility(View.GONE);
                        nine.setVisibility(View.GONE);
                        ten.setVisibility(View.GONE);
                        aa.setVisibility(View.GONE);
                        bb.setVisibility(View.GONE);
                        cc.setVisibility(View.GONE);
                        dd.setVisibility(View.GONE);
                        ee.setVisibility(View.GONE);
                        txtOne.setText("");
                        txtTwo.setText("");
                        arrowOne.setVisibility(View.GONE);
                        arrowTwo.setVisibility(View.GONE);
                    }
                });
                break;
            case R.id.linear_setReminder:
                one.setVisibility(View.GONE);
                two.setVisibility(View.GONE);
                three.setVisibility(View.GONE);
                four.setVisibility(View.GONE);
                five.setVisibility(View.VISIBLE);
                six.setVisibility(View.GONE);
                seven.setVisibility(View.GONE);
                eight.setVisibility(View.GONE);
                nine.setVisibility(View.GONE);
                ten.setVisibility(View.GONE);
                aa.setVisibility(View.GONE);
                bb.setVisibility(View.GONE);
                cc.setVisibility(View.GONE);
                dd.setVisibility(View.GONE);
                ee.setVisibility(View.GONE);
                txtOne.setText("Set Reminder");
                txtTwo.setText("");
                arrowOne.setVisibility(View.VISIBLE);
                arrowTwo.setVisibility(View.GONE);

                txtZero.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        one.setVisibility(View.VISIBLE);
                        two.setVisibility(View.GONE);
                        three.setVisibility(View.GONE);
                        four.setVisibility(View.GONE);
                        five.setVisibility(View.GONE);
                        six.setVisibility(View.GONE);
                        seven.setVisibility(View.GONE);
                        eight.setVisibility(View.GONE);
                        nine.setVisibility(View.GONE);
                        ten.setVisibility(View.GONE);
                        aa.setVisibility(View.GONE);
                        bb.setVisibility(View.GONE);
                        cc.setVisibility(View.GONE);
                        dd.setVisibility(View.GONE);
                        ee.setVisibility(View.GONE);
                        txtOne.setText("");
                        txtTwo.setText("");
                        arrowOne.setVisibility(View.GONE);
                        arrowTwo.setVisibility(View.GONE);
                    }
                });
                break;
            case R.id.liner_documentList:
                one.setVisibility(View.GONE);
                two.setVisibility(View.GONE);
                three.setVisibility(View.GONE);
                four.setVisibility(View.GONE);
                five.setVisibility(View.GONE);
                six.setVisibility(View.VISIBLE);
                seven.setVisibility(View.GONE);
                eight.setVisibility(View.GONE);
                nine.setVisibility(View.GONE);
                ten.setVisibility(View.GONE);
                aa.setVisibility(View.GONE);
                bb.setVisibility(View.GONE);
                cc.setVisibility(View.GONE);
                dd.setVisibility(View.GONE);
                ee.setVisibility(View.GONE);
                txtOne.setText("Document List");
                txtTwo.setText("");
                arrowOne.setVisibility(View.VISIBLE);
                arrowTwo.setVisibility(View.GONE);

                txtZero.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        one.setVisibility(View.VISIBLE);
                        two.setVisibility(View.GONE);
                        three.setVisibility(View.GONE);
                        four.setVisibility(View.GONE);
                        five.setVisibility(View.GONE);
                        six.setVisibility(View.GONE);
                        seven.setVisibility(View.GONE);
                        eight.setVisibility(View.GONE);
                        nine.setVisibility(View.GONE);
                        ten.setVisibility(View.GONE);
                        aa.setVisibility(View.GONE);
                        bb.setVisibility(View.GONE);
                        cc.setVisibility(View.GONE);
                        dd.setVisibility(View.GONE);
                        ee.setVisibility(View.GONE);
                        txtOne.setText("");
                        txtTwo.setText("");
                        arrowOne.setVisibility(View.GONE);
                        arrowTwo.setVisibility(View.GONE);
                    }
                });
                break;
            case R.id.linear_reminder_insurance:
                one.setVisibility(View.GONE);
                two.setVisibility(View.GONE);
                three.setVisibility(View.GONE);
                four.setVisibility(View.GONE);
                five.setVisibility(View.GONE);
                six.setVisibility(View.GONE);
                seven.setVisibility(View.GONE);
                eight.setVisibility(View.VISIBLE);
                nine.setVisibility(View.GONE);
                ten.setVisibility(View.GONE);
                aa.setVisibility(View.GONE);
                bb.setVisibility(View.GONE);
                cc.setVisibility(View.GONE);
                dd.setVisibility(View.GONE);
                ee.setVisibility(View.GONE);
                txtOne.setText("Set Reminder");
                txtTwo.setText("Insurance Reminder");
                arrowOne.setVisibility(View.VISIBLE);
                arrowTwo.setVisibility(View.VISIBLE);

                txtZero.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        one.setVisibility(View.VISIBLE);
                        two.setVisibility(View.GONE);
                        three.setVisibility(View.GONE);
                        four.setVisibility(View.GONE);
                        five.setVisibility(View.GONE);
                        six.setVisibility(View.GONE);
                        seven.setVisibility(View.GONE);
                        eight.setVisibility(View.GONE);
                        nine.setVisibility(View.GONE);
                        ten.setVisibility(View.GONE);
                        aa.setVisibility(View.GONE);
                        bb.setVisibility(View.GONE);
                        cc.setVisibility(View.GONE);
                        dd.setVisibility(View.GONE);
                        ee.setVisibility(View.GONE);
                        txtOne.setText("");
                        txtTwo.setText("");
                        arrowOne.setVisibility(View.GONE);
                        arrowTwo.setVisibility(View.GONE);
                    }
                });

                txtOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        one.setVisibility(View.GONE);
                        two.setVisibility(View.GONE);
                        three.setVisibility(View.GONE);
                        four.setVisibility(View.GONE);
                        five.setVisibility(View.VISIBLE);
                        six.setVisibility(View.GONE);
                        seven.setVisibility(View.GONE);
                        eight.setVisibility(View.GONE);
                        nine.setVisibility(View.GONE);
                        ten.setVisibility(View.GONE);
                        aa.setVisibility(View.GONE);
                        bb.setVisibility(View.GONE);
                        cc.setVisibility(View.GONE);
                        dd.setVisibility(View.GONE);
                        ee.setVisibility(View.GONE);
                        txtOne.setText("Set Reminder");
                        txtTwo.setText("");
                        arrowOne.setVisibility(View.VISIBLE);
                        arrowTwo.setVisibility(View.GONE);
                    }
                });
                break;

            case R.id.linear_reminder_emission:
                one.setVisibility(View.GONE);
                two.setVisibility(View.GONE);
                three.setVisibility(View.GONE);
                four.setVisibility(View.GONE);
                five.setVisibility(View.GONE);
                six.setVisibility(View.GONE);
                seven.setVisibility(View.GONE);
                eight.setVisibility(View.GONE);
                nine.setVisibility(View.VISIBLE);
                ten.setVisibility(View.GONE);
                aa.setVisibility(View.GONE);
                bb.setVisibility(View.GONE);
                cc.setVisibility(View.GONE);
                dd.setVisibility(View.GONE);
                ee.setVisibility(View.GONE);
                txtOne.setText("Set Reminder");
                txtTwo.setText("Emission Reminder");
                arrowOne.setVisibility(View.VISIBLE);
                arrowTwo.setVisibility(View.VISIBLE);

                txtZero.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        one.setVisibility(View.VISIBLE);
                        two.setVisibility(View.GONE);
                        three.setVisibility(View.GONE);
                        four.setVisibility(View.GONE);
                        five.setVisibility(View.GONE);
                        six.setVisibility(View.GONE);
                        seven.setVisibility(View.GONE);
                        eight.setVisibility(View.GONE);
                        nine.setVisibility(View.GONE);
                        ten.setVisibility(View.GONE);
                        aa.setVisibility(View.GONE);
                        bb.setVisibility(View.GONE);
                        cc.setVisibility(View.GONE);
                        dd.setVisibility(View.GONE);
                        ee.setVisibility(View.GONE);
                        txtOne.setText("");
                        txtTwo.setText("");
                        arrowOne.setVisibility(View.GONE);
                        arrowTwo.setVisibility(View.GONE);
                    }
                });

                txtOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        one.setVisibility(View.GONE);
                        two.setVisibility(View.GONE);
                        three.setVisibility(View.GONE);
                        four.setVisibility(View.GONE);
                        five.setVisibility(View.VISIBLE);
                        six.setVisibility(View.GONE);
                        seven.setVisibility(View.GONE);
                        eight.setVisibility(View.GONE);
                        nine.setVisibility(View.GONE);
                        ten.setVisibility(View.GONE);
                        aa.setVisibility(View.GONE);
                        bb.setVisibility(View.GONE);
                        cc.setVisibility(View.GONE);
                        dd.setVisibility(View.GONE);
                        ee.setVisibility(View.GONE);
                        txtOne.setText("Set Reminder");
                        txtTwo.setText("");
                        arrowOne.setVisibility(View.VISIBLE);
                        arrowTwo.setVisibility(View.GONE);
                    }
                });
                break;
            case R.id.linear_reminder_service:
                one.setVisibility(View.GONE);
                two.setVisibility(View.GONE);
                three.setVisibility(View.GONE);
                four.setVisibility(View.GONE);
                five.setVisibility(View.GONE);
                six.setVisibility(View.GONE);
                seven.setVisibility(View.GONE);
                eight.setVisibility(View.GONE);
                nine.setVisibility(View.GONE);
                ten.setVisibility(View.VISIBLE);
                aa.setVisibility(View.GONE);
                bb.setVisibility(View.GONE);
                cc.setVisibility(View.GONE);
                dd.setVisibility(View.GONE);
                ee.setVisibility(View.GONE);
                txtOne.setText("Set Reminder");
                txtTwo.setText("Service Reminder");
                arrowOne.setVisibility(View.VISIBLE);
                arrowTwo.setVisibility(View.VISIBLE);

                txtZero.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        one.setVisibility(View.VISIBLE);
                        two.setVisibility(View.GONE);
                        three.setVisibility(View.GONE);
                        four.setVisibility(View.GONE);
                        five.setVisibility(View.GONE);
                        six.setVisibility(View.GONE);
                        seven.setVisibility(View.GONE);
                        eight.setVisibility(View.GONE);
                        nine.setVisibility(View.GONE);
                        ten.setVisibility(View.GONE);
                        aa.setVisibility(View.GONE);
                        bb.setVisibility(View.GONE);
                        cc.setVisibility(View.GONE);
                        dd.setVisibility(View.GONE);
                        ee.setVisibility(View.GONE);
                        txtOne.setText("");
                        txtTwo.setText("");
                        arrowOne.setVisibility(View.GONE);
                        arrowTwo.setVisibility(View.GONE);
                    }
                });

                txtOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        one.setVisibility(View.GONE);
                        two.setVisibility(View.GONE);
                        three.setVisibility(View.GONE);
                        four.setVisibility(View.GONE);
                        five.setVisibility(View.VISIBLE);
                        six.setVisibility(View.GONE);
                        seven.setVisibility(View.GONE);
                        eight.setVisibility(View.GONE);
                        nine.setVisibility(View.GONE);
                        ten.setVisibility(View.GONE);
                        aa.setVisibility(View.GONE);
                        bb.setVisibility(View.GONE);
                        cc.setVisibility(View.GONE);
                        dd.setVisibility(View.GONE);
                        ee.setVisibility(View.GONE);
                        txtOne.setText("Set Reminder");
                        txtTwo.setText("");
                        arrowOne.setVisibility(View.VISIBLE);
                        arrowTwo.setVisibility(View.GONE);
                    }
                });
                break;
            case R.id.linear_doc_drivingLicense:
                one.setVisibility(View.GONE);
                two.setVisibility(View.GONE);
                three.setVisibility(View.GONE);
                four.setVisibility(View.GONE);
                five.setVisibility(View.GONE);
                six.setVisibility(View.GONE);
                seven.setVisibility(View.GONE);
                eight.setVisibility(View.GONE);
                nine.setVisibility(View.GONE);
                ten.setVisibility(View.GONE);
                aa.setVisibility(View.GONE);
                bb.setVisibility(View.VISIBLE);
                cc.setVisibility(View.GONE);
                dd.setVisibility(View.GONE);
                ee.setVisibility(View.GONE);
                txtOne.setText("Document List");
                txtTwo.setText("Driving Licence");
                arrowOne.setVisibility(View.VISIBLE);
                arrowTwo.setVisibility(View.VISIBLE);

                txtZero.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        one.setVisibility(View.VISIBLE);
                        two.setVisibility(View.GONE);
                        three.setVisibility(View.GONE);
                        four.setVisibility(View.GONE);
                        five.setVisibility(View.GONE);
                        six.setVisibility(View.GONE);
                        seven.setVisibility(View.GONE);
                        eight.setVisibility(View.GONE);
                        nine.setVisibility(View.GONE);
                        ten.setVisibility(View.GONE);
                        aa.setVisibility(View.GONE);
                        bb.setVisibility(View.GONE);
                        cc.setVisibility(View.GONE);
                        dd.setVisibility(View.GONE);
                        ee.setVisibility(View.GONE);
                        txtOne.setText("");
                        txtTwo.setText("");
                        arrowOne.setVisibility(View.GONE);
                        arrowTwo.setVisibility(View.GONE);
                    }
                });

                txtOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        one.setVisibility(View.GONE);
                        two.setVisibility(View.GONE);
                        three.setVisibility(View.GONE);
                        four.setVisibility(View.GONE);
                        five.setVisibility(View.GONE);
                        six.setVisibility(View.VISIBLE);
                        seven.setVisibility(View.GONE);
                        eight.setVisibility(View.GONE);
                        nine.setVisibility(View.GONE);
                        ten.setVisibility(View.GONE);
                        aa.setVisibility(View.GONE);
                        bb.setVisibility(View.GONE);
                        cc.setVisibility(View.GONE);
                        dd.setVisibility(View.GONE);
                        ee.setVisibility(View.GONE);
                        txtOne.setText("Document List");
                        txtTwo.setText("");
                        arrowOne.setVisibility(View.VISIBLE);
                        arrowTwo.setVisibility(View.GONE);
                    }
                });
                break;
            case R.id.linear_doc_insuranse:
                one.setVisibility(View.GONE);
                two.setVisibility(View.GONE);
                three.setVisibility(View.GONE);
                four.setVisibility(View.GONE);
                five.setVisibility(View.GONE);
                six.setVisibility(View.GONE);
                seven.setVisibility(View.GONE);
                eight.setVisibility(View.GONE);
                nine.setVisibility(View.GONE);
                ten.setVisibility(View.GONE);
                aa.setVisibility(View.GONE);
                bb.setVisibility(View.GONE);
                cc.setVisibility(View.VISIBLE);
                dd.setVisibility(View.GONE);
                ee.setVisibility(View.GONE);
                txtOne.setText("Document List");
                txtTwo.setText("Insurance");
                arrowOne.setVisibility(View.VISIBLE);
                arrowTwo.setVisibility(View.VISIBLE);

                txtZero.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        one.setVisibility(View.VISIBLE);
                        two.setVisibility(View.GONE);
                        three.setVisibility(View.GONE);
                        four.setVisibility(View.GONE);
                        five.setVisibility(View.GONE);
                        six.setVisibility(View.GONE);
                        seven.setVisibility(View.GONE);
                        eight.setVisibility(View.GONE);
                        nine.setVisibility(View.GONE);
                        ten.setVisibility(View.GONE);
                        aa.setVisibility(View.GONE);
                        bb.setVisibility(View.GONE);
                        cc.setVisibility(View.GONE);
                        dd.setVisibility(View.GONE);
                        ee.setVisibility(View.GONE);
                        txtOne.setText("");
                        txtTwo.setText("");
                        arrowOne.setVisibility(View.GONE);
                        arrowTwo.setVisibility(View.GONE);
                    }
                });

                txtOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        one.setVisibility(View.GONE);
                        two.setVisibility(View.GONE);
                        three.setVisibility(View.GONE);
                        four.setVisibility(View.GONE);
                        five.setVisibility(View.GONE);
                        six.setVisibility(View.VISIBLE);
                        seven.setVisibility(View.GONE);
                        eight.setVisibility(View.GONE);
                        nine.setVisibility(View.GONE);
                        ten.setVisibility(View.GONE);
                        aa.setVisibility(View.GONE);
                        bb.setVisibility(View.GONE);
                        cc.setVisibility(View.GONE);
                        dd.setVisibility(View.GONE);
                        ee.setVisibility(View.GONE);
                        txtOne.setText("Document List");
                        txtTwo.setText("");
                        arrowOne.setVisibility(View.VISIBLE);
                        arrowTwo.setVisibility(View.GONE);
                    }
                });
                break;
            case R.id.linear_doc_emission:
                one.setVisibility(View.GONE);
                two.setVisibility(View.GONE);
                three.setVisibility(View.GONE);
                four.setVisibility(View.GONE);
                five.setVisibility(View.GONE);
                six.setVisibility(View.GONE);
                seven.setVisibility(View.GONE);
                eight.setVisibility(View.GONE);
                nine.setVisibility(View.GONE);
                ten.setVisibility(View.GONE);
                aa.setVisibility(View.GONE);
                bb.setVisibility(View.GONE);
                cc.setVisibility(View.GONE);
                dd.setVisibility(View.VISIBLE);
                ee.setVisibility(View.GONE);
                txtOne.setText("Document List");
                txtTwo.setText("Emission");
                arrowOne.setVisibility(View.VISIBLE);
                arrowTwo.setVisibility(View.VISIBLE);

                txtZero.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        one.setVisibility(View.VISIBLE);
                        two.setVisibility(View.GONE);
                        three.setVisibility(View.GONE);
                        four.setVisibility(View.GONE);
                        five.setVisibility(View.GONE);
                        six.setVisibility(View.GONE);
                        seven.setVisibility(View.GONE);
                        eight.setVisibility(View.GONE);
                        nine.setVisibility(View.GONE);
                        ten.setVisibility(View.GONE);
                        aa.setVisibility(View.GONE);
                        bb.setVisibility(View.GONE);
                        cc.setVisibility(View.GONE);
                        dd.setVisibility(View.GONE);
                        ee.setVisibility(View.GONE);
                        txtOne.setText("");
                        txtTwo.setText("");
                        arrowOne.setVisibility(View.GONE);
                        arrowTwo.setVisibility(View.GONE);
                    }
                });

                txtOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        one.setVisibility(View.GONE);
                        two.setVisibility(View.GONE);
                        three.setVisibility(View.GONE);
                        four.setVisibility(View.GONE);
                        five.setVisibility(View.GONE);
                        six.setVisibility(View.VISIBLE);
                        seven.setVisibility(View.GONE);
                        eight.setVisibility(View.GONE);
                        nine.setVisibility(View.GONE);
                        ten.setVisibility(View.GONE);
                        aa.setVisibility(View.GONE);
                        bb.setVisibility(View.GONE);
                        cc.setVisibility(View.GONE);
                        dd.setVisibility(View.GONE);
                        ee.setVisibility(View.GONE);
                        txtOne.setText("Document List");
                        txtTwo.setText("");
                        arrowOne.setVisibility(View.VISIBLE);
                        arrowTwo.setVisibility(View.GONE);
                    }
                });
                break;
            case R.id.linear_doc_bills:
                one.setVisibility(View.GONE);
                two.setVisibility(View.GONE);
                three.setVisibility(View.GONE);
                four.setVisibility(View.GONE);
                five.setVisibility(View.GONE);
                six.setVisibility(View.GONE);
                seven.setVisibility(View.GONE);
                eight.setVisibility(View.GONE);
                nine.setVisibility(View.GONE);
                ten.setVisibility(View.GONE);
                aa.setVisibility(View.GONE);
                bb.setVisibility(View.GONE);
                cc.setVisibility(View.GONE);
                dd.setVisibility(View.GONE);
                ee.setVisibility(View.VISIBLE);
                txtOne.setText("Document List");
                txtTwo.setText("Bills");
                arrowOne.setVisibility(View.VISIBLE);
                arrowTwo.setVisibility(View.VISIBLE);

                txtZero.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        one.setVisibility(View.VISIBLE);
                        two.setVisibility(View.GONE);
                        three.setVisibility(View.GONE);
                        four.setVisibility(View.GONE);
                        five.setVisibility(View.GONE);
                        six.setVisibility(View.GONE);
                        seven.setVisibility(View.GONE);
                        eight.setVisibility(View.GONE);
                        nine.setVisibility(View.GONE);
                        ten.setVisibility(View.GONE);
                        aa.setVisibility(View.GONE);
                        bb.setVisibility(View.GONE);
                        cc.setVisibility(View.GONE);
                        dd.setVisibility(View.GONE);
                        ee.setVisibility(View.GONE);
                        txtOne.setText("");
                        txtTwo.setText("");
                        arrowOne.setVisibility(View.GONE);
                        arrowTwo.setVisibility(View.GONE);
                    }
                });

                txtOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        one.setVisibility(View.GONE);
                        two.setVisibility(View.GONE);
                        three.setVisibility(View.GONE);
                        four.setVisibility(View.GONE);
                        five.setVisibility(View.GONE);
                        six.setVisibility(View.VISIBLE);
                        seven.setVisibility(View.GONE);
                        eight.setVisibility(View.GONE);
                        nine.setVisibility(View.GONE);
                        ten.setVisibility(View.GONE);
                        aa.setVisibility(View.GONE);
                        bb.setVisibility(View.GONE);
                        cc.setVisibility(View.GONE);
                        dd.setVisibility(View.GONE);
                        ee.setVisibility(View.GONE);
                        txtOne.setText("Document List");
                        txtTwo.setText("");
                        arrowOne.setVisibility(View.VISIBLE);
                        arrowTwo.setVisibility(View.GONE);
                    }
                });
                break;

            case R.id.linear_reg_registerVehicle:
                Intent reg_veh_one = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                reg_veh_one.putExtra("rakshit", "reg_veh_one");
                startActivity(reg_veh_one);
                break;

            case R.id.linear_reg_vehicleAlreadyRegister:
                Intent reg_veh_two = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                reg_veh_two.putExtra("rakshit", "reg_veh_two");
                startActivity(reg_veh_two);
                break;

            case R.id.linear_reg_claimVehicle:
                Intent reg_veh_three = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                reg_veh_three.putExtra("rakshit", "reg_veh_three");
                startActivity(reg_veh_three);
                break;
            case R.id.linear_reg_deleteVehicle:
                Intent reg_veh_four = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                reg_veh_four.putExtra("rakshit", "reg_veh_four");
                startActivity(reg_veh_four);
                break;

            case R.id.linear_emergency_contact:
                Intent emergency_one = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                emergency_one.putExtra("rakshit", "emergency_one");
                startActivity(emergency_one);
                break;

            case R.id.linear_emergency_addContact:
                Intent emergency_two = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                emergency_two.putExtra("rakshit", "emergency_two");
                startActivity(emergency_two);
                break;

            case R.id.linear_emergency_notReceiving:
                Intent emergency_three = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                emergency_three.putExtra("rakshit", "emergency_three");
                startActivity(emergency_three);
                break;
            case R.id.reminder_one:
                Intent re_one = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                re_one.putExtra("rakshit", "reminder_one");
                startActivity(re_one);
                break;

            case R.id.reminder_two:
                Intent reminder_two = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                reminder_two.putExtra("rakshit", "reminder_two");
                startActivity(reminder_two);
                break;

            case R.id.reminder_three:
                Intent reminder_three = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                reminder_three.putExtra("rakshit", "reminder_three");
                startActivity(reminder_three);
                break;

            case R.id.reminder_four:
                Intent reminder_four = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                reminder_four.putExtra("rakshit", "reminder_four");
                startActivity(reminder_four);
                break;

            case R.id.reminder_five:
                Intent reminder_five = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                reminder_five.putExtra("rakshit", "reminder_five");
                startActivity(reminder_five);
                break;

            case R.id.reminder_six:
                Intent reminder_six = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                reminder_six.putExtra("rakshit", "reminder_six");
                startActivity(reminder_six);
                break;

            case R.id.emission_one:
                Intent emission_one = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                emission_one.putExtra("rakshit", "emission_one");
                startActivity(emission_one);
                break;

            case R.id.emission_two:
                Intent emission_two = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                emission_two.putExtra("rakshit", "emission_two");
                startActivity(emission_two);
                break;

            case R.id.emission_three:
                Intent emission_three = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                emission_three.putExtra("rakshit", "emission_three");
                startActivity(emission_three);
                break;

            case R.id.emission_four:
                Intent emission_four = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                emission_four.putExtra("rakshit", "emission_four");
                startActivity(emission_four);
                break;

            case R.id.emission_five:
                Intent emission_five = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                emission_five.putExtra("rakshit", "emission_five");
                startActivity(emission_five);
                break;

            case R.id.service_one:
                Intent service_one = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                service_one.putExtra("rakshit", "service_one");
                startActivity(service_one);
                break;

            case R.id.service_two:
                Intent service_two = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                service_two.putExtra("rakshit", "service_two");
                startActivity(service_two);
                break;

            case R.id.service_three:
                Intent service_three = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                service_three.putExtra("rakshit", "service_three");
                startActivity(service_three);
                break;

            case R.id.service_four:
                Intent service_four = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                service_four.putExtra("rakshit", "service_four");
                startActivity(service_four);
                break;

            case R.id.service_five:
                Intent service_five = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                service_five.putExtra("rakshit", "service_five");
                startActivity(service_five);
                break;

            case R.id.service_six:
                Intent service_six = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                service_six.putExtra("rakshit", "service_six");
                startActivity(service_six);
                break;

            case R.id.driving_one:
                Intent driving_one = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                driving_one.putExtra("rakshit", "driving_one");
                startActivity(driving_one);
                break;

            case R.id.driving_two:
                Intent driving_two = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                driving_two.putExtra("rakshit", "driving_two");
                startActivity(driving_two);
                break;

            case R.id.driving_three:
                Intent driving_three = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                driving_three.putExtra("rakshit", "driving_three");
                startActivity(driving_three);
                break;

            case R.id.driving_four:
                Intent driving_four = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                driving_four.putExtra("rakshit", "driving_four");
                startActivity(driving_four);
                break;

            case R.id.driving_five:
                Intent driving_five = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                driving_five.putExtra("rakshit", "driving_five");
                startActivity(driving_five);
                break;

            case R.id.driving_six:
                Intent driving_six = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                driving_six.putExtra("rakshit", "driving_six");
                startActivity(driving_six);
                break;

            case R.id.doc_insurance_one:
                Intent doc_insurance_one = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                doc_insurance_one.putExtra("rakshit", "doc_insurance_one");
                startActivity(doc_insurance_one);
                break;

            case R.id.doc_insurance_two:
                Intent doc_insurance_two = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                doc_insurance_two.putExtra("rakshit", "doc_insurance_two");
                startActivity(doc_insurance_two);
                break;

            case R.id.doc_insurance_three:
                Intent doc_insurance_three = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                doc_insurance_three.putExtra("rakshit", "doc_insurance_three");
                startActivity(doc_insurance_three);
                break;

            case R.id.doc_insurance_four:
                Intent doc_insurance_four = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                doc_insurance_four.putExtra("rakshit", "doc_insurance_four");
                startActivity(doc_insurance_four);
                break;

            case R.id.doc_insurance_five:
                Intent doc_insurance_five = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                doc_insurance_five.putExtra("rakshit", "doc_insurance_five");
                startActivity(doc_insurance_five);
                break;

            case R.id.doc_insurance_six:
                Intent doc_insurance_six = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                doc_insurance_six.putExtra("rakshit", "doc_insurance_six");
                startActivity(doc_insurance_six);
                break;

            case R.id.doc_emission_one:
                Intent doc_emission_one = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                doc_emission_one.putExtra("rakshit", "doc_emission_one");
                startActivity(doc_emission_one);
                break;

            case R.id.doc_emission_two:
                Intent doc_emission_two = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                doc_emission_two.putExtra("rakshit", "doc_emission_two");
                startActivity(doc_emission_two);
                break;
            case R.id.doc_emission_three:
                Intent doc_emission_three = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                doc_emission_three.putExtra("rakshit", "doc_emission_three");
                startActivity(doc_emission_three);
                break;

            case R.id.doc_emission_four:
                Intent doc_emission_four = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                doc_emission_four.putExtra("rakshit", "doc_emission_four");
                startActivity(doc_emission_four);
                break;

            case R.id.doc_emission_five:
                Intent doc_emission_five = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                doc_emission_five.putExtra("rakshit", "doc_emission_five");
                startActivity(doc_emission_five);
                break;

            case R.id.doc_emission_six:
                Intent doc_emission_six = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                doc_emission_six.putExtra("rakshit", "doc_emission_six");
                startActivity(doc_emission_six);
                break;

            case R.id.doc_bill_one:
                Intent doc_bill_one = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                doc_bill_one.putExtra("rakshit", "doc_bill_one");
                startActivity(doc_bill_one);
                break;

            case R.id.doc_bill_two:
                Intent doc_bill_two = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                doc_bill_two.putExtra("rakshit", "doc_bill_two");
                startActivity(doc_bill_two);
                break;

            case R.id.doc_bill_three:
                Intent doc_bill_three = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                doc_bill_three.putExtra("rakshit", "doc_bill_three");
                startActivity(doc_bill_three);
                break;

            case R.id.doc_bill_four:
                Intent doc_bill_four = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                doc_bill_four.putExtra("rakshit", "doc_bill_four");
                startActivity(doc_bill_four);
                break;

            case R.id.doc_bill_five:
                Intent doc_bill_five = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                doc_bill_five.putExtra("rakshit", "doc_bill_five");
                startActivity(doc_bill_five);
                break;

            case R.id.doc_bill_six:
                Intent doc_bill_six = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                doc_bill_six.putExtra("rakshit", "doc_bill_six");
                startActivity(doc_bill_six);
                break;

            case R.id.document_one:
                Intent document_one = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                document_one.putExtra("rakshit", "document_one");
                startActivity(document_one);
                break;

            case R.id.document_two:
                Intent document_two = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                document_two.putExtra("rakshit", "document_two");
                startActivity(document_two);
                break;

            case R.id.document_three:
                Intent document_three = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                document_three.putExtra("rakshit", "document_three");
                startActivity(document_three);
                break;

            case R.id.document_four:
                Intent document_four = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                document_four.putExtra("rakshit", "document_four");
                startActivity(document_four);
                break;

            case R.id.document_five:
                Intent document_five = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                document_five.putExtra("rakshit", "document_five");
                startActivity(document_five);
                break;

            case R.id.document_six:
                Intent document_six = new Intent(RefferalCodeActivity.this, HelpViewActivity.class);
                document_six.putExtra("rakshit", "document_six");
                startActivity(document_six);
                break;
            default:
                break;
        }
    }
}
