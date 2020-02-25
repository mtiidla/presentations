@file:Suppress("unused", "UNUSED_VARIABLE")

package ee.mtiida.meetups.kotlincph

import android.view.inputmethod.EditorInfo
import android.widget.EditText
import io.reactivex.rxjava3.core.Single

/**
 * 1. Recommendation: Avoid folded (elvis) return statements
 * 2. Suggestion: Use explicit return type where readability suffers
 * 3. Rule: Always declare return type specifically for public functions
 */








//region Recommendation: Avoid folded (elvis) return statements

fun loadImageSafe(imageUrl: String?) {

    val sanitizedUrl = sanitizeImageUrl(imageUrl)
    if (sanitizedUrl == null) {
        return
    }

    loadImage(sanitizedUrl)
}

private fun sanitizeImageUrl(imageUrl: String?): String? {
    if (imageUrl?.startsWith("http") == false) {
        return "https://img.mov/$imageUrl"
    }
    return imageUrl
}

private fun loadImage(imageUrl: String) {}

// endregion













//region Suggestion: Use explicit return type where readability suffers
// https://proandroiddev.com/why-kotlin-sucks-ab27a6b15cb6
fun setOnKeyboardSubmitListener(editText: EditText, block: () -> Unit) {
    editText.setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            block()
            true
        }
        false

    }
}

//endregion













//region Rule: Always declare return type specifically for public functions

interface MovieDao {

    fun moviesByYear(year: Int) : Single<List<Movie>>
}


class MovieRepo(private val movieDao: MovieDao) {

    fun searchByYear(year: Int) = movieDao.moviesByYear(year)

//    fun moviesByYear(year: Int) = movieDao.moviesByYear(year)
}

class SearchViewModel(private val movieRepo: MovieRepo) {

    fun searchByYear(year: Int) {
        movieRepo.searchByYear(year)
            .subscribe(
                { movies -> /* handle movies only once! */ },
                { error -> /* handle error */ }
            )
    }
}





//class MoviesViewModel(private val movieRepo: MovieRepo) {
//
//    fun observeMoviesByYear(year: Int) {
//        movieRepo.moviesByYear(year)
//            .subscribe(
//                { movies -> /* handle movies multiple times */ },
//                { error -> /* handle error */ }
//            )
//    }
//}

//endregion

