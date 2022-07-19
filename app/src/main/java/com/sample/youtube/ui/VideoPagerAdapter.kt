package com.sample.youtube.ui

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.sample.youtube.R
import com.sample.youtube.databinding.VideoListItemBinding
import com.sample.youtube.model.Items
import com.sample.youtube.utils.getDateTimeDifference


class VideoPagerAdapter: PagingDataAdapter<Items, VideoPagerAdapter.VideosViewHolder>(VideoComparator) {

    override fun onBindViewHolder(holder: VideosViewHolder, position: Int) {
        val video = getItem(position)!!
        val date = getDateTimeDifference(video.snippet?.publishedAt.toString())

        when {
            date.days.toInt()== 0 -> {
                holder.view.timePublished.text = holder.itemView.context.getString(R.string.hours_ago,date.hours)
            }
            date.days.toInt()==1 -> {
                holder.view.timePublished.text = holder.itemView.context.getString(R.string.day_ago)
            }
            date.hours.toInt() == 0 -> {
                holder.view.timePublished.text = holder.itemView.context.getString(R.string.minutes_ago,date.minutes)
            }
            date.hours.toInt()==1 -> {
                holder.view.timePublished.text = holder.itemView.context.getString(R.string.hour_ago)
            }
            date.minutes.toInt() == 0 -> {
                holder.view.timePublished.text = holder.itemView.context.getString(R.string.seconds_ago,date.seconds)
            }
            date.minutes.toInt() == 1 -> {
                holder.view.timePublished.text =  holder.itemView.context.getString(R.string.minute_ago)
            }
            else->{
                holder.view.timePublished.text = holder.itemView.context.getString(R.string.days_ago,date.days)
            }
        }

        holder.view.title.text = video.snippet?.title
        if(video.snippet?.description!!.isNotEmpty()){
            holder.view.description.text = video.snippet?.description
        }
        else{
            holder.view.description.visibility= View.GONE
        }

        val imgUrl = video.snippet?.thumbnails?.high?.url

        imgUrl?.let {
            val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
            holder.view.videoThumbnail.load(imgUri) {
                transformations(RoundedCornersTransformation(25f))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideosViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = VideoListItemBinding.inflate(inflater, parent, false)
        return VideosViewHolder(binding)
    }

   inner class VideosViewHolder(val view: VideoListItemBinding): RecyclerView.ViewHolder(view.root){

        init {
            view.root.apply {
                setOnClickListener {
                    val position = absoluteAdapterPosition
                    val video = getItem(position)
                    if (video != null) {
                        openVideosInYoutube(video.id!!, itemView.context)
                    }
                }
            }
        }
    }

    object VideoComparator: DiffUtil.ItemCallback<Items>() {
        override fun areItemsTheSame(oldItem: Items, newItem: Items): Boolean {
            // Id is unique.
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Items, newItem: Items): Boolean {
            return oldItem == newItem
        }
    }

    private fun openVideosInYoutube(id: String, context: Context) {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$id"))
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=$id"))
        try {
            context.startActivity(appIntent)
        } catch (ex: ActivityNotFoundException) {
            context.startActivity(webIntent)
        }
    }
}