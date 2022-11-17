package com.example.ftbb;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ftbb.models.AppDataBase;
import com.example.ftbb.models.game;
import com.example.ftbb.models.user;

import java.util.ArrayList;
import java.util.List;

public class MatchFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_match, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewGame);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));



        gameAdapter adapter = new gameAdapter(this.getContext(),rootView);

        recyclerView.setAdapter(adapter);


        return rootView;
    }

}

class gameAdapter extends RecyclerView.Adapter<gameAdapter.MyViewHolder> implements Runnable {
    private ArrayList<game> games = new ArrayList<>();
    private List<game> gameadapt;
    Button Addgame;
    EditText team1, team2, dateg,rang;

    Context context;
    gameAdapter adapter = this;

    public gameAdapter(Context context, View rootView) {
        this.context = context;
        getData(context);

        team1 = (EditText) rootView.findViewById(R.id.ajouterteam1);
        team2 = (EditText) rootView.findViewById(R.id.ajouterteam2);
        dateg = (EditText) rootView.findViewById(R.id.ajoutedate);
        rang = (EditText) rootView.findViewById(R.id.ajouterang);

        Addgame = rootView.findViewById(R.id.ajouterteam);
        Addgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppDataBase.getAppDatabase(context).gameDao().findbyteams(team1.getText().toString(),team2.getText().toString())==null)
                {
                    if(!(AppDataBase.getAppDatabase(context).teamDao().findbyname(team1.getText().toString())==null) && !(AppDataBase.getAppDatabase(context).teamDao().findbyname(team2.getText().toString())==null) )
                    {
                        game gm = new game(team1.getText().toString(),team2.getText().toString(),rang.getText().toString(),dateg.getText().toString());
                        AppDataBase.getAppDatabase(context).gameDao().insertOne(gm);
                        games.add(gm);
                        adapter.notifyItemInserted(games.size()-1);

                    }
                    else
                    {
                        Toast.makeText(context,"Team 1 ou 2 n'existe pas",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(context,"EXISTE",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.match_item_list, parent, false);

        return new gameAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nameteam1.setText(games.get(position).getTeamidhome());
        holder.nameteam2.setText(games.get(position).getTeamidaway());
        holder.dategame.setText(games.get(position).getDateg());
        holder.score.setText(games.get(position).getScoret1()+" - "+games.get(position).getScoret2());
        // Picasso.get().load(tracks.get(position).getImage()).into(holder.imageplaylist);
        holder.delete.setOnClickListener(v -> {
            AppDataBase.getAppDatabase(context).gameDao().delete(games.get(position));
            Toast.makeText(context,"Game Deleted !",Toast.LENGTH_SHORT).show();
            adapter.games.remove(position);
            adapter.notifyItemRemoved(position);
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
        private final Button delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameteam1 = (TextView) itemView.findViewById(R.id.nameteam1);
            nameteam2 = (TextView) itemView.findViewById(R.id.nameteam2);
            dategame = (TextView) itemView.findViewById(R.id.datetext);
            score = (TextView) itemView.findViewById(R.id.scoretext);
            delete = (Button) itemView.findViewById(R.id.deletegame);
            itemView.findViewById(R.id.deletegame).setOnClickListener(view -> {
                adapter.games.remove(getAdapterPosition());
                adapter.notifyItemRemoved(getAdapterPosition());
            });

        }

        public TextView getTextView() {
            return nameteam1;
        }

    }
}
