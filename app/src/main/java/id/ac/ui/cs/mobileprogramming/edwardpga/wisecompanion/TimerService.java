package id.ac.ui.cs.mobileprogramming.edwardpga.wisecompanion;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.Observer;

import java.util.List;

public class TimerService extends LifecycleService {
    private long counter;
    private long stackedHunger = 150;
    private long stackedAffection = 100;

    private final int REFRESH_RATE = 1000;

    public static final String TIMER_BROADCAST = "TimerCounting";
    Intent broadcastIntent = new Intent(TIMER_BROADCAST);
    private Handler mHandler = new Handler();

    CompanionViewModel mCompanionViewModel;
    CompanionModel mCompanion;

    private NotificationCompat.Builder mBuilder;
    private String CHANNEL_ID = "4655";
    private int notificationId = 100;
    NotificationManager notificationManager;

    public TimerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mCompanionViewModel = new CompanionViewModel(getApplication());
        mCompanion = mCompanionViewModel.getAllCompanionModel().get(0);

        counter = intent.getLongExtra("startingCounter", -100);

        mCompanionViewModel.getAllCompanion().observe(this, new Observer<List<CompanionModel>>() {

            @Override
            public void onChanged(@Nullable final List<CompanionModel> companions) {
                mCompanion = companions.get(0);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                if (mCompanion.getHungerLevel() <= 0 || mCompanion.getAffectionLevel() <= 0) {
                    notificationManager.notify(notificationId, mBuilder.build());
                }else {
                    notificationManager.cancel(notificationId);
                }
            }
        });

        mHandler.removeCallbacks(startTimer);
        mHandler.postDelayed(startTimer, 0);

        createNotificationChannel();
        setNotification();

        return super.onStartCommand(intent, flags, startId);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "CompanionNotification", importance);
            notificationManager = this.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void setNotification() {
        Intent resultIntent = new Intent(this, MainActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                this, 0, resultIntent, 0
        );
        mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_pet)
                .setContentTitle(getResources().getString(R.string.zero_stat_notif_title))
                .setContentText(getResources().getString(R.string.zero_stat_notif))
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(getResources().getString(R.string.zero_stat_notif)))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(resultPendingIntent)
                .setAutoCancel(true);
    }

    @Override
    public void onDestroy() {
        mHandler.removeCallbacks(startTimer);
        super.onDestroy();
    }

    private Runnable startTimer = new Runnable() {
        @Override
        public void run() {
            counter += 1;
            broadcastIntent.putExtra("counter", counter);

            if (MainActivity.isPaused()) {
                stackedHunger += 150;
                stackedAffection -= 150;
            } else {
                stackedHunger = 150;
                stackedAffection = 100;
            }
            broadcastIntent.putExtra("stackedHunger", stackedHunger);
            broadcastIntent.putExtra("stackedAffection", stackedAffection);


            sendBroadcast(broadcastIntent);
            mHandler.postDelayed(this, REFRESH_RATE);
        }
    };
}
