package com.mustang.teamlistjava.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.mustang.teamlistjava.Model.TeamMember;
import com.mustang.teamlistjava.R;
import com.mustang.teamlistjava.View.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {

    private List<TeamMember> members = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.team_item, parent, false);
        return new TeamViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
        TeamMember currentMember = members.get(position);
        holder.textViewName.setText(currentMember.getMemberName());
        holder.textViewNumber.setText(currentMember.getContact());
        holder.textViewEmail.setText(currentMember.getEmail());
        holder.textViewAddress.setText(currentMember.getAddress());
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public void setMembers(List<TeamMember> members) {
        this.members = members;
        notifyDataSetChanged();
    }

    public TeamMember getMemberAt(int position) {
        return members.get(position);
    }

    class TeamViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewNumber;
        private TextView textViewEmail;
        private TextView textViewAddress;

        public TeamViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewNumber = itemView.findViewById(R.id.textViewNumber);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(members.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(TeamMember teamMember);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
