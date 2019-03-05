package com.example.mostafa.eatitserver.Common;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.mostafa.eatitserver.Model.Request;
import com.example.mostafa.eatitserver.Model.User;
import com.example.mostafa.eatitserver.Remote.APIService;
import com.example.mostafa.eatitserver.Remote.RetrofitClient;

import retrofit2.Retrofit;

/**
 * Created by mostafa on 9/3/2017.
 */

public class Common {
    public static User currentUser;
    public static final String Update="Update";
    public static final String Delete="Delete";
    public static int PICK_IMAGE_REQUEST = 71;
    public static Request currentRequset ;
    public static final String BASE_URL = "https://fcm.googleapis.com/";

    public static APIService getFCMClient() {

         return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
    public static String convertCodeToStatus(String status) {
        if (status.equals("0"))
            return "Placed";
        else if (status.equals("1"))
            return "On my way";
        else
            return "Shipped";
}
    public static boolean isConnected(Context context){
        ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager !=null){
            NetworkInfo[] info =connectivityManager.getAllNetworkInfo();
            for (int i = 0; i<info.length ; i++)
            {
                if (info[i].getState()==NetworkInfo.State.CONNECTED)
                    return  true;
            }
        }
        return false;
    }
}
