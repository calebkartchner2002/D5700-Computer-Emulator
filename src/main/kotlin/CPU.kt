package emulator

import emulator.instructions.InstructionFactory
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

class CPU(
    private val memory: Memory,
    private val registers: Registers,
    private val screen: Screen,
    private val keyboard: Keyboard
) {
    private val instructionFactory = InstructionFactory()
    private val cpuExecutor = Executors.newSingleThreadScheduledExecutor()
    private val timerExecutor = Executors.newSingleThreadScheduledExecutor()

    private var cpuFuture: Future<*>? = null
    private var timerFuture: Future<*>? = null
    private var running = false

    fun start() {
        running = true

        cpuFuture = cpuExecutor.scheduleAtFixedRate(
            ::executeCycle,
            0,
            2,
            TimeUnit.MILLISECONDS
        )

        timerFuture = timerExecutor.scheduleAtFixedRate(
            ::decrementTimer,
            0,
            16,
            TimeUnit.MILLISECONDS
        )
    }

    fun stop() {
        running = false
        cpuFuture?.cancel(true)
        timerFuture?.cancel(true)
    }

    fun shutdown() {
        stop()
        cpuExecutor.shutdown()
        timerExecutor.shutdown()
    }

    private fun executeCycle() {
        if (!running) return

        try {
            val byte1 = memory.readInstruction(registers.programCounter)
            val byte2 = memory.readInstruction(registers.programCounter + 1)

            if (byte1.toInt() == 0 && byte2.toInt() == 0) {
                stop()
                return
            }

            val instruction = instructionFactory.createInstruction(byte1, byte2)
            if (instruction == null) {
                stop()
                return
            }

            instruction.execute(registers, memory, screen, keyboard)

            if (instruction.shouldIncrementPC()) {
                registers.incrementPC()
            }

            screen.render()

        } catch (e: Exception) {
            println("Error: ${e.message}")
            stop()
        }
    }

    private fun decrementTimer() {
        if (registers.timer > 0) {
            registers.timer = (registers.timer - 1).toByte()
        }
    }

    fun waitForCompletion() {
        try {
            cpuFuture?.get()
        } catch (_: Exception) {
        }
        shutdown()
    }
}