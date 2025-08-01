package emulator.instructions

import emulator.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class IOInstructionTest {
    private fun setup() = TestSetup()

    class TestSetup {
        val registers = Registers()
        val memory = Memory()
        val screen = Screen()
        val keyboard = Keyboard()
    }

    @Test
    fun testDrawInstruction() {
        val setup = setup()
        setup.registers.general[1] = 'A'.code.toByte()
        setup.registers.general[2] = 3
        setup.registers.general[3] = 5

        val instruction = DrawInstruction(0xF1.toByte(), 0x23.toByte())
        instruction.execute(setup.registers, setup.memory, setup.screen, setup.keyboard)

        assertEquals('A', setup.screen.getChar(3, 5))
    }

    @Test
    fun testDrawInstructionInvalidASCII() {
        val setup = setup()
        setup.registers.general[0] = 0x80.toByte()
        setup.registers.general[1] = 0
        setup.registers.general[2] = 0

        val instruction = DrawInstruction(0xF0.toByte(), 0x12.toByte())

        assertThrows<IllegalStateException> {
            instruction.execute(setup.registers, setup.memory, setup.screen, setup.keyboard)
        }
    }

    @Test
    fun testTimerInstructions() {
        val setup = setup()

        val setT = SetTInstruction(0xB0.toByte(), 0x3C.toByte())
        setT.execute(setup.registers, setup.memory, setup.screen, setup.keyboard)
        assertEquals(0x3C, setup.registers.timer)

        val readT = ReadTInstruction(0xC2.toByte(), 0x00)
        readT.execute(setup.registers, setup.memory, setup.screen, setup.keyboard)
        assertEquals(0x3C, setup.registers.general[2])
    }

    @Test
    fun testConvertToBase10() {
        val setup = setup()
        setup.registers.general[0] = 0xFF.toByte()
        setup.registers.address = 0x100

        val instruction = ConvertToBase10Instruction(0xD0.toByte(), 0x00)
        instruction.execute(setup.registers, setup.memory, setup.screen, setup.keyboard)

        assertEquals(2, setup.memory.read(0x100))
        assertEquals(5, setup.memory.read(0x101))
        assertEquals(5, setup.memory.read(0x102))
    }

    @Test
    fun testConvertByteToAscii() {
        val setup = setup()

        setup.registers.general[0] = 0x05
        val inst1 = ConvertByteToAsciiInstruction(0xE0.toByte(), 0x10.toByte())
        inst1.execute(setup.registers, setup.memory, setup.screen, setup.keyboard)
        assertEquals('5'.code.toByte(), setup.registers.general[1])

        setup.registers.general[2] = 0x0A
        val inst2 = ConvertByteToAsciiInstruction(0xE2.toByte(), 0x30.toByte())
        inst2.execute(setup.registers, setup.memory, setup.screen, setup.keyboard)
        assertEquals('A'.code.toByte(), setup.registers.general[3])
    }
}