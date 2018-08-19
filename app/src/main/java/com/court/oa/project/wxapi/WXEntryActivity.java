package com.court.oa.project.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
	private IWXAPI api;
	public static final String APP_ID = "wxcec03c638755a612";// 微信开放平台申请到的app_id
	public static final String APP_SECRET = "c961b7b3c9433b35e8abb104b9814af7";// 微信开放平台申请到的app_id对应的app_secret//
	private static final String TAG = "WXEntryActivity";
	protected static final int RETURN_OPENID_ACCESSTOKEN = 0;// 返回openid，accessToken消息码
	protected static final int RETURN_NICKNAME_UID = 1; // 返回昵称，uid消息码

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		api = WXAPIFactory.createWXAPI(this, "wx5b70ac910da6c700", false);
		api.handleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq arg0) {
	}

	@Override
	public void onResp(BaseResp resp) {
		// LogManager.show(TAG, "resp.errCode:" + resp.errCode + ",resp.errStr:"
		// + resp.errStr, 1);
		switch (resp.errCode) {

			case BaseResp.ErrCode.ERR_OK:
				if (ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX == resp.getType()) {
					Toast.makeText(getApplicationContext(), "分享成功",
							Toast.LENGTH_LONG).show();
					this.finish();
					break;
				} else {
					String code = ((SendAuth.Resp) resp).code;
					// HttpUtil.showToast(getApplicationContext(), "登陆成功");
					Log.e("error.........", code);
				}
				// ����ɹ�
				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				// ����ȡ��
				if (ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX == resp.getType()) {
					Toast.makeText(getApplicationContext(), "分享取消",
							Toast.LENGTH_LONG).show();
					this.finish();
					break;
				} else {
					Toast.makeText(getApplicationContext(), "登陆取消",
							Toast.LENGTH_LONG).show();
				}
				System.out.println("ERR_USER_CANCEL");
				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				if (ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX == resp.getType()) {
					Toast.makeText(getApplicationContext(), "分享拒绝",
							Toast.LENGTH_LONG).show();
					this.finish();
					break;
				} else {
					Toast.makeText(getApplicationContext(), "登陆拒绝",
							Toast.LENGTH_LONG).show();
				}
				System.out.println("ERR_AUTH_DENIED");
				// ����ܾ�
				break;

		}
	}



}