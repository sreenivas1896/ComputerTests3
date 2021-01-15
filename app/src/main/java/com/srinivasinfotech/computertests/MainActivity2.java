package com.srinivasinfotech.computertests;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.efaso.admob_advanced_native_recyvlerview.AdmobNativeAdAdapter;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Objects;


public class MainActivity2 extends AppCompatActivity {


    String[] titleArray={"1.Introduction to Computer", "2.Computer Architecture", "3.Computer Hardware ","4.Computer Memory",
            "5.Computer Software","6.Operating System", "7.Programming Concepts ","8.Microsoft Windows",
            "9.MS Office","10.Database Concepts",
            "11.Data Communication and Networking","12.Internet and its Services","13.Computer Security"};

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chapters");

        recyclerView=findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        CustomRecyclerAdapter customAdapter=new CustomRecyclerAdapter();
        //recyclerView.setAdapter(customAdapter);
        AdmobNativeAdAdapter admobNativeAdAdapter=AdmobNativeAdAdapter.Builder
                .with(
                        getString(R.string.native_ad_id),//Create a native ad id from admob console
                        customAdapter,//The adapter you would normally set to your recyClerView
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
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item,parent,false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder,final int position) {

        //holder.imageView.setImageResource(imageArray[position]);
        holder.textView.setText(titleArray[position]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), titleArray[position], Toast.LENGTH_SHORT).show();

                Intent i = new Intent(getApplicationContext(), SetsActivity.class);
                String Key=""+(position+1);
                i.putExtra("key", Key);
                i.putExtra("key1",titleArray[position]);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

// ************************************
            }
        });

    }

    @Override
    public int getItemCount() {
        return titleArray.length;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        //ImageView imageView;
        TextView textView;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            //imageView=itemView.findViewById(R.id.image_title);
            textView=itemView.findViewById(R.id.title);
        }
    }

}
    /*################### end #################### */

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finishAffinity();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onBackPressed() {

        Intent i=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finishAffinity();
    }


}