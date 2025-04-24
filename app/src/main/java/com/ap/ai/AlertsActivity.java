package com.ap.ai;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ap.ai.adapters.AlertsAdapter;
import com.ap.ai.models.Alert;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AlertsActivity extends AppCompatActivity {
    private AlertsViewModel viewModel;
    private AlertsAdapter adapter;
    private TextView emptyView;
    private RecyclerView recyclerView;
    private List<Alert> alerts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize views
        recyclerView = findViewById(R.id.alertsRecyclerView);
        emptyView = findViewById(R.id.emptyView);
        ExtendedFloatingActionButton exportFab = findViewById(R.id.exportFab);

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AlertsAdapter(alerts);
        recyclerView.setAdapter(adapter);

        // Setup ViewModel
        viewModel = new ViewModelProvider(this).get(AlertsViewModel.class);
        viewModel.getAlerts().observe(this, this::updateAlerts);

        // Setup export FAB
        exportFab.setOnClickListener(v -> exportAlerts());
    }

    private void updateAlerts(List<Alert> newAlerts) {
        alerts.clear();
        alerts.addAll(newAlerts);
        adapter.notifyDataSetChanged();

        if (alerts.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }

    private void exportAlerts() {
        if (alerts.isEmpty()) {
            Snackbar.make(findViewById(android.R.id.content), 
                         "No alerts to export", 
                         Snackbar.LENGTH_SHORT).show();
            return;
        }

        try {
            File file = new File(getExternalCacheDir(), "health_alerts.csv");
            FileWriter writer = new FileWriter(file);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

            // Write CSV header
            writer.append("Timestamp,Type,Value,Message\n");

            // Write alerts
            for (Alert alert : alerts) {
                writer.append(dateFormat.format(alert.getTimestamp())).append(",")
                      .append(alert.getReading().getType().toString()).append(",")
                      .append(String.valueOf(alert.getReading().getValue())).append(",")
                      .append(alert.getMessage()).append("\n");
            }

            writer.close();

            // Share file
            Uri uri = FileProvider.getUriForFile(this, 
                                               getApplicationContext().getPackageName() + ".provider",
                                               file);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/csv");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(intent, "Share Alerts History"));

        } catch (IOException e) {
            e.printStackTrace();
            Snackbar.make(findViewById(android.R.id.content), 
                         "Failed to export alerts", 
                         Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
} 