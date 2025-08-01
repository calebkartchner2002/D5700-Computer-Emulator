package emulator.instructions

import emulator.Memory
import emulator.Registers
import emulator.Screen
import emulator.Keyboard

class AddInstruction(byte1: Byte, byte2: Byte) : Instruction(byte1, byte2) {
    override fun execute(registers: Registers, memory: Memory, screen: Screen, keyboard: Keyboard) {
        val x = getRegisterX()
        val y = getRegisterY()
        val z = getRegisterZ()

        val result = (registers.general[x].toInt() and 0xFF) + (registers.general[y].toInt() and 0xFF)
        registers.general[z] = result.toByte()
    }
}