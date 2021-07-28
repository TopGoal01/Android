package com.example.topgoal.db

data class PlayList(
    val etag: String,
    val items: List<Item>,
    val kind: String,
    val pageInfo: PageInfo
)

data class Localized(
    val description: String,
    val title: String
)

