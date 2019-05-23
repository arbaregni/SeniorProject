package com.example.james.seniorproject;

import android.content.ClipData;
import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {
    private RecyclerItemTouchHelperListener listener;

    public interface RecyclerItemTouchHelperListener {
        void onSwiped(RecyclerView.ViewHolder holder, int direction, int position);
        boolean onMove(RecyclerView.ViewHolder dragged, RecyclerView.ViewHolder target);
    }

    public RecyclerItemTouchHelper(int dragDirs, int swipeDirs, RecyclerItemTouchHelperListener listener) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder holder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder holder, int actionState) {
        if (holder != null) {
            final View foregroundView = ((AssignmentAdapter.AssignmentViewHolder) holder).viewForeground;
            final View backgroundView = ((AssignmentAdapter.AssignmentViewHolder) holder).viewBackground;
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                backgroundView.setVisibility(View.VISIBLE);
            } else {
                backgroundView.setVisibility(View.INVISIBLE);
            }
            getDefaultUIUtil().onSelected(foregroundView);
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder holder) {
        final View foregroundView = ((AssignmentAdapter.AssignmentViewHolder) holder).viewForeground;
        getDefaultUIUtil().clearView(foregroundView);
    }

    @Override
    public void onChildDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder holder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((AssignmentAdapter.AssignmentViewHolder) holder).viewForeground;
        getDefaultUIUtil().onDraw(canvas, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder holder, int direction) {
        listener.onSwiped(holder, direction, holder.getAdapterPosition());
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder dragged, RecyclerView.ViewHolder target) {
        return listener.onMove(dragged, target);
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }
}
