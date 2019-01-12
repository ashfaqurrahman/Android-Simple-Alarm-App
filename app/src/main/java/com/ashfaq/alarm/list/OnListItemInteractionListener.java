

package com.ashfaq.alarm.list;

/**
 * Created by Md. Ashfaqur Rahman on 11/01/2019.
 */
public interface OnListItemInteractionListener<T> {
    void onListItemClick(T item, int position);
    void onListItemDeleted(T item);
    void onListItemUpdate(T item, int position);
}
