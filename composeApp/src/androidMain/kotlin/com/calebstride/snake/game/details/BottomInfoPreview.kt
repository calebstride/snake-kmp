package com.calebstride.snake.game.details

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
private fun BottomInfoPreview() {
    BottomInfo(onChangeDirection = {}, score = 120, onOpenMenu = {})
}