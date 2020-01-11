package com.darknet.bvw.db;

import com.darknet.bvw.MyApp;
import com.darknet.bvw.db.Entity.ZcTotalMoneyModel;
import com.darknet.bvw.db.Entity.ZcTotalMoneyModelDao;

import java.util.List;

public class TotalMoneyUtils {

    public static ZcTotalMoneyModelDao zcMoneyModelDao = MyApp.getsInstance().getDaoSession().getZcTotalMoneyModelDao();

    public static void insertZcModel(ZcTotalMoneyModel totalMoneyModel) {
        zcMoneyModelDao.insert(totalMoneyModel);
    }

    //删除本地缓存数据
    public static void delTotalModel(ZcTotalMoneyModel zcMoneyModel) {
        zcMoneyModelDao.delete(zcMoneyModel);
    }

    //获取所有数据
    public static List<ZcTotalMoneyModel> getAllTotalData() {
        List<ZcTotalMoneyModel> listZcModel = zcMoneyModelDao.loadAll();
        return listZcModel;
    }


}
