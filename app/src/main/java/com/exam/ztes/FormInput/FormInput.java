package com.exam.ztes.FormInput;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;

import com.exam.ztes.R;
import com.exam.ztes.Zinclude.WatcherKodeNoHp;

public class FormInput extends Activity {
    TextInputLayout til_nohp;
    TextInputEditText tiet_nohp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_input);

        til_nohp = findViewById(R.id.til_nohp);
        til_nohp.setError("ex : 08xxxxx");

        tiet_nohp = findViewById(R.id.tiet_nohp);
        tiet_nohp.addTextChangedListener(new WatcherKodeNoHp(tiet_nohp));
    }
}