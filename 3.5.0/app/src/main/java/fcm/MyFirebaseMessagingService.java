package fcm;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();


    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);

        Log.e("NEW_TOKEN", s);


    }


}

