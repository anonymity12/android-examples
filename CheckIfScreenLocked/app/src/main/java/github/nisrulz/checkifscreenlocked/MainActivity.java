package github.nisrulz.checkifscreenlocked;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String LOGTAG = "CheckIfScreenLocked";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerScreenLockStateBroadcastReceiver();

    }


    private void registerScreenLockStateBroadcastReceiver() {
        final IntentFilter theFilter = new IntentFilter();
        /** System Defined Broadcast */
        theFilter.addAction(Intent.ACTION_SCREEN_ON);
        theFilter.addAction(Intent.ACTION_SCREEN_OFF);
        theFilter.addAction(Intent.ACTION_USER_PRESENT);

        BroadcastReceiver screenOnOffReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String strAction = intent.getAction();
                // 这里使用 KeyguardManager 是一个 锁屏解锁键盘的类 
                /*
                Class that can be used to lock and unlock the keyboard. 
                The actual class to control the keyboard locking is KeyguardManager.KeyguardLock.
                */
                KeyguardManager kgMgr =
                        (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
                if (strAction.equals(Intent.ACTION_SCREEN_OFF)) {
                    Log.d(LOGTAG, "Screen Off");
                } else if (strAction.equals(Intent.ACTION_SCREEN_ON)) {
                    Log.d(LOGTAG, "Screen On");
                }
                if (strAction.equals(Intent.ACTION_USER_PRESENT) && !kgMgr.inKeyguardRestrictedInputMode()) {
                    Log.d(LOGTAG, "Device UNLOCKED");
                } else {
                    Log.d(LOGTAG, "Device LOCKED");
                }
            }
        };

        getApplicationContext().registerReceiver(screenOnOffReceiver, theFilter);
    }
}
