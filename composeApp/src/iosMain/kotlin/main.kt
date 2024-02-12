import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeUIViewController
import com.pscode.app.App
import com.pscode.app.utils.WindowSize
import platform.UIKit.UIViewController

fun MainViewController(screenWidth: Double): UIViewController =
    ComposeUIViewController(content = { App(WindowSize.basedOnWidth(screenWidth.dp)) })