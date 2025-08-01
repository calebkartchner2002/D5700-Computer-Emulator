package emulator.instructions

import emulator.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ControlFlowInstructionTest {
    private fun setup() = TestSetup()

    class TestSetup {
        val registers = Registers()
        val memory = Memory()
        val screen = Screen()
        val keyboard = Keyboard()
    }

    @Test
    fun testSkipEqualWhenEqual() {
        val setup = setup()
        setup.registers.general[1] = 0x42
        setup.registers.general[2] = 0x42
        setup.registers.programCounter = 10

        val instruction = SkipEqualInstruction(0x81.toByte(), 0x20.toByte())
        instruction.execute(setup.registers, setup.memory, setup.screen, setup.keyboard)

        assertEquals(12, setup.registers.programCounter)
        assertFalse(instruction.shouldIncrementPC())
    }

    @Test
    fun testSkipEqualWhenNotEqual() {
        val setup = setup()
        setup.registers.general[1] = 0x42
        setup.registers.general[2] = 0x43
        setup.registers.programCounter = 10

        val instruction = SkipEqualInstruction(0x81.toByte(), 0x20.toByte())
        instruction.execute(setup.registers, setup.memory, setup.screen, setup.keyboard)

        assertEquals(10, setup.registers.programCounter)
        assertTrue(instruction.shouldIncrementPC())
    }

    @Test
    fun testSkipNotEqualWhenNotEqual() {
        val setup = setup()
        setup.registers.general[3] = 0x11
        setup.registers.general[4] = 0x22
        setup.registers.programCounter = 20

        val instruction = SkipNotEqualInstruction(0x93.toByte(), 0x40.toByte())
        instruction.execute(setup.registers, setup.memory, setup.screen, setup.keyboard)

        assertEquals(22, setup.registers.programCounter)
        assertFalse(instruction.shouldIncrementPC())
    }

    @Test
    fun testSkipNotEqualWhenEqual() {
        val setup = setup()
        setup.registers.general[3] = 0x55
        setup.registers.general[4] = 0x55
        setup.registers.programCounter = 20

        val instruction = SkipNotEqualInstruction(0x93.toByte(), 0x40.toByte())
        instruction.execute(setup.registers, setup.memory, setup.screen, setup.keyboard)

        assertEquals(20, setup.registers.programCounter)
        assertTrue(instruction.shouldIncrementPC())
    }
}