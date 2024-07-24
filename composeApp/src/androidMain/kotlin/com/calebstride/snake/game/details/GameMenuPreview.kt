package com.calebstride.snake.game.details

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.calebstride.snake.game.GameState

@Preview
@Composable
private fun GameMenuGameOverPreview() {
    GameMenu(onChangeGameState = {}, onResetGame = { }, gameState = GameState.GAME_OVER)
}

@Preview
@Composable
private fun GameMenuInitialPreview() {
    GameMenu(onChangeGameState = {}, onResetGame = { }, gameState = GameState.INITIAL)
}

@Preview
@Composable
private fun GameMenuCompletePreview() {
    GameMenu(onChangeGameState = {}, onResetGame = { }, gameState = GameState.COMPLETE)
}

@Preview
@Composable
private fun GameMenuPausedPreview() {
    GameMenu(onChangeGameState = {}, onResetGame = { }, gameState = GameState.PAUSED)
}

@Preview
@Composable
private fun GameMenuRunningPreview() {
    GameMenu(onChangeGameState = {}, onResetGame = { }, gameState = GameState.RUNNING)
}