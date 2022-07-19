package com.sample.youtube.model


data class Snippet (

  var publishedAt : String?= null,
  var channelId   : String?= null,
  var title : String?= null,
  var description : String?= null,
  var thumbnails  : Thumbnails? = Thumbnails(),
  var channelTitle: String?= null,
  var categoryId  : String?= null,
  var liveBroadcastContent : String?= null,
  var localized   : Localized?  = Localized()

)