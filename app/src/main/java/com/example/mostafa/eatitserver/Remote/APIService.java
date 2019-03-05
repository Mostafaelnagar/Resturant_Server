package com.example.mostafa.eatitserver.Remote;



import com.example.mostafa.eatitserver.Model.MyResponse;
import com.example.mostafa.eatitserver.Model.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by mostafa on 1/30/2018.
 */

public interface APIService {
    @Headers(
            {
                    "Content_Type:application/json",
                    "Authorization:key=AAAAhUwJkq0:APA91bFLK59KDqwilyqcBhF2bfD7zrw0if43typJR-lCnbCNAeEPxQ4SXE2wST1U733A40XgH7BHu47sKPnYSpYrS_qZLjI28NX4KK3ti6O58Vt8DpQ0Uid9uJsKeGRtcnA34q0VqQ35"
            }
    )
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
