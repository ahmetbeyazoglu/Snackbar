package com.herpestes.snackbar

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.herpestes.snackbar.ui.theme.SnackBarTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SnackBarTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Sayfa()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Sayfa() {

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {
                       SnackbarHost(hostState = snackbarHostState){
                           Snackbar(
                               snackbarData = it,
                               //containerColor = Color.White,
                               contentColor = Color.Blue,
                               actionColor = Color.Red
                           )
                       }
        },

        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar(message = "Merhaba")
                    }
                }) {
                    Text(text = "Varsayılan")
                }
                Button(onClick = {
                    scope.launch {
                        val sb = snackbarHostState.showSnackbar(message = "Silmek ister misin?", actionLabel = "Evet")
                        if(sb == SnackbarResult.ActionPerformed){
                            snackbarHostState.showSnackbar(message = "Silindi")
                        }
                    }
                }) {
                    Text(text = "SnackBar action")
                }
                Button(onClick = {
                    scope.launch {
                        val sb = snackbarHostState.showSnackbar(message = "İnternet Bağlantısı yok!", actionLabel = "Tekrar Dene")
                        if(sb == SnackbarResult.ActionPerformed){
                            snackbarHostState.showSnackbar(message = "Tekrar denendi")
                        }
                    }
                }) {
                    Text(text = "SnackBar özel")
                }

            }
        }

    )

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SnackBarTheme {
        Sayfa()
    }
}