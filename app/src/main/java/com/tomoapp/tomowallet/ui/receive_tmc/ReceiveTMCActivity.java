package com.tomoapp.tomowallet.ui.receive_tmc;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tomoapp.tomowallet.R;
import com.tomoapp.tomowallet.base.BaseActivity;
import com.tomoapp.tomowallet.base.BaseSocketActivity;
import com.tomoapp.tomowallet.base.MainApplication;
import com.tomoapp.tomowallet.helper.LogUtil;
import com.tomoapp.tomowallet.helper.ToastUtil;
import com.tomoapp.tomowallet.model.userInfo.UserInfoRepository;
import com.tomoapp.tomowallet.model.userInfo.pojo.UserInfo;
import com.tomoapp.tomowallet.model.wallet.Wallet;
import com.tomoapp.tomowallet.model.wallet.WalletRepository;

import net.glxn.qrgen.android.QRCode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by macbook on 12/27/17.
 */

public class ReceiveTMCActivity extends BaseSocketActivity {


    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.container_header)
    LinearLayout containerHeader;
    @BindView(R.id.txt_1)
    TextView txt1;
    @BindView(R.id.btn_1)
    LinearLayout btn1;
    @BindView(R.id.img_qr)
    ImageView imgQr;
    private String walletAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_tmc);
        ButterKnife.bind(this);
        this.walletAddress = new WalletRepository(this).getAddress();
        if (walletAddress == null || walletAddress.isEmpty()){
            ToastUtil.showError();
            finish();
            return;
        }
        loadContent();

    }


    private void loadContent(){
        try {
            txt1.setText(walletAddress);
            Bitmap myBitmap = QRCode.from(walletAddress).bitmap();
            imgQr.setImageBitmap(myBitmap);
            LogUtil.d("activity: " + MainApplication.getCurrentActivity().getClass().getSimpleName());
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }

    @OnClick({R.id.btn_back, R.id.btn_1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_1:
                copyToClipboard();
                break;
        }
    }


    private void copyToClipboard(){
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(walletAddress);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", walletAddress);
            clipboard.setPrimaryClip(clip);
        }
    }
}
