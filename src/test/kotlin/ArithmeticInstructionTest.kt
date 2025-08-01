package emulator.instructions

import emulator.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ArithmeticInstructionTest {
    private fun setup() = TestSetup()

    class TestSetup {
        val registers = Registers()
        val memory = Memory()
        val screen = Screen()
        val keyboard = Keyboard()
    }

    @Test
    fun testAddInstruction() {
        val setup = setup()
        setup.registers.general[0] = 0x10
        setup.registers.general[1] = 0x20

        val instruction = AddInstruction(0x10.toByte(), 0x12.toByte())
        instruction.execute(setup.registers, setup.memory, setup.screen, setup.keyboard)

        assertEquals(0x30, setup.registers.general[2])
    }

    @Test
    fun testAddWithOverflow() {
        val setup = setup()
        setup.registers.general[0] = 0xFF.toByte()
        setup.registers.general[1] = 0x02

        val instruction = AddInstruction(0x10.toByte(), 0x12.toByte())
        instruction.execute(setup.registers, setup.memory, setup.screen, setup.keyboard)

        assertEquals(0x01, setup.registers.general[2])
    }

    @Test
    fun testSubInstruction() {
        val setup = setup()
        setup.registers.general[0] = 0x30
        setup.registers.general[1] = 0x10

        val instruction = SubInstruction(0x20.toByte(), 0x12.toByte())
        instruction.execute(setup.registers, setup.memory, setup.screen, setup.keyboard)

        assertEquals(0x20, setup.registers.general[2])
    }

    @Test
    fun testSubWithUnderflow() {
        val setup = setup()
        setup.registers.general[0] = 0x10
        setup.registers.general[1] = 0x20

        val instruction = SubInstruction(0x20.toByte(), 0x12.toByte())
        instruction.execute(setup.registers, setup.memory, setup.screen, setup.keyboard)

        assertEquals(0xF0.toByte(), setup.registers.general[2])
    }
}