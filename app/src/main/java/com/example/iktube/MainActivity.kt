package com.example.iktube

import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.iktube.ui.theme.IKTUBETheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigation(navController = rememberNavController())
            mainScreen()
        }
    }
}
@Composable
fun Navigation(navController: NavHostController){
    NavHost(navController = navController, startDestination = "splash_screen"){
        //splash screen
        composable("splash_screen"){
            SplashScreen(navController = navController)
        }
        composable("home"){
            HomeScreen()
        }
        composable("shorts_icon"){
            ShortsScreen()
        }
        composable("go_live"){
            LiveScreen()
        }
        composable("subscription_btn"){
            SubscriptionScreen()
        }
        composable("library_btn"){
            LibraryScreen()
        }

    }

}
@Composable
fun SplashScreen(
    navController:NavController
){
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }
    //Animation
    LaunchedEffect(key1 = true){
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(
                durationMillis = 2000,
                easing = { OvershootInterpolator(8f).getInterpolation(it)}
            )
        )
        delay(2000)
        navController.navigate("home")
    }
    //alignmenting image
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Image(painter = painterResource(id = R.drawable.iktubefront), contentDescription = "logo", modifier = Modifier.scale(scale.value) )
    }

}

@Composable
fun BouttonNavigationBar(
    items:List<ButtonNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick:(ButtonNavItem) -> Unit
){
    BottomNavigation(
        modifier = modifier,
        backgroundColor = Color.DarkGray,
        elevation = 5.dp
    ) {
        items.forEach{ item->
            val backStackEntry = navController.currentBackStackEntryAsState()
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(selected = selected,
                onClick = {onItemClick(item)},
                selectedContentColor = Color.White,
                unselectedContentColor = Color.Gray,
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        if(item.badgeCount > 0) {
                            BadgedBox( badge = { Badge { Text(item.badgeCount.toString()) } } ){
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.name
                                )
                            }
                        } else {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.name
                            )
                        }
                        if(selected) {
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp
                            )
                        }
                }
                }

            )
        }
    }
}
@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

    }
}
@Composable
fun ShortsScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Shorts screen")
    }
}
@Composable
fun LiveScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Live screen")
    }
}
@Composable
fun SubscriptionScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "subscriptions screen")
    }
}@Composable
fun LibraryScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Library screen")
    }
}
@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
@Composable
fun mainScreen() {
    val navController = rememberNavController()
    IKTUBETheme {
        Scaffold(
            topBar = {
                if (currentRoute(navController = navController) != "splash_screen") {
                    WoofTopBar()
                }
            },
            content = {
                if ((currentRoute(navController = navController) != "splash_screen") && (currentRoute(
                        navController = navController
                    ) != "shorts_icon")
                ) {
                    Box(modifier = Modifier.padding(it)) {
                        contentBar()
                    }
                }
            },
            bottomBar = {
                if (currentRoute(navController) != "splash_screen") {
                    BouttonNavigationBar(items = listOf(
                        ButtonNavItem(
                            name = "Home",
                            route = "home",
                            icon = Icons.Default.Home
                        ),
                        ButtonNavItem(
                            name = "Shorts",
                            route = "shorts_icon",
                            icon = ImageVector.vectorResource(id = R.drawable.ic_baseline_music_note_24),
                            badgeCount = 3
                        ),
                        ButtonNavItem(
                            name = "Live",
                            route = "go_live",
                            icon = ImageVector.vectorResource(id = R.drawable.ic_round_add_circle_outline_24),
                        ),
                        ButtonNavItem(
                            name = "Subscriptions",
                            route = "subscription_btn",
                            icon = ImageVector.vectorResource(id = R.drawable.ic_baseline_layers_24),
                            badgeCount = 23
                        ),
                        ButtonNavItem(
                            name = "Library",
                            route = "library_btn",
                            icon = ImageVector.vectorResource(id = R.drawable.ic_baseline_video_library_24)
                        ),
                    ),
                        navController = navController,
                        onItemClick = { navController.navigate(it.route) }
                    )
                }
            },
        )

        Navigation(navController = navController)
    }
}


@Composable
fun WoofTopBar(){
    Row(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painter = painterResource(id = R.drawable.youtbelogo), contentDescription =null,
        modifier = Modifier
            .size(64.dp)
            .padding(8.dp)
            )
        Text(text = stringResource(id = R.string.Youtube),
            color = Color.Black,
            fontWeight = FontWeight.Bold,
        )
    }
}
@Composable
fun contentBar(){
    var isLoading by remember {
        mutableStateOf(true)
    }
    LaunchedEffect(key1 = true) {
        delay(10000)
        isLoading = false
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(20) {
            ShimmerListItem(
                isLoading = isLoading,
                contentAfterLoading = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = null,
                            modifier = Modifier.size(100.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "the ik brand company is on the way.be sure to use of the ik brand products thank you" +
                                    "sincerely CEO/FOUNDER ishwor khadka"
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}