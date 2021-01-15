package com.srinivasinfotech.computertests;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

public class ExitDialog extends Dialog
{
    UnifiedNativeAd ad;
    Activity activity;
    public ExitDialog(Activity activity, UnifiedNativeAd ad)
    {
        super(activity);
        this.activity = activity;
        this.ad = ad;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_activity_dialog);
        Button yes =findViewById(R.id.btn_yes);
        Button no =findViewById(R.id.btn_no);
        yes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                activity.finish();
            }
        });
        no.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dismiss();
            }
        });
        TemplateView ad = findViewById(R.id.my_template);
        if(this.ad == null)
        {
            ad.setVisibility(View.GONE);
        }
        else
        {
            ad.setVisibility(View.VISIBLE);
            ad.setNativeAd(this.ad);
        }
    }
}
