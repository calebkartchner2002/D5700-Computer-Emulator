package emulator

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class RegistersTest {
    @Test
    fun testGeneralRegisters() {
        val registers = Registers()
        registers.general[0] = 0x42
        registers.general[7] = 0x7F.toByte()

        assertEquals(0x42, registers.general[0])
        assertEquals(0x7F.toByte(), registers.general[7])
    }

    @Test
    fun testProgramCounterIncrement() {
        val registers = Registers()
        assertEquals(0, registers.programCounter)

        registers.incrementPC()
        assertEquals(2, registers.programCounter)

        registers.incrementPC()
        assertEquals(4, registers.programCounter)
    }

    @Test
    fun testSetPCEvenNumber() {
        val registers = Registers()
        registers.setPC(100)
        assertEquals(100, registers.programCounter)
    }

    @Test
    fun testSetPCOddNumberThrows() {
        val registers = Registers()
        assertThrows<IllegalArgumentException> {
            registers.setPC(101)
        }
    }

    @Test
    fun testSpecialRegisters() {
        val registers = Registers()

        registers.timer = 0x3C
        assertEquals(0x3C, registers.timer)

        registers.address = 0x1234
        assertEquals(0x1234, registers.address)
    }

    @Test
    fun testReset() {
        val registers = Registers()
        registers.general[3] = 0x55
        registers.programCounter = 100
        registers.timer = 0x20
        registers.address = 0x500

        registers.reset()

        assertEquals(0, registers.general[3])
        assertEquals(0, registers.programCounter)
        assertEquals(0, registers.timer)
        assertEquals(0, registers.address)
    }
}