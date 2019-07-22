package com.exam.ztes.Zinclude;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class WatcherKodeNoHp implements TextWatcher {
   private EditText et;

    public WatcherKodeNoHp(EditText et) {
        this.et = et;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        int count = editable.length();
        String str = editable.toString();
        if (count > 1 && count <= 3) {
            if (str.substring(0, 1).equalsIgnoreCase("8")) {
                str = "0" + str.substring(0);
            } else {
                return;
            }
        } else if (count > 3) {
            if (str.substring(0, 3).equalsIgnoreCase("+62")) {
                str = "0" + str.substring(3);
            } else {
                return;
            }
        } else {
            return;
        }
        et.setText(str);
        et.setSelection(et.length());
    }
}
