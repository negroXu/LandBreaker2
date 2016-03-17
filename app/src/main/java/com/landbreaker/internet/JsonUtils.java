package com.landbreaker.internet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Administrator on 2015/12/21.
 */
public class JsonUtils {

    public static <T> T getData(String data, final Class<T> cls) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(data,cls);
        }
        catch(Exception e)
        {
        }
        return t;
    }

}
