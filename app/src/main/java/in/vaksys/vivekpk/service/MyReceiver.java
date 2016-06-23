package in.vaksys.vivekpk.service;

/**
 * Created by vishal on 15/06/2016.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import in.vaksys.vivekpk.extras.MyApplication;

public class MyReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {

       String type = intent.getStringExtra("type");
        String vehicalno = intent.getStringExtra("vehicalno");
        String nofidate = intent.getStringExtra("nofidate");
        String nId = intent.getStringExtra("nId");

        MyApplication.getInstance().showLog("MyReceiver",type + vehicalno + nofidate +nId);
        Intent service1 = new Intent(context, MyAlarmService.class);
        service1.putExtra("type", type);
        service1.putExtra("vehicalno", vehicalno);
        service1.putExtra("nofidate", nofidate);
        service1.putExtra("nId", nId);

        context.startService(service1);

    }
}
