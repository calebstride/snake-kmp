package com.calebstride.snake.game.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.calebstride.snake.game.Direction

@Composable
fun Controls(
    onChangeDirection: (Direction) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.size(size = 150.dp)) {
        ArrowButton(
            onClick = { onChangeDirection(Direction.LEFT) },
            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
            modifier = Modifier.align(Alignment.CenterStart)
        )
        ArrowButton(
            onClick = { onChangeDirection(Direction.UP) },
            imageVector = Icons.Rounded.KeyboardArrowUp,
            modifier = Modifier.align(Alignment.TopCenter)
        )
        ArrowButton(
            onClick = { onChangeDirection(Direction.DOWN) },
            imageVector = Icons.Rounded.KeyboardArrowDown,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
        ArrowButton(
            onClick = { onChangeDirection(Direction.RIGHT) },
            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}

@Composable
private fun ArrowButton(
    onClick: () -> Unit,
    imageVector: ImageVector,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = modifier
    ) {
        Icon(imageVector = imageVector, contentDescription = null)
    }
}