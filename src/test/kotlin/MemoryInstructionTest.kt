package emulator.instructions

import emulator.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MemoryInstructionTest {
    private fun setup() = TestSetup()

    class TestSetup {
        val registers = Registers()
        val memory = Memory()
        val screen = Screen()
        val keyboard = Keyboard()
    }

    @Test
    fun testReadInstruction() {
        val setup = setup()
        setup.memory.write(0x100, 0x42)
        setup.registers.address = 0x100

        val instruction = ReadInstruction(0x33.toByte(), 0x00)
        instruction.execute(setup.registers, setup.memory, setup.screen, setup.keyboard)

        assertEquals(0x42, setup.registers.general[3])
    }

    @Test
    fun testWriteInstruction() {
        val setup = setup()
        setup.registers.general[5] = 0x77
        setup.registers.address = 0x200

        val instruction = WriteInstruction(0x45.toByte(), 0x00)
        instruction.execute(setup.registers, setup.memory, setup.screen, setup.keyboard)

        assertEquals(0x77, setup.memory.read(0x200))
    }

    @Test
    fun testSwitchMemoryInstruction() {
        val setup = setup()
        assertFalse(setup.memory.memoryFlag)

        val instruction = SwitchMemoryInstruction(0x70.toByte(), 0x00)
        instruction.execute(setup.registers, setup.memory, setup.screen, setup.keyboard)

        assertTrue(setup.memory.memoryFlag)
    }

    @Test
    fun testSetAInstruction() {
        val setup = setup()

        val instruction = SetAInstruction(0xA2.toByte(), 0x55.toByte())
        instruction.execute(setup.registers, setup.memory, setup.screen, setup.keyboard)

        assertEquals(0x255, setup.registers.address)
    }
}