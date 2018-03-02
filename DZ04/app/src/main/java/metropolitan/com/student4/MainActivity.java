package metropolitan.com.student4;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener,
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SettingsFragment settingsFragment = new SettingsFragment();
        fragmentTransaction.replace(android.R.id.content, settingsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.registerOnSharedPreferenceChangeListener(this);

        fragmentManager.executePendingTransactions(); //used to force the transactions so that the listeners for date/time picker would work

        Preference datePickerPref = settingsFragment.findPreference("datePicker");
        datePickerPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public boolean onPreferenceClick(Preference preference) {
                showDateDialog();
                return false;
            }
        });

        Preference timePickerPref = settingsFragment.findPreference("timePicker");
        timePickerPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public boolean onPreferenceClick(Preference preference) {
                showTimeDialog();
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals("list_preference"))
        {
            switch (sharedPreferences.getString("list_preference", ""))
            {
                case "Ovaj domaći je loše urađen":
                    {
                        Toast.makeText(this, "Stvarno nije kolega", Toast.LENGTH_LONG).show();
                        break;
                    }
                case "Ovo je najbolji domaći":
                {
                    Toast.makeText(this, "Slažem se u potpunosti", Toast.LENGTH_LONG).show();
                    break;
                }
                case "Ovo je ok domaći":
                {
                    Toast.makeText(this, "Najbolji je kolega", Toast.LENGTH_LONG).show();
                    break;
                }
            }
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
        Log.i("bla","year "+i+" month "+i2+" day "+i3);
    }

    private void showDateDialog()
    {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(this,this, year, month, day).show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Log.i("bla", "Sati: " + hourOfDay + ", minuta: " + minute);
    }

    private void showTimeDialog()
    {
        final Calendar c = Calendar.getInstance();
        int hours = c.get(Calendar.HOUR);
        int minutes = c.get(Calendar.MINUTE);
        new TimePickerDialog(this, this, hours, minutes, true).show();
    }
}
