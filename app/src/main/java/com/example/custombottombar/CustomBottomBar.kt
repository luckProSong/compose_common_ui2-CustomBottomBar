package com.example.custombottombar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

data class BottomNavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val route: String
)

@Composable
fun CustomBottomBar(navHostController: NavHostController) {
    val items = listOf(
        BottomNavItem("Home", Icons.Filled.Home, Icons.Outlined.Home, "home"),
        BottomNavItem("Favorite", Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder, "favorite"),
        BottomNavItem("Profile", Icons.Filled.Person, Icons.Outlined.Person, "profile"),
        BottomNavItem("Settings", Icons.Filled.Settings, Icons.Outlined.Settings, "settings"),
    )

    //  현재화면의 route정보, 변경된화면
    val currentRoute = navHostController.currentBackStackEntryAsState().value?.destination?.route

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 8.dp)
            .navigationBarsPadding(),// 하단시스템바 높이만큼 패딩을 줌
        //  시작과 끝에 여백이 있고 아이템 사이에 공간을 균등하게 분배
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        items.forEach { item ->
            val interactionSource = remember { MutableInteractionSource() }

            Column(
                //  아이콘과 텍스트 정렬
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable(
                        //  클릭시 눌림효과 제거
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        if (currentRoute != item.route) {
                            navHostController.navigate(item.route) {
                                popUpTo(0) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    }
            ) {
                Icon(
                    imageVector = if (currentRoute == item.route) item.selectedIcon
                    else item.unSelectedIcon,
                    contentDescription = item.title,
                )
                Text(
                    text = item.title,
                    fontSize = 12.sp,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomBottomBarPreview() {
    val navController = rememberNavController()
    CustomBottomBar(navController)
}