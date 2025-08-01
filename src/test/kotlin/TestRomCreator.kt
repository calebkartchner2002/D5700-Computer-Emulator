package emulator

import java.io.File

object TestRomCreator {
    fun createHelloWorldRom(filepath: String) {
        val program = mutableListOf<Byte>()

        program.add(0x00); program.add('H'.code.toByte())
        program.add(0xF0.toByte()); program.add(0x00)

        program.add(0x00); program.add('E'.code.toByte())
        program.add(0x01); program.add(0x01)
        program.add(0xF0.toByte()); program.add(0x01)

        program.add(0x00); program.add('L'.code.toByte())
        program.add(0x01); program.add(0x02)
        program.add(0xF0.toByte()); program.add(0x02)

        program.add(0x01); program.add(0x03)
        program.add(0xF0.toByte()); program.add(0x03)

        program.add(0x00); program.add('O'.code.toByte())
        program.add(0x01); program.add(0x04)
        program.add(0xF0.toByte()); program.add(0x04)

        program.add(0x00); program.add(0x00)

        File(filepath).writeBytes(program.toByteArray())
    }

    fun createCounterRom(filepath: String) {
        val program = mutableListOf<Byte>()

        program.add(0x00); program.add(0x30)

        program.add(0xE0.toByte()); program.add(0x10.toByte())

        program.add(0x02); program.add(0x00)
        program.add(0xF1.toByte()); program.add(0x00)

        program.add(0x00); program.add(0x01)
        program.add(0x10.toByte()); program.add(0x00)

        program.add(0x00); program.add(0x0A)
        program.add(0x90.toByte()); program.add(0x01)

        program.add(0x50.toByte()); program.add(0x02.toByte())

        program.add(0x00); program.add(0x00)

        File(filepath).writeBytes(program.toByteArray())
    }
}

fun main() {
    TestRomCreator.createHelloWorldRom("hello.rom")
    TestRomCreator.createCounterRom("counter.rom")
    println("Test ROMs created: hello.rom and counter.rom")
}