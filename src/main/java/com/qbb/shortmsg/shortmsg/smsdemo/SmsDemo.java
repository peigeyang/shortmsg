package com.qbb.shortmsg.shortmsg.smsdemo;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.qbb.shortmsg.shortmsg.util.RedisUtil;
import com.qbb.shortmsg.shortmsg.util.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class SmsDemo {

    @Autowired
    private RedisUtil redisUtil;

    private static final String ACCESSKEYID = "LTAIAGS7NvDuFlTc";
    private static final String ACCESSKEYSECRET = "exlFtkyaDdwSFPrpaGh2kd6rPhG8uD";

    @SuppressWarnings("all")
    public ReturnObject sendMsg(String phone, String status){
        String rdCode = rdCode();

        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESSKEYID, ACCESSKEYSECRET);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers",phone);
        request.putQueryParameter("SignName", "亲宝宝小儿推拿");
        request.putQueryParameter("TemplateCode", "SMS_168341278");
        request.putQueryParameter("TemplateParam", "{code:"+rdCode+"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            JsonParser jp = new JsonParser();
            //将json字符串转化成json对象
            JsonObject jo = jp.parse(response.getData()).getAsJsonObject();
            if("OK".equals(jo.get("Message").getAsString())){
                if("1".equals(status)){
                    redisUtil.set(RedisUtil.SHORTRE_REGISTER_MSG+phone,rdCode,120);
                }else if("2".equals(status)) {
                    redisUtil.set(RedisUtil.SHORTRE_LOGIN_MSG + phone, rdCode, 120);
                }
                System.out.println(response.getData());
                return new ReturnObject().returnObject(null);
            }
        //获取message对应的值
            // String message = jo.get("message").getAsString();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return new ReturnObject(ReturnObject.ERROR,ReturnObject.ERROR_MSG,"");
    }

    @SuppressWarnings("all")
    public static String rdCode() {
        StringBuffer code = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int r = random.nextInt(10); //每次随机出一个数字（0-9）
            code.append(r);  //把每次随机出的数字拼在一起
        }
        return code.toString();
    }
}
