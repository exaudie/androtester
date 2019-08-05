package com.exam.ztes.Zinclude;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exam.ztes.R;
/**
 * Created by admin on 10/31/2017.
 */

public class PopUp {


    //    TODO : POPUP Untuk Blok Untuk Update Aplikasi.
    public static void popUpBlockUpdateApps(final Context cntx, final String apkname, final String appslink) {
        TextView Message;
        Button btnLogout;

        final Dialog dialog = new Dialog(cntx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*atur width gagal*/
//        LinearLayout.LayoutParams dialogParams = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT, 350);
        LayoutInflater li = (LayoutInflater) cntx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = li.inflate(R.layout.item_confirmicon, null, false);
        v.setBackgroundColor(cntx.getResources().getColor(android.R.color.transparent));
        /*atur width gagal*/
//        dialog.setContentView(v,dialogParams);
        dialog.setContentView(v);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        /*atur width Sukses*/
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();

        ImageView iv_banner = (ImageView) dialog.findViewById(R.id.iv_banner);
        iv_banner.setImageBitmap(((BitmapDrawable) cntx.getResources().getDrawable(R.drawable.ic_download)).getBitmap());
        iv_banner.setBackgroundColor(cntx.getResources().getColor(R.color.blue_500));
        Message = (TextView) dialog.findViewById(R.id.tv_message);
        Message.setText("Tersedia Aplikasi Terbaru, Tap OK untuk Update Aplikasi.");
        btnLogout = (Button) dialog.findViewById(R.id.btn_ok);
        btnLogout.setTextColor(cntx.getResources().getColor(R.color.blue_500));
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DownNewVersionApps loadversi = new DownNewVersionApps(cntx, ConfUrl.APKURL, apkname);
//                loadversi.execute();

                Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                i.setData(Uri.parse(appslink));
                cntx.startActivity(i);
                dialog.dismiss();
            }
        });
    }

    //    TODO : POPUP Untuk blok Tidak Dapat Handle Order.
    public static void popUpBlockNotHandleTextHtml(final Activity actv, String sMessage) {
        TextView tvMessage;
        Button btnLogout;

        final Dialog dialog = new Dialog(actv);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*atur width gagal*/
//        LinearLayout.LayoutParams dialogParams = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT, 350);
        LayoutInflater li = (LayoutInflater) actv.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = li.inflate(R.layout.item_confirmicon, null, false);
        v.setBackgroundColor(actv.getResources().getColor(android.R.color.transparent));
        /*atur width gagal*/
//        dialog.setContentView(v,dialogParams);
        dialog.setContentView(v);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        /*atur width Sukses*/
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();

        tvMessage = (TextView) dialog.findViewById(R.id.tv_message);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvMessage.setText(Html.fromHtml(sMessage, Html.FROM_HTML_MODE_LEGACY));
        } else {
            tvMessage.setText(Html.fromHtml(sMessage));
        }
        btnLogout = (Button) dialog.findViewById(R.id.btn_ok);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actv.finish();
                dialog.dismiss();
            }
        });
    }

    //    TODO : POPUP Untuk Info Biasa.
    public static void popInfo(final Context cntx, int type, String message) {
        TextView Message;
        ImageView banner;
        Button btnOk;

        final Dialog dialog = new Dialog(cntx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater li = (LayoutInflater) cntx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = li.inflate(R.layout.item_confirmicon, null, false);

        dialog.setContentView(v);
        dialog.setCanceledOnTouchOutside(false);

        /*atur width Sukses*/
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();
        banner = (ImageView) dialog.findViewById(R.id.iv_banner);
        Message = (TextView) dialog.findViewById(R.id.tv_message);
        btnOk = (Button) dialog.findViewById(R.id.btn_ok);
        if (type == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                banner.setImageDrawable(cntx.getResources().getDrawable(R.drawable.ic_cancel_circle, cntx.getApplicationContext().getTheme()));
            } else {
                banner.setImageDrawable(cntx.getResources().getDrawable(R.drawable.ic_cancel_circle));
            }
            banner.setBackgroundColor(cntx.getResources().getColor(R.color.orange_500));
            btnOk.setTextColor(cntx.getResources().getColor(R.color.orange_500));
        } else if (type == 1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                banner.setImageDrawable(cntx.getResources().getDrawable(R.drawable.ic_check_circle, cntx.getApplicationContext().getTheme()));
            } else {
                banner.setImageDrawable(cntx.getResources().getDrawable(R.drawable.ic_check_circle));
            }
            banner.setBackgroundColor(cntx.getResources().getColor(R.color.green_500));
            btnOk.setTextColor(cntx.getResources().getColor(R.color.green_500));
        }
        Message.setTextColor(cntx.getResources().getColor(R.color.grey_700));
        Message.setText(message);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    /*********************** PopUp konfirmasi ya atau tidak TANPA keterangan ***********************/
    public static void popUpConfimYesOrNoTextHtml(Context cntx, int bgcol, String sMessage, TaskPopUpYesNo parTaskPopUp) {
        final TaskPopUpYesNo taskPopUp = parTaskPopUp;

        final Dialog dialog = new Dialog(cntx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater li = (LayoutInflater) cntx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert li != null;
        View v = li.inflate(R.layout.item_confirm2keticon, null, false);
        dialog.setContentView(v);
        dialog.setCanceledOnTouchOutside(false); // ijinkan tap area lain
        dialog.setCancelable(true); // Ijinkan tap tombol back
        /*atur width Sukses*/
        Window window = dialog.getWindow();
        assert window != null;
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();

        /** **************** Setting View *******************/
        ImageView ivBanner = dialog.findViewById(R.id.iv_banner);
        ivBanner.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ivBanner.setImageDrawable(cntx.getResources().getDrawable(R.drawable.ic_question_circle, cntx.getApplicationContext().getTheme()));
        } else {
            ivBanner.setImageDrawable(cntx.getResources().getDrawable(R.drawable.ic_question_circle));
        }
        ivBanner.setBackgroundColor(cntx.getResources().getColor(bgcol));

        TextView tvMessage = dialog.findViewById(R.id.tv_message);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvMessage.setText(Html.fromHtml(sMessage, Html.FROM_HTML_MODE_LEGACY));
        } else {
            tvMessage.setText(Html.fromHtml(sMessage));
        }

        Button btnYa = dialog.findViewById(R.id.btn_yes);
        btnYa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskPopUp.onClickYa(dialog);
            }
        });
        Button btnTidak = dialog.findViewById(R.id.btn_no);
        btnTidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskPopUp.onClickTidak(dialog);
            }
        });
    }

    /*********************** PopUp konfirmasi ya atau tidak DENGAN keterangan ***********************/
    public static void popUpConfimYesOrNoSetKetTextHtml(Context cntx, int bgcol, String sMessage, TaskPopUpYesNo parTaskPopUp) {
        final TaskPopUpYesNo taskPopUp = parTaskPopUp;

        final Dialog dialog = new Dialog(cntx);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater li = (LayoutInflater) cntx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert li != null;
        View v = li.inflate(R.layout.item_confirm2keticon, null, false);
        dialog.setContentView(v);
        dialog.setCanceledOnTouchOutside(false);
        /*atur width Sukses*/
        Window window = dialog.getWindow();
        assert window != null;
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();

        /** **************** Setting View *******************/
        ImageView ivBanner = dialog.findViewById(R.id.iv_banner);
        ivBanner.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ivBanner.setImageDrawable(cntx.getResources().getDrawable(R.drawable.ic_question_circle, cntx.getApplicationContext().getTheme()));
        } else {
            ivBanner.setImageDrawable(cntx.getResources().getDrawable(R.drawable.ic_question_circle));
        }
        ivBanner.setBackgroundColor(cntx.getResources().getColor(bgcol));

        TextView tvMessage = dialog.findViewById(R.id.tv_message);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvMessage.setText(Html.fromHtml(sMessage, Html.FROM_HTML_MODE_LEGACY));
        } else {
            tvMessage.setText(Html.fromHtml(sMessage));
        }

        TextInputLayout tilKet = dialog.findViewById(R.id.til_ket);
        tilKet.setVisibility(View.VISIBLE);

        Button btnYa = dialog.findViewById(R.id.btn_yes);
        btnYa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskPopUp.onClickYa(dialog);
            }
        });
        Button btnTidak = dialog.findViewById(R.id.btn_no);
        btnTidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskPopUp.onClickTidak(dialog);
            }
        });
    }

    /*********************** PopUp konfirmasi Hanya Berisi Tombol OK ***********************/
    public static void popUpConfimOk(Context cntx, final boolean appOk, String message, final TaskPopUpOk taskPopUpOk) {
        final Dialog dialog = new Dialog(cntx);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater li = (LayoutInflater) cntx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert li != null;
        View v = li.inflate(R.layout.item_confirmicon, null, false);
        dialog.setContentView(v);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        /*atur width Sukses*/
        Window window = dialog.getWindow();
        assert window != null;
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();

        ImageView ivBanner = dialog.findViewById(R.id.iv_banner);
        TextView Message = dialog.findViewById(R.id.tv_message);
//
        Message.setTextColor(cntx.getResources().getColor(R.color.grey_700));
        Message.setText(message);
        Button btnOk = dialog.findViewById(R.id.btn_ok);
        if (appOk) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ivBanner.setImageDrawable(cntx.getResources().getDrawable(R.drawable.ic_check_circle, cntx.getApplicationContext().getTheme()));
            } else {
                ivBanner.setImageDrawable(cntx.getResources().getDrawable(R.drawable.ic_check_circle));
            }
            ivBanner.setBackgroundColor(cntx.getResources().getColor(R.color.green_500));
            btnOk.setTextColor(cntx.getResources().getColor(R.color.green_500));
        }
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (taskPopUpOk != null) {
                    taskPopUpOk.onClickOk(appOk);
                }
                dialog.dismiss();

            }
        });
    }

    /*********************** PopUp konfirmasi Hanya Berisi Tombol OK ***********************/
    public static void popUpConfimOkTextHtml(Context cntx, final boolean appOk, String message, final TaskPopUpOk taskPopUpOk) {
        final Dialog dialog = new Dialog(cntx);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater li = (LayoutInflater) cntx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert li != null;
        View v = li.inflate(R.layout.item_confirmicon, null, false);
        dialog.setContentView(v);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        /*atur width Sukses*/
        Window window = dialog.getWindow();
        assert window != null;
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();

        ImageView ivBanner = dialog.findViewById(R.id.iv_banner);
        TextView Message = dialog.findViewById(R.id.tv_message);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Message.setText(Html.fromHtml(message, Html.FROM_HTML_MODE_LEGACY));
        } else {
            Message.setText(Html.fromHtml(message));
        }

        Button btnOk = dialog.findViewById(R.id.btn_ok);
        if (appOk) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ivBanner.setImageDrawable(cntx.getResources().getDrawable(R.drawable.ic_check_circle, cntx.getApplicationContext().getTheme()));
            } else {
                ivBanner.setImageDrawable(cntx.getResources().getDrawable(R.drawable.ic_check_circle));
            }
            ivBanner.setBackgroundColor(cntx.getResources().getColor(R.color.green_500));
            btnOk.setTextColor(cntx.getResources().getColor(R.color.green_500));
        }
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (taskPopUpOk != null) {
                    taskPopUpOk.onClickOk(appOk);
                }
                dialog.dismiss();

            }
        });
    }


}