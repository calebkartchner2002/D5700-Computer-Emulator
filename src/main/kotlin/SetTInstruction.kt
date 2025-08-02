package emulator.instructions

import emulator.Memory
import emulator.Registers
import emulator.Screen
import emulator.Keyboard

class SetTInstruction(byte1: Byte, byte2: Byte) : Instruction(byte1, byte2) {
    override fun execute(registers: Registers, memory: Memory, screen: Screen, keyboard: Keyboard) {
        // For SET_T, the timer value is in the lower nibble of byte1 and upper nibble of byte2
        val timerValue = ((byte1.toInt() and 0x0F) shl 4) or ((byte2.toInt() and 0xF0) shr 4)
        registers.timer.set(timerValue)

    }
}