package com.example.sh.androidregisterandlogin.TotalBattery;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.databinding.DataBindingUtil;

import android.os.BatteryManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.databinding.ActivityBatteryBinding;

public class BatteryActivity extends AppCompatActivity {
    private ActivityBatteryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_battery);

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
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
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
            binding.tvPercentage.setText("배터리 : " + level + "%");
            binding.tvTitle.setText("상태:\n\n" +
                    "온도:\n\n" +
                    "충전상태:\n\n");
            binding.tvDescription.setText(battery_condition + "\n\n" +
                    "" + temperature_c + "" + (char) 0x00B0 + "C/" + temperature_f + "" + (char) 0x00B0 + "F\n\n" +
                    "" + charging_status + "\n\n");

            int levels = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            float percentage = levels / (float) scale;
//            update the progress bar to display current battery charged percentage
            int mProgressStatus = (int) ((percentage) * 100);
            binding.pb.setProgress(mProgressStatus);
        }
    };
}
