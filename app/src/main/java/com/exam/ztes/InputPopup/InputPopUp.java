package com.exam.ztes.InputPopup;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.exam.ztes.R;

import java.util.ArrayList;
import java.util.List;

public class InputPopUp extends AppCompatActivity implements View.OnClickListener {

    List<objEditTextInput> grupView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inputpopup);

        grupView = new ArrayList<>();
        final TextInputLayout til_et_ = findViewById(R.id.til_et_);
        final TextInputEditText et_ = findViewById(R.id.et_);
        et_.requestFocus();
        et_.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                popUpEditInput(getIndexInput(view));
            }
        });

        setGrupView(til_et_, et_);
        setGrupView((TextInputLayout) findViewById(R.id.til_et_u), (TextInputEditText) findViewById(R.id.et_u));
    }

    @Override
    public void onClick(View view) {
        if (view instanceof TextInputEditText) {
            popUpEditInput(getIndexInput(view));
        }
    }

    private void setGrupView(TextInputLayout TIL, TextInputEditText TIET) {
        TIET.setOnClickListener(this);
        objEditTextInput editTextInput = new objEditTextInput();
        editTextInput.setInputLayout(TIL);
        editTextInput.setInputEdit(TIET);
        grupView.add(editTextInput);
    }

    private int getIndexInput(View view) {
        for (objEditTextInput _item : grupView) {
            if (_item.getInputEdit().equals(view)) {
                return grupView.indexOf(_item);
            }
        }
        return -1;
    }

    private void popUpEditInput(int indexInput) {
        final Dialog dialog = new Dialog(InputPopUp.this);
        LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert li != null;
        View v = li.inflate(R.layout.layout_dialog, null, false);
        /*atur width gagal*/
        dialog.setContentView(v);
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setCancelable(false);

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        objEditTextInput itemGrupView = grupView.get(indexInput);

        TextInputLayout itemGrupLayout = (TextInputLayout) itemGrupView.getInputLayout();
        TextInputLayout textInputLayout = dialog.findViewById(R.id.til_et_);
        textInputLayout.setHint(itemGrupLayout.getHint());

        final TextInputEditText itemGrupEdit = (TextInputEditText) itemGrupView.getInputEdit();
        final TextInputEditText textInputEditText = dialog.findViewById(R.id.tie_et_);
        textInputEditText.setText(itemGrupEdit.getText());
        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                itemGrupEdit.setText(textInputEditText.getText());
            }
        });
        textInputEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == R.id.ia_don || i == EditorInfo.IME_ACTION_GO) {
                    if (!itemGrupEdit.getText().equals(textInputEditText.getText())) {
                        itemGrupEdit.setText(textInputEditText.getText());
                    }
                    dialog.dismiss();
                    return true;
                }
                return false;
            }
        });
        dialog.show();
    }
}