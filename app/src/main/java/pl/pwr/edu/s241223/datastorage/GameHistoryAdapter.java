package pl.pwr.edu.s241223.datastorage;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GameHistoryAdapter extends RecyclerView.Adapter<GameHistoryAdapter.GameHistoryViewHolder> {
    private Context context;
    private Cursor cursor;

    public GameHistoryAdapter(Context context, Cursor cursor){
        this.context = context;
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public GameHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.history_row, parent, false);
        return new GameHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameHistoryViewHolder holder, int position) {
        if(!this.cursor.moveToPosition(position)){
            return;
        }
        String players = this.cursor.getString(this.cursor.getColumnIndex(GameHistory.GameHistoryEntry.COLUMN_NAME));
        String winner = this.cursor.getString(this.cursor.getColumnIndex(GameHistory.GameHistoryEntry.COLUMN_WON));

        holder.tvPlayers.setText(players);
        holder.tvWinner.setText(winner);
    }

    @Override
    public int getItemCount() {
        return this.cursor.getCount();
    }

    public void swapCursor(Cursor newCursor){
        if(this.cursor != null){
            this.cursor.close();
        }
        this.cursor = newCursor;

        if(newCursor  != null){
            notifyDataSetChanged();
        }
    }

    public class GameHistoryViewHolder extends RecyclerView.ViewHolder{
        public TextView tvPlayers;
        public TextView tvWinner;

        public GameHistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPlayers = itemView.findViewById(R.id.tvPlayers);
            tvWinner = itemView.findViewById(R.id.tvWinner);
        }
    }
}
