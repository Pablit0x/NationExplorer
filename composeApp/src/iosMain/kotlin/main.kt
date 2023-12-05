import androidx.compose.ui.window.ComposeUIViewController
import com.pscode.app.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
