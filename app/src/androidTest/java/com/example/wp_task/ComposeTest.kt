package com.example.wp_task

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.wp_task.model.*
import com.example.wp_task.screens.ScreenNav
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScreenNavTest {

    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun screenNavTest() {
        composeTestRule.setContent {
            val movie = Movie(
                _id = "1",
                id = "12345",
                originalTitleText = OriginalTitleText("__typename", "Original Title"),
                position = 1,
                primaryImage = PrimaryImage(
                    "__typename",
                    Caption("__typename", "Image Caption"),
                    1920,
                    "image_id",
                    "https://example.com/image.jpg",
                    1080
                ),
                releaseDate = ReleaseDate("__typename", 10, 2023, 7),
                releaseYear = ReleaseYear("__typename", 2023, 2023),
                titleText = TitleText("__typename", "Movie Title"),
                titleType = TitleType(
                    "__typename",
                    true,
                    listOf(Category("__typename", "Category 1"), Category("__typename", "Category 2")),
                    "title_type_id",
                    false,
                    false,
                    "Title Type"
                )
            )
            val movieList = listOf(MovieData("1", "Test Movie"))

            ScreenNav(
                onEvent = { /* Handle events */ },
                movie = movie,
                movieList = movieList
            )
        }

        // Perform UI interactions and assertions using the composeTestRule
        composeTestRule.onNodeWithText("Movie Title").assertExists()
        composeTestRule.onNodeWithText("No favorites available").assertDoesNotExist()

        // Perform event-based interactions and assertions
        composeTestRule.onNodeWithContentDescription("Refresh").performClick()
        composeTestRule.onNodeWithContentDescription("Favourites").performClick()

        composeTestRule.onRoot()
            .performTouchInput { swipeLeft()
                swipeLeft()}

        composeTestRule.onNodeWithText("Test Movie").assertExists()

    }

}