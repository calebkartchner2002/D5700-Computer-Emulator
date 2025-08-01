package emulator

import emulator.instructions.InstructionFactory
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class HelloLoaderTest {
    @Test
    fun testHelloOutStoreInstructions() {
        val memory = Memory()
        val registers = Registers()
        val screen = Screen()
        val keyboard = Keyboard()
        val factory = InstructionFactory()

        val helloProgram = byteArrayOf(
            0x00, 0x48,  // STORE 'H' in r0
            0x01, 0x45,  // STORE 'E' in r1
            0x02, 0x4C,  // STORE 'L' in r2
            0x03, 0x4C,  // STORE 'L' in r3
            0x04, 0x4F,  // STORE 'O' in r4
            0xF0.toByte(), 0x00,  // DRAW r0 at (0, 0)
            0xF1.toByte(), 0x01,  // DRAW r1 at (0, 1)
            0xF2.toByte(), 0x02,  // DRAW r2 at (0, 2)
            0xF3.toByte(), 0x03,  // DRAW r3 at (0, 3)
            0xF4.toByte(), 0x04   // DRAW r4 at (0, 4)
        )

        memory.loadROM(helloProgram)

        // Execute only the STORE instructions
        for (i in 0..4) {
            val pc = i * 2
            val byte1 = memory.readInstruction(pc)
            val byte2 = memory.readInstruction(pc + 1)

            val instruction = factory.createInstruction(byte1, byte2)
            assertNotNull(instruction, "Failed to create instruction at PC=$pc")
            instruction.execute(registers, memory, screen, keyboard)
        }

        // Verify register contents
        assertEquals('H', registers.general[0].toChar())
        assertEquals('E', registers.general[1].toChar())
        assertEquals('L', registers.general[2].toChar())
        assertEquals('L', registers.general[3].toChar())
        assertEquals('O', registers.general[4].toChar())
    }

    @Test
    fun testHelloOutDrawInstructions() {
        val memory = Memory()
        val registers = Registers()
        val screen = Screen()
        val keyboard = Keyboard()
        val factory = InstructionFactory()

        // Set up registers with ASCII values
        registers.general[0] = 'H'.code.toByte()
        registers.general[1] = 'E'.code.toByte()
        registers.general[2] = 'L'.code.toByte()
        registers.general[3] = 'L'.code.toByte()
        registers.general[4] = 'O'.code.toByte()

        // Test each DRAW instruction
        val drawInstructions = listOf(
            byteArrayOf(0xF0.toByte(), 0x00),  // DRAW r0 at (0, 0)
            byteArrayOf(0xF1.toByte(), 0x01),  // DRAW r1 at (0, 1)
            byteArrayOf(0xF2.toByte(), 0x02),  // DRAW r2 at (0, 2)
            byteArrayOf(0xF3.toByte(), 0x03),  // DRAW r3 at (0, 3)
            byteArrayOf(0xF4.toByte(), 0x04)   // DRAW r4 at (0, 4)
        )

        for ((index, bytes) in drawInstructions.withIndex()) {
            val instruction = factory.createInstruction(bytes[0], bytes[1])
            assertNotNull(instruction, "Failed to create DRAW instruction $index")
            instruction.execute(registers, memory, screen, keyboard)
        }

        // Verify screen contents
        assertEquals('H', screen.getChar(0, 0))
        assertEquals('E', screen.getChar(0, 1))
        assertEquals('L', screen.getChar(0, 2))
        assertEquals('L', screen.getChar(0, 3))
        assertEquals('O', screen.getChar(0, 4))
    }

    @Test
    fun testCompleteHelloOutProgram() {
        val memory = Memory()
        val registers = Registers()
        val screen = Screen()
        val keyboard = Keyboard()
        val factory = InstructionFactory()

        val helloProgram = byteArrayOf(
            0x00, 0x48,  // STORE 'H' in r0
            0x01, 0x45,  // STORE 'E' in r1
            0x02, 0x4C,  // STORE 'L' in r2
            0x03, 0x4C,  // STORE 'L' in r3
            0x04, 0x4F,  // STORE 'O' in r4
            0xF0.toByte(), 0x00,  // DRAW r0 at (0, 0)
            0xF1.toByte(), 0x01,  // DRAW r1 at (0, 1)
            0xF2.toByte(), 0x02,  // DRAW r2 at (0, 2)
            0xF3.toByte(), 0x03,  // DRAW r3 at (0, 3)
            0xF4.toByte(), 0x04,  // DRAW r4 at (0, 4)
            0x00, 0x00   // HALT
        )

        memory.loadROM(helloProgram)

        // Execute all instructions
        var pc = 0
        var instructionCount = 0

        while (pc < helloProgram.size && instructionCount < 20) {
            val byte1 = memory.readInstruction(pc)
            val byte2 = memory.readInstruction(pc + 1)

            if (byte1.toInt() == 0 && byte2.toInt() == 0) {
                break
            }

            val instruction = factory.createInstruction(byte1, byte2)
            assertNotNull(instruction)

            instruction.execute(registers, memory, screen, keyboard)

            if (instruction.shouldIncrementPC()) {
                pc += 2
            } else {
                pc = registers.programCounter
            }

            instructionCount++
        }

        // Verify we executed 10 instructions (5 STORE + 5 DRAW)
        assertEquals(10, instructionCount)

        // Verify screen shows "HELLO"
        assertEquals('H', screen.getChar(0, 0))
        assertEquals('E', screen.getChar(0, 1))
        assertEquals('L', screen.getChar(0, 2))
        assertEquals('L', screen.getChar(0, 3))
        assertEquals('O', screen.getChar(0, 4))
    }
}