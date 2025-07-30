package emulator.instructions

import emulator.Memory
import emulator.Registers
import emulator.Screen
import emulator.Keyboard

abstract class Instruction(val byte1: Byte, val byte2: Byte) {
    abstract fun execute(registers: Registers, memory: Memory, screen: Screen, keyboard: Keyboard)

    open fun shouldIncrementPC(): Boolean = true

    protected fun getRegisterX(): Int = (byte1.toInt() and 0x0F)
    protected fun getRegisterY(): Int = (byte2.toInt() and 0xF0) shr 4
    protected fun getRegisterZ(): Int = (byte2.toInt() and 0x0F)
    protected fun getByte(): Byte = byte2
    protected fun getAddress(): Int = ((byte1.toInt() and 0x0F) shl 8) or (byte2.toInt() and 0xFF)
}