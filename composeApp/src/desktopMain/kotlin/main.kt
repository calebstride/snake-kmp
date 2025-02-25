import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.calebstride.snake.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Snake",
    ) {
        App()
    }
}