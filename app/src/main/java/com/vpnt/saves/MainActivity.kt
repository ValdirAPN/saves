package com.vpnt.saves

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.vpnt.saves.ui.SavesApp
import com.vpnt.saves.ui.designsystem.theme.SavesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SavesTheme {
                SavesApp()
            }
        }
    }
}
