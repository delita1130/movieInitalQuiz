package com.novThirty.movieinitalquiz.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.novThirty.movieinitalquiz.QuizMainActivity;
import com.novThirty.movieinitalquiz.R;

/**
 * Created by Administrator on 2017-08-07.
 */

public class DefaultDialog {
    private Context context;
    private Dialog dlg;
    private View.OnClickListener mPositiveListener;

    public DefaultDialog(Context context, View.OnClickListener positiveListener) {
        this.context = context;
        this.mPositiveListener = positiveListener;
    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction() {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.dlg
        dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.dialog_5);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        LinearLayout layout = (LinearLayout) dlg.findViewById(R.id.okLayout);
        Button okButton = dlg.findViewById(R.id.okButton);

        okButton.setOnClickListener(mPositiveListener);

        dlg.setCancelable(false);

        // 다이얼로그 종료 후 처리
        /*dlg.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });*/
    }

    public void dismiss(){
        dlg.dismiss();
        ((QuizMainActivity) context).finish();
    }

}
