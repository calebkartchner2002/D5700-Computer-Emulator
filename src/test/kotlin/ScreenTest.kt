package emulator

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class ScreenTest {
    @Test
    fun testWriteToFrameBuffer() {
        val screen = Screen()
        screen.writeToFrameBuffer('A'.code.toByte(), 0, 0)
        screen.writeToFrameBuffer('Z'.code.toByte(), 7, 7)

        assertEquals('A', screen.getChar(0, 0))
        assertEquals('Z', screen.getChar(7, 7))
    }

    @Test
    fun testInvalidCoordinates() {
        val screen = Screen()

        assertThrows<IllegalArgumentException> {
            screen.writeToFrameBuffer('X'.code.toByte(), 8, 0)
        }

        assertThrows<IllegalArgumentException> {
            screen.writeToFrameBuffer('X'.code.toByte(), 0, 8)
        }

        assertThrows<IllegalArgumentException> {
            screen.writeToFrameBuffer('X'.code.toByte(), -1, 0)
        }
    }

    @Test
    fun testClearScreen() {
        val screen = Screen()
        screen.writeToFrameBuffer('X'.code.toByte(), 3, 3)
        screen.writeToFrameBuffer('Y'.code.toByte(), 5, 5)

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

    @Test
    fun testFrameBufferAddressing() {
        val screen = Screen()

        // Test that row/col properly maps to linear address
        screen.writeToFrameBuffer('A'.code.toByte(), 0, 0)  // Address 0
        assertEquals('A'.code.toByte(), screen.readFrameBuffer(0))

        screen.writeToFrameBuffer('B'.code.toByte(), 1, 0)  // Address 8
        assertEquals('B'.code.toByte(), screen.readFrameBuffer(8))

        screen.writeToFrameBuffer('C'.code.toByte(), 0, 1)  // Address 1
        assertEquals('C'.code.toByte(), screen.readFrameBuffer(1))

        screen.writeToFrameBuffer('D'.code.toByte(), 7, 7)  // Address 63
        assertEquals('D'.code.toByte(), screen.readFrameBuffer(63))
    }
}