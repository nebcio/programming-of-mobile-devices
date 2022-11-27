package com.pum.studentcrime

import kotlin.random.Random

object CrimesList {
    val crimes = List(20) {
        Crime("Crime$it", "Student ...", Random.nextInt(0, 1000), Random.nextBoolean())
    }
}