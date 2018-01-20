package de.ka.chappted.auth

import io.reactivex.subjects.PublishSubject

/**
 * A user manager.
 */
class UserManager {

    var userJustLoggedIn: PublishSubject<Boolean?> = PublishSubject.create()
}