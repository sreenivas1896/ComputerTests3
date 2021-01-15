package com.srinivasinfotech.computertests;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.srinivasinfotech.computertests.QuestionsLibrary.Chapter1;
import com.srinivasinfotech.computertests.QuestionsLibrary.Chapter10;
import com.srinivasinfotech.computertests.QuestionsLibrary.Chapter11;
import com.srinivasinfotech.computertests.QuestionsLibrary.Chapter12;
import com.srinivasinfotech.computertests.QuestionsLibrary.Chapter13;
import com.srinivasinfotech.computertests.QuestionsLibrary.Chapter2;
import com.srinivasinfotech.computertests.QuestionsLibrary.Chapter3;
import com.srinivasinfotech.computertests.QuestionsLibrary.Chapter4;
import com.srinivasinfotech.computertests.QuestionsLibrary.Chapter5;
import com.srinivasinfotech.computertests.QuestionsLibrary.Chapter6;
import com.srinivasinfotech.computertests.QuestionsLibrary.Chapter7;
import com.srinivasinfotech.computertests.QuestionsLibrary.Chapter8;
import com.srinivasinfotech.computertests.QuestionsLibrary.Chapter9;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class QuestionsActivity extends AppCompatActivity {

    String[] questions, opt, answers,Library;
    String[] sendAns=new String[50];
    int[] score1 = new int[50];
    String[] selAns = {"null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null",
            "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null",
            "null", "null", "null", "null"};
    int[] selAnsCount = new int[50];
    TextView q, op1, op2, op3, op4, op5, qNum, time,Ok;

    Button tv, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10,
            tv11, tv12, tv13, tv14, tv15, tv16, tv17, tv18, tv19, tv20, tv21, tv22, tv23, tv24, tv25, tv26, tv27, tv28, tv29, tv30;
    FloatingActionButton three, next, two, prev, speak, mute;
    int i = 0, sc, selAnsTotal;
    String className, tit, timeTaken, timeT,sets;
    CountDownTimer ct;
    Dialog dialog1;
    private TextToSpeech tts;
    String Q, OP1, OP2, OP3, OP4, OP5;
    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        MobileAds.initialize(this, getString(R.string.app_id));
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitialAd_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        getLibrary();
        Bundle b1 = this.getIntent().getExtras();
        tit = b1.getString("key1");
        Bundle b = this.getIntent().getExtras();
        className = b.getString("key");
        sets=b.getString("s1");

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(tit);
        getSupportActionBar().setSubtitle(sets);

        AdView mAdView;
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        q = (TextView) findViewById(R.id.q1);
        op1 = (TextView) findViewById(R.id.a1);
        op2 = (TextView) findViewById(R.id.a2);
        op3 = (TextView) findViewById(R.id.a3);
        op4 = (TextView) findViewById(R.id.a4);
        op5 = (TextView) findViewById(R.id.a5);
        qNum = (TextView) findViewById(R.id.score);
        prev = findViewById(R.id.prev);
        next = findViewById(R.id.next);
        speak = findViewById(R.id.speaker);
        three = findViewById(R.id.three);
        mute = findViewById(R.id.mute);
        time = findViewById(R.id.time);

        updateQuestion();
        selectionResume();
        optionSelection();
        startTime();
        prev.setImageResource(R.drawable.ic_left_arrow_red);
        next.setImageResource(R.drawable.ic_right_arrow);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (i < questions.length/6 - 1) {
                    selectionReset();
                    nextQuestion();
                    selectionResumeNext();
                    optionSelection();
                    prev.setImageResource(R.drawable.ic_left_arrow);
                    next.setImageResource(R.drawable.ic_right_arrow);
                    i++;
                    //if(i==questions.length-1){next.setText("Finish");}else {next.setText("Next");}
                 if(i==questions.length/6-1){next.setImageResource(R.drawable.ic_done);}
                } else {

                    //submitResult();
                    selAnswerCount();
                    testFinishedDialog();
                    Toast.makeText(QuestionsActivity.this, "Questions Finished", Toast.LENGTH_SHORT).show();
                }
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i > 0) {
                    selectionReset();
                    prevQuestion();
                    selectionResumePrev();
                    optionSelection();
                    prev.setImageResource(R.drawable.ic_left_arrow);
                    next.setImageResource(R.drawable.ic_right_arrow);
                    i--;
                   if(i==0){prev.setImageResource(R.drawable.ic_left_arrow_red);}
                } else if (i == 0) {
                    i = 0;

                    prev.setImageResource(R.drawable.ic_left_arrow_red);
                    next.setImageResource(R.drawable.ic_right_arrow);
                    Toast.makeText(QuestionsActivity.this, "No Questions", Toast.LENGTH_SHORT).show();
                }
            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
                speakerStop();

            }
        });

        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textToSpeech();
                mute.setVisibility(View.VISIBLE);
                speak.setVisibility(View.GONE);
            }
        });

        mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakerStop();
            }
        });
    }

    /* @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/
    public void textToSpeech() {
        tts = new TextToSpeech(QuestionsActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.forLanguageTag("en-US"));
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("error", "This Language is not supported");
                    } else {
                        speakOut();
                    }
                } else
                    Log.e("error", "Initialization Failed!");
            }
        });
    }

    public void speakerStop() {
        if (tts != null) {
            speak.setVisibility(View.VISIBLE);
            mute.setVisibility(View.GONE);
            tts.stop();
            tts.shutdown();
        }
        super.onPause();

    }

    public void speakOut() {
        // Get the text typed
        Q = q.getText().toString();
        OP1 = op1.getText().toString();
        OP2 = op2.getText().toString();
        OP3 = op3.getText().toString();
        OP4 = op4.getText().toString();
        OP5 = op5.getText().toString();
        String text = Q + "" + "\n" + OP1 + "" + "\n" + OP2 + "" + "\n" + OP3 + "" + "\n" + OP4 + "" + "\n" + OP5;
        // If no text is typed, tts will read out 'You haven't typed text'
        // else it reads out the text you typed
        if (text.length() == 0) {
            tts.setSpeechRate((float) 0.2);
            //tts.speak("", TextToSpeech.QUEUE_FLUSH, null);
        } else {
            tts.setSpeechRate((float) 0.5);
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);


        }

    }

    private void updateQuestion() {
        getLibrary();
        q.setText(Html.fromHtml(questions[i*6]));
        op1.setText(questions[(i * 6) + 1]);
        op2.setText(questions[(i * 6) + 2]);
        op3.setText(questions[(i * 6) + 3]);
        op4.setText(questions[(i * 6) + 4]);
        op5.setText(questions[(i * 6) + 5]);
        qNum.setText("Q :" + (i + 1) + "/" + (questions.length/6));

    }

    private void prevQuestion() {
        speakerStop();
        getLibrary();
        q.setText(Html.fromHtml(questions[(i - 1) * 6]));
        op1.setText(questions[(i - 1) * 6+1]);
        op2.setText(questions[(i - 1) * 6 + 2]);
        op3.setText(questions[(i - 1) * 6 + 3]);
        op4.setText(questions[(i - 1) * 6 + 4]);
        op5.setText(questions[(i - 1) * 6 + 5]);
        qNum.setText("Q :" + (i) + "/" + (questions.length/6));
        //Toast toast=Toast.makeText(QuestionsActivity.this,"Q: "+(i),Toast.LENGTH_SHORT);
        //toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
        //toast.setGravity(Gravity.TOP,0,0);
        //toast.show();
    }

    private void nextQuestion() {
        getLibrary();
        speakerStop();
        q.setText(Html.fromHtml(questions[(i + 1)*6]));
        op1.setText(questions[(i + 1) * 6+1]);
        op2.setText(questions[(i + 1) * 6 + 2]);
        op3.setText(questions[(i + 1) * 6 + 3]);
        op4.setText(questions[(i + 1) * 6 + 4]);
        op5.setText(questions[(i + 1) * 6 + 5]);
        qNum.setText("Q :" + (i + 2) + "/" + (questions.length/6));
        //Toast toast=Toast.makeText(QuestionsActivity.this,"Q: "+(i+2),Toast.LENGTH_SHORT);
        //toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
        //toast.setGravity(Gravity.TOP,0,0);
        //toast.show();
    }

    private void optionSelection() {

        /*speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selAns[i] = "null";
                selectionReset();
                Toast.makeText(QuestionsActivity.this, "Clear Selection", Toast.LENGTH_SHORT).show();
            }
        });*/
        op1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionReset();
                op1.setBackgroundColor(Color.parseColor("#0099ff"));
                op1.setTextColor(Color.WHITE);
                selAns[i] = questions[(i * 6) + 1];
                if (answers[i].equals("(1)")) {
                    score1[i] = 1;

                }
            }
        });
        op2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionReset();
                op2.setBackgroundColor(Color.parseColor("#0099ff"));
                op2.setTextColor(Color.WHITE);
                selAns[i] = questions[(i * 6) + 2];
                if (answers[i].equals("(2)")) {
                    score1[i] = 1;

                }
            }
        });
        op3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionReset();
                op3.setBackgroundColor(Color.parseColor("#0099ff"));
                op3.setTextColor(Color.WHITE);
                selAns[i] = questions[(i * 6) + 3];
                if (answers[i].equals("(3)")) {
                    score1[i] = 1;

                }
            }
        });
        op4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionReset();
                op4.setBackgroundColor(Color.parseColor("#0099ff"));
                op4.setTextColor(Color.WHITE);
                selAns[i] = questions[(i * 6) + 4];
                if (answers[i].equals("(4)")) {
                    score1[i] = 1;

                }
            }
        });
        op5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionReset();
                op5.setBackgroundColor(Color.parseColor("#0099ff"));
                op5.setTextColor(Color.WHITE);
                selAns[i] = questions[(i * 6) + 5];
                if (answers[i].equals("(5)")) {
                    score1[i] = 1;

                }
            }
        });

    }

    public void selectionReset() {
        op1.setBackgroundColor(Color.WHITE);
        op1.setTextColor(Color.BLACK);
        op2.setBackgroundColor(Color.WHITE);
        op2.setTextColor(Color.BLACK);
        op3.setBackgroundColor(Color.WHITE);
        op3.setTextColor(Color.BLACK);
        op4.setBackgroundColor(Color.WHITE);
        op4.setTextColor(Color.BLACK);
        op5.setBackgroundColor(Color.WHITE);
        op5.setTextColor(Color.BLACK);
    }

    public void selectionResume() {
        if (selAns[i] == questions[(i * 6) + 1]) {
            op1.setBackgroundColor(Color.parseColor("#0099ff"));
            op1.setTextColor(Color.WHITE);
        } else if (selAns[i] == "null") {
            selectionReset();
        }
        if (selAns[i] == questions[(i * 6) + 2]) {
            op2.setBackgroundColor(Color.parseColor("#0099ff"));
            op2.setTextColor(Color.WHITE);
        } else if (selAns[i] == "null") {
            selectionReset();
        }
        if (selAns[i] == questions[(i * 6) + 3]) {
            op3.setBackgroundColor(Color.parseColor("#0099ff"));
            op3.setTextColor(Color.WHITE);
        } else if (selAns[i] == "null") {
            selectionReset();
        }
        if (selAns[i] == questions[(i * 6) + 4]) {
            op4.setBackgroundColor(Color.parseColor("#0099ff"));
            op4.setTextColor(Color.WHITE);
        } else if (selAns[i] == "null") {
            selectionReset();
        }
        if (selAns[i] == questions[(i * 6) + 5]) {
            op5.setBackgroundColor(Color.parseColor("#0099ff"));
            op5.setTextColor(Color.WHITE);
        } else if (selAns[i] == "null") {
            selectionReset();
        }

    }

    public void selectionResumeNext() {
        if (selAns[i + 1] == questions[(i + 1) * 6 + 1]) {
            op1.setBackgroundColor(Color.parseColor("#0099ff"));
            op1.setTextColor(Color.WHITE);
        } else if (selAns[i + 1] == "null") {
            selectionReset();
        }
        if (selAns[i + 1] == questions[(i + 1) * 6 + 2]) {
            op2.setBackgroundColor(Color.parseColor("#0099ff"));
            op2.setTextColor(Color.WHITE);
        } else if (selAns[i + 1] == "null") {
            selectionReset();
        }
        if (selAns[i + 1] == questions[(i + 1) * 6 + 3]) {
            op3.setBackgroundColor(Color.parseColor("#0099ff"));
            op3.setTextColor(Color.WHITE);
        } else if (selAns[i + 1] == "null") {
            selectionReset();
        }
        if (selAns[i + 1] == questions[(i + 1) * 6 + 4]) {
            op4.setBackgroundColor(Color.parseColor("#0099ff"));
            op4.setTextColor(Color.WHITE);
        } else if (selAns[i + 1] == "null") {
            selectionReset();
        }
        if (selAns[i + 1] == questions[(i + 1) * 6 + 5]) {
            op5.setBackgroundColor(Color.YELLOW);
            op5.setTextColor(Color.BLACK);
        } else if (selAns[i + 1] == "null") {
            selectionReset();
        }

    }

    public void selectionResumePrev() {
        if (selAns[i - 1] == questions[(i - 1) * 6 + 1]) {
            op1.setBackgroundColor(Color.parseColor("#0099ff"));
            op1.setTextColor(Color.WHITE);
        } else if (selAns[i - 1] == "null") {
            selectionReset();
        }
        if (selAns[i - 1] == questions[(i - 1) * 6 + 2]) {
            op2.setBackgroundColor(Color.parseColor("#0099ff"));
            op2.setTextColor(Color.WHITE);
        } else if (selAns[i - 1] == "null") {
            selectionReset();
        }
        if (selAns[i - 1] == questions[(i - 1) * 6 + 3]) {
            op3.setBackgroundColor(Color.parseColor("#0099ff"));
            op3.setTextColor(Color.WHITE);
        } else if (selAns[i - 1] == "null") {
            selectionReset();
        }
        if (selAns[i - 1] == questions[(i - 1) * 6 + 4]) {
            op4.setBackgroundColor(Color.parseColor("#0099ff"));
            op4.setTextColor(Color.WHITE);
        } else if (selAns[i - 1] == "null") {
            selectionReset();
        }
        if (selAns[i - 1] == questions[(i - 1) * 6 + 5]) {
            op5.setBackgroundColor(Color.parseColor("#0099ff"));
            op5.setTextColor(Color.WHITE);
        } else if (selAns[i - 1] == "null") {
            selectionReset();
        }

    }


    private void showAlertDialog() {


        dialog1 = new Dialog(this);
        dialog1.setContentView(R.layout.list__item);
        Ok=dialog1.findViewById(R.id.ok);
        ImageView cancel=dialog1.findViewById(R.id.im3);
        tv = dialog1.findViewById(R.id.text);
        tv = dialog1.findViewById(R.id.item);
        tv2 = dialog1.findViewById(R.id.item2);
        tv3 = dialog1.findViewById(R.id.item3);
        tv4 = dialog1.findViewById(R.id.item4);
        tv5 = dialog1.findViewById(R.id.item5);
        tv6 = dialog1.findViewById(R.id.item6);
        tv7 = dialog1.findViewById(R.id.item7);
        tv8 = dialog1.findViewById(R.id.item8);
        tv9 = dialog1.findViewById(R.id.item9);
        tv10 = dialog1.findViewById(R.id.item10);

        tv11 = dialog1.findViewById(R.id.item11);
        tv12 = dialog1.findViewById(R.id.item12);
        tv13 = dialog1.findViewById(R.id.item13);
        tv14 = dialog1.findViewById(R.id.item14);
        tv15 = dialog1.findViewById(R.id.item15);
        tv16 = dialog1.findViewById(R.id.item16);
        tv17 = dialog1.findViewById(R.id.item17);
        tv18 = dialog1.findViewById(R.id.item18);
        tv19 = dialog1.findViewById(R.id.item19);
        tv20 = dialog1.findViewById(R.id.item20);


       Ok.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               dialog1.dismiss();
           }
       });
       cancel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               dialog1.dismiss();
           }
       });

        dialogItemClicks();
        dialogItemClicks1();

        dialog1.show();

    }

    public void dialogItemClicks() {

        if (selAns[0] != "null") {
            tv.setBackgroundResource(R.drawable.ic_dot_green);
        } else {
            tv.setBackgroundResource(R.drawable.ic_dot_grey);
        }
        if (selAns[1] != "null") {
            tv2.setBackgroundResource(R.drawable.ic_dot_green);
        } else {
            tv2.setBackgroundResource(R.drawable.ic_dot_grey);
        }
        if (selAns[2] != "null") {
            tv3.setBackgroundResource(R.drawable.ic_dot_green);
        } else {
            tv3.setBackgroundResource(R.drawable.ic_dot_grey);
        }
        if (selAns[3] != "null") {
            tv4.setBackgroundResource(R.drawable.ic_dot_green);
        } else {
            tv4.setBackgroundResource(R.drawable.ic_dot_grey);
        }
        if (selAns[4] != "null") {
            tv5.setBackgroundResource(R.drawable.ic_dot_green);
        } else {
            tv5.setBackgroundResource(R.drawable.ic_dot_grey);
        }
        if (selAns[5] != "null") {
            tv6.setBackgroundResource(R.drawable.ic_dot_green);
        } else {
            tv6.setBackgroundResource(R.drawable.ic_dot_grey);
        }
        if (selAns[6] != "null") {
            tv7.setBackgroundResource(R.drawable.ic_dot_green);
        } else {
            tv7.setBackgroundResource(R.drawable.ic_dot_grey);
        }
        if (selAns[7] != "null") {
            tv8.setBackgroundResource(R.drawable.ic_dot_green);
        } else {
            tv8.setBackgroundResource(R.drawable.ic_dot_grey);
        }
        if (selAns[8] != "null") {
            tv9.setBackgroundResource(R.drawable.ic_dot_green);
        } else {
            tv9.setBackgroundResource(R.drawable.ic_dot_grey);
        }
        if (selAns[9] != "null") {
            tv10.setBackgroundResource(R.drawable.ic_dot_green);
        } else {
            tv10.setBackgroundResource(R.drawable.ic_dot_grey);
        }

        if (selAns[10] != "null") {
            tv11.setBackgroundResource(R.drawable.ic_dot_green);
        } else {
            tv11.setBackgroundResource(R.drawable.ic_dot_grey);
        }
        if (selAns[11] != "null") {
            tv12.setBackgroundResource(R.drawable.ic_dot_green);
        } else {
            tv12.setBackgroundResource(R.drawable.ic_dot_grey);
        }
        if (selAns[12] != "null") {
            tv13.setBackgroundResource(R.drawable.ic_dot_green);
        } else {
            tv13.setBackgroundResource(R.drawable.ic_dot_grey);
        }
        if (selAns[13] != "null") {
            tv14.setBackgroundResource(R.drawable.ic_dot_green);
        } else {
            tv14.setBackgroundResource(R.drawable.ic_dot_grey);
        }
        if (selAns[14] != "null") {
            tv15.setBackgroundResource(R.drawable.ic_dot_green);
        } else {
            tv15.setBackgroundResource(R.drawable.ic_dot_grey);
        }
        if (selAns[15] != "null") {
            tv16.setBackgroundResource(R.drawable.ic_dot_green);
        } else {
            tv16.setBackgroundResource(R.drawable.ic_dot_grey);
        }
        if (selAns[16] != "null") {
            tv17.setBackgroundResource(R.drawable.ic_dot_green);
        } else {
            tv17.setBackgroundResource(R.drawable.ic_dot_grey);
        }
        if (selAns[17] != "null") {
            tv18.setBackgroundResource(R.drawable.ic_dot_green);
        } else {
            tv18.setBackgroundResource(R.drawable.ic_dot_grey);
        }
        if (selAns[18] != "null") {
            tv19.setBackgroundResource(R.drawable.ic_dot_green);
        } else {
            tv19.setBackgroundResource(R.drawable.ic_dot_grey);
        }
        if (selAns[19] != "null") {
            tv20.setBackgroundResource(R.drawable.ic_dot_green);
        } else {
            tv20.setBackgroundResource(R.drawable.ic_dot_grey);
        }

    }

    public void dialogItemClicks1() {
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 0;
                dialogItemBody();
                tv.setTextColor(Color.WHITE);
                tv.setBackgroundResource(R.drawable.ic_dot_blue);
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 1;
                dialogItemBody();
                tv2.setTextColor(Color.WHITE);
                tv2.setBackgroundResource(R.drawable.ic_dot_blue);
            }
        });
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 2;
                dialogItemBody();
                tv3.setTextColor(Color.WHITE);
                tv3.setBackgroundResource(R.drawable.ic_dot_blue);
            }
        });
        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 3;
                dialogItemBody();
                tv4.setTextColor(Color.WHITE);
                tv4.setBackgroundResource(R.drawable.ic_dot_blue);
            }
        });
        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 4;
                dialogItemBody();
                tv5.setTextColor(Color.WHITE);
                tv5.setBackgroundResource(R.drawable.ic_dot_blue);
            }
        });
        tv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 5;
                dialogItemBody();
                tv6.setTextColor(Color.WHITE);
                tv6.setBackgroundResource(R.drawable.ic_dot_blue);
            }
        });
        tv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 6;
                dialogItemBody();
                tv7.setTextColor(Color.WHITE);
                tv7.setBackgroundResource(R.drawable.ic_dot_blue);
            }
        });
        tv8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 7;
                dialogItemBody();
                tv8.setTextColor(Color.WHITE);
                tv8.setBackgroundResource(R.drawable.ic_dot_blue);
            }
        });
        tv9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 8;
                dialogItemBody();
                tv9.setTextColor(Color.WHITE);
                tv9.setBackgroundResource(R.drawable.ic_dot_blue);
            }
        });
        tv10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 9;
                dialogItemBody();
                tv10.setTextColor(Color.WHITE);
                tv10.setBackgroundResource(R.drawable.ic_dot_blue);
            }
        });
        tv11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 10;
                dialogItemBody();
                tv11.setTextColor(Color.WHITE);
                tv11.setBackgroundResource(R.drawable.ic_dot_blue);
            }
        });
        tv12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 11;
                dialogItemBody();
                tv12.setTextColor(Color.WHITE);
                tv12.setBackgroundResource(R.drawable.ic_dot_blue);
            }
        });
        tv13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 12;
                dialogItemBody();
                tv13.setTextColor(Color.WHITE);
                tv13.setBackgroundResource(R.drawable.ic_dot_blue);
            }
        });
        tv14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 13;
                dialogItemBody();
                tv14.setTextColor(Color.WHITE);
                tv14.setBackgroundResource(R.drawable.ic_dot_blue);
            }
        });
        tv15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 14;
                dialogItemBody();
                tv15.setTextColor(Color.WHITE);
                tv15.setBackgroundResource(R.drawable.ic_dot_blue);
            }
        });
        tv16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 15;
                dialogItemBody();
                tv16.setTextColor(Color.WHITE);
                tv16.setBackgroundResource(R.drawable.ic_dot_blue);
            }
        });
        tv17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 16;
                dialogItemBody();
                tv17.setTextColor(Color.WHITE);
                tv17.setBackgroundResource(R.drawable.ic_dot_blue);
            }
        });
        tv18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 17;
                dialogItemBody();
                tv18.setTextColor(Color.WHITE);
                tv18.setBackgroundResource(R.drawable.ic_dot_blue);
            }
        });
        tv19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 18;
                dialogItemBody();
                tv19.setTextColor(Color.WHITE);
                tv19.setBackgroundResource(R.drawable.ic_dot_blue);
            }
        });
        tv20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 19;
                dialogItemBody();
                tv20.setTextColor(Color.WHITE);
                tv20.setBackgroundResource(R.drawable.ic_dot_blue);
            }
        });

        /*   #######################  ##################################       */
    }

    public void dialogItemBody() {
        selectionReset();
        updateQuestion();
        selectionResume();
        optionSelection();
        Toast toast = Toast.makeText(QuestionsActivity.this, "Question :  " + (i + 1), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
        //toast.setGravity(Gravity.TOP,0,0);
        toast.show();
        dialogItemClicks();
        if(i==0){prev.setImageResource(R.drawable.ic_left_arrow_red);
        next.setImageResource(R.drawable.ic_right_arrow);}
        else if(i==questions.length/6-1){next.setImageResource(R.drawable.ic_done);
            prev.setImageResource(R.drawable.ic_left_arrow);}else {
            prev.setImageResource(R.drawable.ic_left_arrow);
            next.setImageResource(R.drawable.ic_right_arrow);
        }
        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog1.dismiss();

            }
        }, 800);

    }

    public void submitResult() {
        getLibrary();
        getLibAns();
        int scr = 0;
        for (i = 0; i < questions.length/6; i++) {
            scr = scr + score1[i];
        }
        sc = scr;
        int wrong = questions.length/6 - sc;
        int sum = questions.length/6;
        int[] tot = new int[]{sc, wrong, sum};
        Intent in = new Intent(getApplicationContext(), ResultActivity.class);
        in.putExtra("selAns", selAns);
        in.putExtra("score", tot);
        in.putExtra("questionArray", questions);
        //in.putExtra("optArray", opt);
        in.putExtra("AnswersArray", sendAns);
        timeTaken = timeT;
        in.putExtra("timeTaken", timeTaken);
        String[] ti={tit,sets};
        in.putExtra("keyTitle",ti);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void getLibAns(){
        for(int i=0;i<questions.length/6;i++){
            if(answers[i]=="(1)"){
                sendAns[i]=questions[(i * 6) + 1];
            }else if(answers[i]=="(2)"){
                sendAns[i]=questions[(i * 6) + 2];
            }else if(answers[i]=="(3)"){
                sendAns[i]=questions[(i * 6) + 3];
            }else if(answers[i]=="(4)"){
                sendAns[i]=questions[(i * 6) + 4];
            }else if(answers[i]=="(5)"){
                sendAns[i]=questions[(i * 6) + 5];
            }
        }
    }
    public void getLibrary() {

        if (className != null && className.equals("1")) {
            Chapter1 ch1 = new Chapter1();
            if(sets!=null && sets.equals("Set-1")){
                questions = ch1.getQuestionsSet1();
                answers = ch1.getAnswersSet1();

            }else if(sets!=null && sets.equals("Set-2")){
                questions = ch1.getQuestionsSet2();
                answers = ch1.getAnswersSet2();

            }else if(sets!=null && sets.equals("Set-3")){
                questions = ch1.getQuestionsSet3();
                answers = ch1.getAnswersSet3();

            }else if(sets!=null && sets.equals("Set-4")){
                questions = ch1.getQuestionsSet4();
                answers = ch1.getAnswersSet4();

            }

        } else if (className != null && className.equals("2")) {
            Chapter2 ch2 = new Chapter2();
            if(sets!=null && sets.equals("Set-1")){
                questions = ch2.getQuestionsSet1();
                answers = ch2.getAnswersSet1();

            }else if(sets!=null && sets.equals("Set-2")){
                questions = ch2.getQuestionsSet2();
                answers = ch2.getAnswersSet2();

            }else if(sets!=null && sets.equals("Set-3")){
                questions = ch2.getQuestionsSet3();
                answers = ch2.getAnswersSet3();

            }else if(sets!=null && sets.equals("Set-4")){
                questions = ch2.getQuestionsSet4();
                answers = ch2.getAnswersSet4();

            }
        } else if (className != null && className.equals("3")) {
            Chapter3 ch3 = new Chapter3();
            if(sets!=null && sets.equals("Set-1")){
                questions = ch3.getQuestionsSet1();
                answers = ch3.getAnswersSet1();

            }else if(sets!=null && sets.equals("Set-2")){
                questions = ch3.getQuestionsSet2();
                answers = ch3.getAnswersSet2();

            }else if(sets!=null && sets.equals("Set-3")){
                questions = ch3.getQuestionsSet3();
                answers = ch3.getAnswersSet3();

            }else if(sets!=null && sets.equals("Set-4")){
                questions = ch3.getQuestionsSet4();
                answers = ch3.getAnswersSet4();

            }
        } else if (className != null && className.equals("4")) {
            Chapter4 ch4 = new Chapter4();
            if(sets!=null && sets.equals("Set-1")){
                questions = ch4.getQuestionsSet1();
                answers = ch4.getAnswersSet1();

            }else if(sets!=null && sets.equals("Set-2")){
                questions = ch4.getQuestionsSet2();
                answers = ch4.getAnswersSet2();

            }else if(sets!=null && sets.equals("Set-3")){
                questions = ch4.getQuestionsSet3();
                answers = ch4.getAnswersSet3();

            }else if(sets!=null && sets.equals("Set-4")){
                questions = ch4.getQuestionsSet4();
                answers = ch4.getAnswersSet4();

            }
        } else if (className != null && className.equals("5")) {
            Chapter5 ch5 = new Chapter5();
            if(sets!=null && sets.equals("Set-1")){
                questions =  ch5.getQuestionsSet1();
                answers =  ch5.getAnswersSet1();

            }else if(sets!=null && sets.equals("Set-2")){
                questions =  ch5.getQuestionsSet2();
                answers =  ch5.getAnswersSet2();

            }else if(sets!=null && sets.equals("Set-3")){
                questions = ch5.getQuestionsSet3();
                answers =  ch5.getAnswersSet3();

            }else if(sets!=null && sets.equals("Set-4")){
                questions =  ch5.getQuestionsSet4();
                answers = ch5.getAnswersSet4();

            }
        } else if (className != null && className.equals("6")) {
            Chapter6  ch6 = new Chapter6();
            if(sets!=null && sets.equals("Set-1")){
                questions = ch6.getQuestionsSet1();
                answers = ch6.getAnswersSet1();

            }else if(sets!=null && sets.equals("Set-2")){
                questions = ch6.getQuestionsSet2();
                answers = ch6.getAnswersSet2();

            }else if(sets!=null && sets.equals("Set-3")){
                questions = ch6.getQuestionsSet3();
                answers = ch6.getAnswersSet3();

            }else if(sets!=null && sets.equals("Set-4")){
                questions = ch6.getQuestionsSet4();
                answers = ch6.getAnswersSet4();

            }
        } else if (className != null && className.equals("7")) {
            Chapter7 ch7 = new Chapter7();
            if(sets!=null && sets.equals("Set-1")){
                questions = ch7.getQuestionsSet1();
                answers = ch7.getAnswersSet1();

            }else if(sets!=null && sets.equals("Set-2")){
                questions = ch7.getQuestionsSet2();
                answers = ch7.getAnswersSet2();

            }else if(sets!=null && sets.equals("Set-3")){
                questions = ch7.getQuestionsSet3();
                answers = ch7.getAnswersSet3();

            }else if(sets!=null && sets.equals("Set-4")){
                questions = ch7.getQuestionsSet4();
                answers = ch7.getAnswersSet4();

            }
        } else if (className != null && className.equals("8")) {
            Chapter8 ch8 = new Chapter8();
            if(sets!=null && sets.equals("Set-1")){
                questions = ch8.getQuestionsSet1();
                answers = ch8.getAnswersSet1();

            }else if(sets!=null && sets.equals("Set-2")){
                questions = ch8.getQuestionsSet2();
                answers = ch8.getAnswersSet2();

            }else if(sets!=null && sets.equals("Set-3")){
                questions = ch8.getQuestionsSet3();
                answers = ch8.getAnswersSet3();

            }else if(sets!=null && sets.equals("Set-4")){
                questions = ch8.getQuestionsSet4();
                answers = ch8.getAnswersSet4();

            }
        } else if (className != null && className.equals("9")) {
            Chapter9 ch9 = new Chapter9();
            if(sets!=null && sets.equals("Set-1")){
                questions = ch9.getQuestionsSet1();
                answers = ch9.getAnswersSet1();

            }else if(sets!=null && sets.equals("Set-2")){
                questions = ch9.getQuestionsSet2();
                answers = ch9.getAnswersSet2();

            }else if(sets!=null && sets.equals("Set-3")){
                questions = ch9.getQuestionsSet3();
                answers = ch9.getAnswersSet3();

            }else if(sets!=null && sets.equals("Set-4")){
                questions = ch9.getQuestionsSet4();
                answers = ch9.getAnswersSet4();

            }
        } else if (className != null && className.equals("10")) {
            Chapter10 ch10 = new Chapter10();
            if(sets!=null && sets.equals("Set-1")){
                questions = ch10.getQuestionsSet1();
                answers = ch10.getAnswersSet1();

            }else if(sets!=null && sets.equals("Set-2")){
                questions = ch10.getQuestionsSet2();
                answers = ch10.getAnswersSet2();

            }else if(sets!=null && sets.equals("Set-3")){
                questions = ch10.getQuestionsSet3();
                answers = ch10.getAnswersSet3();

            }else if(sets!=null && sets.equals("Set-4")){
                questions = ch10.getQuestionsSet2();
                answers = ch10.getAnswersSet2();

            }
        } else if (className != null && className.equals("11")) {
            Chapter11 ch11= new Chapter11();
            if(sets!=null && sets.equals("Set-1")){
                questions =ch11.getQuestionsSet1();
                answers = ch11.getAnswersSet1();

            }else if(sets!=null && sets.equals("Set-2")){
                questions = ch11.getQuestionsSet2();
                answers =ch11.getAnswersSet2();

            }else if(sets!=null && sets.equals("Set-3")){
                questions = ch11.getQuestionsSet3();
                answers = ch11.getAnswersSet3();

            }else if(sets!=null && sets.equals("Set-4")){
                questions = ch11.getQuestionsSet4();
                answers = ch11.getAnswersSet4();

            }
        } else if (className != null && className.equals("12")) {
            Chapter12 ch12 = new Chapter12();
            if(sets!=null && sets.equals("Set-1")){
                questions = ch12.getQuestionsSet1();
                answers =ch12.getAnswersSet1();

            }else if(sets!=null && sets.equals("Set-2")){
                questions = ch12.getQuestionsSet2();
                answers = ch12.getAnswersSet2();

            }else if(sets!=null && sets.equals("Set-3")){
                questions = ch12.getQuestionsSet3();
                answers = ch12.getAnswersSet3();

            }else if(sets!=null && sets.equals("Set-4")){
                questions = ch12.getQuestionsSet4();
                answers =ch12.getAnswersSet4();

            }
        } else if (className != null && className.equals("13")) {
            Chapter13 ch13= new Chapter13();
            if(sets!=null && sets.equals("Set-1")){
                questions = ch13.getQuestionsSet1();
                answers =ch13.getAnswersSet1();

            }else if(sets!=null && sets.equals("Set-2")){
                questions = ch13.getQuestionsSet2();
                answers = ch13.getAnswersSet2();

            }else if(sets!=null && sets.equals("Set-3")){
                questions = ch13.getQuestionsSet3();
                answers = ch13.getAnswersSet3();

            }else if(sets!=null && sets.equals("Set-4")){
                questions = ch13.getQuestionsSet4();
                answers = ch13.getAnswersSet4();

            }
        }

    }

    // ************************** time ************************************
    public void startTime() {

        // Time is in millisecond so 50sec = 50000 I have used
        // countdown Interveal is 1sec = 1000 I have used
        final int tTime = questions.length/6;
        ct = new CountDownTimer(60000 * tTime, 1000) {
            public void onTick(long millisUntilFinished) {
                // Used for formatting digit to be in 2 digits only
                NumberFormat f = new DecimalFormat("00");
                //long hour = (millisUntilFinished / 3600000) % 24;
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                long min1 = (tTime-1) - (millisUntilFinished / 60000) % 60;
                long sec1 = 60 - (millisUntilFinished / 1000) % 60;
                timeT = f.format(min1) + " Min " + f.format(sec1) + " Sec.";
                time.setText(Html.fromHtml("Time Left: "+"<b>" + f.format(min) + ":" + f.format(sec)));
            }

            // When the task is over it will print 00:00:00 there
            public void onFinish() {
                time.setText("Time Finished 00:00");
                time.setTextColor(Color.BLACK);
            }
        };
        ct.start();
    }

    /* ******************back to home***************************** */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                onBackPressed();
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
        //super.onBackPressed();
        selAnswerCount();
        Toast.makeText(this, "" + selAnsTotal, Toast.LENGTH_SHORT).show();
        testNotFinishedDialog();
    }

    // ############################### end ################################################
    public void testNotFinishedDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.test_nf_dialog);
        TextView attemptedQ=dialog.findViewById(R.id.attempted_questions);
        Button yes=dialog.findViewById(R.id.btn_yes);
        Button no=dialog.findViewById(R.id.btn_no);
        attemptedQ.setText("Attempted Questions: " + selAnsTotal + "/"+questions.length/6);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                finish();
                overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();

    }

    public void testFinishedDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.test_f_dialog);
        TextView attempted=dialog.findViewById(R.id.attempted_questions);
        TextView exit=dialog.findViewById(R.id.btn_exit);
        ImageView no=dialog.findViewById(R.id.bt_no);
        TextView submit=dialog.findViewById(R.id.btn_submit);
        attempted.setText("Attempted Questions: " + selAnsTotal + "/"+questions.length/6);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                submitResult();
                //finish();
                overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public void selAnswerCount() {
        for (int i = 0; i < questions.length/6; i++) {
            if (selAns[i].equals("null")) {
                selAnsCount[i] = 0;
            } else {
                selAnsCount[i] = 1;
            }
        }
        int st = 0;
        for (int i = 0; i < questions.length/6; i++) {
            st = st + selAnsCount[i];
        }
        selAnsTotal = st;

    }
}