package com.example.siapp;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MessageActivity extends AppCompatActivity implements
        TextToSpeech.OnInitListener{

    private TextToSpeech tts;
    TextView messageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tts = new TextToSpeech(this, (TextToSpeech.OnInitListener) this);

        Intent intent = getIntent();
        messageView = (TextView) findViewById(R.id.messageView);

        if(intent.getStringExtra("Message").equalsIgnoreCase("yes")){
            messageView.setBackgroundResource(R.color.messageTextYesColor);
            messageView.setTextColor(getResources().getColor(R.color.messageTextPlain));
        }

        if(intent.getStringExtra("Message").equalsIgnoreCase("no")){
            messageView.setBackgroundResource(R.color.messageTextNoColor);
            messageView.setTextColor(getResources().getColor(R.color.messageTextPlain));
        }

        messageView.setText(intent.getStringExtra("Message"));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the main_menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.message_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.item1:
                speakOut();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    private void speakOut() {

        messageView = (TextView) findViewById(R.id.messageView);

        String text = messageView.getText().toString();

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }


    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

}
