package com.google.android.nancychen.vacationresponse;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class VacationResponseActivity extends ActionBarActivity {
    public static final String SAVED_VACATION_RESPONSE = "saved_vacation_response";
    public static final String VACATION_RESPONSE_ENABLED = "vacation_response_enabled";
    public static final String VACATION_RESPONSE_PREFERENCE = "vacation_response_preference_file";

    private Switch mEnabledSwitch;
    private SharedPreferences mVacationPreferences;
    private Boolean mIsEditMode;
    private TextView mTextView;
    private EditText mEditView;
    private Button mToggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_response);

        mVacationPreferences = getSharedPreferences(
                VACATION_RESPONSE_PREFERENCE,
                Context.MODE_PRIVATE);

        String response = mVacationPreferences.getString(
                VacationResponseActivity.SAVED_VACATION_RESPONSE, null);

        mIsEditMode = TextUtils.isEmpty(response);
        mEditView = (EditText) findViewById(R.id.edit_message);
        mTextView = (TextView) findViewById(R.id.display_message);
        mToggleButton = (Button) findViewById(R.id.toggle_button);

        if (!mIsEditMode) {
            mTextView.setText(response);
            mEditView.setText(response);
            setDisplay();
        }
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
        return true;
    }

    public void toggleDisplay(View view) {
        if (mIsEditMode) {
            String text = mEditView.getText().toString();
            mTextView.setText(text);

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
        mIsEditMode = !mIsEditMode;
        setDisplay();
    }

    private void setDisplay() {
        // Toggle display mode
        mTextView.setVisibility(mIsEditMode ? View.GONE : View.VISIBLE);
        mEditView.setVisibility(mIsEditMode ? View.VISIBLE : View.GONE);
        mToggleButton.setText(mIsEditMode ? R.string.button_save : R.string.button_edit);
    }
}