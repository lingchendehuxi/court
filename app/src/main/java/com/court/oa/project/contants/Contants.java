package com.court.oa.project.contants;

import com.tencent.mm.opensdk.openapi.IWXAPI;

/**
 * Created by MateBook D on 2018/6/10.
 */

public class Contants {
    public static final String SERVER_ADDRESS = "http://114.55.248.239:8089/api/";// 接口地址
    public static final String MORE = "more";//更多
    public static final String REGIST_FOR_USER = "regist";// 用户注册
    public static final String WAGE_LIST = "wages/list";// 工资条列表
    public static final String WAGE_DETAIL = "wages/detail";// 工资条详情

    public static final String LOGIN_FOR_PWD = "login";// 密码登录
    public static final String ARTICLE_LIST = "article/list";// 信息列表
    public static final String ARTICLE_DETAIL = "article/detail";// 信息详情
    public static final String MEETING_LIST = "meeting/list";//会议列表
    public static final String MEETING_DETAIL = "meeting/detail";//会议详情
    public static final String MEETING_SIGNUP = "meeting/signup";//参加会议
    public static final String LEAVE_LIST = "vacation/list";//请假记录
    public static final String LEAVE_EDIT = "vacation/edit";//请假编辑
    public static final String LEAVE_USERLIST = "vacation/userlist";//审批人员
    public static final String HALL_WEEK = "weekmenu/detail";//食堂周菜单
    public static final String HALL_GOODLIST = "goods/list";//食堂商品列表
    public static final String ORDER_GOODLIST = "order/list";//我的食堂订单列表
    public static final String ORDER_CREATE = "order/create";//食堂创建订单
    public static final String ORDER_DINNER = "order/dinner";//食堂打包创建订单
    public static final String ORDER_DETAIL = "order/detail";//食堂打包创建订单

    public static final String LEAVE_APPLYER = "vacation/audit";//请假审核
    public static final String LEAVE_DETAIL = "vacation/detail";//请假详情

    public static final String MEETING_SINGIN = "meeting/signin";//扫描二维码签到
    public static final String MESSAGE_LIST = "message/list";//通知列表
    public static final String EXAM_DETAIL = "exam/detail";//问卷详情exam/answer/create
    public static final String EXAM_CREATE = "exam/answer/create";//提交问卷
    public static final String CARD_LIST = "attendance/list";//考勤

    //传参
    public static final String QUESTION_ID = "question";
    public static final String QUESTION_GOBACK = "goback";

    //微信支付
    // 微信appId
    public static final String APPID = "wxcec03c638755a612";
    public static final String MCHID = "1511930091";
    public static final String KEY = "wujiangfayuan20180819fayuan12345";
    public static IWXAPI wxApi;
    public static final String WX_POST = "https://api.mch.weixin.qq.com/pay/unifiedorder";
}
