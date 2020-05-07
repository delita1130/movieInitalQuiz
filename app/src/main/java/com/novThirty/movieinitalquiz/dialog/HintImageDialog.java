package com.novThirty.movieinitalquiz.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.novThirty.movieinitalquiz.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HintImageDialog {

    private Context context;
    private Bitmap bitmap;

    public HintImageDialog(Context context) {
        this.context = context;
    }


    // 호출할 다이얼로그 함수를 정의한다.
    public int callFunction(final String imageUrl) {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.dialog_4);

        ImageView hintImage = dlg.findViewById(R.id.HintImage);

        Thread mThread = new Thread() {

            @Override
            public void run() {

                try {
                    URL url = new URL(imageUrl);

                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);

                } catch(Exception ex) {

                }
            }
        };

        mThread.start();

        try {
            mThread.join();
            hintImage.setImageBitmap(bitmap);
        } catch (InterruptedException e) {

        }

        dlg.show();

        LinearLayout layout = dlg.findViewById(R.id.okLayout);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return 1;
    }
}
