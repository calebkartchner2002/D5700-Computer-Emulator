package emulator

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.assertEquals

class EmulatorIntegrationTest {
    @Test
    fun testSimpleProgram() {
        val memory = Memory()
        val registers = Registers()
        val screen = Screen()
        val keyboard = Keyboard()

        val program = byteArrayOf(
            0x00.toByte(), 0x42,
            0x01.toByte(), 0x55,
            0x10.toByte(), 0x12,
            0x00.toByte(), 0x00
        )
        memory.loadROM(program)

        val cpu = CPU(memory, registers, screen, keyboard)

        assertDoesNotThrow {
            for (i in 0..2) {
                val byte1 = memory.readInstruction(registers.programCounter)
                val byte2 = memory.readInstruction(registers.programCounter + 1)

                if (byte1.toInt() == 0 && byte2.toInt() == 0) break

                val factory = emulator.instructions.InstructionFactory()
                val instruction = factory.createInstruction(byte1, byte2)

                instruction?.execute(registers, memory, screen, keyboard)

                if (instruction?.shouldIncrementPC() == true) {
                    registers.incrementPC()
                }
            }
        }

        assertEquals(0x42.toByte(), registers.general[0])
        assertEquals(0x55.toByte(), registers.general[1])
        assertEquals(0x97.toByte(), registers.general[2])
    }

    @Test
    fun testMemoryOperations() {
        val memory = Memory()
        val registers = Registers()
        val screen = Screen()
        val keyboard = Keyboard()

        val program = byteArrayOf(
            0x00.toByte(), 0x99.toByte(),
            0xA1.toByte(), 0x00,
            0x40.toByte(), 0x00,
            0x00.toByte(), 0x00
        )
        memory.loadROM(program)

        val factory = emulator.instructions.InstructionFactory()

        factory.createInstruction(0x00.toByte(), 0x99.toByte())
            ?.execute(registers, memory, screen, keyboard)
        registers.incrementPC()

        factory.createInstruction(0xA1.toByte(), 0x00)
            ?.execute(registers, memory, screen, keyboard)
        registers.incrementPC()

        factory.createInstruction(0x40.toByte(), 0x00)
            ?.execute(registers, memory, screen, keyboard)

        assertEquals(0x99.toByte(), memory.read(0x100))
    }

    @Test
    fun testScreenDrawing() {
        val memory = Memory()
        val registers = Registers()
        val screen = Screen()
        val keyboard = Keyboard()

        registers.general[0] = 'X'.code.toByte()
        registers.general[1] = 3
        registers.general[2] = 4

        val factory = emulator.instructions.InstructionFactory()
        val drawInstruction = factory.createInstruction(0xF0.toByte(), 0x12.toByte())

        drawInstruction?.execute(registers, memory, screen, keyboard)

        assertEquals('X', screen.getChar(3, 4))
    }
}