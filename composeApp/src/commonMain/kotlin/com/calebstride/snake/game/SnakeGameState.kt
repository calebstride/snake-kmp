package com.calebstride.snake.game

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay
import kotlin.random.Random

class SnakeGameState {

    companion object {
        const val WIDTH = 25
        const val HEIGHT = 25
        const val SIZE = WIDTH * HEIGHT
    }

    private val _score : MutableState<Int> = mutableStateOf(0)
    val score : State<Int> = _score

    private val _gameState : MutableState<GameState> = mutableStateOf(GameState.INITIAL)
    val gameState : State<GameState> = _gameState

    private val cellStates = MutableList(SIZE) { CellState() }

    private var direction = Direction.LEFT

    private var delay: Long = 500L

    private val snakeIndices = ArrayDeque<Int>()

    init {
        initStartPosition()
        createNewFood()
    }

    private fun initStartPosition() {
        snakeIndices.addFirst(randomPosition())
        updateCellType(snakeIndices.first(), CellType.SNAKE)
    }

    fun getCellState(x: Int, y: Int): CellState {
        return cellStates[cellPosToIndex(x, y)]
    }

    /**
     * Translate a cell as x, y into the list of cells. (0, 0) is the top left position
     */
    private fun cellPosToIndex(x: Int, y: Int): Int {
        return (y * WIDTH) + x
    }

    private fun updateCellType(index: Int, cellType: CellType) {
        cellStates[index].setCellType(cellType)
    }

    fun updateDirection(direction: Direction) {
        if (direction.isOpposite(this.direction)) return
        this.direction = direction
    }

    fun updateGameState(gameState: GameState) {
        _gameState.value = gameState
    }

    private fun randomPosition(): Int {
        return Random.nextInt(0, SIZE)
    }

    /**
     * Called every time we want to update the game
     */
    suspend fun tick() {
        delay(delay)
        if (_gameState.value != GameState.RUNNING) return
        // Figure out the space we're going to move to
        val nextIndex = getNextIndex(snakeIndices.first(), direction)
        val cellType = cellStates[nextIndex].cellType.value
        when(cellType) {
            // Add the snake head to this position but don't shrink the snake, also create new food
            CellType.FOOD -> {
                snakeIndices.addFirst(nextIndex)
                updateCellType(nextIndex, CellType.SNAKE)
                createNewFood()
                increaseScore()
            }
            // Add the snake head to this position and move the last snake position
            CellType.EMPTY -> {
                updateCellType(snakeIndices.removeLast(), CellType.EMPTY)
                snakeIndices.addFirst(nextIndex)
                updateCellType(nextIndex, CellType.SNAKE)
            }
            // We've hit the snake so game over
            CellType.SNAKE -> {
                _gameState.value = GameState.GAME_OVER
            }
        }
    }

    private fun getNextIndex(currentIndex: Int, direction: Direction): Int {
        return when (direction) {
            Direction.DOWN -> (currentIndex + WIDTH) % SIZE
            Direction.UP -> if (currentIndex - WIDTH < 0) currentIndex - WIDTH + SIZE else currentIndex - WIDTH
            Direction.LEFT -> moveRightOrLeft(currentIndex, currentIndex - 1, true)
            Direction.RIGHT -> moveRightOrLeft(currentIndex, currentIndex + 1, false)
        }
    }

    private fun moveRightOrLeft(currentIndex: Int, newIndex: Int, moveLeft: Boolean): Int {
        val currentRow = currentIndex / WIDTH
        val newRow = newIndex / WIDTH
        return if (newIndex >= SIZE || newIndex < 0 || newRow != currentRow) {
            newIndex + if (moveLeft) WIDTH else -WIDTH
        } else {
            newIndex
        }
    }

    private fun increaseScore() {
        _score.value += 1 + (0.5 * snakeIndices.size).toInt()
        delay = (delay * 0.98).toLong()
    }

    private fun createNewFood() {
        val queryIndex = randomPosition()
        for (i in queryIndex..queryIndex + SIZE) {
            if (cellStates[queryIndex % SIZE].cellType.value == CellType.EMPTY) {
                updateCellType(queryIndex, CellType.FOOD)
                return
            }
        }
        // No space was empty so we can complete the game
        _gameState.value = GameState.COMPLETE
    }

    fun restartGame() {
        _score.value = 0
        delay = 500L
        for (cellState in cellStates) {
            cellState.setCellType(CellType.EMPTY)
        }
        while (snakeIndices.isNotEmpty()) {
            snakeIndices.removeLast()
        }
        initStartPosition()
        createNewFood()
        _gameState.value = GameState.RUNNING
    }
}

@Composable
fun rememberSnakeGameState(): SnakeGameState {
    return remember {
        SnakeGameState()
    }
}