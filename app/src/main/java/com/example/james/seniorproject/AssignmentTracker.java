package com.example.james.seniorproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Assignment tracker class
 * Created by james on 5/2/2019.
 */

public class AssignmentTracker {
    private static final java.lang.String EDIT_TAG = "assignment editor dialog box";

    public int assignmentCount = 0;
    public Activity activity;
    ArrayList<View.OnClickListener> assignmentListeners = new ArrayList<>();
    ArrayList<TextView> assignmentTitles = new ArrayList<>();

    public static class EditDialog extends Dialog {
        AssignmentTracker assignmentTracker;
        int id;

        public EditDialog(@NonNull Context context, AssignmentTracker assignmentTracker, int assignmentId) {
            super(context);
            this.assignmentTracker = assignmentTracker;
            this.id = assignmentId;

        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.edit_dialog);
            String titleName = assignmentTracker.getTitle(id);
            final EditText editableTitle = (EditText) findViewById(R.id.edit_dialog_title);
            editableTitle.setHint(titleName);
            Button cancelButton = (Button) findViewById(R.id.edit_dialog_cancel);
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // User clicked Cancel button
                    EditDialog.this.dismiss();
                }
            });
            Button okButton = (Button) findViewById(R.id.edit_dialog_ok);
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    assignmentTracker.editAssignment(id, editableTitle.getText().toString());
                    EditDialog.this.dismiss();
                }
            });
                /*

                TODO
            Tip: If you want a custom dialog, you can instead display an Activity
            as a dialog instead of using the Dialog APIs. Simply create an activity
            and set its theme to Theme.Holo.Dialog in the <activity> manifest element:
                <activity android:theme="@android:style/Theme.Holo.Dialog" >
            That's it. The activity now displays in a dialog window instead of fullscreen.

            String title = assignmentTracker.getTitle(id);
            AlertDialog.Builder builder = new AlertDialog.Builder(assignmentTracker.activity);
            final EditText editTitle = new EditText(builder.getContext());
            editTitle.setHint(title);
            builder.setCustomTitle(editTitle)
                    .setMessage("Editing " + title)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //TODO edit the assignment to the match the value of the text in our title

                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked Cancel button
                            dialog.dismiss();
                        }
                    });
            return builder.create();*/
        }
    }

    public AssignmentTracker(Activity activity) {
        this.activity = activity;
    }


    /**
     * @param context the context of the activity where we add the assignment to
     * @param layout the layout that the assignment ui items become a part of
     * @return the assignment id (the index of the array list) of the new assignment
     */
    int addBlankAssignment(final Context context, LinearLayout layout) {
        // the assignment we're creating needs to open it's dialog box when clicked
        // we'll create one listener and use it for every visible component of this assignment
        View.OnClickListener onClickListener = new View.OnClickListener() {
            // each on click listener needs a different assignment id
            int assignmentId = assignmentCount;
            @Override
            public void onClick(View v) {
                // create a dialogue to edit this assignment
                EditDialog dialog = new EditDialog(context, AssignmentTracker.this, assignmentId);
                dialog.show();
            }
        };
        // create and add the text view
        TextView title = new TextView(context);
        // the view should be placed on top of the, bumping everything else down
        layout.addView(title, 0);
        title.setText(R.string.blank_assignment_title);
        title.setTextSize(24.0f);
        title.setOnClickListener(onClickListener);
        assignmentTitles.add(title);

        assignmentListeners.add(onClickListener);
        return assignmentCount++;
    }

    void editAssignment(int assignmentId, String title) {
        assignmentTitles.get(assignmentId).setText(title);
    }

    /**
     * @param assignmentId the id of the assignment to edit
     * @return a dialog object. Call show() in order to make it appear
     */
    Dialog createDialogBox(final Context context, final int assignmentId) {
        return new AssignmentTracker.EditDialog(context, AssignmentTracker.this, assignmentId);
    }

    private String getTitle(int assignmentId) {
        return assignmentTitles.get(assignmentId).getText().toString();
    }
}


