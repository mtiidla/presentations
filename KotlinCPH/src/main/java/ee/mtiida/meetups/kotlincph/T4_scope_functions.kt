@file:Suppress("unused", "UNUSED_VARIABLE")

package ee.mtiida.meetups.kotlincph

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.threeten.bp.LocalDateTime

/**
 * 1. Opinion: Avoid overusing scope functions
 * 2. Opinion: Do not use .let as glorified if statement
 * 3. Suggestion: Make sure you and your team understands the differences
 * 4. Opinion: Prefer .also over .apply
 * 5. Opinion: Prefer .let over .run
 */












//region Suggestion: Avoid overusing scope functions

fun createDrama() {

    // Example 1
    val drama1 = listOf("Drama").let {
        Movie(genres = it)
    }

    val genres = listOf("Drama")
    val drama2 = Movie(genres = genres)


    // Example 2
    val movie = Movie()
    movie.run {
        println(title)
        println(year)
    }

    println(movie.title)
    println(movie.year)

}

//endregion












//region Suggestion: Do not use .let as glorified if statement

fun isDramaLet(movie: Movie?): Boolean {
    return movie?.let {
        it.genres.any { it == "Drama" }
    } ?: false
}

fun isDrama(movie: Movie?): Boolean {
    if (movie == null) {
        return false
    }
    return movie.genres.any { it == "Drama" }
}

//endregion












//region Suggestion: Make sure you and your team understands the differences

fun quickReminderOfScopeFunctions() {

    // use to configure mutable objects

    val apply = "Hello".apply {
        reversed()
    } // == "Hello"

    val also = "Hello".also {
        it.reversed()
    } // == "Hello"


    // use to configure immutable objects

    val run = "Hello".run {
        reversed()
    } // == "olleH

    val let = "Hello".let {
        it.reversed()
    } // == "olleH

}

//endregion













//region Opinion: Prefer .also over .apply

class MovieListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.apply {
            setHasFixedSize(true)
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}

//endregion













//region Opinion: Prefer .let over .run

fun configureImmutableObject() {

    val tomorrowNextYear = LocalDateTime.now()
        .let {
            it.plusDays(1)
            it.plusYears(1)
        }
}

//endregion















class MovieAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("not implemented")
    }
    override fun getItemCount(): Int = 0
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }
}
