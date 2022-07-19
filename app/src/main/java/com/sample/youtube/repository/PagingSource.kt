package com.sample.youtube.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sample.youtube.api.YoutubeApiInterface
import com.sample.youtube.model.Items
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

const val STARTING_PAGE_INDEX = 1
const val NETWORK_PAGE_SIZE = 5

class PagingSource @Inject constructor(
    private val apiService: YoutubeApiInterface,
    private val part: String?,
    private val chart: String?,
    private val key: String?
) :
    PagingSource<Int, Items>() {
    override fun getRefreshKey(state: PagingState<Int, Items>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Items> {
        val position = params.key ?: STARTING_PAGE_INDEX
        try{
            val response =
                apiService.getVideos(
                    part!!,
                    chart,
                    key!!,
                    YoutubeApiInterface.token
                )
            YoutubeApiInterface.token = response.nextPageToken!!
            val repos = response.items
            val nextKey = position + 1
            return LoadResult.Page(
                data = repos!!,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        }
        catch(e:IOException){
            return LoadResult.Error(e)
        }
        catch(e:HttpException){
            return LoadResult.Error(e)
        }
        catch(e:Exception){
            return LoadResult.Error(e)
        }
    }

}