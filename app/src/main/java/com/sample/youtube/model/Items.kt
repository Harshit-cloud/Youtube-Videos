package com.sample.youtube.model

import com.sample.youtube.model.Snippet


data class Items (

 var kind    : String?  = null,
  var etag    : String?  = null,
 var id      : String?  = null,
 var snippet : Snippet? = Snippet()

)