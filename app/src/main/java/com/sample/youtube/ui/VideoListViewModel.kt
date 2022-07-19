package com.sample.youtube.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sample.youtube.BuildConfig
import com.sample.youtube.model.Items
import com.sample.youtube.repository.VideoRepository
import com.sample.youtube.utils.AppConstants.CHART
import com.sample.youtube.utils.AppConstants.PART
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VideoListViewModel @Inject constructor(
    private val repository: VideoRepository
):ViewModel() {
    fun getVideoList(): LiveData<PagingData<Items>> {
        return repository.getVideos(PART, CHART, BuildConfig.API_KEY).cachedIn(viewModelScope)
    }

}