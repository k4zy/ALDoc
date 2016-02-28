package com.kazy.doclet.extension

import com.kazy.doclet.model.AccessLevel
import com.sun.javadoc.ClassDoc
import com.sun.javadoc.ConstructorDoc
import com.sun.javadoc.MethodDoc


fun MethodDoc.accessLevel(): AccessLevel {
    if (isPublic) {
        return AccessLevel.public
    } else if (isPrivate) {
        return AccessLevel.private
    } else if (isProtected) {
        return AccessLevel.protected
    } else {
        return AccessLevel.package_private
    }
}

fun ClassDoc.accessLevel(): AccessLevel {
    if (isPublic) {
        return AccessLevel.public
    } else if (isPrivate) {
        return AccessLevel.private
    } else if (isProtected) {
        return AccessLevel.protected
    } else {
        return AccessLevel.package_private
    }
}

fun ConstructorDoc.accessLevel(): AccessLevel {
    if (isPublic) {
        return AccessLevel.public
    } else if (isPrivate) {
        return AccessLevel.private
    } else if (isProtected) {
        return AccessLevel.protected
    } else {
        return AccessLevel.package_private
    }
}

