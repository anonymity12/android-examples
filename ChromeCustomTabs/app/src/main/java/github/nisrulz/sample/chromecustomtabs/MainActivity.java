package github.nisrulz.sample.chromecustomtabs;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Launch Custom Chrome Tab ?", Snackbar.LENGTH_LONG)
            .setAction("Yes", new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                loadChromeCustomTab(MainActivity.this, "http://crushingcode.co/");
              }
            })
            .show();
      }
    });
  }

  private void loadChromeCustomTab(Context context, String url) {
    Uri uri = Uri.parse(url);

    // Use a CustomTabsIntent.Builder to configure CustomTabsIntent.
    //所谓的自定义的 chrome tab 就 都是由它  CustomTabsIntent.Builder  来完成
    //  比如 分享图片的加载 见 mark1 
    //  比如 关闭图标的加载 见 mark2
    CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();

    // set toolbar color
    intentBuilder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary));
    intentBuilder.setSecondaryToolbarColor(
        ContextCompat.getColor(context, R.color.colorPrimaryDark));
    intentBuilder.setShowTitle(true);

    // set start and exit animations
    intentBuilder.setStartAnimations(context, android.R.anim.slide_in_left,
        android.R.anim.slide_out_right);
    intentBuilder.setExitAnimations(context, android.R.anim.slide_out_right,
        android.R.anim.slide_in_left);

    // Once ready, call CustomTabsIntent.Builder.build() to create a CustomTabsIntent
    // NOTE : If you do not have Chrome installed, the intent will launch the default
    // browser installed on the device. The CustomTabsIntent simply launches an
    // implicit intent (android.intent.action.VIEW) and passes an extra data in the
    // intent (i.e. android.support.customtabs.extra.SESSION and
    // android.support.customtabs.extra.TOOLBAR_COLOR) that gets ignored if the
    // default browser cannot process this information.
    CustomTabsIntent customTabsIntent = intentBuilder.build();

    // add share action to menu list
    intentBuilder.addDefaultShareMenuItem();

    // Setup a icon in the menu bar
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_share_white_24dp);
    Intent intent = new Intent(Intent.ACTION_SEND);
    intent.setType("text/plain");
    intent.putExtra(Intent.EXTRA_TEXT, url);
    int requestCode = 100;
    PendingIntent pendingIntent =
        PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    // Map the bitmap, text, and pending intent to this icon
    // Set tint to be true so it matches the toolbar color
    // mark1 
    intentBuilder.setActionButton(bitmap, "Share Link", pendingIntent, true);

    // Set close button icon
    Bitmap bitmapBack =
        BitmapFactory.decodeResource(getResources(), R.drawable.ic_arrow_back_white_24dp);
    // 关闭图标的加载 mark2
    intentBuilder.setCloseButtonIcon(bitmapBack);

    // and launch the desired Url with CustomTabsIntent.launchUrl()
    customTabsIntent.launchUrl(context, uri);
  }
}
