package org.d3ifcool.vultracount

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import org.d3ifcool.vultracount.ui.screen.TracountScreen
import org.d3ifcool.vultracount.ui.theme.VulTracountTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VulTracountTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    content = { innerPadding ->
                        val context = LocalContext.current
                        TracountScreen(
                            modifier = Modifier.padding(innerPadding),
                            context = context
                        )
                    }
                )
            }
        }
    }
}

