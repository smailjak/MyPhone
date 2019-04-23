package com.example.sh.androidregisterandlogin.TotalBattery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sh.androidregisterandlogin.R;

public class BatteryActivity extends AppCompatActivity {

    //    Views
    TextView textView1, textView2, batteryPercentage, mTextViewPercentage;

    private double batteryCapacity;
    private ProgressBar mProgressBar;
    private int mProgressStatus = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);

        batteryPercentage = findViewById(R.id.battery_percentage);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        mTextViewPercentage = findViewById(R.id.tv_percentage);
        mProgressBar = findViewById(R.id.pb);

        Context context = getApplicationContext();
        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        context.registerReceiver(mBroadCastReceiver, iFilter);

        powerProfile();

    }

    public void powerProfile() {
        Object mPowerProfile = null;
        String POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile";
        try {
            mPowerProfile = Class.forName(POWER_PROFILE_CLASS).getConstructor(Context.class).newInstance(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            batteryCapacity = (Double) Class
                    .forName(POWER_PROFILE_CLASS)
                    .getMethod("getAveragePower", String.class)
                    .invoke(mPowerProfile, "battery.capacity");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // goto previous activity when backbutton from actionbar is clicked
        return true;
    }

    private BroadcastReceiver mBroadCastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String charging_status = "", battery_condition = "";
//            Get battery percentage
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
//            get battery condition
            int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
            if (health == BatteryManager.BATTERY_HEALTH_COLD) {
                battery_condition = "Cold";
            }
            if (health == BatteryManager.BATTERY_HEALTH_DEAD) {
                battery_condition = "Dead";
            }
            if (health == BatteryManager.BATTERY_HEALTH_GOOD) {
                battery_condition = "Good";
            }
            if (health == BatteryManager.BATTERY_HEALTH_OVERHEAT) {
                battery_condition = "Over Heat";
            }
            if (health == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE) {
                battery_condition = "Over Voltage";
            }
            if (health == BatteryManager.BATTERY_HEALTH_UNKNOWN) {
                battery_condition = "Unknown";
            }
            if (health == BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE) {
                battery_condition = "Unspecified Failure";
            }
//             온도
            int temperature_c = (intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)) / 10;
            int temperature_f = (int) (temperature_c * 1.8 + 32);
//               배터리 상태
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
                charging_status = "충전중";
            }
            if (status == BatteryManager.BATTERY_STATUS_DISCHARGING) {
                charging_status = "충전해줭";
            }
            if (status == BatteryManager.BATTERY_STATUS_FULL) {
                charging_status = "배불러용";
            }
            if (status == BatteryManager.BATTERY_STATUS_UNKNOWN) {
                charging_status = "Unknown";
            }
            if (status == BatteryManager.BATTERY_STATUS_NOT_CHARGING) {
                charging_status = "충전안했어!";
            }

//            Display the output of battery status
            batteryPercentage.setText("배터리 : " + level + "%");
            textView1.setText("상태:\n\n" +
                    "온도:\n\n" +
                    "충전상태:\n\n");
            textView2.setText(battery_condition + "\n\n" +
                    "" + temperature_c + "" + (char) 0x00B0 + "C/" + temperature_f + "" + (char) 0x00B0 + "F\n\n" +
                    "" + charging_status + "\n\n");

            int levels = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            float percentage = levels / (float) scale;
//            update the progress bar to display current battery charged percentage
            mProgressStatus = (int) ((percentage) * 100);
            mTextViewPercentage.setText("" + mProgressStatus + "%");
            mProgressBar.setProgress(mProgressStatus);
        }
    };

}
