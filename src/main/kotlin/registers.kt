package emulator

class Registers {
    val general = ByteArray(8)
    var programCounter: Int = 0
    val timer = java.util.concurrent.atomic.AtomicInteger(0)
    var address: Int = 0

    fun reset() {
        general.fill(0)
        programCounter = 0
        timer.set(0)
        address = 0
    }

    fun incrementPC() {
        programCounter += 2
    }

    fun setPC(value: Int) {
        if (value % 2 != 0) {
            throw IllegalArgumentException("Program counter must be even")
        }
        programCounter = value
    }
}