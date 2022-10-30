package com.tlopez.telloShare.architecture

abstract class BaseRoutingViewModel<
        TypeOfViewState : ViewState,
        TypeOfViewEvent : ViewEvent,
        TypeOfDestination : Destination>
    : BaseViewModel<TypeOfViewState, TypeOfViewEvent>() {

    private var router: Router<TypeOfDestination>? = null

    fun attachRouter(router: Router<TypeOfDestination>) {
        this.router = router
        onRouterAttached()
    }

    fun routeTo(destination: TypeOfDestination) {
        router?.routeTo(destination)
    }

    /** Optional in implementing ViewModel **/
    open fun onRouterAttached() {}
}