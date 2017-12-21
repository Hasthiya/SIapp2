package com.example.siapp;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements
        TextToSpeech.OnInitListener {


    private TextToSpeech tts;
    private EditText messagetext;
    private ImageButton readMessgae;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tts = new TextToSpeech(this, this);
        readMessgae = (ImageButton) findViewById(R.id.readButton);
        messagetext = (EditText) findViewById(R.id.messageText);
        ImageButton showMessageButton = (ImageButton) findViewById(R.id.showMessageButton);
        Button button = (Button) findViewById(R.id.button);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MessageActivity.class);
                intent.putExtra("Message","YES");
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MessageActivity.class);
                intent.putExtra("Message","NO");
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MessageActivity.class);
                intent.putExtra("Message","MAYBE");
                startActivity(intent);
            }
        });

        final TextView message = (TextView) findViewById(R.id.messageText);

        showMessageButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if(!message.getText().toString().equalsIgnoreCase("")){
                Intent intent = new Intent(getBaseContext(), MessageActivity.class);
                intent.putExtra("Message",message.getText().toString());
                startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter a message to show",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        readMessgae.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(!message.getText().toString().equalsIgnoreCase("")) {
                    speakOut();
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter a message to read",
                            Toast.LENGTH_LONG).show();
                }
            }

        });
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

        String text = messagetext.getText().toString();

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onResume(){
        super.onResume();
        TextView message = findViewById(R.id.messageText);
        message.setText(null);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the main_menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.item1:
                Toast.makeText(getApplicationContext(), "Not Yet Implemented",
                        Toast.LENGTH_LONG).show();
                break;
            case R.id.item2:
                Intent intent = new Intent(getBaseContext(), TutorialActivity.class);
                startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                readMessgae.setEnabled(true);
                speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }
}
