package com.example.james.seniorproject;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditDialog extends Dialog {
    AssignmentAdapter assignmentAdapter;
    int maybeId;

    /**
     * @param context the current context
     * @param assignmentAdapter the assignment Adapter
     * @param maybeId the id of the assignment to edit (must be a valid index of assignmentAdapter.assignments) or -1 to create a new assignment
     */
    public EditDialog(@NonNull Context context, AssignmentAdapter assignmentAdapter, int maybeId) {
        super(context);
        this.assignmentAdapter = assignmentAdapter;
        this.maybeId = maybeId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_dialog);

        final Assignment assignment = assignmentAdapter.getAssignmentOrBlank(maybeId);

        // initialize components of edit dialog
        final EditText editableTitle = findViewById(R.id.editter_title);
        editableTitle.setHint(assignment.title);

        final EditText editableDescription = findViewById(R.id.editter_description);
        editableDescription.setHint(assignment.contentDescription);

        final DatePicker datePicker = findViewById(R.id.editter_date_picker);
        datePicker.setMinDate(assignment.creationDate.getTime());

        Button cancelButton = findViewById(R.id.editter_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // User clicked Cancel button --> dismiss the dialog
                EditDialog.this.dismiss();
            }
        });

        Button okButton = findViewById(R.id.editter_ok);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // User clicked Ok --> find out preferences and apply them to assignmentAdapter
                String titleText = editableTitle.getText().toString();
                if (titleText.length() == 0) {
                    titleText = editableTitle.getHint().toString();
                }
                String contentDescription = editableDescription.getText().toString();
                if (contentDescription.length() == 0) {
                    contentDescription = editableDescription.getText().toString();
                }
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, datePicker.getYear());
                cal.set(Calendar.MONTH, datePicker.getMonth());
                cal.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                Date dueDate = cal.getTime();
                // if we need to insert a blank assignment, do that before editing it with the proper values
                if (maybeId == -1)
                    assignmentAdapter.appendAssignmentAndEditAndSave(assignment, titleText, contentDescription, dueDate);
                else
                    assignmentAdapter.editAssignmentAndSave(maybeId, titleText, contentDescription, dueDate);

                EditDialog.this.dismiss();
            }
        });
    }
}