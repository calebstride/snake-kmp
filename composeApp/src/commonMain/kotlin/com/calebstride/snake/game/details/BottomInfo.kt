package com.calebstride.snake.game.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.calebstride.snake.game.Direction

/**
 * The information shown at the bottom of the screen below the game grid. Contains the score,
 * menu button and the direction buttons.
 */
@Composable
fun BottomInfo(
    onChangeDirection: (Direction) -> Unit,
    onOpenMenu: () -> Unit,
    score: Int,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        ScoreAndMenuButton(score = score, onOpenMenu = onOpenMenu)
        Controls(onChangeDirection = onChangeDirection)
    }
}

@Composable
private fun ScoreAndMenuButton(score: Int, onOpenMenu: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.height(150.dp).padding(all = 20.dp)
    ) {
        Text(
            text = "Score: $score",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium
        )

        Button(onClick = onOpenMenu) {
            Text(text = "Menu")
        }
    }
}