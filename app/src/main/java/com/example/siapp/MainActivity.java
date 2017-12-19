package com.example.siapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton showMessageButton = (ImageButton) findViewById(R.id.showMessageButton);
        final TextView message = (TextView) findViewById(R.id.messageText);

        showMessageButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MessageActivity.class);
                intent.putExtra("Message",message.getText().toString());
                startActivity(intent);
            }
        });


    }
}
