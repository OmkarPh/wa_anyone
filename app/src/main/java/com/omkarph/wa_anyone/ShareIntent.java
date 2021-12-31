package com.omkarph.wa_anyone;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.widget.Toast;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ShareIntent extends IntentService {

    public ShareIntent() {
        super("ShareIntent");
    }

    public static void shareApp(Context context) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Checkout this awesome application: Whatsapp anyone");
            String shareMessage= "With the help of this application, you can whatsapp anyone without saving their phone number !!\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            context.startService(Intent.createChooser(shareIntent, "How'd you like to share our app.."));
//            startActivity(Intent.createChooser(shareIntent, "How'd you like to share our app.."));
        } catch(Exception e) {
            Toast.makeText(context, "Some error occured while sharing :(", Toast.LENGTH_LONG).show();
        }

//        Intent intent = new Intent(context, ShareIntent.class);
//        intent.setAction(ACTION_BAZ);
//        intent.putExtra(EXTRA_PARAM1, param1);
//        intent.putExtra(EXTRA_PARAM2, param2);
//        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            shareApp(this);
        }
    }
}