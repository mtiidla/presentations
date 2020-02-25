@file:Suppress("unused")

package ee.mtiida.meetups.kotlincph

/**
 * 1. Recommendation: Always exhaust when statements
 * 2. Recommendation: Always exhaust when statements for unsupported branches
 * 3. Opinion: Do not use when statement as a glorified if statement
 */




data class Asset(
    val title: String = "Pulp Fiction",
    val type: AssetType = AssetType.MOVIE,
    val coverImageUrl: String = "https://img.mov/pulp_fiction_cover.png",
    val backdropImageUrl: String = "https://img.mov/pulp_fiction_1920x1080.png"
)

enum class AssetType {
    MOVIE,
    TV_SHOW,
    TV_PROGRAM
}

fun displayAssetImageProblematic(asset: Asset) {
    when(asset.type) {
        AssetType.TV_SHOW -> loadImage(asset.backdropImageUrl)
        AssetType.TV_PROGRAM -> loadImage(asset.backdropImageUrl)
        AssetType.MOVIE -> loadImage(asset.coverImageUrl)
    }
}
















//region OPINIONATED SOLUTION #1 - Refactor into function return type

fun getAssetImage(asset: Asset) : String {
    return when(asset.type) {
        AssetType.TV_SHOW -> asset.backdropImageUrl
        AssetType.TV_PROGRAM -> asset.backdropImageUrl
        AssetType.MOVIE -> asset.coverImageUrl
    }
}

fun displayAssetImage1(asset: Asset) {
    val imageUrl = getAssetImage(asset)
    loadImage(imageUrl)
}

//endregion
















//region OPINIONATED SOLUTION #2 - Store result into local variable

fun displayAssetImage2(asset: Asset) {
    @Suppress("UNUSED_VARIABLE") // exhaust when statement
    val loadImage = when (asset.type) {
        AssetType.TV_SHOW -> loadImage(asset.backdropImageUrl)
        AssetType.TV_PROGRAM -> loadImage(asset.backdropImageUrl)
        AssetType.MOVIE -> loadImage(asset.coverImageUrl)
    }
}

//endregion













//region OPINIONATED SOLUTION #3 - Create exhaustive extension function

fun displayAssetImage3(asset: Asset) {
    when (asset.type) {
        AssetType.TV_SHOW -> loadImage(asset.backdropImageUrl)
        AssetType.TV_PROGRAM -> loadImage(asset.backdropImageUrl)
        AssetType.MOVIE -> loadImage(asset.coverImageUrl)
    }.exhaustive()
}


fun Any?.exhaustive() = Unit

//endregion














//region OPINIONATED SOLUTION #4 - Create exhaust extension property

fun displayAssetImage4(asset: Asset) {
    when (asset.type) {
        AssetType.TV_SHOW -> loadImage(asset.backdropImageUrl)
        AssetType.TV_PROGRAM -> loadImage(asset.backdropImageUrl)
        AssetType.MOVIE -> loadImage(asset.coverImageUrl)
    }.exhaustive
}

// https://github.com/android/plaid/blob/a33ba90e51a4c48fe4acc7d883ed2b160e5b03b8/core/src/main/java/io/plaidapp/core/util/Extensions.kt
val <T> T.exhaustive: T
    get() = this

//endregion














//region OPINIONATED SOLUTION #5 - Cast result

fun displayAssetImage5(asset: Asset) {
    when (asset.type) {
        AssetType.TV_SHOW -> loadImage(asset.backdropImageUrl)
        AssetType.TV_PROGRAM -> loadImage(asset.backdropImageUrl)
        AssetType.MOVIE -> loadImage(asset.coverImageUrl)
    } as Any
}

//endregion














//region OPINIONATED SOLUTION #6 - Use result as class

fun displayAssetImage6(asset: Asset) {
    when (asset.type) {
        AssetType.TV_SHOW -> loadImage(asset.backdropImageUrl)
        AssetType.TV_PROGRAM -> loadImage(asset.backdropImageUrl)
        AssetType.MOVIE -> loadImage(asset.coverImageUrl)
    }::class
}

//endregion














//region OPINIONATED SOLUTION #7 - Get creative

fun displayAssetImage7(asset: Asset) {
    Do exhaustive when (asset.type) {
        AssetType.TV_SHOW -> loadImage(asset.backdropImageUrl)
        AssetType.TV_PROGRAM -> loadImage(asset.backdropImageUrl)
        AssetType.MOVIE -> loadImage(asset.coverImageUrl)
    }
}

// https://stackoverflow.com/questions/38169933/force-compilation-error-with-sealed-classes/50642387#50642387
object Do {
    inline infix fun<reified T> exhaustive(any: T?) = any
}

//endregion













// Opinion: Do not use WHEN as a glorified IF statement

private fun loadImage(imageUrl: String) {
    when {
        !imageUrl.startsWith("http") -> {
            loadImage("https://img.mov/$imageUrl")
        }
        else -> {
            // actually load image
        }
    }
}
