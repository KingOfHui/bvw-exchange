package com.darknet.bvw.net.retrofit;

import com.darknet.bvw.base.BaseListBean;
import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.mall.bean.CategoryBean;
import com.darknet.bvw.mall.bean.GoodsDetailBean;
import com.darknet.bvw.mall.bean.ShopHomeBean;
import com.darknet.bvw.model.DictByKeyResponse;
import com.darknet.bvw.model.response.CreateTradeResponse.SendTx;
import com.darknet.bvw.model.response.NoticeResponse;
import com.darknet.bvw.model.response.SendTradeResponse;
import com.darknet.bvw.order.bean.CartData;
import com.darknet.bvw.order.bean.CouponBean;
import com.darknet.bvw.order.bean.MyCouponBean;
import com.darknet.bvw.order.bean.ShippingAddress;
import com.darknet.bvw.order.bean.OrderResp;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("api/v1/announcement/getTop")
    Observable<BaseResponse<NoticeResponse.NoticeData>> getTopic1();

    /***********              商城-首页            **********/
    //商城首页数据
    @GET("api/shop/home/data")
    Observable<BaseResponse<ShopHomeBean>> shopHome();

    /***********              商城-产品            **********/
    //获取产品详情
    @GET("api/shop/product/detail/{id}")
    Observable<BaseResponse<GoodsDetailBean>> getProductDetail(@Path("id") int id);

    /***********              商城-购物车            **********/
    //添加商品到购物车
    @POST("api/shop/cart/add")
    Observable<BaseResponse<Object>> addToCart(@Body RequestBody body);

    //减少商品到购物车
    @POST("api/shop/cart/subtract")
    Observable<BaseResponse<Object>> subCartGoods(@Body RequestBody body);

    //获取购物车中的数据
    @GET("api/shop/cart/list")
    Observable<BaseResponse<CartData>> getCartList();

    //选中商品按产品,入参为商品ID,多个以逗号隔开
//    {
//        "check": 0,
//            "ids": ""
//    }
    @POST("api/shop/cart/checkedByProduct")
    Observable<BaseResponse<Object>> checkCartByProduct(@Body RequestBody body);

    @POST("api/shop/cart/checkedBySku")
    Observable<BaseResponse<Object>> checkCartBySku(@Body RequestBody body);

    @POST("api/shop/cart/deleteBySku")
    Observable<BaseResponse<Object>> deleteBySku(@Body RequestBody body);

    /***********              商城-优惠券            **********/
    @GET("api/shop/couponTemplate/list")
    Observable<BaseResponse<BaseListBean<CouponBean>>> getCouponList();

    @GET("api/shop/coupon/list")
    Observable<BaseResponse<BaseListBean<MyCouponBean>>> getMyCouponList();

    /***********              商城-订单            **********/
    //订单列表
    @GET("api/shop/order/list")
    Observable<BaseResponse<BaseListBean<OrderResp>>> getOrderList(@Query("trade_state") int trade_state, @Query("limit") int limit, @Query("page") int pageNum);
    //订单列表
    @GET("api/shop/order/stateList")
    Observable<BaseResponse<Map<String,String>>> getOrderStateList();

    //订单详情
    @GET("api/shop/order/detail/{id}")
    Observable<BaseResponse<OrderResp>> getOrderDetail(@Path("id") int id);

    //取消订单
    @POST("api/shop/order/cancel")
    Observable<BaseResponse<Object>> cancelOrder(@Body RequestBody body);

    //确认收货
    @POST("api/shop/order/confirmReceive")
    Observable<BaseResponse<Object>> confirmReceive(@Body RequestBody body);

    //提醒发货
    @POST("api/shop/order/remind")
    Observable<BaseResponse<Object>> tipDelivery(@Body RequestBody body);

    //提交订单
    @POST("api/shop/order/submit")
    Observable<BaseResponse<List<OrderResp>>> submitOrder(@Body RequestBody body);


    //提交购物车订单
    @POST("api/shop/order/submitCart")
    Observable<BaseResponse<List<OrderResp>>> submitCart(@Body RequestBody body);

    //修改订单地址
    @POST("api/shop/order/updateAddress")
    Observable<BaseResponse<Object>> updateOrderAddress(@Body RequestBody body);

    /***********              商城-收货地址            **********/
    //新增地址
    @POST("api/shop/address/add")
    Observable<BaseResponse<Object>> addAddress(@Body RequestBody body);

    //删除地址
    @POST("api/shop/address/delete/{id}")
    Observable<BaseResponse<Object>> deleteAddress(@Path("id") int id);

    //我的地址列表
    @GET("api/shop/address/list")
    Observable<BaseResponse<List<ShippingAddress>>> getAddressList();

    //设置默认地址
    @POST("api/shop/address/setDefault/{id}")
    Observable<BaseResponse<Object>> setDefault(@Path("id") String id);

    //修改地址
    @POST("api/shop/address/update")
    Observable<BaseResponse<Object>> updateAddress(@Body RequestBody body);


    //一级分类
    @GET("api/shop/catalog/list")
    Observable<BaseResponse<List<CategoryBean>>> category();

    //二级分类
    @GET("api/shop/catalog/list2/{parent_id}")
    Observable<BaseResponse<List<CategoryBean>>> categorySecond(@Path("parent_id") int parent_id);

    //关键字搜索
    @GET("api/shop/product/keyword")
    Observable<BaseResponse<BaseListBean<GoodsDetailBean>>> search(
            @Query("keyword") String keyword
            , @Query("limit") Integer limit
            , @Query("page") Integer page
    );

    //热搜关键词
    @GET("api/shop/home/hotkeys")
    Observable<BaseResponse<String>> hotKeyword();

    //商品列表(根据分类)
    @GET("api/shop/product/category")
    Observable<BaseResponse<BaseListBean<GoodsDetailBean>>> shopListByCategory(
            @Query("root_category_id") int root_category_id // 一级分类
            , @Query("category_id") Integer category_id // 二级分类 不传返回全部
            , @Query("limit") Integer limit
            , @Query("order_field") String order_field //排序字段,可用值:sale,price,create_time
            , @Query("order_side") String order_side //升序降序,可用值:desc,asc
            , @Query("page") Integer page
    );

    //SHOP_PAY_COUPON：购买优惠券
    //获取字典信息
    @GET("api/v1/dictByKey/{key}")
    Observable<BaseResponse<DictByKeyResponse>> dictByKey(@Path("key") String key);

    //创建交易
    @POST("api/v1/wallet/createRawTx")
    Observable<BaseResponse<SendTx>> createRawTx(@Body RequestBody body);

    //发送交易
    @POST("api/v1/wallet/sendRawTx")
    Observable<BaseResponse<Object>> sendRawTx(@Body RequestBody body);
}
