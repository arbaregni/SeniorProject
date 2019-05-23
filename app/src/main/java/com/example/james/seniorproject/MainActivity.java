package com.example.james.seniorproject;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.icu.util.TimeUnit;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    public ImageButton manualButton;
    public ImageButton alarmButton;

    public RecyclerView recyclerView;

    public AssignmentAdapter assignmentAdapter;

    public DailyAlarm alarm;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.content_main);

        manualButton = findViewById(R.id.manual_button);
        manualButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create a dialog box for the end of the list
                assignmentAdapter.summonDialogBox(v.getContext(), -1);
            }
        });
        alarmButton = findViewById(R.id.alarm_button);
        alarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open dialog which sets the alarm
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);
                TimePickerDialog timePicker = new TimePickerDialog(MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                MainActivity.this.scheduleAlarm(selectedHour, selectedMinute);
                            }
                        }, hour, minute, true);
                timePicker.show();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //recyclerView.setHasFixedSize(true);
        // add the layout manager
        LinearLayoutManager llm = new LinearLayoutManager(this.getBaseContext());
        llm.setReverseLayout(true);
        llm.setStackFromEnd(false);
        recyclerView.setLayoutManager(llm);
        // add the item touch helper
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        // load previous assignments from memory
        DataLore.context = getBaseContext();
        assignmentAdapter = new AssignmentAdapter(DataLore.readAssignmentsFromMemory());

        recyclerView.setAdapter(assignmentAdapter);
    }

    void scheduleAlarm(int hourOfDay, int minute) {
        if (alarm == null)
            alarm = new DailyAlarm();

        alarm.cancelAlarm(getBaseContext());
        alarm.setAlarm(getBaseContext(), hourOfDay, minute);

        Snackbar snackbar;
        if (true) {
            snackbar = Snackbar.make(recyclerView, "Alarm set successfully", Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.BLUE);
        } else {
            snackbar = Snackbar.make(recyclerView, "Unable to create alarm!", Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.RED);
        }
        snackbar.show();
    }
    /**
     * Call back when recycler view is swiped
     * item will be removed
     * option to restore it is shown
     * @param holder
     * @param direction
     * @param position
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder holder, int direction, int position) {
        if (holder instanceof AssignmentAdapter.AssignmentViewHolder) {
            // get the name for when we display the option to restore it
            String name = ((AssignmentAdapter.AssignmentViewHolder) holder).title.getText().toString();

            final int deletedIndex = holder.getAdapterPosition();
            final Assignment deletedAssignment = assignmentAdapter.assignments.get(deletedIndex);

            assignmentAdapter.removeAssignmentAndSave(deletedIndex);

            Snackbar snackbar = Snackbar
                    .make(recyclerView, name + " deleted!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    assignmentAdapter.insertAssignmentAndSave(deletedAssignment, deletedIndex);

                }
            });
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        }
    }
    @Override
    public boolean onMove(RecyclerView.ViewHolder holder0, RecyclerView.ViewHolder holder1) {
        assignmentAdapter.swapAssignmentsAndSave(holder0.getAdapterPosition(), holder1.getAdapterPosition());
        return true;
    }
}
