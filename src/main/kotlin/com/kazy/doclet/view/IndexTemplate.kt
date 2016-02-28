package com.kazy.doclet.view

import com.github.jknack.handlebars.Handlebars
import com.kazy.doclet.model.RootInfo

object IndexTemplate {

    fun render(handlebars: Handlebars, rootInfo: RootInfo) {
        val template = handlebars.compile("index");
        System.out.println(template.apply(rootInfo));
    }

}
