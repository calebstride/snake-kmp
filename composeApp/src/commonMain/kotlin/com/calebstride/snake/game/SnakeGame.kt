package com.calebstride.snake.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.calebstride.snake.game.details.BottomInfo
import kotlinx.coroutines.delay

@Composable
fun SnakeGame(
    modifier: Modifier = Modifier,
    snakeGameState: SnakeGameState = rememberSnakeGameState()
) {

    LaunchedEffect(Unit) {
        while(true) {
            snakeGameState.tick()
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        SnakeGrid(snakeGameState = snakeGameState)
        BottomInfo(
            score = snakeGameState.score.value,
            onChangeDirection = snakeGameState::updateDirection
        )
    }
}

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

@Composable
private fun SnakeCell(cellState: CellState) {
    val colour = when(cellState.cellType.value) {
        CellType.EMPTY -> MaterialTheme.colorScheme.surface
        CellType.FOOD -> MaterialTheme.colorScheme.inversePrimary
        CellType.SNAKE ->  MaterialTheme.colorScheme.primary
    }
    Box(modifier = Modifier.size(10.dp).background(color = colour))
}