package com.court.oa.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.court.oa.project.R;
import com.court.oa.project.application.MyApplication;
import com.court.oa.project.tool.FitStateUI;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Mine_leave_addmine_activity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private LinearLayout leave_approver;
    private LinearLayout ll_showPop,ll_begin,ll_end;
    private RadioGroup radio1,radio2,radio3;
    private RadioButton rb_b,rb_s,rb_tq,rb_c,rb_hl,rb_h,rb_sang,rb_qt;
    private TextView tv_begin,tv_end;

    Calendar selectedDate = Calendar.getInstance();
    Calendar startDate = Calendar.getInstance();
    Calendar endDate = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE); // 设置无标题栏
        MyApplication.getInstance().addActivity(this);
        FitStateUI.setImmersionStateMode(this);
        setContentView(R.layout.activity_mine_leave_addmine_activity);
        initView();
    }

    private void initView() {
        startDate.set(2010,0,1);//设置起始年份
        endDate.set(2030,0,0);//设置结束年份
        ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("请假");
        TextView tv_sort = findViewById(R.id.tv_sort);
        tv_sort.setVisibility(View.INVISIBLE);
        ImageView iv_set = findViewById(R.id.iv_set);
        iv_set.setVisibility(View.INVISIBLE);
        ll_begin = findViewById(R.id.ll_begin);
        ll_end = findViewById(R.id.ll_end);
        leave_approver = findViewById(R.id.leave_approver);
        ll_showPop = findViewById(R.id.ll_showPop);
        tv_begin = findViewById(R.id.tv_begin);
        tv_end = findViewById(R.id.tv_end);
        ll_showPop.setOnClickListener(this);
        ll_begin.setOnClickListener(this);
        ll_end.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.rb_b:
                radio2.clearCheck();
                radio3.clearCheck();
                break;
            case R.id.rb_s:
                radio2.clearCheck();
                radio3.clearCheck();
                break;
            case R.id.rb_tq:
                radio2.clearCheck();
                radio3.clearCheck();
                break;
            case R.id.rb_c:
                radio1.clearCheck();
                radio3.clearCheck();
                break;
            case R.id.rb_hl:
                radio1.clearCheck();
                radio3.clearCheck();
                break;
            case R.id.rb_h:
                radio1.clearCheck();
                radio3.clearCheck();
                break;
            case R.id.rb_sang:
                radio1.clearCheck();
                radio2.clearCheck();
                break;
            case R.id.rb_qt:
                radio1.clearCheck();
                radio2.clearCheck();
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                this.finish();
                break;
            case R.id.leave_approver:
                startActivity(new Intent(Mine_leave_addmine_activity.this,Mine_leave_chose_activity.class));
                break;
            case R.id.ll_showPop:
                showPopup();
                break;
            case R.id.ll_begin:
                if(isFastClick()){
                    showDataDialog(0);
                }
                break;
            case R.id.ll_end:
                if(isFastClick()){
                    showDataDialog(1);
                }
                break;
        }
    }
    private void showPopup(){
        View popupView = this.getLayoutInflater().inflate(R.layout.activity_leave_popup, null);
        radio1 = popupView.findViewById(R.id.radio1);
        radio2 = popupView.findViewById(R.id.radio2);
        radio3 = popupView.findViewById(R.id.radio3);
        rb_b = popupView.findViewById(R.id.rb_b);
        rb_s = popupView.findViewById(R.id.rb_s);
        rb_tq = popupView.findViewById(R.id.rb_tq);
        rb_c = popupView.findViewById(R.id.rb_c);
        rb_hl = popupView.findViewById(R.id.rb_hl);
        rb_h = popupView.findViewById(R.id.rb_h);
        rb_sang = popupView.findViewById(R.id.rb_sang);
        rb_qt = popupView.findViewById(R.id.rb_qt);
        rb_b.setOnCheckedChangeListener(this);
        rb_s.setOnCheckedChangeListener(this);
        rb_tq.setOnCheckedChangeListener(this);
        rb_c.setOnCheckedChangeListener(this);
        rb_hl.setOnCheckedChangeListener(this);
        rb_h.setOnCheckedChangeListener(this);
        rb_sang.setOnCheckedChangeListener(this);
        rb_qt.setOnCheckedChangeListener(this);
        WindowManager wm = (WindowManager) this
                .getSystemService(this.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        PopupWindow window = new PopupWindow(popupView,width- 100,height/2+80);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        // TODO: 2016/5/17 设置动画
        //window.setAnimationStyle(R.style.popup_window_anim);
        // TODO: 2016/5/17 设置可以获取焦点
        window.setFocusable(true);
        // TODO: 2016/5/17 设置可以触摸弹出框以外的区域
        window.setOutsideTouchable(true);
        // TODO：更新popupwindow的状态
        window.update();
        // TODO: 2016/5/17 以下拉的方式显示，并且可以设置显示的位置
        window.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0,0);
    }

    /**
     * @param index
     */
    public void showDataDialog(final int index)
    {
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                switch (index){
                    case 0:
                        tv_begin.setText(getTime(date));
                        break;
                    case 1:
                        tv_end.setText(getTime(date));
                        break;
                }
            }
        }).setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentSize(18)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleText("选择时间")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
//                .isCyclic(true)//是否循环滚动
//                //.setTitleColor(Color.BLACK)//标题文字颜色
//                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
//                .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                //.setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//                .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate,endDate)//起始终止年月日设定
//                .setLabel("年","月","日","时","分","秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                .isDialog(true)//是否显示为对话框样式
                .build();

        pvTime.show();
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     */
    public static String getStringDate(Long date)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        String dateString = formatter.format(date);

        return dateString;
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        //"YYYY-MM-DD HH:MM:SS"        "yyyy-MM-dd"
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }
}
