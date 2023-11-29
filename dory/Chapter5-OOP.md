# OOP

### 55. 인터페이스

- 클래스가 무엇을 하는지 기술한다. (= 형태를 제시한다.)

```kotlin
interface Computer {
    fun prompt(): String
    fun calculateAnswer(): Int
}

class Desktop : Computer {
    override fun prompt(): String {TODO("Not yet implemented") }
    override fun calculateAnswer(): Int {TODO("Not yet implemented") }
}
```

- Computer는 prompt와 calculateAnswer()를 선언하지만 아무 구현도 제공하지 않는다.
- 이 인터페이스를 구현하는 클래스는 인터페이스가 선언한 모든 멤버를 구현하는 본문을 제공해야 한다.

##### SAM 변환

- 단일 추상 메서드 (Single Abstract Method) 인터페이스는 자바 개념으로, 자바에서는 멤버 함수를 '메서드'라고 부른다.
- SAM 인터페이스 구현 방식
  - 클래스를 통해 구현
  - 람다를 넘기는 방식으로 구현 -> SAM 변환이라고 한다

```kotlin
fun interface ZeroArg {
    fun f(): Int
}

// 클래스 통해 구현
class VerboseZero : ZeroArg {
    override fun f() = 11
}
val verboseZero = VerboseZero()

// 람다 구현
val samZero = ZeroArg { 11 }
```

- 람다를 SAM 인터페이스가 필요한 곳에 넘길 수도 있다.

```kotlin
fun interface Action {
    fun act()
}

fun delayAction(action: Action) {
    action.act()
}
```

### 56. 복잡한 생성자

- val이나 var은 파라미터 목록에 있는 파라미터에 붙이면, 그 파라미터를 프로퍼티로 만들어준다.

```kotlin
fun Alien(val name: String)

fun main() {
    val alien = Alien("Pencilvester")
    alien.name eq "Pencilvester"
}
```

- 생성 과정을 제어하고 싶으면 클래스 본문에 생성자 코드를 추가해라.

```kotlin
class Message(text: String) {
    private val content: String
    init {
        counter += 10
        content = "[$counter] $text"
    }
    override fun toString() = content
}

fun main() {
    val m1 = Message("big")
    m1 eq "[10] big"
    val m2 = Message("hi")
    m2 eq "[20] hi"
}
```

### 57. 부생성자

- 오버로드한 생성자를 <b>부생성자</b>라고 한다.

```kotlin
class WithSecondary(i: Int) {
    // 주생성자
    init {
        trace("Primary: $i")
    }
    // 부생성자
    constructor(c: Char): this(c-'A') {
        trace("Secondary: '$c'")
    }
  // 부생성자2
    constructor(s: String){
      this(s.first())
      trace("Secondary: '$c'")
    }
}
```

- 주생성자는 언제나 부생성자에 의해 직접 호출되거나 다른 부생성자 호출을 통해 간접적으로 호출되어야 한다.

### 58. 상속

- 상속: 기존 클래스를 재사용하면서 변경해 새로운 클래스를 만드는 매커니즘

```kotlin
// 상속가능 : open
open class Parent

clas Child : Parent()

// 상속 불가
final class Single
class AnotherSingle
```

- 함수 오버라이드하기

```kotlin
open class GreatApe {
  protected val energy = 0 // 외부 세계에 대해 닫혀 있고, 하위 클래스에서만 접근이나 오버라이드 가능
  open fun call() = "Hoo!"
  open fun eat() {
    energy += 10
  }
  fun climb(x: Int) {
    energy -= x
  }
  fun energyLevel() = "Energy: $energy"
}

class Bonobo: GreatApe() {
    override fun call() = "Eep!"
    override fun eat() {
        // 부모 클래스의 프로퍼티를 변경한다.
        energy += 10
        // 부모 클래스의 함수를 호출한다
        super.eat()
    }
    // 함수를 호출한다.
    fun run() = "Bonobo run"
}
```

### 59. 기반 클래스 초기화

- 부모 클래스에 생성자 파라미터가 있다면, 자식 클래스가 생성되는 동안 반드시 부모 클래스의 생성자 인자를 제공해야 한다.
- 자식 클래스는 부모 클래스의 부생성자를 호출할 수도 있다.

```kotlin
open  class GreatApe (
        val weight: Double,
        val age: Int
)

open class Bonobo(weight: Double, age: Int):
    GreatApe(weight, age)
```

### 60. 추상 클래스

- 클래스 멤버에서 본문이나 초기화를 제거하려면 abstract 변경자를 해당 멤버 앞에 붙여야 한다.

```kotlin
abstract class WithProperty {
    abstract val x: Int
}
```

- 인터페이스와 추상 클래스 차이점
  - 추상 클래스에는 상태가 있으나, 인터페이스에는 상태가 없다.
  - 상태: 프로퍼티 안에 저장된 데이터를 의미한다.

### 61. 업캐스트

- 업캐스트: 객체 참조를 받아서 그 객체의 기반 타입에 대한 참조처럼 취급하는 것

```kotlin
interface Shape {
    fun draw(): String
    fun erase(): String
}

class Circle: Shape {
    override fun draw(): String {TODO("Not yet implemented") }
    override fun erase(): String {TODO("Not yet implemented")}
}

class Square: Shape {
  override fun draw(): String {TODO("Not yet implemented") }
  override fun erase(): String {TODO("Not yet implemented")}
}

fun show(shape: Shape) {
    trace("")
}

fun main() {
    listOf(Circle(), Square()).forEach(::show) // 각 타입은 모두 shape 클래스의 객체처럼 취급 -> 업캐스트
}
```

<b>실제로 업캐스트를 사용하지 않는데 상속을 사용하는 거의 모든 경우는 상속을 잘못 사용하는 것이다!</b>

### 62. 다형성

- 다형성: 부모 클래스 참조가 자식 클래스의 인스턴스를 가리키는 경우 발생한다.

```kotlin
import kotlin.contracts.contract

abstract class Character(val name: String) {
    abstract fun play(): String
}

interface Fighter {
    fun fight() = "Fight"
}

interface Magician {
    fun doMagic() = "Magic!"
}

class Warrior :
    Character("Warrior"), Fighter {
        override fun play() = fight()
    }

open class Elf(name: String = "Elf"):
        Character(name), Magician {
            override fun play() = doMagic()
}

class FightingElf :
        Elf("FightingElf"), Fighter {
            override fun play() = super.play() + fight()
        }

fun Character.playTurn() =
        trace(name + ":" + play())

fun main() {
    val characters: List<Character> = listOf(
            Warrior(), Elf(), FightingElf()
    )
  characters.forEach { it.playTurn() }
  trace eq """
    Warrior: Fight!
    Elf: Magic!
    FightingElf: Magic!Fight!
  """
}
```

### 63. 합성

- 기좆 클래스의 객체를 새 클래스 안에 생성하는 좀 더 직접적인 접근 방법을 택할 수도 있다. -> 합성
- 합성을 쓸 경우는 기본 코드의 기능을 재사용하는 것이다.

```kotlin
// 상속: 집은 건물이다
// 합성: 부엌을 포함한다.
interface House: Building {
    val kitchen: Kitchen
}
```

### 64. 상속과 확장

- 기존 클래스를 새로운 목적으로 활용하기 위해 새로운 함수를 추가해야 할 때가 있다.
- 이때 기존 클래스를 변경할 수 없으면 새 함수를 추가하기 위해 상속을 사용해야 한다.
- 이로 인해 코드를 이해하고 유지 보수하기 어려워진다.

```kotlin
open class Heater {
    fun heat(temperature: Int) = "heating to $temperature"
}

fun warm(heater: Heater) {
    heater.heat(70) eq "heating to 70"
}

class HVAC : Heater() {
    fun cool(temperature: Int) = "cooling to $temperature"
}
```

### 65. 클래스 위임

- 클래스 위임은 상속과 합성의 중간 지점이다.
  - 새 클래스 안에 멤버 객체를 심고, 상속과 마찬가지로 심겨진 하위 객체의 인터페이스를 노출시킨다.

```kotlin
// 클래스 위임
interface AI
class A: AI

class B(val a: A) : AI by a
```

- 클래스 위임을 사용해 다중 클래스 상속을 흉내낼 수 있다.

### 66. 다운캐스트

- 다운 캐스트는 이전에 업캐스트 했던 객체의 구체적인 타입을 발견한다.
- 다운 캐스트는 실행 시점에 일어나며 실행 시점 타입 식별이라고도 한다.

### 67. 봉인된 클래스 
- sealed 키워드로 상속을 제한한 클래스를 봉인된 클래스라고 부른다.
  - sealed 클래스를 직접 상속한 하위 클래스는 반드시 기반 클래스와 같은 패키지와 모듈 안에 있어야 한다.
  - sealed 클래스는 tram 같은 새 하위 클래스를 도입했을 때 변경해야 하는 모든 지점을 표시해준다.

```kotlin
sealed class Transport 

data class Train(
        val line: String
) : Transport()

data class Bus(
        val number: String,
        val capacity: Int
) : Transport()

// else가 필요없어짐 -> sealed가 관리하기 때문에
fun travel(transport: Transport) = 
        when (transport) {
            is Train -> 
                "Train ${transport.line}"
            is Bus -> 
                "Bus ${transport.number}: " +
                        "size ${transport.capacity}"
        }

fun main() {
    listOf(Train("S1"), Bus("11", 90))
            .map(::travel) eq "[Train S1, Bus 11: size 90]"
}
```

- sealed 클래스는 기본적으로 하위 클래스가 모두 같은 파일 안에 정의되어야 한다는 제약이 가해진 abstract 클래스다.
- 그러나 직접적 상속이 아니면 다른 파일에 있을 수도 있음! 

##### 하위 클래스 열거하기
- Top의 직접적인 하위클래스만 나타남!
```kotlin
sealed class Top
class Middle1 : Top()
class Middle2 : Top()
open class Middle3 : Top()
class Bottom3 : Middle3()

fun main() {
    Top::class.sealedSubclasses
            .map { it.simpleName } eq
            "[Middle1, Middle2, Middle3]"
}
```
