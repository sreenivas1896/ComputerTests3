package com.srinivasinfotech.computertests;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.efaso.admob_advanced_native_recyvlerview.AdmobNativeAdAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class ShowQAresultActivity extends AppCompatActivity {
    String[] selAns,answersArray,qa;
    String ansOpt;

    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_q_aresult);

        MobileAds.initialize(this, getString(R.string.app_id));
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitialAd_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle q=this.getIntent().getExtras();
        assert q != null;
        qa=q.getStringArray("QuestionsArray");
        //opt=q.getStringArray("optArray");
        answersArray=q.getStringArray("AnswersArray");
        selAns=q.getStringArray("selAns");
        String[] ti=q.getStringArray("keyTit");
        getSupportActionBar().setTitle("Answers");
        getSupportActionBar().setSubtitle(ti[0]+"("+ti[1]+")");

        RecyclerView recyclerView=findViewById(R.id.lv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        CustomRecyclerAdapter ca=new CustomRecyclerAdapter();
        //lv.setAdapter(ca);
        AdmobNativeAdAdapter admobNativeAdAdapter=AdmobNativeAdAdapter.Builder
                .with(
                        getString(R.string.native_ad_id),//Create a native ad id from admob console
                        ca,//The adapter you would normally set to your recyClerView
                        "small"//Set it with "small","medium" or "custom"
                )
                .adItemIterval(3)//native ad repeating interval in the recyclerview
                .build();
        recyclerView.setAdapter(admobNativeAdAdapter);//set your RecyclerView adapter with the admobNativeAdAdapter

    }

    //  *************************** recycler view *****************************
    /*########## Recycle view Adapter class ###########*/
    public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.CustomViewHolder>{

        @NonNull
        @Override
        public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.result_list_item,parent,false);
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final CustomViewHolder holder, final int position) {

            holder.q.setText(Html.fromHtml(qa[position*6]));
            holder.opt1.setText(qa[(position * 6)+1]+"\n"+qa[(position*6)+2]
                    +"\n"+qa[(position*6)+3]+"\n"+qa[(position*6)+4]
                    +"\n"+qa[(position*6)+5]);


            holder.cora.setText("Correct Answer =>"+"\n"+answersArray[position]);
            if(!selAns[position].equals("null")){
                holder.sela.setText("Your Answer =>"+"\n"+selAns[position]);
            }else {holder.sela.setText("Not Attempted");
            }

            if(answersArray[position].equals(selAns[position])){
                holder.tickmark.setImageResource(R.drawable.ic_rightmark);
                holder.sela.setTextColor(Color.parseColor("#1B5E20"));
            }
            else {
                holder.sela.setTextColor(Color.RED);
                holder.tickmark.setImageResource(R.drawable.ic_wrongmark);
            }

        }

        @Override
        public int getItemCount() {
            return qa.length/6;
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {
            ImageView tickmark;
            TextView q,cora,sela,opt1;
            public CustomViewHolder(@NonNull View itemView) {
                super(itemView);
                 q=itemView.findViewById(R.id.question);
                 cora=itemView.findViewById(R.id.corAns);
                 sela=itemView.findViewById(R.id.selAns);
                 tickmark=itemView.findViewById(R.id.mark);
                 opt1=itemView.findViewById(R.id.op1);

            }
        }

    }
    /*################### end #################### */

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);


    }
}