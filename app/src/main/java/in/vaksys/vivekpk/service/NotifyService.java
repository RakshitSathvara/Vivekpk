package in.vaksys.vivekpk.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.activities.NotificationActivity;
import in.vaksys.vivekpk.extras.MyApplication;

/**
 * Created by vishal on 14/06/2016.
 */
public class NotifyService extends Service {

    final static String ACTION = "NotifyServiceAction";
    final static String STOP_SERVICE_BROADCAST_KEY="StopServiceBroadcastKey";
    final static int RQS_STOP_SERVICE = 1;

    NotifyServiceReceiver notifyServiceReceiver;


    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        notifyServiceReceiver = new NotifyServiceReceiver();



        super.onCreate();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub

//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(ACTION);
//        registerReceiver(notifyServiceReceiver, intentFilter);
//
//        // Send Notification
//        Context context = getApplicationContext();
//        String notificationTitle = "Demo of Notification!";
//        String notificationText = "Course Website";
//        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(myBlog));
//        PendingIntent pendingIntent
//                = PendingIntent.getActivity(getBaseContext(),
//                0, myIntent,
//                Intent.FLAG_ACTIVITY_NEW_TASK);


//        String notificationTitle=  intent.getStringExtra("type");
//        String vehicalno = intent.getStringExtra("vehicalno");
//        String nofidate = intent.getStringExtra("nofidate");
//        String nid = intent.getStringExtra("nId");

      String dddd= String.valueOf(MyApplication.c.getTimeInMillis());

      //  MyApplication.getInstance().showLog("nodate",nofidate);
        MyApplication.getInstance().showLog("cander date",dddd);

        Intent pintent = new Intent(this, NotificationActivity.class);

        PendingIntent pIntent = PendingIntent.getBroadcast(this, 1, pintent, PendingIntent.FLAG_UPDATE_CURRENT);


        Notification notification = new Notification.Builder(this)
                .setContentIntent(pIntent)
                .setContentTitle("jhgjyg")
                .setSmallIcon(R.drawable.search_icon)
                .build();


//
//        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, MyApplication.c.getTimeInMillis(), pIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        notification.flags = notification.flags
//                | Notification.FLAG_ONGOING_EVENT;
//        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, notification);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        this.unregisterReceiver(notifyServiceReceiver);
        super.onDestroy();
    }

    public class NotifyServiceReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            // TODO Auto-generated method stub
            int rqs = arg1.getIntExtra(STOP_SERVICE_BROADCAST_KEY, 0);

            if (rqs == RQS_STOP_SERVICE){
                stopSelf();
                ((NotificationManager) getSystemService(NOTIFICATION_SERVICE))
                        .cancelAll();
            }
        }
    }
}
