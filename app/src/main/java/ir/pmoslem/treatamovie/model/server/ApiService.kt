package ir.pmoslem.treatamovie.model.server

import com.google.gson.JsonObject
import ir.pmoslem.treatamovie.model.db.MovieDetailsObject
import ir.pmoslem.treatamovie.model.db.ResultObject
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("GetContentList")
    suspend fun getContentList(@Body body: JsonObject): ResultObject

    @Headers("Content-Type: application/json", "Accept-Language: fa-IR")
    @POST("GetContent")
    suspend fun getContentDetail(@Body body: JsonObject): MovieDetailsObject


}