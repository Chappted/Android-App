package de.ka.chapptedapi

import de.ka.chapptedapi.repository.ChapptedRepository
import de.ka.chapptedapi.repository.Repository

object ChapptedApi {

    var repository: Repository? = null

    /**
     * Initializes the chappted api module
     */
    fun init(url: String = BuildConfig.BASE_URL) {
        repository = ChapptedRepository(baseUrl = url)
    }


}