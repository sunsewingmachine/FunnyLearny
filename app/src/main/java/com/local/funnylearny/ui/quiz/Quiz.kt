package com.local.funnylearny.ui.quiz

data class Quiz(val question : String, val command : String, val rule : String,
                val optionOne : String, val optionTwo : String, val optionThree : String, val optionFour : String,
                val reasonOne : String, val reasonTwo : String, val reasonThree : String, val reasonFour : String,val answer : String)
