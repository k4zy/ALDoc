package com.kazy.doclet.view

import com.github.jknack.handlebars.Handlebars
import com.kazy.doclet.model.ClassInfo
import com.kazy.doclet.model.RootInfo
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter

object ClassTemplate {

    fun render(handlebars: Handlebars, classInfo: ClassInfo, rootInfo: RootInfo) {
        val packageDepth = classInfo.packageName.split(".").size
        val assetsPathPrefix = Array(packageDepth) { i -> "../" }.reduce { s1, s2 -> s1 + s2 }
        val dirPath = "doc/${classInfo.packagePath}"
        val dirs = File(dirPath)
        dirs.mkdirs()
        val file = File("$dirPath/${classInfo.name}.html");
        file.delete();
        file.createNewFile();
        val pw = PrintWriter(BufferedWriter(FileWriter(file)));
        val template = handlebars.compile("class");
        template.apply(ClassPack(classInfo, rootInfo, assetsPathPrefix), pw)
        pw.close()
    }

    class ClassPack(val classInfo: ClassInfo, val rootInfo: RootInfo, val assetsPathPrefix: String)

}