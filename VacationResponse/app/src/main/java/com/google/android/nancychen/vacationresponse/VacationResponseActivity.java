package com.google.android.nancychen.vacationresponse;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

public class VacationResponseActivity extends ActionBarActivity {
    public static final String SAVED_VACATION_RESPONSE = "saved_vacation_response";
    public static final String VACATION_RESPONSE_ENABLED = "vacation_response_enabled";
    public static final String VACATION_RESPONSE_PREFERENCE = "vacation_response_preference_file";

    private Switch mEnabledSwitch;
    private SharedPreferences mVacationPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_response);
        mVacationPreferences = getSharedPreferences(
                VACATION_RESPONSE_PREFERENCE,
                Context.MODE_PRIVATE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vacation_response, menu);
        MenuItem enableDisable = menu.findItem(R.id.enable_disable);
        enableDisable
                .setActionView(R.layout.enable_disable_switch)
                .getActionView()
                .findViewById(R.id.switchForActionBar)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor editor = mVacationPreferences.edit();
                        editor.putBoolean(VACATION_RESPONSE_ENABLED, mEnabledSwitch.isChecked());
                        editor.commit();
                    }
        });

        Boolean enabled = mVacationPreferences.getBoolean(
                VacationResponseActivity.VACATION_RESPONSE_ENABLED, false);
        mEnabledSwitch = (Switch)
                enableDisable.getActionView().findViewById(R.id.switchForActionBar);
        mEnabledSwitch.setChecked(enabled);
        Log.i("VacationResponseActivity", "Checked: "+String.valueOf(mEnabledSwitch.isChecked()));
        return true;
    }

    public void saveResponse(View view) {
        String text = ((EditText) findViewById(R.id.edit_message)).getText().toString();
        SharedPreferences.Editor editor = mVacationPreferences.edit();
        editor.putString(SAVED_VACATION_RESPONSE, text);
        editor.commit();

        // Inform the user that the text was saved
        new AlertDialog.Builder(this).setMessage(text)
                .setTitle(R.string.dialog_saved)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();

    }
}
