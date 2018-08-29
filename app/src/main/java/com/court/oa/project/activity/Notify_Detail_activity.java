package com.court.oa.project.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.court.oa.project.R;
import com.court.oa.project.application.MyApplication;
import com.court.oa.project.bean.ArticalDetailBean;
import com.court.oa.project.contants.Contants;
import com.court.oa.project.okhttp.OkHttpManager;
import com.court.oa.project.save.SharePreferenceUtils;
import com.court.oa.project.tool.FitStateUI;
import com.court.oa.project.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Request;

public class Notify_Detail_activity extends AppCompatActivity implements View.OnClickListener, OkHttpManager.DataCallBack {
    private String title;
    private int id;
    private ArticalDetailBean articalDetailBean;
    private TextView notifyList_title,notifyList_name,notifyList_time,notifyList_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE); // 设置无标题栏
        MyApplication.getInstance().addActivity(this);
        FitStateUI.setImmersionStateMode(this);
        setContentView(R.layout.activity_notify_list_detail);
        initView();
    }

    private void initView() {
        ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        TextView tv_title = findViewById(R.id.tv_title);
        title = getIntent().getStringExtra("title");
        id = getIntent().getIntExtra("id", 0);
        tv_title.setText(title);
        TextView tv_sort = findViewById(R.id.tv_sort);
        tv_sort.setVisibility(View.INVISIBLE);
        ImageView iv_set = findViewById(R.id.iv_set);
        iv_set.setVisibility(View.INVISIBLE);
        notifyList_title = findViewById(R.id.notifyList_title);
        notifyList_name = findViewById(R.id.notifyList_name);
        notifyList_time = findViewById(R.id.notifyList_time);
        notifyList_content = findViewById(R.id.notifyList_content);
        initArticalDetail();
    }

    private void initArticalDetail() {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("articleId", id);
        parameters.put("appToken", SharePreferenceUtils.readUser("appToken", this));
        OkHttpManager.postAsync(
                Contants.ARTICLE_DETAIL, parameters,
                this, Contants.ARTICLE_DETAIL);
    }

    public void requestFailure(Request request, IOException e, String method) {
        ToastUtil.getShortToastByString(this,
                getString(R.string.networkRequst_resultFailed));
    }

    @Override
    public void requestSuccess(String result, String method) throws Exception {
        JSONObject object = new JSONObject(result);
        if (object.getInt("code") == 1) {
            String jsonObj1 = object.getString("data");
            switch (method) {
                case Contants.ARTICLE_DETAIL:
                    Gson gson = new Gson();
                    articalDetailBean = gson.fromJson(jsonObj1, new TypeToken<ArticalDetailBean>() {
                    }.getType());
                    notifyList_title.setText(articalDetailBean.getTitle());
                    notifyList_time.setText(articalDetailBean.getPublishTime());
                    notifyList_name.setVisibility(View.GONE);
                    notifyList_content.setText(articalDetailBean.getDesc());
                    break;

                default:
                    break;
            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                this.finish();
                break;
        }
    }
}
