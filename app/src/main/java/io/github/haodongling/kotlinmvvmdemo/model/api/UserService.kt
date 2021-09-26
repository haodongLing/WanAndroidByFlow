package io.github.haodongling.kotlinmvvmdemo.model.api

import io.github.haodongling.lib.common.model.bean.User
import io.github.haodongling.lib.common.model.WanResponse
import io.github.haodongling.lib.common.model.bean.UserDetail
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Author: tangyuan
 * Time : 2021/8/16
 * Description:
 */
interface UserService {

    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(@Field("username") userName: String, @Field("password") passWord: String): WanResponse<User>

    @GET("/user/logout/json")
    suspend fun logOut(): WanResponse<Any>

    @FormUrlEncoded
    @POST("/user/register")
    suspend fun register(@Field("username") userName: String, @Field("password") passWord: String, @Field("repassword") rePassWord: String): WanResponse<User>

    @GET("/user/lg/userinfo/json")
    suspend fun getUserDetail():WanResponse<UserDetail>
}