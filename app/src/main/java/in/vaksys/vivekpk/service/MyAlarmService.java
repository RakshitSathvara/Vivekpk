package in.vaksys.vivekpk.service;

/**
 * Created by vishal on 15/06/2016.
 */

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.activities.NotificationActivity;
import in.vaksys.vivekpk.extras.MyApplication;


public class MyAlarmService extends Service {


    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String type = intent.getStringExtra("type");
        String vehicalno = intent.getStringExtra("vehicalno");
        String nofidate = intent.getStringExtra("nofidate");
        String nId = intent.getStringExtra("nId");

        MyApplication.getInstance().showLog("MyAlarmService",type + vehicalno + nofidate +nId);

        Intent pintent = new Intent(this, NotificationActivity.class);

        pintent.putExtra("type", type);
        pintent.putExtra("vehicalno", vehicalno);
        pintent.putExtra("nofidate", nofidate);
        pintent.putExtra("nId", nId);

        PendingIntent pIntent = PendingIntent.getActivity(this, 1, pintent, PendingIntent.FLAG_UPDATE_CURRENT);


        Notification notification = new Notification.Builder(this)
                .setContentIntent(pIntent)
                .setContentTitle(type)
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();


//
//        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, MyApplication.c.getTimeInMillis(), pIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        notification.flags = notification.flags
//                | Notification.FLAG_ONGOING_EVENT;
//        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(Integer.parseInt(nId), notification);

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

}