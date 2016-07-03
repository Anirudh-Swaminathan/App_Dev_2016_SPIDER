package com.anirudh.anirudhswami.spider_2016_3;

import com.anirudh.anirudhswami.spider_2016_3.model.MovieRow;
import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Anirudh Swami on 03-07-2016.
 */
public class DatabaseConfigUtil extends OrmLiteConfigUtil {

    private static final Class<?>[] classes = new Class[]{MovieRow.class};

    public static void main(String[] args) throws IOException, SQLException {
        writeConfigFile("ormlite_config.txt", classes);
    }
}
