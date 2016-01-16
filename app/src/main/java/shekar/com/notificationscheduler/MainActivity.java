package shekar.com.notificationscheduler;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


/**
 * This sample demonstrates how to schedule an alarm that causes a service to
 * be started. This is useful when you want to schedule alarms that initiate
 * long-running operations, such as retrieving a daily forecast.
 * This particular sample retrieves content from the Google home page once a day and  
 * checks it for the search string "doodle". If it finds this string, that indicates 
 * that the page contains a custom doodle instead of the standard Google logo.
 */
public class MainActivity extends AppCompatActivity {
    SampleAlarmReceiver alarm = new SampleAlarmReceiver();
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((android.support.v7.widget.Toolbar) findViewById(R.id.toolbar));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // Menu options to set and cancel the alarm.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // When the user clicks START ALARM, set the alarm.
            case R.id.start_action:
                alarm.setAlarm(this);
                return true;
            // When the user clicks CANCEL ALARM, cancel the alarm. 
            case R.id.cancel_action:
                for (int i = 0; i <5 ; i++) {
                    boolean alarmUp = (PendingIntent.getBroadcast(this, i,
                            new Intent(this, SampleAlarmReceiver.class),
                            PendingIntent.FLAG_NO_CREATE) != null);

                    if (alarmUp)
                    {
                        Log.d("===myTag", "==Alarm is already active");
                    }
                }
               // alarm.cancelAlarm(this);
                return true;
        }
        return false;
    }

    public void showNotification(View view) {
        sendNotification("Hello");
    }


    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent i=new Intent(this,DetailActivity.class);

        TaskStackBuilder tb=TaskStackBuilder.create(this);
        tb.addParentStack(DetailActivity.class);
        tb.addNextIntent(i);

        PendingIntent contentIntent =tb.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);

//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
//                new Intent(this, DetailActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(getString(R.string.doodle_alert))
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(1, mBuilder.build());
    }
}
