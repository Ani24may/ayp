package com.example.loginview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MemberRecyclerViewAdapter extends RecyclerView.Adapter<MemberRecyclerViewAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public CircleImageView ci;
        public TextView name;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            ci = (CircleImageView) itemView.findViewById(R.id.profilePhoto);
            name = (TextView) itemView.findViewById(R.id.nameofuser);
        }
    }


    private List<Registration> registrations;
    private Context context;
    // Pass in the contact array into the constructor
    public MemberRecyclerViewAdapter(List<Registration> reg,Context ctx) {
        registrations = reg;
        context=ctx;

    }






    @NonNull
    @Override
    public MemberRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.member_lits_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MemberRecyclerViewAdapter.ViewHolder holder, int position) {

        Registration regstrtn=registrations.get(position);
        TextView textView = holder.name;
        textView.setText(regstrtn.getFirstName()+" "+regstrtn.getLastName());
        Glide.with(context).load("http://b1170a04.ngrok.io/downloadFile/"+String.valueOf(regstrtn.getRegId())+".jpg").into(holder.ci);

    }

    @Override
    public int getItemCount() {
        return registrations.size();
    }
}
