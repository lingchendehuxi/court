package com.court.oa.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.court.oa.project.R;
import com.court.oa.project.adapter.ViewPagerAdapter;
import com.court.oa.project.application.MyApplication;
import com.court.oa.project.bean.QuestionDetailBean;
import com.court.oa.project.bean.QuestionOptionBean;
import com.court.oa.project.bean.QuestionOptionValueBean;
import com.court.oa.project.bean.SubmitQuestionChildren;
import com.court.oa.project.bean.SubmitQuestionParent;
import com.court.oa.project.contants.Contants;
import com.court.oa.project.fragment.QuestionFragment;
import com.court.oa.project.okhttp.OkHttpManager;
import com.court.oa.project.save.SharePreferenceUtils;
import com.court.oa.project.tool.FitStateUI;
import com.court.oa.project.utils.ToastUtil;
import com.court.oa.project.view.NoScrollViewPager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Request;

import static com.court.oa.project.contants.Contants.EXAM_CREATE;
import static com.court.oa.project.contants.Contants.EXAM_DETAIL;

public class Question_activity extends AppCompatActivity implements View.OnClickListener, OkHttpManager.DataCallBack {

    private NoScrollViewPager vp;
    private ArrayList<Fragment> list;
    private String exId;
    private QuestionDetailBean qustion;
    private ArrayList<QuestionOptionBean> listOption;
    private TextView tv_next;
    private int currentPosition;
    public ArrayList<SubmitQuestionParent> listparent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE); // 设置无标题栏
        MyApplication.getInstance().addActivity(this);
        FitStateUI.setImmersionStateMode(this);
        Intent intent = getIntent();
        exId = intent.getStringExtra("exId");
        if (listparent == null) {
            listparent = new ArrayList<>();
        }
        initQuestionData();
    }

    private void initQuestionData() {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("exId", exId);
        parameters.put("uid", SharePreferenceUtils.readUser("userId", this));
        parameters.put("appToken", SharePreferenceUtils.readUser("appToken", this));
        OkHttpManager.postAsync(
                EXAM_DETAIL, parameters,
                this, EXAM_DETAIL);
    }

    @Override
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
                case EXAM_DETAIL:
                    Gson gson = new Gson();
                    qustion = gson.fromJson(jsonObj1, new TypeToken<QuestionDetailBean>() {
                    }.getType());
                    if (qustion.getStatus() == 1) {
                        Intent intent = new Intent(Question_activity.this, Notify_question_result_activity.class);
                        intent.putExtra("result",qustion);
                        startActivity(intent);
                        this.finish();
                    } else {
                        setContentView(R.layout.activity_question);
                        tv_next = findViewById(R.id.tv_next);
                        tv_next.setOnClickListener(this);
                        initFragment();
                    }
                    break;
                case EXAM_CREATE:
                    Gson gson1 = new Gson();
                    qustion = gson1.fromJson(jsonObj1, new TypeToken<QuestionDetailBean>() {
                    }.getType());
                    Intent intent = new Intent(Question_activity.this,Notify_question_result_activity.class);
                    intent.putExtra("result",qustion);
                    startActivity(intent);
                    break;

                default:
                    break;
            }
        }

    }

    public void initFragment() {
        ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("问卷调查");
        TextView tv_sort = findViewById(R.id.tv_sort);
        tv_sort.setVisibility(View.INVISIBLE);
        ImageView iv_set = findViewById(R.id.iv_set);
        iv_set.setVisibility(View.INVISIBLE);
        vp = findViewById(R.id.app_viewpage);
        list = new ArrayList<>();
        listOption = (ArrayList<QuestionOptionBean>) qustion.getQuestions();
        if(listOption.size()==1){
            tv_next.setText("提交");
        }
        for (int i = 0; i < listOption.size(); i++) {
            SubmitQuestionParent parent = new SubmitQuestionParent();
            parent.setQusId(listOption.get(i).getQusId());
            listparent.add(parent);
            QuestionFragment fragment = new QuestionFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(Contants.QUESTION_ID, listOption.get(i));
            bundle.putInt(Contants.QUESTION_GOBACK, i);
            fragment.setArguments(bundle);
            list.add(fragment);
        }
        FragmentManager manager = getSupportFragmentManager();
        ViewPagerAdapter adapter = new ViewPagerAdapter(manager, list);
        vp.setNoScroll(true);
        vp.setAdapter(adapter);
    }

    //接口函数写值
    public void setQustionValue(ArrayList<SubmitQuestionChildren> childrens, int currentPosition) {
        listparent.get(currentPosition).setChoseOptions(childrens);
    }

    //提交调查表
    private void sumbitQuestion() {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("exId", exId);
        parameters.put("questions", listparent);
        parameters.put("uId", SharePreferenceUtils.readUser("userId", this));
        parameters.put("appToken", SharePreferenceUtils.readUser("appToken", this));
        OkHttpManager.postAsync(
               EXAM_CREATE, parameters,
                this, EXAM_CREATE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                this.finish();
                break;
            case R.id.tv_next:
                if(listparent==null || listparent.size()==0){//IndexOutOfBoundsException
                    return;
                }
                if (listparent.get(currentPosition).getChoseOptions() == null) {
                    ToastUtil.getShortToastByString(this, "请先选中答案");
                    return;
                }
                if ("提交".equals(tv_next.getText().toString().trim())) {
                    sumbitQuestion();
                }
                currentPosition++;
                if (currentPosition == listOption.size() - 1) {
                    vp.setCurrentItem(currentPosition);
                    tv_next.setText("提交");
                } else if (currentPosition < listOption.size() - 1) {
                    vp.setCurrentItem(currentPosition);
                } else {
                    currentPosition--;
                }

                break;
        }
    }
}

