package com.srinivasinfotech.computertests;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

import java.util.Objects;

public class SetsActivity extends AppCompatActivity {

    TextView set1,set2,set3,set4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);

        Bundle b=getIntent().getExtras();
        final String Key=b.getString("key");
        final String title=b.getString("key1");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setSubtitle("Sets");
        /* *************** native ad************************** */
        MobileAds.initialize(this, getString(R.string.app_id));
        AdLoader adLoader = new AdLoader.Builder(this, getString(R.string.native_ad_id))
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {

                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().build();

                        TemplateView template = findViewById(R.id.my_template);
                        template.setStyles(styles);
                        template.setNativeAd(unifiedNativeAd);


                    }
                })
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());
           /* ***********************native ad end******************* */
        set1=findViewById(R.id.set1);
        set2=findViewById(R.id.set2);
        set3=findViewById(R.id.set3);
        set4=findViewById(R.id.set4);
        set1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), QuestionsActivity.class);
                i.putExtra("s1","Set-1");
                i.putExtra("key", Key);
                i.putExtra("key1",title);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        set2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), QuestionsActivity.class);
                i.putExtra("s1","Set-2");
                i.putExtra("key", Key);
                i.putExtra("key1",title);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        set3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), QuestionsActivity.class);
                i.putExtra("s1","Set-3");
                i.putExtra("key", Key);
                i.putExtra("key1",title);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        set4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), QuestionsActivity.class);
                i.putExtra("s1","Set-4");
                i.putExtra("key", Key);
                i.putExtra("key1",title);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                Intent intent=new Intent(SetsActivity.this,MainActivity2.class);
                startActivity(intent);
                finishAffinity();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                /*if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }*/
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void onBackPressed() {
        Intent intent=new Intent(SetsActivity.this,MainActivity2.class);
        startActivity(intent);
        finishAffinity();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}