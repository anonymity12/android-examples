package github.nisrulz.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
//静态注册的一个Receiver
public class MyBroadcastReceiver extends BroadcastReceiver {

    String ACTION = "github.nisrulz.action.MY_ACTION";


    public MyBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction().equals(ACTION)) {
            String data = intent.getStringExtra("data");
            Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
        }
    }
}
