package com.court.oa.project.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.court.oa.project.R;
import com.court.oa.project.application.MyApplication;
import com.court.oa.project.tool.FitStateUI;
import com.court.oa.project.utils.Utils;

import java.util.Calendar;

public class Mine_leave_addmine_activity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private LinearLayout leave_approver;
    private LinearLayout ll_showPop,ll_begin,ll_end;
    private RadioGroup radio1,radio2,radio3;
    private RadioButton rb_b,rb_s,rb_tq,rb_c,rb_hl,rb_h,rb_sang,rb_qt;
    private TextView tv_begin,tv_end,tv_owner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE); // 设置无标题栏
        MyApplication.getInstance().addActivity(this);
        FitStateUI.setImmersionStateMode(this);
        setContentView(R.layout.activity_mine_leave_addmine_activity);
        initView();
        getDate();
    }

    private void initView() {
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
        tv_owner = findViewById(R.id.tv_owner);
        ll_showPop.setOnClickListener(this);
        ll_begin.setOnClickListener(this);
        ll_end.setOnClickListener(this);
        tv_owner.setOnClickListener(this);
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
                if(Utils.isFastClick()){
                    DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker arg0, int year, int month, int day) {
                            tv_begin.setText(year+"-"+(++month)+"-"+day);//将选择的日期显示到TextView中,因为之前获取month直接使用，所以不需要+1，这个地方需要显示，所以+1
                        }
                    };
                    DatePickerDialog dialog=new DatePickerDialog(Mine_leave_addmine_activity.this, 0,listener,year,month,day);//后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
                    dialog.show();
                }
                break;
            case R.id.ll_end:
                if(Utils.isFastClick()){
                    DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker arg0, int year, int month, int day) {
                            tv_end.setText(year+"-"+(++month)+"-"+day);//将选择的日期显示到TextView中,因为之前获取month直接使用，所以不需要+1，这个地方需要显示，所以+1
                        }
                    };
                    DatePickerDialog dialog=new DatePickerDialog(Mine_leave_addmine_activity.this, 0,listener,year,month,day);//后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
                    dialog.show();
                }
                break;
            case R.id.tv_owner:
                startActivity(new Intent(this,Mine_leave_chose_activity.class));
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

    private Calendar cal;
    private int year,month,day;

    //获取当前日期
    private void getDate() {
        cal=Calendar.getInstance();
        year=cal.get(Calendar.YEAR);       //获取年月日时分秒
        month=cal.get(Calendar.MONTH);   //获取到的月份是从0开始计数
        day=cal.get(Calendar.DAY_OF_MONTH);
    }


}
