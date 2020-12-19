package com.darknet.bvw.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.darknet.bvw.db.Entity.DaoMaster;
import com.darknet.bvw.db.Entity.ShippingAddressModel;
import com.darknet.bvw.db.Entity.ShippingAddressModelDao;
import com.github.yuweiguocn.library.greendao.MigrationHelper;

import org.greenrobot.greendao.database.Database;

public class MyDBHelper extends DaoMaster.OpenHelper {
    public static final String DBNAME = "wallet.db";

    public MyDBHelper(Context context, String name) {
        this(context, name, null);
    }

    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    /**
     * 当数据库版本更新的时候回调函数
     *
     * @param db         数据库对象
     * @param oldVersion 数据库旧版本
     * @param newVersion 数据库新版本
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
//        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
//            @Override
//            public void onCreateAllTables(Database db, boolean ifNotExists) {
//                DaoMaster.createAllTables(db, ifNotExists);
//            }
//
//            @Override
//            public void onDropAllTables(Database db, boolean ifExists) {
//                DaoMaster.dropAllTables(db, ifExists);
//            }
//        }, ShippingAddressModelDao.class);
        if (newVersion > oldVersion) {
            if (newVersion == 2) {

                String sqlOne = "ALTER TABLE ZC_MONEY_MODEL ADD CHAIN_DEPOSIT_ADDRESS TEXT";
                db.execSQL(sqlOne);

//            if (newVersion == 2) {
//            String sqlOne = "CREATE TABLE \"MUSIC_ITEM_DOWN\" (\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ,\"MEDIA_ID\" INTEGER NOT NULL ,\"FILE_URL\" TEXT,\"FILE_NAME\" TEXT,\"PIC_URL\" TEXT,\"PLAY_NUM\" TEXT,\"DOWNLOAD_NUM\" TEXT,\"FILE_SIZE\" TEXT,\"IMG_TEXT\" TEXT,\"ALBUM_ID\" INTEGER NOT NULL ,\"SHOW\" TEXT,\"ADD_TIME\" TEXT,\"ALL_TIME\" TEXT,\"COMMENT_NUM\" TEXT,\"LOCAL_PATH\" TEXT,\"LAST_PLAY\" INTEGER NOT NULL ,\"FILE_STATE\" INTEGER NOT NULL ,\"CURRENT_PALY\" INTEGER NOT NULL );\n";
//            String sqlTwo = "CREATE TABLE \"MUSIC\" (\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ,\"ALBUM_ID\" INTEGER NOT NULL ,\"TYPE\" INTEGER NOT NULL ,\"EXPLAIN\" TEXT,\"ALBUM_NAME\" TEXT,\"ALBUM_PIC_URL\" TEXT,\"ALBUM_PIC_URL_BIG\" TEXT,\"ATTENTION_NUM\" INTEGER NOT NULL ,\"PLAY_NUM\" INTEGER NOT NULL ,\"COST\" TEXT,\"TOP\" INTEGER NOT NULL ,\"POPULARITY\" TEXT,\"LABEL\" TEXT,\"IMG_TEXT\" TEXT,\"BANNER\" TEXT,\"COMMENT_NUM\" INTEGER NOT NULL ,\"SHOW\" TEXT,\"ADD_TIME\" TEXT,\"COLUMN_ID\" INTEGER NOT NULL ,\"HOME\" TEXT,\"ATTENTION\" INTEGER NOT NULL ,\"IS_ALL_DOWN\" INTEGER NOT NULL ,\"IS_COMPLETE\" INTEGER NOT NULL ,\"CURRENT_PLAY\" INTEGER NOT NULL );\n";
//            db.execSQL(sqlOne);
//            db.execSQL(sqlTwo);
//            }

//            ZqhLogUtils.e("db", "...update...");
                // 创建临时表
                // 复制旧数据到临时表
                // 数据库（表）删除
                // 临时表重命名
            } else if (newVersion <= 3) {
                db.execSQL("CREATE TABLE SHIPPING_ADDRESS_MODEL ('_id' INTEGER PRIMARY KEY AUTOINCREMENT,'ADDRESS' TEXT,'NAME' TEXT,'MOBILE' TEXT, 'IS_DEFAULT' Integer default 0)");
//                db.execSQL("ALTER TABLE SHIPPING_ADDRESS_MODEL ADD COLUMN 'address' TEXT");
                Log.e("dhdhdh", "onUpgrade: ------------");
            }
        }
    }
}
