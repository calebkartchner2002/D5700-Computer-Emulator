package emulator

class Memory {
    private val ram = ByteArray(4096)
    private val rom = ByteArray(4096)
    var memoryFlag = false

    fun read(address: Int): Byte {
        return if (memoryFlag) {
            rom[address]
        } else {
            ram[address]
        }
    }

    fun write(address: Int, value: Byte) {
        if (memoryFlag) {
            throw IllegalStateException("Cannot write to ROM")
        } else {
            ram[address] = value
        }
    }

    fun loadROM(bytes: ByteArray) {
        bytes.copyInto(rom, 0, 0, minOf(bytes.size, 4096))
    }

    fun toggleMemoryFlag() {
        memoryFlag = !memoryFlag
    }
}