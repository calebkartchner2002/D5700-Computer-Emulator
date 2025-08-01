package emulator.instructions

import emulator.Memory
import emulator.Registers
import emulator.Screen
import emulator.Keyboard

class ConvertToBase10Instruction(byte1: Byte, byte2: Byte) : Instruction(byte1, byte2) {
    override fun execute(registers: Registers, memory: Memory, screen: Screen, keyboard: Keyboard) {
        val registerIndex = getRegisterX()
        val value = registers.general[registerIndex].toInt() and 0xFF

        val hundreds = value / 100
        val tens = (value % 100) / 10
        val ones = value % 10

        memory.write(registers.address, hundreds.toByte())
        memory.write(registers.address + 1, tens.toByte())
        memory.write(registers.address + 2, ones.toByte())
    }
}