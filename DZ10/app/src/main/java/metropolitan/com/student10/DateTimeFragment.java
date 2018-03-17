package metropolitan.com.student10;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class DateTimeFragment extends Fragment
{

    public DateTimeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_date_time, container, false);

        TextView dateTimeTv = (TextView) rootView.findViewById(R.id.dateTimeTv);

        Timer myTimer = new Timer("MyTimer", true);
        myTimer.scheduleAtFixedRate(new UpdateTask(dateTimeTv), 0, 10);

        return rootView;
    }

    private class UpdateTask extends TimerTask
    {
        TextView dateTimeTv;

        public UpdateTask(TextView dateTimeTv)
        {
            this.dateTimeTv = dateTimeTv;
        }

        public void run()
        {
            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
            dateTimeTv.setText(currentDateTimeString);
        }
    }

}
