package com.darknet.bvw.net.retrofit;

import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.mall.bean.CategoryBean;
import com.darknet.bvw.mall.bean.GoodsDetailBean;
import com.darknet.bvw.mall.bean.ProductDetailResp;
import com.darknet.bvw.mall.bean.ShopHomeBean;
import com.darknet.bvw.model.response.NoticeResponse;
import com.darknet.bvw.order.bean.OrderDetailResp;

import java.util.List;

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
    Observable<BaseResponse<ProductDetailResp>> getProductDetail(@Path("id")String id);

    /***********              商城-购物车            **********/
    //添加商品到购物车
    @POST("api/shop/cart/add")
    Observable<BaseResponse<Object>> addToCart(@Body RequestBody body);

    /***********              商城-订单            **********/
    //订单列表
    @GET("api/shop/order/list")
    Observable<BaseResponse<Object>> getOrderList(@Query("trade_state") int trade_state, @Query("limit") int limit, @Query("page") int pageNum);

    //订单详情
    @GET("api/order/detail/{id}")
    Observable<BaseResponse<OrderDetailResp>> getOrderDetail(@Path("id") int id);

    //取消订单
    @POST("api/shop/order/cancel")
    Observable<BaseResponse<Object>> cancelOrder(@Body RequestBody body);

    //确认收货
    @POST("api/shop/order/confirmReceive")
    Observable<BaseResponse<Object>> confirmReceive(@Body RequestBody body);

    //提交订单
    @POST("api/shop/order/submit")
    Observable<BaseResponse<Object>> submitOrder(@Body RequestBody body);


    //提交购物车订单
    @POST("api/shop/order/submitCart")
    Observable<BaseResponse<Object>> submitCart(@Body RequestBody body);

    //修改订单地址
    @POST("api/shop/order/updateAddress")
    Observable<BaseResponse<Object>> updateOrderAddress(@Body RequestBody body);

    /***********              商城-收货地址            **********/
    //新增地址
    @POST("api/shop/address/add")
    Observable<BaseResponse<Object>> addAddress(@Body RequestBody body);

    //删除地址
    @POST("api/shop/address/delete/{id}")
    Observable<BaseResponse<Object>> submitCart(@Path("id") String id);

    //我的地址列表
    @GET("api/shop/address/list")
    Observable<BaseResponse<Object>> getAddressList();

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

    //商品列表(根据分类)
    @GET("api/shop/product/category")
    Observable<BaseResponse<List<GoodsDetailBean>>> shopListByCategory(
            @Query("root_category_id") int root_category_id // 一级分类
            , @Query("category_id") Integer category_id // 二级分类 不传返回全部
            , @Query("limit") Integer limit
            , @Query("order_field") String order_field //排序字段,可用值:sale,price,create_time
            , @Query("order_side") String order_side //升序降序,可用值:desc,asc
            , @Query("page") Integer page
    );
}
