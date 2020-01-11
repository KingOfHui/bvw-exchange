package com.darknet.bvw.db.Entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class ETHWalletModel {

    @Id(autoincrement = true)
    private Long id;

    public String address;
    private String name;
    private String password;
    private String keystorePath;
    private String mnemonic;
    private boolean isCurrent;
    private boolean isBackup;
    private String privateKey;
    private String publicKey;
    //0新创建，1助记词导入，2keystore导入，3密钥导入
    private int importType;
    //存储
    private String keyStoreVal;
    //1表示当前选中，0表示未选中
    private int currentSelect;


    @Generated(hash = 2096710061)
    public ETHWalletModel(Long id, String address, String name, String password,
            String keystorePath, String mnemonic, boolean isCurrent,
            boolean isBackup, String privateKey, String publicKey, int importType,
            String keyStoreVal, int currentSelect) {
        this.id = id;
        this.address = address;
        this.name = name;
        this.password = password;
        this.keystorePath = keystorePath;
        this.mnemonic = mnemonic;
        this.isCurrent = isCurrent;
        this.isBackup = isBackup;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.importType = importType;
        this.keyStoreVal = keyStoreVal;
        this.currentSelect = currentSelect;
    }
    @Generated(hash = 1275522749)
    public ETHWalletModel() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getKeystorePath() {
        return this.keystorePath;
    }
    public void setKeystorePath(String keystorePath) {
        this.keystorePath = keystorePath;
    }
    public String getMnemonic() {
        return this.mnemonic;
    }
    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }

    public boolean getIsBackup() {
        return this.isBackup;
    }
    public void setIsBackup(boolean isBackup) {
        this.isBackup = isBackup;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

    public boolean getIsCurrent() {
        return this.isCurrent;
    }

    public void setIsCurrent(boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public boolean isCurrent() {
        return isCurrent;
    }
    public String getPrivateKey() {
        return this.privateKey;
    }
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
    public String getPublicKey() {
        return this.publicKey;
    }
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
    public int getImportType() {
        return this.importType;
    }
    public void setImportType(int importType) {
        this.importType = importType;
    }
    public String getKeyStoreVal() {
        return this.keyStoreVal;
    }
    public void setKeyStoreVal(String keyStoreVal) {
        this.keyStoreVal = keyStoreVal;
    }
    public int getCurrentSelect() {
        return this.currentSelect;
    }
    public void setCurrentSelect(int currentSelect) {
        this.currentSelect = currentSelect;
    }


    @Override
    public String toString() {
        return "ETHWalletModel{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", keystorePath='" + keystorePath + '\'' +
                ", mnemonic='" + mnemonic + '\'' +
                ", isCurrent=" + isCurrent +
                ", isBackup=" + isBackup +
                ", privateKey='" + privateKey + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", importType=" + importType +
                ", keyStoreVal='" + keyStoreVal + '\'' +
                ", currentSelect=" + currentSelect +
                '}';
    }
}
