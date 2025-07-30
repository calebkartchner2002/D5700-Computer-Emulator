package emulator

class Screen {
    private val buffer = Array(8) { CharArray(8) { ' ' } }

    fun draw(char: Char, row: Int, col: Int) {
        if (row !in 0..7 || col !in 0..7) {
            throw IllegalArgumentException("Invalid screen coordinates")
        }
        buffer[row][col] = char
    }

    fun clear() {
        for (row in 0..7) {
            for (col in 0..7) {
                buffer[row][col] = ' '
            }
        }
    }

    fun render() {
        print("\u001b[2J\u001b[H")
        println("╔════════╗")
        for (row in 0..7) {
            print("║")
            for (col in 0..7) {
                print(buffer[row][col])
            }
            println("║")
        }
        println("╚════════╝")
    }

    fun getChar(row: Int, col: Int): Char {
        if (row !in 0..7 || col !in 0..7) {
            throw IllegalArgumentException("Invalid screen coordinates")
        }
        return buffer[row][col]
    }
}