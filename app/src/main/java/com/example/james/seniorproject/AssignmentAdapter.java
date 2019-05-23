package com.example.james.seniorproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder>{
    ArrayList<Assignment> assignments;

    public static class AssignmentViewHolder extends RecyclerView.ViewHolder {
        View viewForeground, viewBackground;
        TextView title, content, dateDisplay;
        ImageView icon;
        AssignmentViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.assignment_title);
            icon = itemView.findViewById(R.id.assignment_icon);
            content = itemView.findViewById(R.id.assignment_content);
            dateDisplay = itemView.findViewById(R.id.assignment_date_text);
            viewForeground = itemView.findViewById(R.id.assignment_constraint_layout);
            viewBackground = itemView.findViewById(R.id.assignment_background);
        }
    }

    public AssignmentAdapter(ArrayList<Assignment> assignments) {
        this.assignments = assignments;
    }

    @Override
    public int getItemCount() {
        return assignments.size();
    }

    @Override
    public AssignmentViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.assignment_item, viewGroup, false);
        return new AssignmentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AssignmentViewHolder holder, int i) {
        holder.title.setText(assignments.get(i).title);
        holder.content.setText(assignments.get(i).contentDescription);
        Date currentDate = new Date();
        //TODO can be optimized ?
        String dateString = String.format("Created: %s (%s)\nDue: %s (%s)",
                DateFormat.getDateInstance().format(assignments.get(i).creationDate),
                Util.formatDateDifference(currentDate, assignments.get(i).creationDate),
                DateFormat.getDateInstance().format(assignments.get(i).dueDate),
                Util.formatDateDifference(currentDate, assignments.get(i).dueDate)
        );
        holder.dateDisplay.setText(dateString);
        final int holderPosition = i;
        holder.viewForeground.setOnClickListener(new View.OnClickListener() {
            int index = holderPosition;
            @Override
            public void onClick(View v) {
                AssignmentAdapter.this.summonDialogBox(v.getContext(), index);
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    /**
     * @param maybeId an index of the assignments ArrayList or -1
     * @return the assignment at that index or a default assignment when given -1
     */
    public Assignment getAssignmentOrBlank(int maybeId) {
        if (maybeId == -1)
            return new Assignment("New Assignment", "", new Date(), new Date());
        return assignments.get(maybeId);
    }

    /**
     * inserts the assignment and updates the view
     * @param assignment the assignment to insert
     * @return the index of that assignment
     */
    public void insertAssignmentAndSave(Assignment assignment, int i) {
        // all fields are overwritten later by editAssignmentAndSave
        assignments.add(i, assignment);
        notifyItemInserted(i);
        DataLore.writeAssignmentsToMemory(assignments);
    }

    public void swapAssignmentsAndSave(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(assignments, i, i + 1);
            }
        }
        if (toPosition < fromPosition) {
            for (int i = fromPosition; i> toPosition; i--) {
                Collections.swap(assignments, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        DataLore.writeAssignmentsToMemory(assignments);
    }


    /**
     * Removes the assignment from the list
     * @param i index to remove
     */
    public void removeAssignmentAndSave(int i) {
        assignments.remove(i);
        notifyItemRemoved(i);
        DataLore.writeAssignmentsToMemory(assignments);
    }

    /**
     * Edits the assignment to match the given values, notifies the RecyclerView and saves the new list in internal memory
     * @param id the id to edit
     * @param title the new title
     * @param contentDescription the new content description
     * @param dueDate the new due date
     */
    void editAssignmentAndSave(int id, String title, String contentDescription, Date dueDate) {
        assignments.get(id).title = title;
        assignments.get(id).contentDescription = contentDescription;
        assignments.get(id).dueDate = dueDate;
        notifyItemChanged(id);
        DataLore.writeAssignmentsToMemory(assignments);
    }

    /**
     * @param context the current context
     * @param maybeId the id of the assignment to edit or -1 to create a new assignment
     */
    void summonDialogBox(@NonNull Context context, int maybeId) {
        //TODO store the dialog boxes
        EditDialog dialog = new EditDialog(context, this, maybeId);
        dialog.show();
    }

    /**
     * creates a new assignment and edits it with the correct parameters
     * @param titleText the title
     * @param contentDescription the content description
     * @param dueDate the due date
     */
    void appendAssignmentAndEditAndSave(Assignment blank, String titleText, String contentDescription, Date dueDate) {
        // all fields are overwritten later by editAssignmentAndSave
        assignments.add(blank);
        notifyItemInserted(assignments.size() - 1);
        editAssignmentAndSave(assignments.size() - 1, titleText, contentDescription, dueDate);
    }
}