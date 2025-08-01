package emulator.instructions

import emulator.Memory
import emulator.Registers
import emulator.Screen
import emulator.Keyboard

class ConvertByteToAsciiInstruction(byte1: Byte, byte2: Byte) : Instruction(byte1, byte2) {
    override fun execute(registers: Registers, memory: Memory, screen: Screen, keyboard: Keyboard) {
        val srcReg = getRegisterX()
        val destReg = getRegisterY()

        val value = registers.general[srcReg].toInt() and 0x0F
        if (value > 0x0F) {
            throw IllegalStateException("Value must be <= 0xF")
        }

        val asciiValue = if (value <= 9) {
            '0'.code + value
        } else {
            'A'.code + (value - 10)
        }

        registers.general[destReg] = asciiValue.toByte()
    }
}