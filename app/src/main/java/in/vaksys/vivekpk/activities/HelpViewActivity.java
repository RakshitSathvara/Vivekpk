package in.vaksys.vivekpk.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import in.vaksys.vivekpk.R;

public class HelpViewActivity extends AppCompatActivity {

    TextView viewTxt, viewHelp;

    Bundle bundle;
    String vehOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_view);

        viewTxt = (TextView) findViewById(R.id.viewTxt);
        viewHelp = (TextView) findViewById(R.id.viewHelp);

        vehOne = getIntent().getStringExtra("rakshit");

        if (vehOne.equalsIgnoreCase("reg_veh_one")) {
            viewHelp.setText(R.string.reg_veh_one);
            viewTxt.setText("Enter Vehicle Details");
        }

        if (vehOne.equalsIgnoreCase("reg_veh_two")) {
            viewHelp.setText(R.string.reg_veh_two);
            viewTxt.setText("Vehicle already registered");
        }

        if (vehOne.equalsIgnoreCase("reg_veh_three")) {
            viewHelp.setText(R.string.reg_veh_three);
            viewTxt.setText("Claim vehicle");
        }

        if (vehOne.equalsIgnoreCase("reg_veh_four")) {
            viewHelp.setText(R.string.reg_veh_four);
            viewTxt.setText("Delete vehicle/vehicle details");
        }

        if (vehOne.equalsIgnoreCase("emergency_one")) {
            viewHelp.setText(R.string.emergency_one);
            viewTxt.setText("How emergency contact helps?");
        }

        if (vehOne.equalsIgnoreCase("emergency_two")) {
            viewHelp.setText(R.string.emergency_two);
            viewTxt.setText("How can I add/delete contacts?");
        }

        if (vehOne.equalsIgnoreCase("emergency_three")) {
            viewHelp.setText(R.string.emergency_three);
            viewTxt.setText("Contacts 'Not Receiving' emergency help / accident report");
        }


        if (vehOne.equalsIgnoreCase("reminder_one")) {
            viewHelp.setText(R.string.insurance_one);
            viewTxt.setText("How can I set reminder");
        }

        if (vehOne.equalsIgnoreCase("reminder_two")) {
            viewHelp.setText(R.string.insurance_two);
            viewTxt.setText("Do I need to be online to receive reminder");
        }

        if (vehOne.equalsIgnoreCase("reminder_three")) {
            viewHelp.setText(R.string.insurance_three);
            viewTxt.setText("How can I Edit/Delete the set reminder");
        }

        if (vehOne.equalsIgnoreCase("reminder_four")) {
            viewHelp.setText(R.string.insurance_four);
            viewTxt.setText("I am not able to find the Insurance Company Name");
        }

        if (vehOne.equalsIgnoreCase("reminder_five")) {
            viewHelp.setText(R.string.insurance_five);
            viewTxt.setText("I am not able to Set a Reminder");
        }

        if (vehOne.equalsIgnoreCase("reminder_six")) {
            viewHelp.setText(R.string.insurance_six);
            viewTxt.setText("When will the app trigger the reminder?");
        }

        if (vehOne.equalsIgnoreCase("emission_one")) {
            viewHelp.setText(R.string.emission_one);
            viewTxt.setText("How can I set reminder");
        }

        if (vehOne.equalsIgnoreCase("emission_two")) {
            viewHelp.setText(R.string.emission_two);
            viewTxt.setText("Do I need to be online to receive reminder");
        }

        if (vehOne.equalsIgnoreCase("emission_three")) {
            viewHelp.setText(R.string.emission_three);
            viewTxt.setText("How can I Edit/Delete the set reminder");
        }

        if (vehOne.equalsIgnoreCase("emission_four")) {
            viewHelp.setText(R.string.emission_four);
            viewTxt.setText("I am not able to Set a Reminder");
        }

        if (vehOne.equalsIgnoreCase("emission_five")) {
            viewHelp.setText(R.string.emission_five);
            viewTxt.setText("When will the app trigger the reminder?");
        }

        if (vehOne.equalsIgnoreCase("service_one")) {
            viewHelp.setText(R.string.service_one);
            viewTxt.setText("How can I set reminder");
        }

        if (vehOne.equalsIgnoreCase("service_two")) {
            viewHelp.setText(R.string.service_two);
            viewTxt.setText("Do I need to be online to receive reminder");
        }

        if (vehOne.equalsIgnoreCase("service_three")) {
            viewHelp.setText(R.string.service_three);
            viewTxt.setText(" How can I Edit/Delete the set reminder");
        }

        if (vehOne.equalsIgnoreCase("service_four")) {
            viewHelp.setText(R.string.service_four);
            viewTxt.setText("I am not able to Set a Reminder");
        }

        if (vehOne.equalsIgnoreCase("service_five")) {
            viewHelp.setText(R.string.service_five);
            viewTxt.setText("When will the app trigger the reminder?");
        }

        if (vehOne.equalsIgnoreCase("service_six")) {
            viewHelp.setText(R.string.service_six);
            viewTxt.setText("What is the use of Note field?");
        }

        if (vehOne.equalsIgnoreCase("driving_one")) {
            viewHelp.setText(R.string.document_list_one);
            viewTxt.setText("How can I add driving licence");
        }

        if (vehOne.equalsIgnoreCase("driving_two")) {
            viewHelp.setText(R.string.document_list_two);
            viewTxt.setText("I am not able to upload the DL image");
        }

        if (vehOne.equalsIgnoreCase("driving_three")) {
            viewHelp.setText(R.string.document_list_three);
            viewTxt.setText("How many images of driving licence can I add?");
        }

        if (vehOne.equalsIgnoreCase("driving_four")) {
            viewHelp.setText(R.string.document_list_four);
            viewTxt.setText("How can I Replace/delete?");
        }

        if (vehOne.equalsIgnoreCase("driving_five")) {
            viewHelp.setText(R.string.document_list_five);
            viewTxt.setText("I am not able to Replace/delete the DL image");
        }

        if (vehOne.equalsIgnoreCase("driving_six")) {
            viewHelp.setText(R.string.document_list_six);
            viewTxt.setText("Do I need to be online to view the driving licence?");
        }

        if (vehOne.equalsIgnoreCase("doc_insurance_one")) {
            viewHelp.setText(R.string.doc_insurance_one);
            viewTxt.setText("How can I add Insurance documents?");
        }

        if (vehOne.equalsIgnoreCase("doc_insurance_two")) {
            viewHelp.setText(R.string.doc_insurance_two);
            viewTxt.setText("I am not able to upload the Insurance image");
        }

        if (vehOne.equalsIgnoreCase("doc_insurance_three")) {
            viewHelp.setText(R.string.doc_insurance_three);
            viewTxt.setText("How many images of driving Insurance can I add?");
        }

        if (vehOne.equalsIgnoreCase("doc_insurance_four")) {
            viewHelp.setText(R.string.doc_insurance_four);
            viewTxt.setText("How can I Replace/delete?");
        }

        if (vehOne.equalsIgnoreCase("doc_insurance_five")) {
            viewHelp.setText(R.string.doc_insurance_five);
            viewTxt.setText("I am not able to Replace/delete the Insurance image");
        }

        if (vehOne.equalsIgnoreCase("doc_insurance_six")) {
            viewHelp.setText(R.string.doc_insurance_six);
            viewTxt.setText("Do I need to be online to view the Insurance?");
        }

        if (vehOne.equalsIgnoreCase("doc_emission_one")) {
            viewHelp.setText(R.string.doc_insurance_one);
            viewTxt.setText("How can I add Emission Certificate documents?");
        }

        if (vehOne.equalsIgnoreCase("doc_emission_two")) {
            viewHelp.setText(R.string.doc_insurance_two);
            viewTxt.setText("I am not able to upload the Emission Certificate image");
        }

        if (vehOne.equalsIgnoreCase("doc_emission_three")) {
            viewHelp.setText(R.string.doc_insurance_three);
            viewTxt.setText("How many images of Emission Certificate can I add?");
        }

        if (vehOne.equalsIgnoreCase("doc_emission_four")) {
            viewHelp.setText(R.string.doc_insurance_four);
            viewTxt.setText("How can I Replace/delete?");
        }

        if (vehOne.equalsIgnoreCase("doc_emission_five")) {
            viewHelp.setText(R.string.doc_insurance_five);
            viewTxt.setText(" I am not able to Replace/delete the Emission Certificate image");
        }

        if (vehOne.equalsIgnoreCase("doc_emission_six")) {
            viewHelp.setText(R.string.doc_insurance_six);
            viewTxt.setText("Do I need to be online to view the Emission Certificate?");
        }

        if (vehOne.equalsIgnoreCase("doc_bill_one")) {
            viewHelp.setText(R.string.doc_insurance_one);
            viewTxt.setText("How can I add Bills/Receipts related to vehicle?");
        }

        if (vehOne.equalsIgnoreCase("doc_bill_two")) {
            viewHelp.setText(R.string.doc_insurance_two);
            viewTxt.setText("I am not able to upload the Bills image");
        }

        if (vehOne.equalsIgnoreCase("doc_bill_three")) {
            viewHelp.setText(R.string.doc_insurance_three);
            viewTxt.setText("How many images of Bills can I add?");
        }

        if (vehOne.equalsIgnoreCase("doc_bill_four")) {
            viewHelp.setText(R.string.doc_insurance_four);
            viewTxt.setText("How can I Replace/delete?");
        }

        if (vehOne.equalsIgnoreCase("doc_bill_five")) {
            viewHelp.setText(R.string.doc_insurance_five);
            viewTxt.setText("I am not able to Replace/delete the Bills image");
        }

        if (vehOne.equalsIgnoreCase("doc_bill_six")) {
            viewHelp.setText(R.string.doc_insurance_six);
            viewTxt.setText("Do I need to be online to view the Bills?");
        }

        if (vehOne.equalsIgnoreCase("document_one")) {
            viewHelp.setText(R.string.document_one);
            viewTxt.setText("Update My Name");
        }

        if (vehOne.equalsIgnoreCase("document_two")) {
            viewHelp.setText(R.string.document_two);
            viewTxt.setText("Forgot My Password");
        }

        if (vehOne.equalsIgnoreCase("document_three")) {
            viewHelp.setText(R.string.document_three);
            viewTxt.setText("Changing My Password");
        }

        if (vehOne.equalsIgnoreCase("document_four")) {
            viewHelp.setText(R.string.document_four);
            viewTxt.setText("Changing email id");
        }

        if (vehOne.equalsIgnoreCase("document_five")) {
            viewHelp.setText(R.string.document_five);
            viewTxt.setText("I would like to deactivate my account");
        }

        if (vehOne.equalsIgnoreCase("document_six")) {
            viewHelp.setText(R.string.document_six);
            viewTxt.setText("Changing mobile number");
        }
    }
}
