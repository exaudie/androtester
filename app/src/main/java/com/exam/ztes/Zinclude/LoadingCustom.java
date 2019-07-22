package id.co.multindo.sismafmobile.in.zpopup;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import id.co.multindo.sismafmobile.in.R;

public class LoadingCustom extends Dialog {
    Context cntx;
    Activity actv;
    String textDefault = "Loading...";
    TextView textView;

    public LoadingCustom(@NonNull Context context) {
        super(context);
        this.cntx = context;
    }

    public LoadingCustom(@NonNull Context context, Activity actv) {
        super(context);
        this.cntx = context;
        this.actv = actv;
    }

    public LoadingCustom(@NonNull Context context, String textDefault) {
        super(context);
        this.cntx = context;
        this.textDefault = textDefault;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_layout_progress_dialog);
        setCancelable(false);
//        Window window = getWindow();
//        assert window != null;
//        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textView = findViewById(R.id.dialog_progress_text);
        textView.setText(textDefault);
    }
}
