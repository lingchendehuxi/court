package com.court.oa.project.bean;

/**
 * Created by liuhong on 2018/8/20.
 */

public class WXPrePost {
    //必须带的参数
    public String appid;//微信开放平台审核通过的应用APPID
    public String mch_id;//微信支付分配的商户号
    public String nonce_str;//随机字符串，不长于32位。推荐随机数生成算法
    public String sign;//签名，详见签名生成算法
    public String body;//商品描述交易字段格式根据不同的应用场景按照以下格式：APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。
    public String out_trade_no;//商户系统内部的订单号,32个字符内、可包含字母, 其他说明见商户订单号
    public int total_fee;//订单总金额，单位为分，详见支付金额
    public String spbill_create_ip;//用户端实际ip
    public String notify_url;//接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
    public String trade_type;//支付类型

    //非必须携带的参数
    public String device_info;//终端设备号(门店号或收银设备ID)，默认请传"WEB"
    public String detail;//商品名称明细列表
    public String attach;//附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
    public String fee_type;//符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
    public String time_start;//订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
    public String time_expire;//订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则 注意：最短失效时间间隔必须大于5分钟
    public String goods_tag;//商品标记，代金券或立减优惠功能的参数，说明详见代金券或立减优惠
    public String limit_pay;//no_credit--指定不能使用信用卡支付

    public String prepayid;//预支付交易会话ID
}
