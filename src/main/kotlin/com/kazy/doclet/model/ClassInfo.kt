package com.kazy.doclet.model

class ClassInfo(val name: String,
                val packageName: String,
                val packagePath: String,
                val accessLevel: AccessLevel,

                val publicConstructorList: List<ConstructorInfo>,
                val hasPublicConstructor: Boolean,
                val protectedConstructorList: List<ConstructorInfo>,
                val hasProtectedConstructor: Boolean,
                val privateConstructorList: List<ConstructorInfo>,
                val hasPrivateConstructor: Boolean,

                val publicMethodList: List<MethodInfo>,
                val hasPublicMethod: Boolean,
                val protectedMethodList: List<MethodInfo>,
                val hasProtectedMethod: Boolean,
                val privateMethodList: List<MethodInfo>,
                val hasPrivateMethod: Boolean,

                val summary: String,
                val isClass: Boolean,
                val isInterface: Boolean,
                val isException: Boolean,
                val isEnum: Boolean,
                val isAbstract: Boolean) {
}
