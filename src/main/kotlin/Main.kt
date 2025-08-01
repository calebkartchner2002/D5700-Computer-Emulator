package emulator

fun main() {
    val emulator = Emulator()

    print("Enter ROM file path: ")
    val filepath = readLine() ?: ""

    try {
        emulator.loadROM(filepath)
        emulator.run()
    } catch (e: Exception) {
        println("Error: ${e.message}")
    }
}