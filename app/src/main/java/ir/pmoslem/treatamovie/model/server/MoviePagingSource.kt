package ir.pmoslem.treatamovie.model.server

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import ir.pmoslem.treatamovie.model.db.Movie
import ir.pmoslem.treatamovie.model.db.MovieDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

private const val MOVIE_STARTING_PAGE_INDEX = 1

class MoviePagingSource constructor(private val api: ApiService, private val movieDao: MovieDao): PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: MOVIE_STARTING_PAGE_INDEX
        val innerJson = JsonObject()
        innerJson.addProperty("RequestType", 2)
        innerJson.add("RequestId", JsonNull.INSTANCE)
        innerJson.addProperty("PageSize", params.loadSize)
        innerJson.addProperty("PageIndex", position)
        innerJson.addProperty("OrderBy", "createdate")
        innerJson.addProperty("Order", "desc")
        innerJson.get("request")

        val requestBody = JsonObject()

        requestBody.add("request", innerJson)

        return try {
            val response = api.getContentList(requestBody)
            val movieObject = response.moviesList
            val movieList = movieObject.moviesList

            LoadResult.Page(
                data = movieList,
                prevKey = if(position == MOVIE_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if(movieList.isEmpty()) null else position + 1
            ).also { GlobalScope.launch(Dispatchers.IO) {
                movieDao.insertMoviesData(movieList)
                }
            }
        }catch (e: IOException){
            LoadResult.Error(e)
        }catch (e: HttpException){
            LoadResult.Error(e)
        }
    }
}