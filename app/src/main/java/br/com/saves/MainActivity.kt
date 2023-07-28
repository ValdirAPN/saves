package br.com.saves

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.com.saves.data.util.NetworkMonitor
import br.com.saves.ui.theme.SavesTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SavesTheme {
                SavesApp(
                    networkMonitor = networkMonitor
                )
            }
        }
    }
}
