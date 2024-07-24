package com.calebstride.snake.game

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

/**
 * The state of a single cell in the snake grid
 */
@Stable
class CellState {

    private val _cellType : MutableState<CellType> = mutableStateOf(CellType.EMPTY)
    val cellType: State<CellType> = _cellType

    fun setCellType(cellType: CellType) {
        _cellType.value = cellType
    }

}

/**
 * The type that the cell contains
 */
enum class CellType {
    FOOD,
    EMPTY,
    SNAKE
}