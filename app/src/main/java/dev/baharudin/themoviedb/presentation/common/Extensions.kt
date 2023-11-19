package dev.baharudin.themoviedb.presentation.common

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Suppress("SpellCheckingInspection")
fun String.toDate(
    format: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
    locale: Locale = Locale.getDefault(),
    timeZone: TimeZone = TimeZone.getTimeZone("UTC")
): Date? {
    val parser = SimpleDateFormat(format, locale)
    parser.timeZone = timeZone
    return parser.parse(this)
}

fun Date.toString(
    format: String,
    locale: Locale = Locale.getDefault(),
    timeZone: TimeZone = TimeZone.getTimeZone("UTC")
): String {
    val formatter = SimpleDateFormat(format, locale)
    formatter.timeZone = timeZone
    return formatter.format(this)
}

fun String.toImageUrl(): String = "https://image.tmdb.org/t/p/original$this"