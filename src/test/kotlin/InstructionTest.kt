package emulator.instructions

import emulator.Memory
import emulator.Registers
import emulator.Screen
import emulator.Keyboard
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class InstructionTest {
    @Test
    fun testStoreInstruction() {
        val registers = Registers()
        val memory = Memory()
        val screen = Screen()
        val keyboard = Keyboard()

        val instruction = StoreInstruction(0x00, 0x42.toByte())
        instruction.execute(registers, memory, screen, keyboard)

        assertEquals(0x42, registers.general[0])
        assertTrue(instruction.shouldIncrementPC())
    }

    @Test
    fun testStoreInstructionDifferentRegister() {
        val registers = Registers()
        val memory = Memory()
        val screen = Screen()
        val keyboard = Keyboard()

        val instruction = StoreInstruction(0x07, 0xFF.toByte())
        instruction.execute(registers, memory, screen, keyboard)

        assertEquals(0xFF.toByte(), registers.general[7])
    }

    @Test
    fun testJumpInstruction() {
        val registers = Registers()
        val memory = Memory()
        val screen = Screen()
        val keyboard = Keyboard()

        val instruction = JumpInstruction(0x51.toByte(), 0xF2.toByte())
        instruction.execute(registers, memory, screen, keyboard)

        assertEquals(0x1F2, registers.programCounter)
        assertFalse(instruction.shouldIncrementPC())
    }

    @Test
    fun testJumpInstructionOddAddressThrows() {
        val registers = Registers()
        val memory = Memory()
        val screen = Screen()
        val keyboard = Keyboard()

        val instruction = JumpInstruction(0x51.toByte(), 0xF3.toByte())

        assertThrows<IllegalArgumentException> {
            instruction.execute(registers, memory, screen, keyboard)
        }
    }
}