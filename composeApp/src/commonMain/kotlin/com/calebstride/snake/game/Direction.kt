package com.calebstride.snake.game

/**
 * Represents the direction the snake is headed in
 */
enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    /**
     * Checks if the current directions is opposite from the given direction
     *
     * @param direction The direction to check against
     */
    fun isOpposite(direction: Direction): Boolean {
        return (this == UP && direction == DOWN) || (this == DOWN && direction == UP) ||
                (this == LEFT && direction == RIGHT) || (this == RIGHT && direction == LEFT)
    }
}