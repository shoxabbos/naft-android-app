package uz.itmaker.naft.Utils;

import uz.itmaker.naft.Model.LoginResponce.RetrofitClientLogin;

import io.realm.Realm;

public class DatabaseUtil {
    private static Realm realm;
    private static DatabaseUtil databaseUtil;
    private DatabaseUtil() {
        realm = Realm.getDefaultInstance();
    }

    public static DatabaseUtil getInstance() {
        if (databaseUtil == null) {
            databaseUtil = new DatabaseUtil();
        }
        return databaseUtil;
    }

    public void storeUser(final RetrofitClientLogin user) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(user);
            }
        });
    }

    public RetrofitClientLogin getUser() {
        return realm.where(RetrofitClientLogin.class).findFirst();
    }
}
