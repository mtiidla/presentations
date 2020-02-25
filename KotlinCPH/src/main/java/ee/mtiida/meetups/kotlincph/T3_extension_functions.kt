@file:Suppress("unused", "LiftReturnOrAssignment", "RedundantIf", "UNUSED_VARIABLE")

package ee.mtiida.meetups.kotlincph

import android.widget.TextView
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.internal.canParseAsIpAddress

/**
 * 1. Opinion: Limit introducing public extension functions
 * 2. Suggestion: Avoid introducing extension functions on primitive types
 * 3. Suggestion: Follow proposed flow-chart before adding extension function
 */






data class Movie(
    val title: String = "Pulp fiction",
    val genres: List<String> = listOf("Drama", "Crime"),
    val year: Int = 1994
)












//region Wrong use of extension function

fun storeMovieInCache(movie: Movie) {

    // Opinion: Don't do this!
    movie.save()

    // Opinion: Prefer this
    movieRepository.save(movie)
}

fun Movie.save() {
    // save movie via global reference to a storage mechanism
}

class MovieRepository {
    fun save(movie: Movie) {}
}


private lateinit var movieRepository: MovieRepository

//endregion












//region Questionable use of extension function in owned domain

fun displayMovieSubtitle(textView: TextView, movie: Movie) {

    textView.text = movie.subtitle()

    textView.text = MovieFormatter.subtitle(movie)
}

/**
 * Example subtitle "Drama, Crime | 1994"
 */
fun Movie.subtitle(): String {
    return "${genres.joinToString()} | $year"
}

object MovieFormatter {

    fun subtitle(movie: Movie): String = with(movie) {
        return "${genres.joinToString()} | $year"
    }

}

// endregion













//region Questionable use of extension function in public namespace

fun trashPublicNamespaceInDefaultLibrary() {

    "12345".toInt()

    NumberParser.parseAsInt("12345")

}

fun trashPublicNamespaceInThirdPartyLibrary() {

    "application/json".toMediaType()

    MediaType.parse("application/json")

    "okhttp".canParseAsIpAddress()
}

object NumberParser {

    fun parseAsInt(input: String) : Int = input.toInt()
}

//endregion













// Suggestion: Flow chart to consider before adding an extension function
fun shouldAddExtensionFunction(): Boolean {
    /**
     * Be absolutely sure that it makes perfect sense
     * for the entire application and business domain
     * that the function makes sense in the class.
     */
    if (functionBelongsToTheClass()) {
        if (weOwnTheClass()) {
            /**
             * No reason for extension function
             * Add function directly to the class
             */
            return false
        } else {
            /**
             * Cannot add function to the class
             * perfect reason to create extension function
             */
            return true
        }
    } else {
        /**
         * Create a service object with a function that
         * uses the class and operates on it, this way
         * we get improved readability and discoverability
         */
        return false
    }
}





private fun functionBelongsToTheClass(): Boolean {
    TODO("not implemented")
}

private fun weOwnTheClass(): Boolean {
    TODO("not implemented")
}
