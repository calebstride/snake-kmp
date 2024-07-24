package com.calebstride.snake


import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.calebstride.snake.game.SnakeGame
import com.calebstride.snake.ui.theme.SnakeTheme
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    SnakeTheme {
        Surface(color = MaterialTheme.colorScheme.surfaceVariant) {
            SnakeGame()
        }
    }
}