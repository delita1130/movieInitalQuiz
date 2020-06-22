package com.novThirty.movieinitalquiz.dialog;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.novThirty.movieinitalquiz.R;
import com.novThirty.movieinitalquiz.config.GameStatus;
import com.novThirty.movieinitalquiz.database.GameDao;

public class HintDialog{

        private Context context;
        private int confirmNumber = 0;

        private View.OnClickListener mPositiveListener = null;
        private View.OnClickListener mNegativeListener = null;

        private Dialog dlg;

        public HintDialog(Context context,View.OnClickListener positiveListener, View.OnClickListener negativeListener) {
            this.context = context;
            this.mPositiveListener = positiveListener;
            this.mNegativeListener = negativeListener;
        }

        // 호출할 다이얼로그 함수를 정의한다.
        public int callFunction(final String mainTextString, final String pointTextString ) {

            // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
            dlg = new Dialog(context);

            // 액티비티의 타이틀바를 숨긴다.
            dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

            // 커스텀 다이얼로그의 레이아웃을 설정한다.
            dlg.setContentView(R.layout.dialog_1);

            // 커스텀 다이얼로그를 노출한다.
            dlg.show();

            LinearLayout layout = dlg.findViewById(R.id.okLayout);

            Button okButton = dlg.findViewById(R.id.OKButton);
            Button cancelButton = dlg.findViewById(R.id.CancelButton);

            TextView mainText = dlg.findViewById(R.id.mainText);
            TextView pointText = dlg.findViewById(R.id.pointText);

            mainText.setText(mainTextString);
            pointText.setText(pointTextString);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });

            okButton.setOnClickListener(mPositiveListener);
            cancelButton.setOnClickListener(mNegativeListener);

            return 1;
        }
        public void dismiss(){
            dlg.dismiss();
        }
}
