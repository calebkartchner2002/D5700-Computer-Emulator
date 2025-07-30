package emulator.instructions

class InstructionFactory {
    fun createInstruction(byte1: Byte, byte2: Byte): Instruction? {
        val opcode = (byte1.toInt() and 0xF0) shr 4

        return when (opcode) {
            0x0 -> StoreInstruction(byte1, byte2)
            0x5 -> JumpInstruction(byte1, byte2)
            else -> null
        }
    }
}