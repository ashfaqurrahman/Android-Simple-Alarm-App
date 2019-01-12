

package com.ashfaq.alarm.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;

/**
 * Created by Md. Ashfaqur Rahman on 11/01/2019.
 *
 * Base class for creating AlertDialogs with 'cancel' and 'ok' actions.
 */
public abstract class BaseAlertDialogFragment extends AppCompatDialogFragment {

    protected abstract void onOk();

    @Override
    public final Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onOk();
                    }
                });
        return createFrom(builder);
    }

    /**
     * Subclasses can override this to make any modifications to the given Builder instance,
     * which already has its negative and positive buttons set.
     * <p></p>
     * The default implementation creates and returns the {@code AlertDialog} as is.
     */
    protected AlertDialog createFrom(AlertDialog.Builder builder) {
        return builder.create();
    }
}
