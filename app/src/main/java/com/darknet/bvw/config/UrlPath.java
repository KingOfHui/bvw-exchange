package com.darknet.bvw.config;

public class UrlPath {

    public static String LOGIN_URL = "api/v1/user/info"; //获取个人信息

    public static String CREATE_TRADE_URL = "api/v1/wallet/createRawTx"; //创建交易

    public static String SEND_TRADE_URL = "api/v1/wallet/sendRawTx"; //发送交易

    public static String TRADE_LIST_URL = "api/v1/wallet/tokenTransferList"; //交易记录

    public static String CHECK_MONEY_URL = "api/v1/wallet/asset"; //查询余额 asset

//    public static String CHECK_MONEY_URL = "api/v1/wallet/balances"; //查询余额 asset

//    public static String MONEY_TYPE_URL = "api/v1/wallet/toke/list"; //支持币种

    public static String UPDATE_URL = "api/v1/getAppOnline"; //检查更新

    public static String ADD_PEOPLE_URL = "api/v1/address/add"; //新增地址

    public static String PEOPLE_LIST_URL = "api/v1/address/list"; //获取地址列表

    public static String PUBLIC_ADDRESS_URL = "api/v1/dictByType"; //获取官方地址
    public static String PUBLIC_DICT_BY_KEY_URL = "api/v1/dictByKey"; //获取官方地址

    public static String FIND_BID_STATE_URL = "api/v1/user/info"; //查询bid状态
    public static String USER_BID_INFO_URL = "api/v1/user/info"; //查询bid状态新街口

    public static String REAL_PAY_BID_PRICE_URL = "api/v1/user/bidPrice"; //查询bid状态


    public static String SUANLI_FIRST_URL = "api/v1/user/powerInfo"; //算力情况接口

    public static String FenLie_FIRST_URL = "api/v1/fl/stageData"; //分裂阶段1.2接口

    public static String FenLie_SECOND_URL = "api/v1/fl/levelList"; //分裂横着的柱子接口

    public static String FenLie_SCROLLVIEW_URL = "api/v1/fl/getResultList"; //分裂滚动文字接口

    public static String FenLie_FIRST_ORDER_URL = "api/v1/fl/orderList"; //分裂订单接口

    public static String ZHENLIE_FIRST_URL = "api/v1/user/tree"; //阵列接口

    public static String QIANDAO_FIRST_URL = "api/v1/user/sign"; //签到接口


    public static String FENLIE_LUCKY_URL = "api/v1/fl/luckN"; //top30幸运

    public static String FENLIE_TOP_URL = "api/v1/fl/topN"; //top30

    public static String EXCHANGE_ACCOUNT_URL = "api/ex/wallet/list"; //交易所资产

    public static String TRADE_MYLIST_URL = "api/v1/exchange/trade/myList"; //我的交易列表

    public static String TRADE_DEPTH_URL = "api/v1/exchange/orderBook/";// 深度
    public static String TRADE_DEPTH_TWO_URL = "api/ex/order/orderBook/";// 深度2
    public static String CREATE_ORDER_URL = "api/ex/order/create";//创建订单
    public static String GET_ACCOUNT_LIST_URL = "api/ex/wallet/list";//资产列表
    public static String HUAZHANG_FROM_SUO_TO_BAO = "api/ex/account/transferToBRC20"; //划账，交易所到钱包
    public static String HANGQING_ZIXUAN_URL = "api/ex/market/favor/list"; //行情，自选列表
    //    public static String HANGQING_ZFB_URL = "api/ex/market/list"; //行情，涨幅榜
    public static String HANGQING_ZFB_URL = "api/ex/market/thumbList"; //行情，涨幅榜
    public static String SIGN_CHECK_URL = "api/v1/user/checkSign"; //验证，是否签到
    public static String WEITUO_URL = "api/ex/order/list"; //当前委托
    public static String HISTORY_WEITUO_URL = "api/ex/order/list/1"; //历史委托
    public static String CANCEL_WEITUO_URL = "api/ex/order/cancel"; //历史委托
    public static String TOKEN_LIST_URL = "api/ex/market/quoteSymbolList"; //获取计价币列表
    public static String COIN_LIST_URL = "api/ex/market/list"; //获取计价币列表.
    public static String ADD_ZIXUAN_URL = "api/ex/market/favor/add/"; //添加自选
    public static String DEL_ZIXUAN_URL = "api/ex/market/favor/delete/"; //删除自选
    public static String DEAL_LIST_URL = "api/ex/trade/list/"; //交易列表
    public static String KLINE_ALL_DATA_URL = "api/ex/market/kline/";// 获取K线历史数据
    public static String SYMBOL_TICKER_URL = "api/v1/exchange/ticker";
    public static String BIW_USDT_URL = "api/ex/market/detail/"; //交易对详情
    /**
     * 我的业绩聚合接口
     */
    public static String BIW_PERFORMANCE_URL = "api/v1/leaderPlan/performance";
    /**
     * 是否是社区领导用户
     */
    public static String BIW_STATE_URL = "api/v1/leaderPlan/state";
    public static String NOTICE_CONTENT_URL = "api/v1/announcement/getTop"; //签到接口
    public static String ADD_IN_YUBI_URL = "api/v1/bvwInvest/apply"; //加入余币宝
    public static String YUBIBAO_LIST_URL = "api/v1/bvwInvest/applyList"; //余币宝申请列表
    public static String MONEY_LEFT_URL = "api/v1/user/dayWithdrawLimit"; //用户每日提币剩余额度
    public static String MONEY_HUAZHANG_LEFT_URL = "api/v1/user/dayHzLimit"; //用户每日划转剩余额度

    public static final String GET_MINERAL_STATUS = "api/v1/miner/status";//获取矿机状态
    public static final String GET_MINERAL_LIST = "api/v1/miner/list";//获取矿机列表
    public static final String GET_MINERAL_BONUS_LIST = "api/v1/miner/bonusList";//获取矿机收益列表
    public static final String GET_REFER_BONUS_LIST = "api/v1/miner/referBonusList";//获取矿机推荐收益列表
    public static final String GET_USER_INVITE_DATA = "api/v1/user/inviteData";//获取我的邀请信息和邀请人列表

    public static final String GET_MINERAL_PAY_ADDRESS = "api/v1/dictByKey/MINER_PAY_BTW";
    public static final String CTEATE_RAW_TX = "api/v1/wallet/createRawTx";//创建交易
    public static final String SEND_RAW_TX = "api/v1/wallet/sendRawTx";//发送交易
    public static final String OPEN_BID = "api/v1/user/openBid";//开通Bid
    public static final String GET_ADDRESS_WITHDRAW = "api/v1/wallet/resetAddress/";//激活钱包

    public static final String  GET_POS_PRODUCT_LIST="/api/v1/pos/productList";//获取区块宝产品列表
    public static final String  GET_POS_BONUS_LIST="/api/v1/pos/bonusList";//获取收益列表
    public static final String  GET_POS_IN_OUT_LIST="/api/v1/pos/inOutList";//获取转出转入记录
    public static final String  GET_POS_WALLET_DATA="/api/v1/pos/walletData";//获取区块宝账户数据统计
    public static final String  GET_POS_WALLET_LIST="/api/v1/pos/walletList";//获取区块宝账户列表
    public static final String  CHECK_CAN_SHOW = "/api/v1/share/checkCanShow";//是否展示分享空投按钮
}
