package emulator.instructions

import emulator.Memory
import emulator.Registers
import emulator.Screen
import emulator.Keyboard

class DrawInstruction(byte1: Byte, byte2: Byte) : Instruction(byte1, byte2) {
    override fun execute(registers: Registers, memory: Memory, screen: Screen, keyboard: Keyboard) {
        val charReg = getRegisterX()
        val rowReg = getRegisterY()
        val colReg = getRegisterZ()

        val charValue = registers.general[charReg].toInt() and 0xFF
        if (charValue > 0x7F) {
            throw IllegalStateException("ASCII value must be <= 0x7F")
        }

        val row = registers.general[rowReg].toInt() and 0xFF
        val col = registers.general[colReg].toInt() and 0xFF

        screen.draw(charValue.toChar(), row, col)
    }
}