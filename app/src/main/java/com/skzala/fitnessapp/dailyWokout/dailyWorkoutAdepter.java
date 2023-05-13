package com.skzala.fitnessapp.dailyWokout;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.skzala.fitnessapp.R;
import com.skzala.fitnessapp.workoutDesc;

import java.util.List;

public class dailyWorkoutAdepter extends RecyclerView.Adapter<dailyWorkoutAdepter.MyViewHolder> {
    private List<dailyWorkoutModel> dataList;

    public dailyWorkoutAdepter(List<dailyWorkoutModel> dataList) {
        this.dataList = dataList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView descriptionTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.txtWorkoutName);
            descriptionTextView = itemView.findViewById(R.id.text_view_item_description);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemveiw_daily_workout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        dailyWorkoutModel data = dataList.get(position);
        holder.nameTextView.setText(data.getName());
        holder.descriptionTextView.setText(data.getDescription());

        holder.descriptionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text1 = holder.descriptionTextView.getText().toString();
                int id = data.getId();

                if(id == 0){
                    Intent intent = new Intent(v.getContext(), showScheduleDesc.class);
                    intent.putExtra("name", text1);
                    v.getContext().startActivity(intent);

                }else{
                    Intent intent = new Intent(v.getContext(), workoutDesc.class);
                    intent.putExtra("name", text1);
                    v.getContext().startActivity(intent);

                }

               // Toast.makeText(v.getContext(), "Text 1: " + text1, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
