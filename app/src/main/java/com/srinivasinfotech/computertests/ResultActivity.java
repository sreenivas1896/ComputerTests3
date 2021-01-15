package com.srinivasinfotech.computertests;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

public class ResultActivity extends AppCompatActivity {
    TextView showqa, percent, t_taken;
    Button back,home;
    int[] sc;
    String[] selAnsarray, answersArray, optionsArray,questionsArray;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Result");
        AdView mAdView;
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        MobileAds.initialize(this, getString(R.string.app_id));
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitialAd_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
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
        t_taken = (TextView) findViewById(R.id.time_taken);
        back = (Button) findViewById(R.id.back_btn);
        home=(Button) findViewById(R.id.home_btn);
        showqa = findViewById(R.id.showqa);
        percent = findViewById(R.id.per);

        Bundle q = this.getIntent().getExtras();
        assert q != null;
        questionsArray = q.getStringArray("questionArray");
        //optionsArray = q.getStringArray("optArray");
        answersArray = q.getStringArray("AnswersArray");
        selAnsarray = q.getStringArray("selAns");
        sc = q.getIntArray("score");
        final String[] tit=q.getStringArray("keyTitle");
        double sc1, sc2;
        sc1 = sc[0];
        sc2 = sc[2];
        getSupportActionBar().setSubtitle(tit[0]+"("+tit[1]+")");
        int per = (int) Math.round((sc1 / sc2) * 100);

        percent.setText("" + per + "%");
        String t = q.getString("timeTaken");
        t_taken.setText(Html.fromHtml("<font color=#0099ff>" + "Time taken: "+ "<b>" + t + "</b>" + "</font>" + "<br/>"
                +"<font color=#2E8B57>" + "Correct Answers: " + "<b>" + sc[0] + "</b>" + "</font>" + "<br/>"
                + "<font color=red>" + "Wrong Answers: " + "<b>" + sc[1] + "</b>" + "</font>" + "<br/>"
                + "<font color=#800000>" + "Your Efficiency: " + "<b>" + per + "%" + "</b>" + "</font>"));

        ProgressBar p = findViewById(R.id.stats);
        p.setProgress(per);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                    Intent openMainActivity = new Intent(ResultActivity.this, SetsActivity.class);
                    openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivityIfNeeded(openMainActivity, 0);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openMainActivity = new Intent(ResultActivity.this, MainActivity2.class);
                startActivity(openMainActivity);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        showqa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), ShowQAresultActivity.class);
                in.putExtra("selAns", selAnsarray);
                in.putExtra("QuestionsArray", questionsArray);
                in.putExtra("AnswersArray", answersArray);
                in.putExtra("keyTit",tit);
                startActivity(in);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                Intent openMainActivity = new Intent(ResultActivity.this, SetsActivity.class);
                openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(openMainActivity, 0);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                    Intent openMainActivity = new Intent(ResultActivity.this, SetsActivity.class);
                    openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivityIfNeeded(openMainActivity, 0);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onBackPressed() {

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
            Intent openMainActivity = new Intent(ResultActivity.this, SetsActivity.class);
            openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivityIfNeeded(openMainActivity, 0);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }
}
