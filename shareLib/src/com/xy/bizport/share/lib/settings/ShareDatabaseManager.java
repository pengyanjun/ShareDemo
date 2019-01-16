package com.xy.bizport.share.lib.settings;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.xy.bizport.androidcommon.db.DatabaseHelper;
import com.xy.bizport.androidcommon.db.DatabaseManager;
import com.xy.bizport.share.lib.BizportShare;

public class ShareDatabaseManager extends DatabaseManager {
    private static volatile ShareDatabaseManager instance;

    public static ShareDatabaseManager getInstance() {
        if (instance == null) {
            synchronized (ShareDatabaseManager.class) {
                if (instance == null) {
                    instance = new ShareDatabaseManager();
                }
            }
        }
        return instance;
    }

    // for test
    static void nullInstance() {
        instance = null;
    }

    private ShareDatabaseManager() {
    }

    @Override
    protected SQLiteDatabase createDatabase() {
        SQLiteOpenHelper helper = new DatabaseHelper(BizportShare.getApplicationContext(), this);
        return helper.getWritableDatabase();
    }

    @Override
    public String getName() {
        return "bizportshare.db";
    }

    @Override
    public int getVersion() {
        return 1;
    }
}
