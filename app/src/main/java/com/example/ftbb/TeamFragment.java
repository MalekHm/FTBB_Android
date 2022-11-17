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

import com.example.ftbb.R;
import com.example.ftbb.models.AppDataBase;
import com.example.ftbb.models.team;
import com.example.ftbb.models.user;

import java.util.ArrayList;
import java.util.List;

public class TeamFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_team, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewTeam);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));



        teamAdapter adapter = new teamAdapter(this.getContext(),rootView);

        recyclerView.setAdapter(adapter);


        return rootView;

    }

}

class teamAdapter extends RecyclerView.Adapter<teamAdapter.MyViewHolder> implements Runnable {
    private ArrayList<team> teams = new ArrayList<>();
    private List<team> teamadapt;
    Button AddTeam;
    EditText name, logo;

    Context context;
    teamAdapter adapter = this;

    public teamAdapter(Context context, View rootView) {
        this.context = context;
        getData(context);

        name = (EditText) rootView.findViewById(R.id.ajoutteamnom);
        logo = (EditText) rootView.findViewById(R.id.ajouteteamlogo);

        AddTeam = rootView.findViewById(R.id.ajouterteam);
        AddTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppDataBase.getAppDatabase(context).teamDao().findbyname(name.getText().toString())==null)
                {
                    team tem = new team(name.getText().toString(),logo.getText().toString());
                    AppDataBase.getAppDatabase(context).teamDao().insertOne(tem);
                    teams.add(tem);
                    adapter.notifyItemInserted(teams.size()-1);
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
                .inflate(R.layout.team_item_list, parent, false);

        return new teamAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nom.setText("Nom : "+teams.get(position).getName());
        // Picasso.get().load(tracks.get(position).getImage()).into(holder.imageplaylist);
        holder.delete.setOnClickListener(v -> {
            AppDataBase.getAppDatabase(context).teamDao().delete(teams.get(position));
            Toast.makeText(context,"Team Deleted !",Toast.LENGTH_SHORT).show();
            adapter.teams.remove(position);
            adapter.notifyItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        if (teams == null)
            return 0;
        else
            return teams.size();
    }

    @Override
    public void run() {

    }

    void getData(Context cntx) {


        teamadapt = AppDataBase.getAppDatabase(cntx).teamDao().getAll();

        for (int i = 0; i < teamadapt.size(); i++) {
            System.out.println(teamadapt.get(i));

            teams.add(teamadapt.get(i));
        }
        adapter.notifyDataSetChanged();

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView nom,logo;
        private final Button delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nom = (TextView) itemView.findViewById(R.id.teamnametext);
            logo = (TextView) itemView.findViewById(R.id.roletext);
            delete = (Button) itemView.findViewById(R.id.deleteteam);
            itemView.findViewById(R.id.deleteteam).setOnClickListener(view -> {
                adapter.teams.remove(getAdapterPosition());
                adapter.notifyItemRemoved(getAdapterPosition());
            });

        }

        public TextView getTextView() {
            return nom;
        }

    }
}
