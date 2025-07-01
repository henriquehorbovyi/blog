package dev.henriquehorbovyi.blog.navigation

import org.w3c.dom.Window

class ExternalNavigator(
    private val window: Window
) {

    fun open(url: String, newTab: Boolean = true) {
        window.open(url, if (newTab) "_blank" else "_self")
    }
}
