package pl.pwr.edu.s241223.datastorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InputPlayersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_players);

        Intent intent = getIntent();
        String title = intent.getStringExtra("Player");

        Button bAccept = findViewById(R.id.bAccept);
        TextView tvPlayerName = findViewById(R.id.tvPlayerName);
        tvPlayerName.setText(title);

        tvPlayerName.setText(title);
        final EditText playerName = findViewById(R.id.etName);

        bAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playerIntent = new Intent();

                playerIntent.putExtra("NameOfPlayer", playerName.getText().toString());

                setResult(RESULT_OK, playerIntent);
                finish();
            }
        });
    }
}
