package com.sample.youtube.model

data class Thumbnails (
var default: Default?= Default(),
var medium : Medium? = Medium(),
var high : High? = High(),
var standard : Standard? = Standard(),
var maxres : Maxres? = Maxres()
)