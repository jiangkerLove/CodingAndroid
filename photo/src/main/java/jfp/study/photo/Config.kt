package jfp.study.photo

object Config {

    private const val BASE_URL: String = BuildConfig.BASE_PHOTO_URL

    val PHOTO_MAX_INDEX: Int = BuildConfig.PHOTO_MAX_INDEX

    fun getPhotoUrl(index: Int): String {
        return String.format(BASE_URL, index)
    }
}