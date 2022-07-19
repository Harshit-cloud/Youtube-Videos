package com.sample.youtube.model


data class CommonResponse (

 var kind : String?= null,
 var etag : String?= null,
 var items: List<Items>?=null,
 var nextPageToken : String? = null,
 var pageInfo: PageInfo? = PageInfo()
)