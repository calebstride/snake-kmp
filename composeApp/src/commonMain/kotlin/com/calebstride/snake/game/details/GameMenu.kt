package com.calebstride.snake.game.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.calebstride.snake.game.GameState

/**
 * The game menu that's shown when the game isn't in the [GameState.RUNNING] state.
 */
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
            .background(color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f))
    ) {
        if (gameState == GameState.PAUSED) {
            PauseMenu(onChangeGameState = onChangeGameState, onResetGame = onResetGame)
        }
        if (gameState == GameState.INITIAL) {
            Button(onClick = { onChangeGameState(GameState.RUNNING) }) {
                Text(text = "Start")
            }
        }
        if (gameState == GameState.GAME_OVER) {
            GameOver(onResetGame = onResetGame)
        }
        if (gameState == GameState.COMPLETE) {
            Congrats(onResetGame = onResetGame)
        }
    }
}

/**
 * The pause menu when the game is paused
 */
@Composable
private fun ColumnScope.PauseMenu(onChangeGameState: (GameState) -> Unit, onResetGame: () -> Unit) {
    Text(
        text = "Paused.",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(vertical = 12.dp)
    )
    Button(onClick = { onChangeGameState(GameState.RUNNING) }) {
        Text(text = "Continue")
    }
    Button(onClick = onResetGame) {
        Text(text = "Restart")
    }
}

/**
 * The game over menu when you lose
 */
@Composable
private fun ColumnScope.GameOver(onResetGame: () -> Unit) {
    Text(
        text = "Game Over",
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(vertical = 12.dp)
    )
    Button(onClick = onResetGame) {
        Text("Restart")
    }
}

/**
 * The congratulations screen when beating the game
 */
@Composable
private fun ColumnScope.Congrats(onResetGame: () -> Unit) {
    Text(
        text = "Congratulations. You won.",
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(vertical = 12.dp)
    )
    Button(onClick = onResetGame) {
        Text("Restart")
    }
}