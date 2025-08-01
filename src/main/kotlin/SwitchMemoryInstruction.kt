package emulator.instructions

import emulator.Memory
import emulator.Registers
import emulator.Screen
import emulator.Keyboard

class SwitchMemoryInstruction(byte1: Byte, byte2: Byte) : Instruction(byte1, byte2) {
    override fun execute(registers: Registers, memory: Memory, screen: Screen, keyboard: Keyboard) {
        memory.toggleMemoryFlag()
    }
}