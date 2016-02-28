package com.kazy.doclet.model

import java.util.*

data class ConstructorInfo(val name: String,
                           val accessLevel: AccessLevel,
                           val params: ArrayList<ParamInfo>,
                           val summary: String,
                           val isPublic: Boolean,
                           val isProtected: Boolean,
                           val isPrivate: Boolean) {
}