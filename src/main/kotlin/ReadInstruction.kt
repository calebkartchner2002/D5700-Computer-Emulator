package emulator.instructions

import emulator.Memory
import emulator.Registers
import emulator.Screen
import emulator.Keyboard

class ReadInstruction(byte1: Byte, byte2: Byte) : Instruction(byte1, byte2) {
    override fun execute(registers: Registers, memory: Memory, screen: Screen, keyboard: Keyboard) {
        val registerIndex = getRegisterX()
        val value = memory.read(registers.address)
        registers.general[registerIndex] = value
    }
}