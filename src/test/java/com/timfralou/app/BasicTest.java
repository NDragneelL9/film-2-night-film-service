package com.timfralou.app;

import com.timfralou.app.postgresql.PostgreDB;
import com.timfralou.app.postgresql.dbType;

import io.github.cdimascio.dotenv.Dotenv;

public class BasicTest {
    public static final Dotenv localEnv = Dotenv.configure().filename(".env.test").load();
    public static final PostgreDB dbMAIN = new PostgreDB(dbType.MAIN, localEnv);
    public static final PostgreDB dbTEST = new PostgreDB(dbType.TEST, localEnv);

}
