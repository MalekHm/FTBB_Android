package com.example.ftbb;

import static com.example.ftbb.LoginActivity.FILE_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ftbb.models.AppDataBase;
import com.example.ftbb.models.game;

import java.util.ArrayList;
import java.util.List;

public class Arbitre_DashboardActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_navigation, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arbitre_dashboard);
        sharedPreferences = getSharedPreferences(FILE_NAME,MODE_PRIVATE);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewGameArbitre);
        gameforArbitreAdapter adapter = new gameforArbitreAdapter(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);



    }

    public void Logout(MenuItem item) {
        sharedPreferences.edit().clear().apply();
        Intent intent = new Intent(Arbitre_DashboardActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();


    }

}

class gameforArbitreAdapter extends RecyclerView.Adapter<gameforArbitreAdapter.MyViewHolder> implements Runnable {
    private ArrayList<game> games = new ArrayList<>();
    private List<game> gameadapt;
    Button Addgame;
    EditText team1, team2, dateg,rang;

    Context context;
    gameforArbitreAdapter adapter = this;

    public gameforArbitreAdapter(Context context) {
        this.context = context;
        getData(context);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.matchforarbitre_item_list, parent, false);

        return new gameforArbitreAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nameteam1.setText(games.get(position).getTeamidhome());
        holder.nameteam2.setText(games.get(position).getTeamidaway());
        holder.dategame.setText(games.get(position).getDateg());
        holder.score.setText(games.get(position).getScoret1()+" - "+games.get(position).getScoret2());
        // Picasso.get().load(tracks.get(position).getImage()).into(holder.imageplaylist);
        holder.addfort1.setOnClickListener(v -> {
            game g =  AppDataBase.getAppDatabase(context).gameDao().findbyteams(games.get(position).getTeamidhome(),games.get(position).getTeamidaway());
            int scoretmp = Integer.parseInt(g.getScoret1());
            scoretmp+=1;
            g.setScoret1(String.valueOf(scoretmp));
            AppDataBase.getAppDatabase(context).gameDao().updateGame(g);
            Toast.makeText(context,"Score ADD !",Toast.LENGTH_SHORT).show();
            adapter.games.set(position,g);
            adapter.notifyItemChanged(position);
        });
        holder.addfort2.setOnClickListener(v -> {
            game g =  AppDataBase.getAppDatabase(context).gameDao().findbyteams(games.get(position).getTeamidhome(),games.get(position).getTeamidaway());
            int scoretmp = Integer.parseInt(g.getScoret2());
            scoretmp+=1;
            g.setScoret2(String.valueOf(scoretmp));
            AppDataBase.getAppDatabase(context).gameDao().updateGame(g);
            Toast.makeText(context,"Score ADD !",Toast.LENGTH_SHORT).show();
            adapter.games.set(position,g);
            adapter.notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        if (games == null)
            return 0;
        else
            return games.size();
    }

    @Override
    public void run() {

    }

    void getData(Context cntx) {


        gameadapt = AppDataBase.getAppDatabase(cntx).gameDao().getAll();

        for (int i = 0; i < gameadapt.size(); i++) {
            System.out.println(gameadapt.get(i));

            games.add(gameadapt.get(i));
        }
        adapter.notifyDataSetChanged();

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameteam1,nameteam2,dategame,score;
        private final Button addfort1,addfort2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameteam1 = (TextView) itemView.findViewById(R.id.arbitre_nameteam1);
            nameteam2 = (TextView) itemView.findViewById(R.id.arbitre_nameteam2);
            dategame = (TextView) itemView.findViewById(R.id.arbitre_datetext);
            score = (TextView) itemView.findViewById(R.id.arbitre_scoretext);
            addfort1 = (Button) itemView.findViewById(R.id.addpointteam1);
            addfort2 = (Button) itemView.findViewById(R.id.addpointteam2);
            itemView.findViewById(R.id.addpointteam1).setOnClickListener(view -> {
                adapter.notifyDataSetChanged();
            });
            itemView.findViewById(R.id.addpointteam2).setOnClickListener(view -> {
                adapter.notifyDataSetChanged();
            });


        }

        public TextView getTextView() {
            return nameteam1;
        }

    }
}
