package emulator

class Screen {
    private val frameBuffer = ByteArray(64) { ' '.code.toByte() }

    fun writeToFrameBuffer(asciiValue: Byte, row: Int, col: Int) {
        if (row !in 0..7 || col !in 0..7) {
            throw IllegalArgumentException("Invalid screen coordinates")
        }
        val address = row * 8 + col
        frameBuffer[address] = asciiValue
    }

    fun clear() {
        frameBuffer.fill(' '.code.toByte())
    }

    fun render() {
        print("\u001b[H\u001b[2J")
        println("╔════════╗")
        for (row in 0..7) {
            print("║")
            for (col in 0..7) {
                val address = row * 8 + col
                val char = (frameBuffer[address].toInt() and 0xFF).toChar()
                print(char)
            }
            println("║")
        }
        println("╚════════╝")
    }

    fun getChar(row: Int, col: Int): Char {
        if (row !in 0..7 || col !in 0..7) {
            throw IllegalArgumentException("Invalid screen coordinates")
        }
        val address = row * 8 + col
        return (frameBuffer[address].toInt() and 0xFF).toChar()
    }

    fun readFrameBuffer(address: Int): Byte {
        if (address !in 0..63) {
            throw IllegalArgumentException("Invalid frame buffer address")
        }
        return frameBuffer[address]
    }
}