package pl.pwr.edu.s241223.datastorage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class GameHistoryActivity extends AppCompatActivity {

    private SQLiteDatabase database;

    private GameHistoryAdapter adapter;
    private Button bBack;
    private TextView tvHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_history);

        GameDBHelper dbHelper = new GameDBHelper(this);
        database = dbHelper.getWritableDatabase();

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GameHistoryAdapter(this, getAllItems());
        recyclerView.setAdapter(adapter);

        bBack = findViewById(R.id.bBack);
        tvHistory = findViewById(R.id.tvHistory);


        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private Cursor getAllItems(){
        return database.query(GameHistory.GameHistoryEntry.TABLE_NAME,
                null,null,null,null,null,
                GameHistory.GameHistoryEntry.COLUMN_TIMESTAMP + " DESC");
    }
}
