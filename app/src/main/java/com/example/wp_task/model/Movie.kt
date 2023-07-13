package com.example.wp_task.model

import androidx.room.Entity
import androidx.room.PrimaryKey


data class Data(
    val entries: Int,
    val results: List<Movie>
)

/**
MovieData class passed to room database, only necessary variables!
 */
@Entity(tableName = "movie")
data class MovieData(
    @PrimaryKey val _id: String,
    val titleText: String
)


data class Movie(
    val _id: String,
    val id: String,
    val originalTitleText: OriginalTitleText,
    val position: Int,
    val primaryImage: PrimaryImage,
    val releaseDate: ReleaseDate,
    val releaseYear: ReleaseYear,
    val titleText: TitleText,
    val titleType: TitleType
)

data class OriginalTitleText(
    val __typename: String,
    val text: String
)

data class PrimaryImage(
    val __typename: String,
    val caption: Caption,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)

data class ReleaseDate(
    val __typename: String,
    val day: Int,
    val month: Int,
    val year: Int
)

data class ReleaseYear(
    val __typename: String,
    val endYear: Any,
    val year: Int
)

data class TitleText(
    val __typename: String,
    val text: String
)

data class TitleType(
    val __typename: String,
    val canHaveEpisodes: Boolean,
    val categories: List<Category>,
    val id: String,
    val isEpisode: Boolean,
    val isSeries: Boolean,
    val text: String
)

data class Caption(
    val __typename: String,
    val plainText: String
)

data class Category(
    val __typename: String,
    val value: String
)