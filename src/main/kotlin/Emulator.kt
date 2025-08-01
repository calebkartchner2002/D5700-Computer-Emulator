package emulator

import java.io.File

class Emulator {
    private val memory = Memory()
    private val registers = Registers()
    private val screen = Screen()
    private val keyboard = Keyboard()
    private val cpu = CPU(memory, registers, screen, keyboard)

    fun loadROM(filepath: String) {
        val file = File(filepath)
        if (!file.exists()) {
            throw IllegalArgumentException("ROM file not found: $filepath")
        }

        val bytes = file.readBytes()
        memory.loadROM(bytes)
        println("Loaded ROM: ${file.name} (${bytes.size} bytes)")
    }

    fun run() {
        println("Starting D5700 Emulator...")
        screen.clear()
        screen.render()

        cpu.start()
        cpu.waitForCompletion()

        println("\nEmulation complete.")
    }

    fun reset() {
        registers.reset()
        screen.clear()
    }
}