package com.google.android.nancychen.vacationresponse;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class VacationResponseActivity extends ActionBarActivity {
    public static final String SAVED_VACATION_RESPONSE = "saved_vacation_response";
    public static final String VACATION_RESPONSE_PREFERENCE = "vacation_response_preference_file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_response);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vacation_response, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveResponse(View view) {
        String text = ((EditText) findViewById(R.id.edit_message)).getText().toString();
        SharedPreferences pref = getSharedPreferences(
                VACATION_RESPONSE_PREFERENCE,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
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
