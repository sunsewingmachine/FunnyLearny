package com.local.funnylearny.ui.quiz

data class Quiz(val question : String, val command : String, val rule : String,
                val option1 : String, val option2 : String, val option3 : String, val option4 : String,
                val reason1 : String, val reason2 : String, val reason3 : String, val reason4 : String,val answer : String)
