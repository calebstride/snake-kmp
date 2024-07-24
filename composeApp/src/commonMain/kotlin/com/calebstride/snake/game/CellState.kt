package com.calebstride.snake.game

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

class CellState {

    private val _cellType : MutableState<CellType> = mutableStateOf(CellType.EMPTY)
    val cellType: State<CellType> = _cellType

    fun setCellType(cellType: CellType) {
        _cellType.value = cellType
    }

}

enum class CellType {
    FOOD,
    EMPTY,
    SNAKE
}