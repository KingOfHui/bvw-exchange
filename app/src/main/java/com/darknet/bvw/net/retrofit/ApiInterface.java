package com.darknet.bvw.net.retrofit;

import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.mall.bean.CategoryBean;
import com.darknet.bvw.mall.bean.GoodsDetailBean;
import com.darknet.bvw.mall.bean.ShopHomeBean;
import com.darknet.bvw.model.response.NoticeResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("api/v1/announcement/getTop")
    Observable<BaseResponse<NoticeResponse.NoticeData>> getTopic1();

    //商城首页数据
    @GET("api/shop/home/data")
    Observable<BaseResponse<ShopHomeBean>> shopHome();

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
