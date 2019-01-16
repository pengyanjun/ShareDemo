package com.xy.bizport.share.lib.settings;

import com.xy.bizport.androidcommon.db.BaseDao;
import com.xy.bizport.androidcommon.db.DatabaseManager;
import com.xy.bizport.androidcommon.db.generator.TextColumn;

public class ShareSettingsDao extends BaseDao {
    public static final String KEY = "key";
    public static final String VALUE = "value";

    public ShareSettingsDao() {
        super(ShareDatabaseManager.getInstance(), "tb_share_settings");
    }

    @Override
    protected int getTableVersion() {
        return 1;
    }

    @Override
    protected void createTable(DatabaseManager db, int oldVersion) throws Exception {
        table.addColumn(new TextColumn("key").primaryKey().notNull());
        table.addColumn(new TextColumn(VALUE).notNull());

        if (oldVersion == 0) {
            db.execSQL(table.buildTableSQL());
        }
    }
}
