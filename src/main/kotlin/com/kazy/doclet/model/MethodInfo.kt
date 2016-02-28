package com.kazy.doclet.model

import java.util.*

data class MethodInfo(val name: String,
                      val accessLevel: AccessLevel,
                      val returnType: String,
                      val params: ArrayList<ParamInfo>,
                      val summary: String,
                      val isPublic: Boolean,
                      val isProtected: Boolean,
                      val isPrivate: Boolean) {
}
