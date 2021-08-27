package com.example.topgoal.db

data class PlayListItems(
    val etag: String,
    val items: List<Item>,
    val kind: String,
    val pageInfo: PageInfo
)

data class Default(
    val height: Int,
    val url: String,
    val width: Int
)

data class High(
    val height: Int,
    val url: String,
    val width: Int
)
data class Item(
    val etag: String,
    val id: String,
    val kind: String,
    val snippet: Snippet,
    val contentDetails: ContentDetails
)

data class ContentDetails(
        val caption: String,
        val contentRating: ContentRating,
        val definition: String,
        val dimension: String,
        val duration: String,
        val licensedContent: Boolean,
        val projection: String
)

class ContentRating(
)

data class Maxres(
    val height: Int,
    val url: String,
    val width: Int
)

data class Medium(
    val height: Int,
    val url: String,
    val width: Int
)

data class PageInfo(
    val resultsPerPage: Int,
    val totalResults: Int
)

data class ResourceId(
    val kind: String,
    val videoId: String
)

data class Snippet(
    val channelId: String,
    val channelTitle: String,
    val description: String,
    val playlistId: String,
    val position: Int,
    val publishedAt: String,
    val resourceId: ResourceId,
    val thumbnails: Thumbnails,
    val title: String,
    val videoOwnerChannelId: String,
    val videoOwnerChannelTitle: String,

    val localized: Localized
)

data class Standard(
    val height: Int,
    val url: String,
    val width: Int
)

data class Thumbnails(
    val default: Default,
    val high: High,
    val maxres: Maxres,
    val medium: Medium,
    val standard: Standard
)