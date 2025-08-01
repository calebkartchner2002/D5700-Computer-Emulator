package emulator.instructions

import emulator.Memory
import emulator.Registers
import emulator.Screen
import emulator.Keyboard

class SkipEqualInstruction(byte1: Byte, byte2: Byte) : Instruction(byte1, byte2) {
    private var shouldSkip = false

    override fun execute(registers: Registers, memory: Memory, screen: Screen, keyboard: Keyboard) {
        val x = getRegisterX()
        val y = getRegisterY()

        if (registers.general[x] == registers.general[y]) {
            registers.incrementPC()
            shouldSkip = true
        }
    }

    override fun shouldIncrementPC(): Boolean = !shouldSkip
}