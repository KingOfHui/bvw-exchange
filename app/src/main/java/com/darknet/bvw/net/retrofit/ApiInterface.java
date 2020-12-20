package com.darknet.bvw.net.retrofit;

import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.mall.bean.ShopHomeBean;
import com.darknet.bvw.model.response.NoticeResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("api/v1/announcement/getTop")
    Observable<BaseResponse<NoticeResponse.NoticeData>> getTopic1();

    //商城首页数据
    @GET("api/shop/home/data")
    Observable<BaseResponse<ShopHomeBean>> shopHome();
}
