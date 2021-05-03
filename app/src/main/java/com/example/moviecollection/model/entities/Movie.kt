package com.example.moviecollection.model.entities

data class Movie(
        val title: String = "Побег из Шоушенка",
        val yearOfRelease: String = "1994",
        val genre: String = "Драма",
        val briefDescription: String = "Выдающаяся драма о силе таланта, важности дружбы, стремлении к свободе и Рите Хэйворт",
        val originCountry: String = "США",
        val director: String = "Фрэнк Дарабонт",
        val screenwriter: String = "Фрэнк Дарабонт, Стивен Кинг",
        val duration: Int = 142,
        val rating: Long = 761000
)
