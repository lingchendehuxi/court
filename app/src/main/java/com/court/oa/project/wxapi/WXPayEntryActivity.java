package com.court.oa.project.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

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
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        intent.setAction("payment_complete");
        if (resp.errCode == 0) {
            bundle.putBoolean("IsSuccess", true);
            intent.putExtras(bundle);
            sendBroadcast(intent);
            this.finish();
            return;
        }
        // 用户取消支付
        if (resp.errCode == -2) {
            bundle.putBoolean("IsSuccess", false);
            intent.putExtras(bundle);
            sendBroadcast(intent);
            this.finish();
            return;
        }
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            String errorMsg = MessageFormat.format("微信支付结果：{0};错误代码：{1}", resp.errStr, String.valueOf(resp.errCode));
            bundle.putBoolean("IsSuccess", false);
            bundle.putString("ErrorMsg", errorMsg);
            intent.putExtras(bundle);
            sendBroadcast(intent);
            this.finish();
        }
    }
}