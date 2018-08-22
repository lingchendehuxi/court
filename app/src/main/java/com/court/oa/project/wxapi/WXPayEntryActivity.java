package com.court.oa.project.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.court.oa.project.utils.ToastUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.text.MessageFormat;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {


    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String wxAppId = "wxcec03c638755a612";
        api = WXAPIFactory.createWXAPI(this, wxAppId);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.errCode == 0) {
            ToastUtil.getLongToastByString(this,"支付成功");
            this.finish();
        }
        // 用户取消支付
        else if (resp.errCode == -2) {
            ToastUtil.getLongToastByString(this,"支付取消");
            this.finish();
        }
        else if (resp.errCode == -1) {
            ToastUtil.getLongToastByString(this,"支付错误");
            this.finish();
        }
    }
}