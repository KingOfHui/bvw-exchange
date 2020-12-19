package com.darknet.bvw.db;

import com.darknet.bvw.MyApp;
import com.darknet.bvw.db.Entity.ShippingAddressModel;
import com.darknet.bvw.db.Entity.ShippingAddressModelDao;

import java.util.List;

public class AddressDaoUtils {
    public static final ShippingAddressModelDao addressDao = MyApp.getInstance().getDaoSession().getShippingAddressModelDao();

    /**
     * 查询所有地址
     */
    public static List<ShippingAddressModel> queryAll() {
        return addressDao.loadAll();
    }
    /**
     * 插入地址
     */
    public static void insertNewAddress(ShippingAddressModel addressModel) {
        addressDao.insert(addressModel);
    }

    /**
     * 更新地址
     */
    public static void updateAddress(ShippingAddressModel addressModel) {
        addressDao.update(addressModel);
    }

    /**
     * 删除地址
     */
    public static void deleteAddress(ShippingAddressModel addressModel) {
        addressDao.delete(addressModel);
    }
    /**
     * 设置为默认地址
     */
    public static void setDefaultAddress(ShippingAddressModel addressModel) {
        List<ShippingAddressModel> list = queryAll();
        for (int i = 0; i < list.size(); i++) {
            ShippingAddressModel model = list.get(i);
            model.setIsDefault(0);
            updateAddress(model);
        }
        addressModel.setIsDefault(1);
        updateAddress(addressModel);
    }

    /**
     * 获取默认地址
     */
    public static ShippingAddressModel getDefaultAddress() {
        List<ShippingAddressModel> list = queryAll();
        for (ShippingAddressModel model : list) {
            if (model.getIsDefault() == 1) {
                return model;
            }
        }
        return null;
    }
}
