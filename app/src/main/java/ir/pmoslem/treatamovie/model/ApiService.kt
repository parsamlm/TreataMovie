package ir.pmoslem.treatamovie.model

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("GetContentList")
    suspend fun getContentList(@Body body: JsonObject):Response<ResultObject>

    @POST("GetContent")
    suspend fun getContentDetail(@Body body: JsonObject)


}