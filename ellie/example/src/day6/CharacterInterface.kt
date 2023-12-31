package day6
import atomictest.*

abstract class Character(val name: String) {
    abstract fun play(): String
}

interface Fighter {
    fun fight() = "Fight!"
}

interface Magician {
    fun doMagic() = "Magic!"
}

interface Flyer {
    fun fly() = "Fly!"
}

class Warrior :
    Character("Warrior"), Fighter {
    override fun play() = fight()
}

open class Elf(name: String = "Elf") :
    Character(name), Magician {
    override fun play() = doMagic()
}

class FightingElf :
    Elf("FightingElf"), Fighter {
    override fun play() =
        super.play() + fight()
}

// TODO class Dragon
class Dragon : Character("Dragon"), Flyer {
    override fun play(): String = super.fly()

}


// TODO class Wizard
class Wizard : Character("Wizard"), Magician, Flyer {
    override fun play(): String = super.doMagic() + super.fly()

}

fun Character.playTurn() = name + ": " + play()

fun main() {
    listOf(
        Warrior(),
        Elf(),
        FightingElf(),
        Dragon(),
        Wizard()
    ).map { it.playTurn() } eq
            "[Warrior: Fight!, Elf: Magic!, " +
            "FightingElf: Magic!Fight!, " +
            "Dragon: Fly!, Wizard: Magic!Fly!]"
}