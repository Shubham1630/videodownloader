package fcm;

import android.util.Log;

public class MyFirebaseInstanceIDService extends MyFirebaseMessagingService {
    private static final String TAG = "MyFirebaseIIDService";


    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d("NEW_TOKEN",s);

    }

}
