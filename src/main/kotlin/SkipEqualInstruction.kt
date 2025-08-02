package emulator.instructions

import emulator.Memory
import emulator.Registers
import emulator.Screen
import emulator.Keyboard

class SkipEqualInstruction(byte1: Byte, byte2: Byte) : Instruction(byte1, byte2) {
    override fun execute(registers: Registers, memory: Memory, screen: Screen, keyboard: Keyboard) {
        val x = getRegisterX()
        val y = getRegisterY()

        // If equal, skip the *next* instruction: oldPC + 4.
        // Else do nothing; CPU will auto-increment +2 after this method.
        if (registers.general[x] == registers.general[y]) {
            registers.setPC(registers.programCounter + 4)
        }
    }
    // keep default shouldIncrementPC() behavior from base Instruction
}
