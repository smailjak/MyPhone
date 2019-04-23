package com.example.sh.androidregisterandlogin.TotalApp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.sh.androidregisterandlogin.R;
import java.util.ArrayList;
import java.util.List;

public class UserAppsActivity extends AppCompatActivity {

    private List<AppList> installedApps;
    private AppAdapter installedAppAdapter;
//    Context userAppsContext; //Context 를 여기서 선언을 하고 installedAppAdapter = new AppAdapter(~~~ 생성자에 넣게 되면 오류가 발생한다.
    //    ListView
    ListView userInstalledAppLV;
    TextView appsCount;
    List<PackageInfo> packs;
    ArrayList<AppList> apps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_apps);
//        ListView
        userInstalledAppLV = findViewById(R.id.installed_app_list);
        appsCount = findViewById(R.id.appsCount);
//        call method to get installed apps
        installedApps = getInstalledApps();
//        Adapter
        installedAppAdapter = new AppAdapter(UserAppsActivity.this, installedApps);
//        set adapter
        userInstalledAppLV.setAdapter(installedAppAdapter);
//        list item click listener

        userInstalledAppLvClick();
        String size = Integer.toString(userInstalledAppLV.getCount());
        appsCount.setText("어플개수  : " + size);
    }

    private void userInstalledAppLvClick() {
        userInstalledAppLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long id) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(UserAppsActivity.this);
//                Alert dialog builder
                builder.setTitle("메뉴").setIcon(R.drawable.app); //여기서 다이얼 로그 색깔을 바꾸고 싶은데 어떻게 해야할까 ??
//                set title
                String[] options = {"앱실행", "앱정보", "앱삭제"};
//                set options
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int optionsNumber) {
                        if (optionsNumber == 0) {
//                            it means "Open App" is clicked
                            Intent intent = getPackageManager().getLeanbackLaunchIntentForPackage(installedApps.get(i).packages);
                            if (intent != null) {
                                startActivity(intent);
                            } else {
//                                if anything goes wrong display error message
                                Toast.makeText(UserAppsActivity.this, "Error , please tr again", Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (optionsNumber == 1) {
//                            it means "App Info" is clicked
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.setData(Uri.parse("package:" + installedApps.get(i).packages));
//                            show package name in toast(optional)
                            Toast.makeText(UserAppsActivity.this, installedApps.get(i).packages, Toast.LENGTH_SHORT).show();
                            startActivity(intent);  //start / open settings activity
                        }
                        if (optionsNumber == 2) {
//                            it means "Uninstall" is clicked
                            String packages = installedApps.get(i).packages; //get package name to uninstall
                            Intent intent = new Intent(Intent.ACTION_DELETE); // intent to delete / uninstall app
                            intent.setData(Uri.parse("package : " + packages));
                            startActivity(intent);
                            recreate(); // restart activity to update app list after uninstalling app
                        }
                    }
                });
//                show dialog
                builder.show();
            }
        });
    }

    //    get app information
    private List<AppList> getInstalledApps() {

        apps = new ArrayList<>();
        packs = getPackageManager().getInstalledPackages(0);

        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
//              validate if app is not system app
            if ((!isSystemPackage(p))) {
//                  get application name
                String appName = p.applicationInfo.loadLabel(getPackageManager()).toString();
//                  get application icon
                Drawable icon = p.applicationInfo.loadIcon(getPackageManager());
//                  get application package name
                String packages = p.applicationInfo.packageName;
//                  get application version
                String version = p.versionName;

//                add data
                apps.add(new AppList(appName, icon, packages, version));
            }
        }

        return apps;
    }

    private boolean isSystemPackage(PackageInfo pkgInfo) {
//        function to check if app is not system app because we will display only user apps
        return (pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;

    }

    @Override
    public boolean onSupportNavigateUp() {
//        go back to previous activity on back button click
        onBackPressed();
        return true;
    }
}
