package de.ka.chappted.commons.base

import android.os.Bundle
import android.support.v4.app.Fragment
import de.ka.chappted.Chappted

import de.ka.chappted.api.Repository
import javax.inject.Inject

/**
 * A base fragment.
 *
 * Created by Thomas Hofmann on 27.12.17.
 */
open class BaseFragment : Fragment() {

    @Inject lateinit var repository: Repository

    private var stopRepository: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Chappted.chapptedComponent.inject(this)
    }

    fun enableAutoRepositoryStopping(stopRepository: Boolean) {
        this.stopRepository = stopRepository
    }

    override fun onPause() {
        if (stopRepository) {
            repository.stop()
        }
        super.onPause()
    }
}
