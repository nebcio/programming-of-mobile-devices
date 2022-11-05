package com.pum.physicsquiz

object Questions {
    val questions: List<Question>
        get() = listOf(
                Question("Sound does not create heat", false),
                Question("Venus is the only planet that spins in the opposite direction to Earth.", true),
                Question("Astronomical unit is a unit of length effectively equal to the average, or mean, distance between Earth and the Sun", true),
                Question("The unit of electric current is Amper", true),
                Question("The unit of magnetic flux density is Sigma", false),
                Question("Nucleus contains protons and neutrons", true),
                Question("Neutrons are neutral particles", false),
                Question("Electrons never change their shells", false),
                Question("Newton built the first practical reflecting telescope", true),
                Question("Gravity is not the same everywhere on Earth", true))

}