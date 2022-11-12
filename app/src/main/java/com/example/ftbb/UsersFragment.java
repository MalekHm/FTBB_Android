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
import com.example.ftbb.models.user;

import java.util.ArrayList;
import java.util.List;


public class UsersFragment extends Fragment {
    ProgressDialog progressDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_users, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        progressDialog = new ProgressDialog(this.getContext());
        progressDialog.show();
        progressDialog.setContentView(R.layout.custom_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        userAdapter adapter = new userAdapter(this.getContext(), progressDialog,rootView);

        recyclerView.setAdapter(adapter);


        return rootView;
    }
}


class userAdapter extends RecyclerView.Adapter<userAdapter.MyViewHolder> implements Runnable {
    private ArrayList<user> users = new ArrayList<>();
    private List<user> useradapt;
    Button Addarbitre;
    EditText username, email, password;

    Context context;
    userAdapter adapter = this;

    public userAdapter(Context context, ProgressDialog progressDialog, View rootView) {
        this.context = context;
        getData(context, progressDialog);

        username = (EditText) rootView.findViewById(R.id.ajoutnom);
        email = (EditText) rootView.findViewById(R.id.ajoutemail);
        password = (EditText) rootView.findViewById(R.id.ajoutmdp);

        Addarbitre = rootView.findViewById(R.id.ajouterarbitre);
        Addarbitre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppDataBase.getAppDatabase(context).userDao().findbyusername(username.getText().toString())==null)
                {
                    user usr = new user(username.getText().toString(),password.getText().toString(),email.getText().toString(),"arbitre");
                    AppDataBase.getAppDatabase(context).userDao().insertOne(usr);
                    users.add(usr);
                    adapter.notifyItemInserted(users.size()-1);
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
                .inflate(R.layout.user_item_list, parent, false);

        return new userAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.username.setText("Nom : "+users.get(position).getUsername());
        holder.role.setText("Role : "+users.get(position).getRole());
        // Picasso.get().load(tracks.get(position).getImage()).into(holder.imageplaylist);
        holder.delete.setOnClickListener(v -> {
            AppDataBase.getAppDatabase(context).userDao().delete(users.get(position));
            Toast.makeText(context,"User Deleted !",Toast.LENGTH_SHORT).show();
            adapter.users.remove(position);
            adapter.notifyItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        if (users == null)
            return 0;
        else
            return users.size();
    }

    @Override
    public void run() {

    }

    void getData(Context cntx, ProgressDialog progressDialog) {


        useradapt = AppDataBase.getAppDatabase(cntx).userDao().getAll();

        for (int i = 0; i < useradapt.size(); i++) {
            System.out.println(useradapt.get(i));

            users.add(useradapt.get(i));
        }
        adapter.notifyDataSetChanged();
        progressDialog.dismiss();

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView username,role;
        private final Button delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.usernametext);
            role = (TextView) itemView.findViewById(R.id.roletext);
            delete = (Button) itemView.findViewById(R.id.deleteuser);
            itemView.findViewById(R.id.deleteuser).setOnClickListener(view -> {
                adapter.users.remove(getAdapterPosition());
                adapter.notifyItemRemoved(getAdapterPosition());
            });

        }

        public TextView getTextView() {
            return username;
        }

    }
}

