package com.calebstride.snake.game.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.calebstride.snake.game.GameState

@Composable
fun GameMenu(
    onChangeGameState: (GameState) -> Unit,
    onResetGame: () -> Unit,
    gameState: GameState,
    modifier: Modifier = Modifier
) {
    if (gameState == GameState.RUNNING) return

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
            .fillMaxHeight(0.4f)
            .background(color = MaterialTheme.colorScheme.surface.copy(alpha = 0.4f))
    ) {
        if (gameState == GameState.PAUSED) {
            Button(onClick = { onChangeGameState(GameState.RUNNING) }) {
                Text("Continue")
            }
            Button(onClick = onResetGame) {
                Text("Restart")
            }
        }
        if (gameState == GameState.INITIAL) {
            Button(onClick = { onChangeGameState(GameState.RUNNING) }) {
                Text("Start")
            }
        }
        if (gameState == GameState.GAME_OVER) {
            Button(onClick = onResetGame) {
                Text("Restart")
            }
        }
    }

}