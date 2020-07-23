package com.darknet.bvw.db;

import android.util.Log;

import com.darknet.bvw.MyApp;
import com.darknet.bvw.db.Entity.ZcMoneyModel;
import com.darknet.bvw.db.Entity.ZcMoneyModelDao;

import java.util.List;

public class ZcDaoUtils {

    public static ZcMoneyModelDao zcMoneyModelDao = MyApp.getInstance().getDaoSession().getZcMoneyModelDao();


    //插入数据
    public static void insertZcModel(ZcMoneyModel zcMoneyModel) {

        Log.e("money","insert..data="+zcMoneyModel.toString());
        zcMoneyModelDao.insert(zcMoneyModel);
    }


    //删除本地缓存数据
    public static void delZcModel(ZcMoneyModel zcMoneyModel){

        Log.e("money","delete..id="+zcMoneyModel.getId());
        zcMoneyModelDao.deleteByKey(zcMoneyModel.getId());
    }


    public static List<ZcMoneyModel> getAllZcData(){
        List<ZcMoneyModel> listZcModel = zcMoneyModelDao.loadAll();
        return listZcModel;
    }

}
