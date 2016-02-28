package com.kazy.doclet.model

class RootInfo(val packageNameList: List<PackageInfo>,
               val allClassList: List<ClassInfo>,
               val classList: List<ClassInfo>,
               val hasClass: Boolean,
               val abstractClassList: List<ClassInfo>,
               val hasAbstuctClass: Boolean,
               val enumList: List<ClassInfo>,
               val hasEnum: Boolean,
               val exceptionList: List<ClassInfo>,
               val hasException: Boolean
               ) {
}