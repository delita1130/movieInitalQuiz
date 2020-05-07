package com.novThirty.movieinitalquiz.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.novThirty.movieinitalquiz.R;

public class CorrectDialog {

        private Context context;

        public CorrectDialog(Context context) {
            this.context = context;
        }

        // 호출할 다이얼로그 함수를 정의한다.
        public int callFunction(final String pointTextString ) {

            // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
            final Dialog dlg = new Dialog(context);

            // 액티비티의 타이틀바를 숨긴다.
            dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

            // 커스텀 다이얼로그의 레이아웃을 설정한다.
            dlg.setContentView(R.layout.dialog_2);

            // 커스텀 다이얼로그를 노출한다.
            dlg.show();

            LinearLayout layout = dlg.findViewById(R.id.okLayout);
            TextView point = dlg.findViewById(R.id.pointText);

            point.setText(pointTextString);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            return 1;
        }
}
