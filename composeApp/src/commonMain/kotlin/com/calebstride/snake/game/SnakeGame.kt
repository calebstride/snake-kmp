package com.calebstride.snake.game

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.calebstride.snake.game.details.BottomInfo
import com.calebstride.snake.game.details.GameMenu

/**
 * The composable that holds the snake grid and the controls / menu.
 *
 * @param snakeGameState The state of the snake game
 */
@Composable
fun SnakeGame(
    modifier: Modifier = Modifier,
    snakeGameState: SnakeGameState = rememberSnakeGameState()
) {

    // This runs the main game loop when the game is within the view
    LaunchedEffect(Unit) {
        while(true) {
            snakeGameState.tick()
        }
    }

    Box(modifier = modifier.height(IntrinsicSize.Max)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            SnakeGrid(
                snakeGameState = snakeGameState,
                modifier = Modifier.padding(vertical = 12.dp)
                    .border(width = 2.dp, color = MaterialTheme.colorScheme.outline)
            )
            BottomInfo(
                score = snakeGameState.score.value,
                onChangeDirection = snakeGameState::updateDirection,
                onOpenMenu = { snakeGameState.updateGameState(GameState.PAUSED) },
                modifier = Modifier.background(color = MaterialTheme.colorScheme.surfaceVariant)
                    .padding(all = 12.dp)
            )
        }
        GameMenu(
            onChangeGameState = snakeGameState::updateGameState,
            gameState = snakeGameState.gameState.value,
            onResetGame = snakeGameState::restartGame,
            modifier = Modifier.fillMaxHeight()
        )
    }
}

/**
 * The snake grid that holds the food and snake positions
 */
@Composable
private fun SnakeGrid(snakeGameState: SnakeGameState, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        for (col in 0..<SnakeGameState.HEIGHT) {
            Row {
                for (row in 0..<SnakeGameState.WIDTH) {
                    SnakeCell(cellState = snakeGameState.getCellState(row, col))
                }
            }
        }
    }
}

/**
 * A single cell in the snake grid. This shows a different colour depending on the content of the
 * cell.
 */
@Composable
private fun SnakeCell(cellState: CellState) {
    val colour = when(cellState.cellType.value) {
        CellType.EMPTY -> MaterialTheme.colorScheme.surface
        CellType.FOOD -> MaterialTheme.colorScheme.tertiary
        CellType.SNAKE ->  MaterialTheme.colorScheme.primary
    }
    Box(modifier = Modifier.size(10.dp).background(color = colour))
}