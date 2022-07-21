package edu.neu.firebasechatapp.Fragments;

import edu.neu.firebasechatapp.Notifications.Sender;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class APIService {
    @Headers (
        {
            "Content-Type:application/json",
                    "Authorization:key=AAAAF9BlnRQ:APA91bFVkZnqwpWSE8ffTP1rxkW3DyV81aftF_SMZCSLSeS_yS5Y2f3lZfYaY6D43zs9mGlo6dkC2FBM8ZoyyaNpzqGnw-Gqjq9jZusPmM5FmIF0Q2QUtr5zHhjsZlfS_-CnfQmgx4es"
        }
    )

    @POST ("fcm/send")
    Call<Response> sendNotification(@Body Sender body);
}
