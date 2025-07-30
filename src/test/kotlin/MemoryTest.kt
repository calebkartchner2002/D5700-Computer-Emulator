package emulator

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MemoryTest {
    @Test
    fun testRAMReadWrite() {
        val memory = Memory()
        memory.write(0x100, 0x42)
        assertEquals(0x42, memory.read(0x100))
    }

    @Test
    fun testROMLoad() {
        val memory = Memory()
        val romData = byteArrayOf(0x12, 0x34, 0x56, 0x78)
        memory.loadROM(romData)

        memory.toggleMemoryFlag()
        assertEquals(0x12, memory.read(0))
        assertEquals(0x34, memory.read(1))
        assertEquals(0x56, memory.read(2))
        assertEquals(0x78, memory.read(3))
    }

    @Test
    fun testROMWriteProtection() {
        val memory = Memory()
        memory.toggleMemoryFlag()

        assertThrows<IllegalStateException> {
            memory.write(0, 0x42)
        }
    }

    @Test
    fun testMemoryFlagToggle() {
        val memory = Memory()
        assertFalse(memory.memoryFlag)

        memory.toggleMemoryFlag()
        assertTrue(memory.memoryFlag)

        memory.toggleMemoryFlag()
        assertFalse(memory.memoryFlag)
    }

    @Test
    fun testLargeROMLoad() {
        val memory = Memory()
        val largeROM = ByteArray(5000) { it.toByte() }
        memory.loadROM(largeROM)

        memory.toggleMemoryFlag()
        assertEquals(4095.toByte(), memory.read(4095))
    }
}