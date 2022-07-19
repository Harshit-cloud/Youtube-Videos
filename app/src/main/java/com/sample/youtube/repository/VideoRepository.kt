package com.sample.youtube.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.sample.youtube.api.YoutubeApiInterface
import com.sample.youtube.model.Items
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideoRepository @Inject constructor(
    private val service: YoutubeApiInterface
) {

    fun getVideos(
        part: String?,
        chart: String?,
        key: String?
    ): LiveData<PagingData<Items>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PagingSource(
                    service,
                    part,
                    chart,
                    key
                )
            }
        ).liveData

    }

}