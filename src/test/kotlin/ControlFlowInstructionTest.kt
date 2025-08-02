package emulator.instructions

import emulator.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
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
        setup.registers.general[1] = 0x42.toByte()
        setup.registers.general[2] = 0x42.toByte()
        setup.registers.programCounter = 10

        val instruction = SkipEqualInstruction(0x81.toByte(), 0x20.toByte())
        instruction.execute(setup.registers, setup.memory, setup.screen, setup.keyboard)

        // Taken skip → PC advances by +4 (skips next instruction)
        assertEquals(14, setup.registers.programCounter)
        assertTrue(instruction.shouldIncrementPC())
    }

    @Test
    fun testSkipEqualWhenNotEqual() {
        val setup = setup()
        setup.registers.general[1] = 0x42.toByte()
        setup.registers.general[2] = 0x43.toByte()
        setup.registers.programCounter = 10

        val instruction = SkipEqualInstruction(0x81.toByte(), 0x20.toByte())
        instruction.execute(setup.registers, setup.memory, setup.screen, setup.keyboard)

        // Not taken → instruction doesn't move PC; CPU would later auto +2
        assertEquals(10, setup.registers.programCounter)
        assertTrue(instruction.shouldIncrementPC())
    }

    @Test
    fun testSkipNotEqualWhenNotEqual() {
        val setup = setup()
        setup.registers.general[3] = 0x11.toByte()
        setup.registers.general[4] = 0x22.toByte()
        setup.registers.programCounter = 20

        val instruction = SkipNotEqualInstruction(0x93.toByte(), 0x40.toByte())
        instruction.execute(setup.registers, setup.memory, setup.screen, setup.keyboard)

        // Taken skip → PC advances by +4 (skips next instruction)
        assertEquals(24, setup.registers.programCounter)
        assertTrue(instruction.shouldIncrementPC())
    }

    @Test
    fun testSkipNotEqualWhenEqual() {
        val setup = setup()
        setup.registers.general[3] = 0x55.toByte()
        setup.registers.general[4] = 0x55.toByte()
        setup.registers.programCounter = 20

        val instruction = SkipNotEqualInstruction(0x93.toByte(), 0x40.toByte())
        instruction.execute(setup.registers, setup.memory, setup.screen, setup.keyboard)

        // Not taken → instruction doesn't move PC; CPU would later auto +2
        assertEquals(20, setup.registers.programCounter)
        assertTrue(instruction.shouldIncrementPC())
    }
}
