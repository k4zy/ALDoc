package com.kazy.doclet.view

import org.apache.commons.io.FileUtils
import java.io.File

object AssetsTransporter {
    fun copy() {
        val filePath = arrayOf(
                "css/class.css",
                "css/perfect-scrollbar.css",
                "css/prettify.css",
                "js/perfect-scrollbar.jquery.js",
                "js/jquery-1.11.3.min.js",
                "js/prettify.js"
        )
        filePath.forEach { path ->
            val url = javaClass.getResource("/template/assets/$path")
            val dest = File("doc/assets/$path")
            FileUtils.copyURLToFile(url, dest);
        }
    }
}