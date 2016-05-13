package in.vaksys.vivekpk.extras;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import in.vaksys.vivekpk.service.HttpService;

/**
 * Created by Harsh on 10-05-2016.
 */
public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = SmsReceiver.class.getSimpleName();
    String verificationCode = "";

    @Override
    public void onReceive(Context context, Intent intent) {

        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (Object aPdusObj : pdusObj) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) aPdusObj);
                    String senderAddress = currentMessage.getDisplayOriginatingAddress();
                    String message = currentMessage.getDisplayMessageBody();

                    Log.e(TAG, "Received SMS: " + message + ", Sender: " + senderAddress);

                    // if the SMS is not from our gateway, ignore the message
                    if (!senderAddress.toLowerCase().contains(AppConfig.SMS_ORIGIN.toLowerCase())) {
                        return;
                    }

                    // verification code from sms
                    String verificationCode = getVerificationCode(message);

                    Log.e(TAG, "OTP received: " + verificationCode);
                    SharedPreferences sharedPreferences = MyApplication.getInstance().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);

                    Intent hhtpIntent = new Intent(context, HttpService.class);
                    hhtpIntent.putExtra("otp", verificationCode);
                    hhtpIntent.putExtra("mobile", sharedPreferences.getString("mobile", ""));
                    context.startService(hhtpIntent);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }


    private String getVerificationCode(String message) {
        String code = null;
        code = message.replaceAll("[^0-9]", "");
        Log.e(TAG, "getVerificationCode: " + code);
        return code;
    }


}
