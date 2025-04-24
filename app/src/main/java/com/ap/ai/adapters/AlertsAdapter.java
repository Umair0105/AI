package com.ap.ai.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ap.ai.R;
import com.ap.ai.models.Alert;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class AlertsAdapter extends RecyclerView.Adapter<AlertsAdapter.AlertViewHolder> {
    private final List<Alert> alerts;
    private final SimpleDateFormat dateFormat;

    public AlertsAdapter(List<Alert> alerts) {
        this.alerts = alerts;
        this.dateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss", Locale.getDefault());
    }

    @NonNull
    @Override
    public AlertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.item_alert, parent, false);
        return new AlertViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlertViewHolder holder, int position) {
        Alert alert = alerts.get(position);
        holder.messageText.setText(alert.getMessage());
        holder.timeText.setText(dateFormat.format(alert.getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return alerts.size();
    }

    static class AlertViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;

        AlertViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.alertMessageText);
            timeText = itemView.findViewById(R.id.alertTimeText);
        }
    }
} 