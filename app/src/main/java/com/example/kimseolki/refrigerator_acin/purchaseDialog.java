package com.example.kimseolki.refrigerator_acin;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

/**
 * Created by 6201P-03 on 2017-05-24.
 */

public class purchaseDialog extends Dialog {
    private ImageButton ibEmart;
    private ImageButton ib11Street;
    private ImageButton ibGmarket;
    private ImageButton ibHomeplus;

    private View.OnClickListener mEmartClickListener;
    private View.OnClickListener mGmarketClickListener;
    private View.OnClickListener mHomeplusClickListener;
    private View.OnClickListener m11StreetClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.dialog_purchase);

        ibEmart = (ImageButton) findViewById(R.id.ibEmart);
        ibGmarket = (ImageButton) findViewById(R.id.ibGmarket);
        ibHomeplus = (ImageButton) findViewById(R.id.ibHomeplus);
        ib11Street = (ImageButton) findViewById(R.id.ib11bg);

        // 클릭 이벤트 셋팅
        if (mEmartClickListener != null && m11StreetClickListener != null
                && mGmarketClickListener != null && mHomeplusClickListener != null) {
            ibEmart.setOnClickListener(mEmartClickListener);
            ibGmarket.setOnClickListener(mGmarketClickListener);
            ibHomeplus.setOnClickListener(mHomeplusClickListener);
            ib11Street.setOnClickListener(m11StreetClickListener);
        }

    }

    public purchaseDialog(@NonNull Context context, View.OnClickListener clickListener1,
                          View.OnClickListener clickListener2, View.OnClickListener clickListener3,
                          View.OnClickListener clickListener4) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mEmartClickListener = clickListener1;
        this.mGmarketClickListener = clickListener2;
        this.mHomeplusClickListener = clickListener3;
        this.m11StreetClickListener = clickListener4;
    }


}
