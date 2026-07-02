package com.jagadishvjr.demo1

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jagadishvjr.demo1.domin.model.Address
import com.jagadishvjr.demo1.domin.model.Geo
import com.jagadishvjr.demo1.domin.model.User
import com.jagadishvjr.demo1.ui.theme.Demo1Theme
import com.jagadishvjr.presentation.UserScreen
import com.jagadishvjr.presentation.UserUiState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserScreenTest {

    @get: Rule
    val composeRule = createComposeRule()

    @Test
    fun loadingState_showsLoading(){
        composeRule.setContent {
            UserScreen(state = UserUiState.Loading)
        }

        composeRule.onNodeWithTag("loading_indicator").assertExists()
    }

    @Test
    fun errorState_showsErrorMessage(){
        composeRule.setContent {
            Demo1Theme {
                UserScreen(state = UserUiState.Error("Something went wrong"))
            }
        }

        composeRule.onNodeWithTag("error_message").assertExists()
        composeRule.onNodeWithText("Something went wrong").assertExists()
    }

    @Test
    fun successState_showsUserNameAndCity(){
        val users = listOf(
            User(
                id = 101,
                name = "Jagdish",
                distanceFromCurrentUserKm = 12.5,
                username = "vjr",
                email = "vjr@mail.com",
                website = "abc",
                phone = "9603343",
                address = Address(
                    city = "Hyd",
                    geo = Geo(
                        lat = 17.3850,
                        lng = 78.4867
                    ),
                    zipcode = "500086"
                )
            )
        )

        composeRule.setContent {
            Demo1Theme {
                UserScreen(state = UserUiState.Success(users))
            }
        }

        composeRule.onNodeWithText("Jagdish").assertExists()
        composeRule.onNodeWithText("Hyd").assertExists()
        composeRule.onNodeWithText("Distance: 12.50 km").assertExists()
    }
}
