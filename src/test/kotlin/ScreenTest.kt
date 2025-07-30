package emulator

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class ScreenTest {
    @Test
    fun testDrawCharacter() {
        val screen = Screen()
        screen.draw('A', 0, 0)
        screen.draw('Z', 7, 7)

        assertEquals('A', screen.getChar(0, 0))
        assertEquals('Z', screen.getChar(7, 7))
    }

    @Test
    fun testInvalidCoordinates() {
        val screen = Screen()

        assertThrows<IllegalArgumentException> {
            screen.draw('X', 8, 0)
        }

        assertThrows<IllegalArgumentException> {
            screen.draw('X', 0, 8)
        }

        assertThrows<IllegalArgumentException> {
            screen.draw('X', -1, 0)
        }
    }

    @Test
    fun testClearScreen() {
        val screen = Screen()
        screen.draw('X', 3, 3)
        screen.draw('Y', 5, 5)

        screen.clear()

        assertEquals(' ', screen.getChar(3, 3))
        assertEquals(' ', screen.getChar(5, 5))
    }

    @Test
    fun testInitialState() {
        val screen = Screen()

        for (row in 0..7) {
            for (col in 0..7) {
                assertEquals(' ', screen.getChar(row, col))
            }
        }
    }
}