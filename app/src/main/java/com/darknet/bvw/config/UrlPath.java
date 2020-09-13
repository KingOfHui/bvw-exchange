package com.darknet.bvw.config;

public class UrlPath {

    public static String LOGIN_URL = "xchain-api/user/info"; //获取个人信息

    public static String CREATE_TRADE_URL = "xchain-api/wallet/createRawTx"; //创建交易

    public static String SEND_TRADE_URL = "xchain-api/wallet/sendRawTx"; //发送交易

    public static String TRADE_LIST_URL = "xchain-api/wallet/tokenTransferList"; //交易记录

    public static String CHECK_MONEY_URL = "xchain-api/wallet/asset"; //查询余额 asset

//    public static String CHECK_MONEY_URL = "xchain-api/wallet/balances"; //查询余额 asset

//    public static String MONEY_TYPE_URL = "xchain-api/wallet/toke/list"; //支持币种

    public static String UPDATE_URL = "xchain-api/appCheckVersion"; //检查更新

    public static String ADD_PEOPLE_URL = "xchain-api/address/add"; //新增地址

    public static String PEOPLE_LIST_URL = "xchain-api/address/list"; //获取地址列表

    public static String PUBLIC_ADDRESS_URL = "xchain-api/dictByType"; //获取官方地址

    public static String FIND_BID_STATE_URL = "xchain-api/user/bidInfo"; //查询bid状态

    public static String REAL_PAY_BID_PRICE_URL = "xchain-api/user/bidPrice"; //查询bid状态


    public static String SUANLI_FIRST_URL = "xchain-api/user/powerInfo"; //算力情况接口

    public static String FenLie_FIRST_URL = "xchain-api/fl/stageData"; //分裂阶段1.2接口

    public static String FenLie_SECOND_URL = "xchain-api/fl/levelList"; //分裂横着的柱子接口

    public static String FenLie_SCROLLVIEW_URL = "xchain-api/fl/getResultList"; //分裂滚动文字接口

    public static String FenLie_FIRST_ORDER_URL = "xchain-api/fl/orderList"; //分裂订单接口

    public static String ZHENLIE_FIRST_URL = "xchain-api/user/tree"; //阵列接口

    public static String QIANDAO_FIRST_URL = "xchain-api/user/sign"; //签到接口


    public static String FENLIE_LUCKY_URL = "xchain-api/fl/luckN"; //top30幸运

    public static String FENLIE_TOP_URL = "xchain-api/fl/topN"; //top30

    public static String EXCHANGE_ACCOUNT_URL = "xchain-api/exchange/account/list"; //交易所资产

    public static String TRADE_MYLIST_URL = "xchain-api/exchange/trade/myList"; //我的交易列表

    public static String TRADE_DEPTH_URL = "xchain-api/exchange/orderBook/";// 深度

    public static String TRADE_DEPTH_TWO_URL = "xchain-api/exchange/orderBook2/";// 深度2

    public static String CREATE_ORDER_URL = "xchain-api/exchange/order/create";//创建订单

    public static String GET_ACCOUNT_LIST_URL = "xchain-api/exchange/account/list";//资产列表


    public static String HUAZHANG_FROM_SUO_TO_BAO = "xchain-api/exchange/account/transferToBRC20"; //划账，交易所到钱包


    public static String HANGQING_ZIXUAN_URL = "xchain-api/exchange/favor/list"; //行情，自选列表

    public static String HANGQING_ZFB_URL = "xchain-api/exchange/ticker"; //行情，涨幅榜

    public static String SIGN_CHECK_URL = "xchain-api/user/checkSign"; //验证，是否签到

    public static String WEITUO_URL = "xchain-api/exchange/order/myList"; //当前委托

    public static String HISTORY_WEITUO_URL = "xchain-api/exchange/order/historyEntrustOrderList"; //历史委托

    public static String CANCEL_WEITUO_URL = "xchain-api/exchange/order/cancel"; //历史委托

    public static String TOKEN_LIST_URL = "xchain-api/exchange/market/quoteTokenList"; //获取计价币列表

    public static String COIN_LIST_URL = "xchain-api/exchange/market/list/v2"; //获取计价币列表

    public static String ADD_ZIXUAN_URL = "xchain-api/exchange/favor/add/"; //添加自选
    public static String DEL_ZIXUAN_URL = "xchain-api/exchange/favor/delete/"; //删除自选

    public static String DEAL_LIST_URL = "xchain-api/exchange/trade/list"; //交易列表

    public static String KLINE_ALL_DATA_URL = "xchain-api/exchange/kline/";// 获取K线历史数据

    public static String SYMBOL_TICKER_URL = "xchain-api/exchange/ticker";

    public static String BVW_USDT_URL = "xchain-api/exchange/market/detail/";
    /**
     * 我的业绩聚合接口
     */
    public static String BVW_PERFORMANCE_URL = "xchain-api/leaderPlan/performance";
    /**
     * 是否是社区领导用户
     */
    public static String BVW_STATE_URL = "xchain-api/leaderPlan/state";


    public static String NOTICE_CONTENT_URL = "xchain-api/announcement/getTop"; //签到接口

    public static String ADD_IN_YUBI_URL = "xchain-api/bvwInvest/apply"; //加入余币宝

    public static String YUBIBAO_LIST_URL = "xchain-api/bvwInvest/applyList"; //余币宝申请列表


    public static String MONEY_LEFT_URL = "xchain-api/user/dayWithdrawLimit"; //用户每日提币剩余额度

    public static String MONEY_HUAZHANG_LEFT_URL = "xchain-api/user/dayHzLimit"; //用户每日划转剩余额度

    public static final String GET_MINERAL_STATUS = "api/v1/miner/status";//获取矿机状态
    public static final String GET_MINERAL_LIST = "api/v1/miner/list";//获取矿机列表
    public static final String GET_MINERAL_BONUS_LIST = "api/v1/miner/bonusList";//获取矿机收益列表

}
