package com.example.myapplication.utils
import com.example.myapplication.Task
class SampleData {
    val sampleTaskList = mutableListOf<Task>()

    init {
        sampleTaskList.add(
            Task
                (
                0,
                "Go to Market",
                "2020-08-14 04:45",
                "buy Ak-47",
                false
            )
        )
        sampleTaskList.add(
            Task
                (
                0,
                "Book Flight Ticket",
                "2021-08-14 08:45",
                "to LA",
                false
            )
        )
        sampleTaskList.add(
            Task
                (
                0,
                "Study",
                "2010-08-14 04:45",
                "maths and science",
                true
            )
        )
        sampleTaskList.add(
            Task
                (
                0,
                "Eat Breakfast",
                "2021-08-14 08:45",
                "bacon and eggs",
                true
            )
        )
        sampleTaskList.add(
            Task
                (
                0,
                "news dive",
                "2020-08-14 04:45",
                "and then eat pizza",
                false
            )
        )
        sampleTaskList.add(
            Task
                (
                0,
                "Eat water",
                "2021-01-14 04:45",
                "and then eat pizza",
                false
            )
        )
        sampleTaskList.add(
            Task
                (
                0,
                "go to the magical land of Narnia",
                "2021-01-14 04:45",
                "and then eat pizza",
                true
            )
        )
    }


}