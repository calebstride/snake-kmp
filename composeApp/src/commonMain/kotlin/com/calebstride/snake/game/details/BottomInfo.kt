package com.calebstride.snake.game.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.calebstride.snake.game.Direction

@Composable
fun BottomInfo(
    onChangeDirection: (Direction) -> Unit,
    score: Int,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Score(score = score)
        Controls(onChangeDirection = onChangeDirection)
    }
}

@Composable
private fun Score(score: Int) {
    Column(modifier = Modifier.padding(12.dp)) {
        Text("Score: $score", style = MaterialTheme.typography.titleMedium)
    }
}