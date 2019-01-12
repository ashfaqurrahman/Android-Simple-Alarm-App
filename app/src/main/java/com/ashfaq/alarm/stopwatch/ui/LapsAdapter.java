

package com.ashfaq.alarm.stopwatch.ui;

import android.view.ViewGroup;

import com.ashfaq.alarm.list.BaseCursorAdapter;
import com.ashfaq.alarm.list.OnListItemInteractionListener;
import com.ashfaq.alarm.stopwatch.Lap;
import com.ashfaq.alarm.stopwatch.data.LapCursor;

/**
 * Created by Md. Ashfaqur Rahman on 11/01/2019.
 */
public class LapsAdapter extends BaseCursorAdapter<Lap, LapViewHolder, LapCursor> {
    public static final int VIEW_TYPE_FIRST_LAP = 1; // TOneverDO: 0, that's the default view type

    public LapsAdapter() {
        super(null/*OnListItemInteractionListener*/);
    }

    @Override
    protected LapViewHolder onCreateViewHolder(ViewGroup parent, OnListItemInteractionListener<Lap> listener, int viewType) {
        // TODO: Consider defining a view type for the lone first lap. We should persist this first lap
        // when is is created, but this view type will tell us it should not be visible until there
        // are at least two laps.
        // Or could we return null? Probably not because that will get passed into onBindVH, unless
        // you check for null before accessing it.
        // RecyclerView.ViewHolder has an internal field that holds viewType for us,
        // and can be retrieved by the instance via getItemViewType().
        return new LapViewHolder(parent);
    }

    @Override
    public int getItemViewType(int position) {
        return getItemCount() == 1 ? VIEW_TYPE_FIRST_LAP : super.getItemViewType(position);
    }
}
