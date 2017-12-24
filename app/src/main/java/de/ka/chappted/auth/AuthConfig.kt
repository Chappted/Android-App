package de.ka.chappted.auth

/**
 * A default auth config.
 *
 * Created by Thomas Hofmann on 18.12.17.
 */
data class AuthConfig(var accountName: String?,
                      val isAddingNew: Boolean = false){
}