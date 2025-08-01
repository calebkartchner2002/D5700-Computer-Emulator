package emulator.instructions

import emulator.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DrawDebugTest {
    @Test
    fun testDrawInstructionDebug() {
        val registers = Registers()
        val memory = Memory()
        val screen = Screen()
        val keyboard = Keyboard()

        // Set up register 1 with 'A'
        registers.general[1] = 'A'.code.toByte()
        println("r1 contains: ${registers.general[1]} (${registers.general[1].toChar()})")

        // Create DRAW instruction F1 35 (draw r1 at row 3, col 5)
        val byte1: Byte = 0xF1.toByte()
        val byte2: Byte = 0x35.toByte()

        println("Instruction bytes: ${"%02X".format(byte1.toInt() and 0xFF)} ${"%02X".format(byte2.toInt() and 0xFF)}")

        // Extract parameters
        val charReg = byte1.toInt() and 0x0F
        val row = (byte2.toInt() and 0xF0) shr 4
        val col = byte2.toInt() and 0x0F

        println("charReg: $charReg")
        println("row: $row")
        println("col: $col")

        // Execute instruction
        val instruction = DrawInstruction(byte1, byte2)
        instruction.execute(registers, memory, screen, keyboard)

        // Check what's at position (3,5)
        val result = screen.getChar(3, 5)
        println("Character at (3,5): '$result' (${result.code})")

        // Also check the frame buffer directly
        val address = 3 * 8 + 5
        val fbValue = screen.readFrameBuffer(address)
        println("Frame buffer at address $address: $fbValue (${fbValue.toChar()})")

        assertEquals('A', result)
    }
}