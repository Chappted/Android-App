package de.ka.chapptedapi.jlsapi

import de.ka.chapptedapi.ChapptedApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A web service callback.
 * <p>
 * Created by Thomas Hofmann on 12.06.18.
 */
abstract class JlsCallback<T> : Callback<T> {

    abstract fun onSuccess(response: JlsResponse<T>)

    abstract fun onFailed(error: JlsError?)

    var call: Call<*>? = null

    /**
     * Sets a call to enable the pending state. This will auto-add and auto-remove
     * the call when finished from the repository.
     * Useful for stopping or receiving all pending call of the
     * repository with [de.ka.chapptedapi.repository.Repository.pendingCalls].
     *
     * @param call the pending call
     */
    fun setOrigin(call: Call<*>?) {
        this.call = call

        if (call != null) {
            ChapptedApi.repository?.pendingCalls()?.add(call)
        }
    }

    override fun onResponse(call: Call<T>, response: Response<T>?) {

        dispose()

        if (response != null) {

            if (response.isSuccessful) {
                onSuccess(JlsResponse(response))

                return
            }
        }

        onFailed(null)
    }

    override fun onFailure(call: Call<T>, t: Throwable) {

        dispose()
        onFailed(null)
    }

    private fun dispose() {
        ChapptedApi.repository?.pendingCalls()?.remove(call)
    }

}
