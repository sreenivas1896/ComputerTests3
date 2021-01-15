package com.srinivasinfotech.computertests;


import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.navigation.NavigationView;


import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    DrawerLayout drawer;
    AdLoader adLoader;
    UnifiedNativeAd adobj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setSubtitle(R.string.app_name);
        /* *************** native ad************************** */
        MobileAds.initialize(MainActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
        AdLoader.Builder builder = new AdLoader.Builder(MainActivity.this, getString(R.string.native_ad_id));
        builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                //Toast.makeText(MainActivity.this, loadAdError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                adobj = unifiedNativeAd;
                //Toast.makeText(MainActivity.this, "Ad loaded", Toast.LENGTH_SHORT).show();
            }
        });
        adLoader = builder.build();
        adLoader.loadAd(new AdRequest.Builder().build());
        /* ***********************native ad end******************* */

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_rateApp, R.id.nav_share)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.nav_rateApp:
                        Toast.makeText(MainActivity.this, "rate app", Toast.LENGTH_SHORT).show();
                        Uri uri = Uri.parse("market://details?id=" + getPackageName());
                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        try {
                            startActivity(goToMarket);
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                        }
                        break;
                    case R.id.nav_share:
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_SEND);
                        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                        intent.putExtra(Intent.EXTRA_TEXT, "Install This Computer Awareness tests app for all competitive exams-> https://play.google.com/store/apps/details?id" + getPackageName());
                        intent.setType("text/plain");
                        startActivity(intent);

                        break;
                    case R.id.privacy:
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://sites.google.com/view/computerawarenesstests/privacy-policy")));

                        break;
                    case R.id.terms:
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://sites.google.com/view/computerawarenesstests/terms-conditions")));

                        break;
                    default:
                        break;

                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_about:

                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.activity_about);

                TextView aboutUs = dialog.findViewById(R.id.aboutus);
                aboutUs.setText(Html.fromHtml("<big>" + "<b>" + "<font color=yellow>" + getString(R.string.app_name) + "</font>" + "</b>" + "</big>" + "<br/>"
                        + "Version: " + getString(R.string.version_name) + "<br/>" + "<br/>"
                        + "<i>" + "Created by  " + "<b>" + getString(R.string.author_name) + "</b>" + "</i>" + "<br/>" + "<br/>"
                        + "Copyright \u00a9 " + Calendar.getInstance().get(Calendar.YEAR) + "<br/>"
                        + "All rights reserved."));
                TextView OK = dialog.findViewById(R.id.ok);
                OK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void onBackPressed() {
        ExitDialog exitDialog = new ExitDialog(MainActivity.this, this.adobj);
        exitDialog.show();
        Window window = exitDialog.getWindow();
        assert window != null;
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

}