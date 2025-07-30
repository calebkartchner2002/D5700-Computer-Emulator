package emulator

class Keyboard {
    fun readHexInput(): Byte {
        print("Enter hex digit (0-F): ")
        val input = readLine()?.uppercase()?.trim() ?: ""

        return when {
            input.isEmpty() -> 0
            input.length >= 2 -> {
                try {
                    input.substring(0, 2).toInt(16).toByte()
                } catch (e: NumberFormatException) {
                    0
                }
            }
            else -> {
                try {
                    input.toInt(16).toByte()
                } catch (e: NumberFormatException) {
                    0
                }
            }
        }
    }
}