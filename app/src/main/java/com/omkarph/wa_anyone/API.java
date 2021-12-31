package com.omkarph.wa_anyone;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;

public class API {

    public static final int WHATSAPP = 0;
    public static final int WHATSAPP_BUSINESS = 1;
    public static final String appID = BuildConfig.APPLICATION_ID;
    public static final String shareSubject = "Checkout this awesome application: Whatsapp anyone";
    public static final String driveLink = "https://drive.google.com/file/d/1Mk_gpAik0CAxBBMtgbyoFZ-bc5kRFpNp/view?usp=sharing";
    public static final String playStoreLink = "https://play.google.com/store/apps/details?id=" + appID;
    public static final String shareMessage =
            "With the help of this application, you can whatsapp anyone without saving their phone number !!\n\n"
            + "Download app here:\n"
            + driveLink + "\n";

    /**
     * Triggers whatsapp's public API to directly open the chat
     * This doesn't read/edit any contact on the device.
     * However, it stores a record of it for in-app history.
     * Note- No data leaves your phone !
     *
     * @param  phone  Phone with country code, eg. +919988776655
     * @param appContext Context of the activity/fragment to trigger the API from
     */
    public static void goToWhatsapp(String phone, int WHATSAPP_TYPE, Context appContext){
        String url = "https://wa.me/"+phone;
        try{
            PackageManager packageManager = appContext.getPackageManager();
            Intent i = new Intent(Intent.ACTION_VIEW);
            if(WHATSAPP_TYPE == WHATSAPP_BUSINESS)
                i.setPackage("com.whatsapp.w4b");
            else
                i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));

            if (i.resolveActivity(packageManager) != null) {
                Log.e("API", "Opening chat to desired whatsapp variant");
                appContext.startActivity(i);
            }else {
                if(WHATSAPP_TYPE == WHATSAPP_BUSINESS){
                    Toast.makeText(appContext, "Whatsapp Business isn't installed\nSelect whatsapp instead", Toast.LENGTH_LONG).show();
                    return;
                }
                Log.e("API", "Opening fallback whatsapp Browser API");
                Intent fallback = new Intent(Intent.ACTION_VIEW);
                fallback.setData(Uri.parse(url));
                appContext.startActivity(fallback);
            }
        } catch(Exception e) {
            Log.e("API", e.toString());
            Log.e("API", "Couldn't resolve API");
            Toast.makeText(appContext, "Some error occured while opening whatsapp :(\nContact the developers for more info", Toast.LENGTH_LONG).show();
        }
    }


    /**
     * @param appContext Context of the activity/fragment to trigger the API from
     */
    public static void shareApp(Activity appContext){
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
//            appContext.startService(Intent.createChooser(shareIntent, "How'd you like to share our app.."));
            appContext.startActivity(Intent.createChooser(shareIntent, "How'd you like to share our app.."));
        } catch(Exception e) {
//            Toast.makeText(appContext, "Some error occured while sharing :(", Toast.LENGTH_LONG).show();
            Toast.makeText(appContext, e.toString(), Toast.LENGTH_LONG).show();
            Log.e("Share exception", "exception", e);
        }
    }

    private static void legacyRate(Activity appContext){
        Uri uri = Uri.parse("market://details?id="+appID);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);

        // To count with Play market backstack, After pressing back button, to taken back to our application, following flags should be added to the intent.
//        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

        try {
            appContext.startService(goToMarket);
        } catch (ActivityNotFoundException exception) {
            appContext.startService(
                    new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id="+appID)));
        }catch(Exception e){
            Log.e("Legacy rate exception", "exception", e);
        }
    }

    /**
     * @param appContext Context of the activity/fragment to trigger the API from
     */
    public static void rateApp(Activity appContext){
        ReviewManager manager = ReviewManagerFactory.create(appContext);
        Task<ReviewInfo> request = manager.requestReviewFlow();
        Log.i("Debug", "Created task");
        request.addOnCompleteListener(task -> {
            try{
                if (task.isSuccessful()) {
                    Log.i("Debug", "Task successful");
                    // We can get the ReviewInfo object
                    ReviewInfo reviewInfo = task.getResult();
                    Log.i("Review info", "No success");
                    Task flow = manager.launchReviewFlow(appContext, reviewInfo);
                    Log.i("Debug", "Flow launched");
                    flow.addOnCompleteListener(task2 -> {});
                } else {
                    Log.i("Rate unsuccessful", "No success");
                    // There was some problem, continue regardless of the result
                }
            }catch(Exception e){
                Log.e("In app rate exception", "exception", e);
                legacyRate(appContext);
            }
        });
    }
}
