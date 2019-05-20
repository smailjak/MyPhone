package com.example.sh.androidregisterandlogin.TotalApp;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import androidx.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.TotalDataItem.AppDataItem;
import com.example.sh.androidregisterandlogin.databinding.ActivityUserAppsBinding;
import java.util.ArrayList;
import java.util.List;

public class UserAppsActivity extends AppCompatActivity {

    private ActivityUserAppsBinding binding;
    List<PackageInfo> packs;
    ArrayList<AppDataItem> apps;
    private AppAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=DataBindingUtil.setContentView(this,R.layout.activity_user_apps);
        initRv(binding.rcvApps);
        String size = Integer.toString(getInstalledApps().size());
        binding.tvAppCount.setText("어플개수 : " + size);
    }

    private void initRv(RecyclerView rv) {
        adapter = new AppAdapter(getInstalledApps(), this);
        rv.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);
    }

//    private void userInstalledAppLvClick() {
//        binding.appsRv.set(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long id) {
//                final AlertDialog.Builder builder = new AlertDialog.Builder(UserAppsActivity.this);
////                Alert dialog builder
//                builder.setTitle("메뉴").setIcon(R.drawable.app); //여기서 다이얼 로그 색깔을 바꾸고 싶은데 어떻게 해야할까 ??
////                set title
//                String[] options = {"앱실행", "앱정보", "앱삭제"};
////                set options
//                builder.setItems(options, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int optionsNumber) {
//                        if (optionsNumber == 0) {
////                            it means "Open App" is clicked
//                            Intent intent = getPackageManager().getLeanbackLaunch`IntentForPackage(installedApps.get(i).packages);
//                            if (intent != null) {
//                                startActivity(intent);
//                            } else {
////                                if anything goes wrong display error message
//                                Toast.makeText(UserAppsActivity.this, "Error , please tr again", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                        if (optionsNumber == 1) {
////                            it means "App Info" is clicked
//                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                            intent.setData(Uri.parse("package:" + installedApps.get(i).packages));
////                            show package name in toast(optional)
//                            Toast.makeText(UserAppsActivity.this, installedApps.get(i).packages, Toast.LENGTH_SHORT).show();
//                            startActivity(intent);  //start / open settings activity
//                        }
//                        if (optionsNumber == 2) {
////                            it means "Uninstall" is clicked
//                            String packages = installedApps.get(i).packages; //get package name to uninstall
//                            Intent intent = new Intent(Intent.ACTION_DELETE); // intent to delete / uninstall app
//                            intent.setData(Uri.parse("package : " + packages));
//                            startActivity(intent);
//                            recreate(); // restart activity to update app list after uninstalling app
//                        }
//                    }
//                });
////                show dialog
//                builder.show();
//            }
//        });
//    }

    //    get app information
    private List<AppDataItem> getInstalledApps() {

        apps = new ArrayList<>();
        packs = getPackageManager().getInstalledPackages(0);

        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
//              validate if app is not system app
            if ((!isSystemPackage(p))) {
                String appName = p.applicationInfo.loadLabel(getPackageManager()).toString();
                Drawable icon = p.applicationInfo.loadIcon(getPackageManager());
                String packages = p.applicationInfo.packageName;
                String version = p.versionName;
//                add data
                apps.add(new AppDataItem(appName, icon, packages, version));
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
