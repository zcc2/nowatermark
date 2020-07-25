package com.pbrx.mylib.net;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.pbrx.mylib.util.LogUtil;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Converter;


/**
 * Author jzy
 * Date 2016/8/29
 */
public class JsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    JsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        JsonReader jsonReader = null;

        String result = value.string();
        LogUtil.e("Result", result);
        //先将返回的json数据解析到Response中，如果code==200，则解析到我们的实体基类中，否则抛异常
        Response httpResult = gson.fromJson(result, Response.class);

        try {
            if (result.contains(",\"data\":\"\"")) {
                result = result.replace(",\"data\":\"\"", "");
            }
            if (result.contains("\"data\":null")) {
                result = result.replace("\"data\":null", "\"data\":{}");
            }
//            BaseModel baseModel = gson.fromJson(result, BaseModel.class);
//            if (baseModel != null && baseModel.getCode() == AppConfig.TOKEN_EXPRIED) {
//                Intent intent = new Intent(MainApplication.getContext(), LoginActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                MainApplication.getContext().startActivity(intent);
//            }
            Reader reader = new StringReader(result);
            jsonReader = gson.newJsonReader(reader);
            jsonReader.setLenient(true);
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }

    }
}
