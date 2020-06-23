package com.novThirty.movieinitalquiz.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Layout;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.novThirty.movieinitalquiz.R;

/**
 * Created by Administrator on 2017-08-07.
 */

public class IncorrectDialog {

    private Context context;
    private String message;
    private Dialog dlg;
    public IncorrectDialog(Context context) {
        this.context = context;
    }
    public IncorrectDialog(Context context,String message) {
        this.context = context;
        this.message = message;
    }
    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction(final TextView textView) {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
         dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.dialog_3);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        LinearLayout layout = (LinearLayout) dlg.findViewById(R.id.okLayout);
        TextView messageText = (TextView)dlg.findViewById(R.id.messageText);

        messageText.setText(message);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("");
                dlg.dismiss();
            }
        });
    }
    public void dismiss(){
        dlg.dismiss();
    }
}
