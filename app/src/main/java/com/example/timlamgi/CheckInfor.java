package com.example.timlamgi;

import android.app.Activity;
import android.widget.EditText;

public class CheckInfor {
    Activity activity;

    public CheckInfor(Activity myActivity) {
        activity = myActivity;
    }

    public void checkEmpty(EditText check, String warning) {
        if (check.getText().toString().isEmpty()) {
            check.setError(warning);
            check.isFocused();
        }
    }
}
