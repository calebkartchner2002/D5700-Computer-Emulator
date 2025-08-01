package emulator.instructions

import emulator.Memory
import emulator.Registers
import emulator.Screen
import emulator.Keyboard

class DrawInstruction(byte1: Byte, byte2: Byte) : Instruction(byte1, byte2) {
    override fun execute(registers: Registers, memory: Memory, screen: Screen, keyboard: Keyboard) {
        val charReg = getRegisterX()
        val row = (byte2.toInt() and 0xF0) ushr 4
        val col = byte2.toInt() and 0x0F

        val charValue = registers.general[charReg]
        val charInt = charValue.toInt() and 0xFF
        if (charInt > 0x7F) {
            throw IllegalStateException("ASCII value must be <= 0x7F")
        }

        if (row > 7 || col > 7) {
            throw IllegalArgumentException("Invalid screen coordinates")
        }

        screen.writeToFrameBuffer(charValue, row, col)
    }
}