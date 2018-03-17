package metropolitan.com.student10.fcm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class FirebaseMessageService extends Service
{
    public FirebaseMessageService()
    {
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
