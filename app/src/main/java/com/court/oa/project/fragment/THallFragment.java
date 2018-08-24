package com.court.oa.project.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.court.oa.project.R;
import com.court.oa.project.adapter.Hall_week_dataAdapter;
import com.court.oa.project.adapter.THall_Leave_Adapter;
import com.court.oa.project.bean.HallPackageGoodBean;
import com.court.oa.project.bean.HallWeekBean;
import com.court.oa.project.bean.SubmitHallBean;
import com.court.oa.project.bean.WXPrePost;
import com.court.oa.project.contants.Contants;
import com.court.oa.project.okhttp.DailogShow;
import com.court.oa.project.okhttp.OkHttpManager;
import com.court.oa.project.save.SharePreferenceUtils;
import com.court.oa.project.tool.RefreshLayout;
import com.court.oa.project.utils.MD5Utils;
import com.court.oa.project.utils.ToastUtil;
import com.court.oa.project.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class THallFragment extends Fragment implements View.OnClickListener, RefreshLayout.OnLoadListener, SwipeRefreshLayout.OnRefreshListener, RadioGroup.OnCheckedChangeListener, OkHttpManager.DataCallBack {
    private HorizontalScrollView hs;
    private View view;
    private LinearLayout hall_date, ll_week_one, ll_week_two, ll_week_thr, ll_week_fou, ll_week_fiv, ll_week_six, ll_week_sev;
    private TextView hall_buy, tv_1, tv_2, tv_3, tv_4, tv_5, tv_6, tv_7;
    private List<String> listTime;
    private List<String> newListTime;
    private String currentTime, newPackageData;
    private String[] workDate = new String[]{"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
    private String[] workUpDate = new String[]{"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
    private LinearLayout hall_leave;
    private LinearLayout hall_work;
    private LinearLayout hall_package;
    private LinearLayout hall_commit;
    //
    private RefreshLayout swipeLayout;
    private ArrayList list;
    private ListView listView, listview_1, listview_2, listview_3;
    private THall_Leave_Adapter adapter;
    private Hall_week_dataAdapter adapterWeek;
    private int page = 1;
    private ArrayList<HallPackageGoodBean> listGood;
    private double allPrice;
    private Object[] newObject = new Object[4];
    private ArrayList<SubmitHallBean> newList;
    private String[] newWeekData = new String[7];
    private RadioButton rb_normal;

    Handler mHandler = new Handler() {
        /**
         * handleMessage接收消息后进行相应的处理
         * @param msg
         */
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    WXPrePost wxPrePost = (WXPrePost) msg.obj;
//                    ToastUtil.show(getActivity(),"SUCCESS"+wxPrePost.nonce_str+wxPrePost.prepayid);
                    getWeChatPlay(wxPrePost.prepayid, wxPrePost.nonce_str);
                    break;
                case 2:
                    ToastUtil.show(getActivity(), "获取订单失败！");
                    break;
            }

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.thallfragment, null);
        initView();
        return view;
    }

    private void initView() {
        hall_date = view.findViewById(R.id.hall_date);
        hall_buy = view.findViewById(R.id.hall_buy);
        hall_leave = view.findViewById(R.id.hall_leave);
        hall_work = view.findViewById(R.id.hall_work);
        hall_package = view.findViewById(R.id.hall_package);
        hall_commit = view.findViewById(R.id.hall_commit);
        swipeLayout = view.findViewById(R.id.swipe_container);
        listview_1 = view.findViewById(R.id.listview_1);
        listview_2 = view.findViewById(R.id.listview_2);
        listview_3 = view.findViewById(R.id.listview_3);
        listView = (ListView) view.findViewById(R.id.list);
        hall_buy.setOnClickListener(this);
        RadioGroup radio = view.findViewById(R.id.radio);
        hs = view.findViewById(R.id.hs);
        tv_1 = view.findViewById(R.id.tv_1);
        tv_2 = view.findViewById(R.id.tv_2);
        tv_3 = view.findViewById(R.id.tv_3);
        tv_4 = view.findViewById(R.id.tv_4);
        tv_5 = view.findViewById(R.id.tv_5);
        tv_6 = view.findViewById(R.id.tv_6);
        tv_7 = view.findViewById(R.id.tv_7);
        ll_week_one = view.findViewById(R.id.ll_week_one);
        ll_week_one.setOnClickListener(this);
        ll_week_two = view.findViewById(R.id.ll_week_two);
        ll_week_two.setOnClickListener(this);
        ll_week_thr = view.findViewById(R.id.ll_week_thr);
        ll_week_thr.setOnClickListener(this);
        ll_week_fou = view.findViewById(R.id.ll_week_fou);
        ll_week_fou.setOnClickListener(this);
        ll_week_fiv = view.findViewById(R.id.ll_week_fiv);
        ll_week_fiv.setOnClickListener(this);
        ll_week_six = view.findViewById(R.id.ll_week_six);
        ll_week_six.setOnClickListener(this);
        ll_week_sev = view.findViewById(R.id.ll_week_sev);
        ll_week_sev.setOnClickListener(this);
        radio.setOnCheckedChangeListener(this);
        rb_normal = view.findViewById(R.id.rb_normal);
        newList = new ArrayList<>();
        listTime = Utils.test(7);
        newListTime = new ArrayList<>();
        TextView[] textViews = new TextView[]{tv_1, tv_2, tv_3, tv_4, tv_5, tv_6, tv_7};
        for (int i = 0; i < listTime.size(); i++) {
            String string = listTime.get(i);
            String newString = string.substring(5, string.length());
            newListTime.add(newString.replace("-", "."));
            textViews[i].setText(newListTime.get(i));
        }
        for (int j = 0; j < newListTime.size(); j++) {
            newWeekData[j] = newListTime.get(j);
        }
        rb_normal.setChecked(true);
    }

    private void goneView() {
        hall_buy.setVisibility(View.GONE);
        hall_leave.setVisibility(View.GONE);
        hall_package.setVisibility(View.GONE);
        hall_work.setVisibility(View.GONE);
        hall_commit.setVisibility(View.GONE);
        hs.setVisibility(View.GONE);
    }

    //设置周菜单
    private void setWorkData() {
        goneView();
        hs.setVisibility(View.VISIBLE);
        hall_work.setVisibility(View.VISIBLE);
        hall_date.removeAllViews();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        final TextView[] textViews = new TextView[workDate.length];
        final TextView[] textViewslow = new TextView[workDate.length];
        for (int i = 0; i < workDate.length; i++) {
            final LinearLayout ll = new LinearLayout(getContext());
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            ll.setLayoutParams(params1);
            ll.setOrientation(LinearLayout.VERTICAL);
            final TextView textView = new TextView(getContext());
            final TextView textViewlow = new TextView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dm.widthPixels / 5, 25 * 3);
            textView.setLayoutParams(params);
            textViewlow.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER | Gravity.BOTTOM);
            textViewlow.setGravity(Gravity.CENTER);
            textView.setBackgroundResource(R.drawable.hall_title_chose);
            textViewlow.setBackgroundResource(R.drawable.hall_title_chose);
            textView.setText(workDate[i]);
            if (newListTime.size() > 0) {
                textViewlow.setText(newWeekData[i]);
            } else {
                textViewlow.setText(workDate[i]);
            }
            textViews[i] = textView;
            textViewslow[i] = textViewlow;
            final int j = i;
            ll.addView(textView);
            ll.addView(textViewlow);
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int t = 0; t < workDate.length; t++) {
                        textViews[t].setSelected(false);
                        textViewslow[t].setSelected(false);
                    }
                    textView.setSelected(true);
                    textViewlow.setSelected(true);
                    initHallWeekDate(workUpDate[j]);
                }
            });
            hall_date.addView(ll);
        }
        textViews[0].setSelected(true);
        textViewslow[0].setSelected(true);
        initHallWeekDate("星期一");
    }

    //设置打包
    private void setLeaveData() {
        goneView();
        hs.setVisibility(View.VISIBLE);
        hall_buy.setVisibility(View.VISIBLE);
        hall_leave.setVisibility(View.VISIBLE);
        setListener();
        hall_date.removeAllViews();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        final TextView[] textViews = new TextView[workDate.length];
        final TextView[] textViewslow = new TextView[workDate.length];
        for (int i = 0; i < workDate.length; i++) {
            final LinearLayout ll = new LinearLayout(getContext());
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            ll.setLayoutParams(params1);
            ll.setOrientation(LinearLayout.VERTICAL);
            final TextView textView = new TextView(getContext());
            final TextView textViewlow = new TextView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dm.widthPixels / 5, 25 * 3);
            textView.setLayoutParams(params);
            textViewlow.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER | Gravity.BOTTOM);
            textViewlow.setGravity(Gravity.CENTER);
            textView.setBackgroundResource(R.drawable.hall_title_chose);
            textViewlow.setBackgroundResource(R.drawable.hall_title_chose);
            textView.setText(workDate[i]);
            if (newListTime.size() > 0) {
                textViewlow.setText(newWeekData[i]);
            } else {
                textViewlow.setText(workDate[i]);
            }
            textViews[i] = textView;
            textViewslow[i] = textViewlow;
            final int j = i;
            ll.addView(textView);
            ll.addView(textViewlow);
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int t = 0; t < newListTime.size(); t++) {
                        textViews[t].setSelected(false);
                        textViewslow[t].setSelected(false);
                    }
                    textView.setSelected(true);
                    textViewlow.setSelected(true);
                    currentTime = listTime.get(j);
                }
            });
            hall_date.addView(ll);
        }
        currentTime = listTime.get(0);
        textViews[0].setSelected(true);
        textViewslow[0].setSelected(true);
        initHallGoodListDate(currentTime);
    }

    //周菜单
    private void initHallWeekDate(String weekDay) {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("weekDay", weekDay);
        parameters.put("appToken", SharePreferenceUtils.readUser("appToken", getActivity()));
        OkHttpManager.postAsync(
                Contants.HALL_WEEK, parameters,
                this, Contants.HALL_WEEK);
    }

    //商品列表
    private void initHallGoodListDate(String time) {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("showTime", "2018-7-24");//7-24
        parameters.put("ctgId", SharePreferenceUtils.readUser("userId", getActivity()));
        parameters.put("pageIndex", "" + 1);
        parameters.put("pageSize", "10");
        parameters.put("appToken", SharePreferenceUtils.readUser("appToken", getActivity()));
        OkHttpManager.postAsync(
                Contants.HALL_GOODLIST, parameters,
                this, Contants.HALL_GOODLIST);
    }

    //更多商品列表
    private void initHallMoreGoodListDate(String time) {
        page++;
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("showTime", time);
        parameters.put("ctgId", SharePreferenceUtils.readUser("userId", getActivity()));
        parameters.put("pageIndex", "" + page);
        parameters.put("pageSize", "10");
        parameters.put("appToken", SharePreferenceUtils.readUser("appToken", getActivity()));
        OkHttpManager.postAsync(
                Contants.HALL_GOODLIST, parameters,
                this, Contants.MORE);
    }

    //外卖商品订单
    private void initHallGoodOrder() {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("originalPrice", "" + allPrice);//原价相同
        parameters.put("sumPrice", "" + allPrice);
        parameters.put("uid", SharePreferenceUtils.readUser("userId", getActivity()));
        parameters.put("takeUser", SharePreferenceUtils.readUser("realName", getActivity()));
        parameters.put("takeMobile", SharePreferenceUtils.readUser("mobile", getActivity()));
        parameters.put("takeTime", "2018-7-27");
        parameters.put("subOrders", newList);
        parameters.put("appToken", SharePreferenceUtils.readUser("appToken", getActivity()));
        OkHttpManager.postAsync(
                Contants.ORDER_CREATE, parameters,
                this, Contants.ORDER_CREATE);
    }

    //打包商品订单
    private void initHallPackageOrder(String data) {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("uid", SharePreferenceUtils.readUser("userId", getActivity()));
        parameters.put("takeTime", data);
        parameters.put("subCount", 1);
        parameters.put("appToken", SharePreferenceUtils.readUser("appToken", getActivity()));
        OkHttpManager.postAsync(
                Contants.ORDER_DINNER, parameters,
                this, Contants.ORDER_DINNER);
    }

    @Override
    public void requestFailure(Request request, IOException e, String method) {
        ToastUtil.getShortToastByString(getActivity(),
                getString(R.string.networkRequst_resultFailed));
    }

    @Override
    public void requestSuccess(String result, String method) throws Exception {
        JSONObject object = new JSONObject(result);
        if (object.getInt("code") == 1) {
            String jsonObj1 = object.getString("data");
            switch (method) {
                case Contants.HALL_WEEK:
                    Gson gson = new Gson();
                    ArrayList<HallWeekBean> listDept = gson.fromJson(jsonObj1, new TypeToken<List<HallWeekBean>>() {
                    }.getType());
                    if (listDept.size() <= 0) {
                        listview_1.setVisibility(View.INVISIBLE);
                        listview_2.setVisibility(View.INVISIBLE);
                        listview_3.setVisibility(View.INVISIBLE);
                    } else {
                        listview_1.setVisibility(View.VISIBLE);
                        listview_2.setVisibility(View.VISIBLE);
                        listview_3.setVisibility(View.VISIBLE);
                    }
                    if ("早".equals(listDept.get(0).getTimeType())) {
                        adapterWeek = new Hall_week_dataAdapter(getActivity(), listDept.get(0).getMenus());
                        listview_1.setAdapter(adapterWeek);
                    }
                    if ("中".equals(listDept.get(1).getTimeType())) {
                        adapterWeek = new Hall_week_dataAdapter(getActivity(), listDept.get(1).getMenus());
                        listview_2.setAdapter(adapterWeek);
                    }
                    if ("晚".equals(listDept.get(2).getTimeType())) {
                        adapterWeek = new Hall_week_dataAdapter(getActivity(), listDept.get(2).getMenus());
                        listview_3.setAdapter(adapterWeek);
                    }
                    break;
                case Contants.ORDER_CREATE:
                    getWeChatMsg(jsonObj1);
                    break;
                case Contants.HALL_GOODLIST:
                    Gson gson1 = new Gson();
                    listGood = gson1.fromJson(jsonObj1, new TypeToken<List<HallPackageGoodBean>>() {
                    }.getType());
                    adapter = new THall_Leave_Adapter(getActivity(), listGood, MyHander);
                    listView.setAdapter(adapter);
                    break;
                case Contants.MORE:
                    Gson gson2 = new Gson();
                    ArrayList<HallPackageGoodBean> listGood1 = gson2.fromJson(jsonObj1, new TypeToken<List<HallPackageGoodBean>>() {
                    }.getType());
                    if (listGood1.size() > 0) {
                        for (int i = 0; i < listGood1.size(); i++) {
                            listGood.add(listGood1.get(i));
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        swipeLayout.setOnLoadListener(null);
                    }
                    break;
                case Contants.ORDER_DINNER:
                    setCommitSucess();
                    initSelect();
                    break;

            }
        } else {
            switch (method) {
                case Contants.ORDER_CREATE:
                    ToastUtil.show(getActivity(), "后台获取订单失败");
                    break;
            }
        }
    }

    //处理handler
    private Handler MyHander = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            SubmitHallBean bean = (SubmitHallBean) msg.obj;
            //添加
            if (msg.what == 100) {
                addNewList(bean);
            } else if (msg.what == 101) {
                addNewList(bean);
            }
        }
    };

    private void addNewList(SubmitHallBean bean) {
        for (int i = 0; i < newList.size(); i++) {
            if (newList.get(i).getGid() == bean.getGid()) {
                newList.remove(i);
            }
        }
        if (bean.getSubCount() != 0) {
            newList.add(bean);
        }
    }

    //获取总价
    private void setAllPrice(ArrayList<SubmitHallBean> list) {
        allPrice = 0.0;
        for (int i = 0; i < list.size(); i++) {
            allPrice += Double.valueOf(list.get(i).getSubSumPrice());
        }
    }


    private void setPackageDate() {
        goneView();
        hall_buy.setText("提交");
        hall_buy.setVisibility(View.VISIBLE);
        hall_package.setVisibility(View.VISIBLE);

    }

    private void setCommitSucess() {
        goneView();
        hall_commit.setVisibility(View.VISIBLE);

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (hall_date.getChildCount() > 0) {
            hall_date.removeAllViews();
        }
        switch (i) {
            case R.id.rb_normal:
                setWorkData();
                break;
            case R.id.rb_send:
                setLeaveData();
                break;
            case R.id.rb_package:
                setPackageDate();
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hall_buy:
                if (hall_package.getVisibility() == View.VISIBLE) {
                    selectData();
                    if (TextUtils.isEmpty(newPackageData)) {
                        ToastUtil.getShortToastByString(getActivity(), "请先选择日期！");
                        return;
                    }
                    initHallPackageOrder(newPackageData);
                    return;
                }
                if (newList.size() <= 0) {
                    ToastUtil.getShortToastByString(getActivity(), "请先添加商品");
                    return;
                }
                setAllPrice(newList);
                initHallGoodOrder();
                break;
            case R.id.ll_week_one:
                if (ll_week_one.isSelected()) {
                    ll_week_one.setSelected(false);
                } else {
                    ll_week_one.setSelected(true);
                }
                break;
            case R.id.ll_week_two:
                if (ll_week_two.isSelected()) {
                    ll_week_two.setSelected(false);
                } else {
                    ll_week_two.setSelected(true);
                }
                break;
            case R.id.ll_week_thr:
                if (ll_week_thr.isSelected()) {
                    ll_week_thr.setSelected(false);
                } else {
                    ll_week_thr.setSelected(true);
                }
                break;
            case R.id.ll_week_fou:
                if (ll_week_fou.isSelected()) {
                    ll_week_fou.setSelected(false);
                } else {
                    ll_week_fou.setSelected(true);
                }
                break;
            case R.id.ll_week_fiv:
                if (ll_week_fiv.isSelected()) {
                    ll_week_fiv.setSelected(false);
                } else {
                    ll_week_fiv.setSelected(true);
                }
                break;
            case R.id.ll_week_six:
                if (ll_week_six.isSelected()) {
                    ll_week_six.setSelected(false);
                } else {
                    ll_week_six.setSelected(true);
                }
                break;
            case R.id.ll_week_sev:
                if (ll_week_sev.isSelected()) {
                    ll_week_sev.setSelected(false);
                } else {
                    ll_week_sev.setSelected(true);
                }
                break;
        }
    }

    //判断是否被选中
    private void selectData() {
        LinearLayout[] lls = new LinearLayout[]{ll_week_one, ll_week_two, ll_week_thr, ll_week_fou, ll_week_fiv, ll_week_six, ll_week_sev};
        newPackageData = "";
        for (int i = 0; i < lls.length; i++) {
            if (lls[i].isSelected()) {
                if (i == lls.length - 1) {
                    newPackageData += listTime.get(i);
                } else {
                    newPackageData += listTime.get(i) + ",";
                }

            }
        }
    }

    //设置全为未选中
    private void initSelect() {
        LinearLayout[] lls = new LinearLayout[]{ll_week_one, ll_week_two, ll_week_thr, ll_week_fou, ll_week_fiv, ll_week_six, ll_week_sev};
        for (int i = 0; i < lls.length; i++) {
            lls[i].setSelected(false);
        }
    }

    //set Leave


    /**
     * 设置监听
     */
    private void setListener() {
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setOnLoadListener(null);
    }

    /**
     * 上拉刷新
     */
    @Override
    public void onRefresh() {
        swipeLayout.postDelayed(new Runnable() {

            @Override
            public void run() {
                // 更新数据  更新完后调用该方法结束刷新
                listGood.clear();
                initHallGoodListDate(currentTime);
                swipeLayout.setRefreshing(false);
            }
        }, 2000);

    }

    /**
     * 加载更多
     */
    @Override
    public void onLoad() {
        swipeLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 更新数据  更新完后调用该方法结束刷新
                initHallMoreGoodListDate(currentTime);
                swipeLayout.setLoading(false);
            }
        }, 2000);
    }


    //微信支付

    /**
     * 获取用微信支付的信息
     */
    private void getWeChatMsg(String result) {
        try {
            JSONObject object = new JSONObject(result);
            // 统一下单
            WXPrePost post = new WXPrePost();
            post.appid = Contants.APPID;
            post.mch_id = Contants.MCHID;
            post.nonce_str = genNonceStr();//随机字符串  **1
            post.body = object.getString("title");
            post.detail = object.getString("desc");
            post.out_trade_no = object.getString("orderNum"); //商户订单号 **2
            post.total_fee = (int) (Double.valueOf(object.getString("sumPrice")) * 100);//单位是分
            post.spbill_create_ip = Utils.getLocalIpAddress();//ip地址  **3
            post.notify_url = object.getString("payNotifyUrl");//这里是后台接受支付结果通知的url地址
            post.trade_type = "APP";
            post.sign = genPackageSign(post);//签名  **4

            play(Utils.toXml(getFirstSignParams(post)));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String genNonceStr() {
        Random random = new Random();
        //这里就用到了微信实例代码中的MD5那个类
        return MD5Utils.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    /**
     * 生成签名
     *
     * @param params
     */
    private String genPackageSign(WXPrePost params) {
        //拼接排序list
        List<NameValuePair> packageParams = new LinkedList<>();
        packageParams.add(new BasicNameValuePair("appid", params.appid));
        packageParams.add(new BasicNameValuePair("body", params.body));
        packageParams.add(new BasicNameValuePair("detail", params.detail));
        packageParams.add(new BasicNameValuePair("mch_id", params.mch_id));
        packageParams.add(new BasicNameValuePair("nonce_str", params.nonce_str));
        packageParams.add(new BasicNameValuePair("notify_url", params.notify_url));
        packageParams.add(new BasicNameValuePair("out_trade_no", params.out_trade_no));
        packageParams.add(new BasicNameValuePair("spbill_create_ip", params.spbill_create_ip));
        packageParams.add(new BasicNameValuePair("total_fee", params.total_fee + ""));
        packageParams.add(new BasicNameValuePair("trade_type", params.trade_type));
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < packageParams.size(); i++) {
            sb.append(packageParams.get(i).getName());
            sb.append('=');
            sb.append(packageParams.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Contants.KEY);//key设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
        //这里又用到了从实例代码复制的MD5 可以去上面copy
        String packageSign = MD5Utils.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        return packageSign;
    }

    private List<NameValuePair> getFirstSignParams(WXPrePost params) {
        List<NameValuePair> packageParams = new LinkedList<>();
        packageParams.add(new BasicNameValuePair("appid", params.appid));
        packageParams.add(new BasicNameValuePair("body", params.body));
        packageParams.add(new BasicNameValuePair("detail", params.detail));
        packageParams.add(new BasicNameValuePair("mch_id", params.mch_id));
        packageParams.add(new BasicNameValuePair("nonce_str", params.nonce_str));
        packageParams.add(new BasicNameValuePair("notify_url", params.notify_url));
        packageParams.add(new BasicNameValuePair("out_trade_no", params.out_trade_no));
        packageParams.add(new BasicNameValuePair("spbill_create_ip", params.spbill_create_ip));
        packageParams.add(new BasicNameValuePair("total_fee", params.total_fee + ""));
        packageParams.add(new BasicNameValuePair("trade_type", params.trade_type));
        packageParams.add(new BasicNameValuePair("sign", params.sign));
        return packageParams;
    }

    private void play(String xml) throws IOException {
        DailogShow.showWaitDialog(getActivity());
        RequestBody body = RequestBody.create(MediaType.parse("application/xml"), xml);
        Request request = new Request.Builder()
                .url(Contants.WX_POST)
                .post(body)
                .build();
        OkHttpClient client = new OkHttpClient();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                DailogShow.dismissWaitDialog();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                response.body().close();
                //判断是否成功
                //...
                //再次签名(参与签名的字段有 :Appid partnerId prepayId nonceStr TimeStamp package)
                Map<String, String> stringMap = Utils.decodeXml(result);
                String return_code = "";
                String result_code = "";
                String appId = "";
                String prepayId = "";
                String nonceStr = "";
                for (Map.Entry<String, String> entry : stringMap.entrySet()) {
                    if ("appid".equals(entry.getKey())) {
                        appId = entry.getValue();
                    } else if ("prepay_id".equals(entry.getKey())) {
                        prepayId = entry.getValue();
                    } else if ("nonce_str".equals(entry.getKey())) {
                        nonceStr = entry.getValue();
                    } else if ("return_code".equals(entry.getKey())) {
                        return_code = entry.getValue();
                    } else if ("result_code".equals(entry.getKey())) {
                        result_code = entry.getValue();
                    }
                }
                DailogShow.dismissWaitDialog();

                if ("SUCCESS".equals(return_code) && "SUCCESS".equals(result_code)) {
                    Message message = Message.obtain();
                    message.what = 1;
                    WXPrePost wxPrePost = new WXPrePost();
                    wxPrePost.prepayid = prepayId;
                    wxPrePost.nonce_str = nonceStr;
                    message.obj = wxPrePost;
                    mHandler.sendMessage(message);
                } else {
                    Message message = Message.obtain();
                    message.what = 2;
                    mHandler.sendMessage(message);
                }
            }
        });
    }

    //*************************************上面都是获取订单的逻辑（第一次签名），下面是调支付（第二次签名）****************************************
    //调起支付
    private void getWeChatPlay(String prepayId, String nonceStr) {
        String timeStamp = String.valueOf(Utils.genTimeStamp());
        List<NameValuePair> list = getSecondSignParams(prepayId, nonceStr, timeStamp);
        try {
            //一下所有的参数上面均获取到了
            PayReq req = new PayReq();
            req.appId = Contants.APPID;
            req.partnerId = Contants.MCHID;
            req.prepayId = prepayId;
            req.nonceStr = nonceStr;
            req.timeStamp = timeStamp;
            req.packageValue = "Sign=WXPay";
            req.sign = genSecondPackageSign(list);
            // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
            Contants.wxApi = WXAPIFactory.createWXAPI(getActivity(), null);
            Contants.wxApi.registerApp(Contants.APPID);
            Contants.wxApi.sendReq(req);

        } catch (Exception e) {
            Log.e("PAY_GET", "异常：" + e.getMessage());
        }
    }

    //获取参数列表
    private List<NameValuePair> getSecondSignParams(String prepayId, String nonceStr, String timeStamp) {
        //appId，partnerId，prepayId，nonceStr，timeStamp，package
        List<NameValuePair> packageParams = new LinkedList<>();
        packageParams.add(new BasicNameValuePair("appid", Contants.APPID));
        packageParams.add(new BasicNameValuePair("noncestr", nonceStr));
        packageParams.add(new BasicNameValuePair("package", "Sign=WXPay"));
        packageParams.add(new BasicNameValuePair("partnerid", Contants.MCHID));
        packageParams.add(new BasicNameValuePair("prepayid", prepayId));
        packageParams.add(new BasicNameValuePair("timestamp", timeStamp));
        return packageParams;
    }

    //第二次签名
    private String genSecondPackageSign(List<NameValuePair> params) {
        //拼接排序list
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Contants.KEY);//上面已经获取
        String packageSign = MD5Utils.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        return packageSign;
    }
}