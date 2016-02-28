package com.kazy.doclet

import com.github.jknack.handlebars.Handlebars
import com.github.jknack.handlebars.io.ClassPathTemplateLoader
import com.kazy.doclet.extension.accessLevel
import com.kazy.doclet.model.*
import com.kazy.doclet.view.AssetsTransporter
import com.kazy.doclet.view.ClassTemplate
import com.sun.javadoc.ClassDoc
import com.sun.javadoc.ConstructorDoc
import com.sun.javadoc.MethodDoc
import com.sun.javadoc.RootDoc
import java.util.*

class AndroidDoclet {
    companion object {

        @JvmStatic fun start(rootDoc: RootDoc): Boolean {

            val loader = ClassPathTemplateLoader()
            loader.prefix = "/template"
            loader.suffix = ".hbs"
            val handlebars = Handlebars(loader)

            val rootInfo = convertToRootInfo(rootDoc)
            rootInfo.allClassList.forEach { classInfo ->
                ClassTemplate.render(handlebars, classInfo, rootInfo)
            }

            AssetsTransporter.copy()

            return true
        }

        fun convertToRootInfo(rootDoc: RootDoc): RootInfo {
            val packageInfoList = rootDoc.classes()
                    .fold(ArrayList<PackageInfo>()) { list, doc ->
                        val packageInfo = PackageInfo(doc.containingPackage().name())
                        list.add(packageInfo)
                        list
                    }
                    .distinctBy { item -> item.name }

            val allClassList = convertToCassInfoList(rootDoc)
            val classList = allClassList.filter { item -> item.isClass }
            val hasClass = classList.size != 0
            val abstractClassList = allClassList.filter { item -> item.isAbstract }
            val hasAbstractClass = abstractClassList.size != 0
            val enumList = allClassList.filter { item -> item.isEnum }
            val hasEnum = enumList.size != 0
            val exceptionList = allClassList.filter { item -> item.isException }
            val hasException = exceptionList.size != 0

            return RootInfo(
                    packageInfoList,
                    allClassList,
                    classList,
                    hasClass,
                    abstractClassList,
                    hasAbstractClass,
                    enumList,
                    hasEnum,
                    exceptionList,
                    hasException
            )
        }

        fun convertToCassInfoList(rootDoc: RootDoc): ArrayList<ClassInfo> {
            return rootDoc.classes().fold(ArrayList<ClassInfo>()) { list, doc ->

                val allConstructorList = convertToConstructorInfoList(doc)
                val publicConstructorList = allConstructorList.filter { item -> item.isPublic }
                val hasPublicConstructor = publicConstructorList.size != 0
                val protectedConstructorList = allConstructorList.filter { item -> item.isProtected }
                val hasProtectedConstructor = protectedConstructorList.size != 0
                val privateConstructorList = allConstructorList.filter { item -> item.isPrivate }
                val hasPrivateConstructor = privateConstructorList.size != 0

                val allMethodList = convertToMethodInfoList(doc)
                val publicMethodList = allMethodList.filter { item -> item.isPublic }
                val hasPublicMethod = publicMethodList.size != 0
                val protectedMethodList = allMethodList.filter { item -> item.isProtected }
                val hasProtectedMethod = protectedMethodList.size != 0
                val privateMethodList = allMethodList.filter { item -> item.isPrivate }
                val hasPrivateMethod = privateMethodList.size != 0

                val packageName = doc.containingPackage().name()
                val packagePath = packageName.replace(".", "/")

                val comment = doc.commentText()

                val isClass = doc.isClass
                val isInterface = doc.isInterface && doc.isAbstract
                val isAbstract = doc.isClass && doc.isAbstract
                val isEnum = doc.isEnum
                val isException = doc.isClass && doc.isException
                val accessLevel = doc.accessLevel()

                val classInfo = ClassInfo(
                        doc.name(),
                        packageName,
                        packagePath,
                        accessLevel,

                        publicConstructorList,
                        hasPublicConstructor,
                        protectedConstructorList,
                        hasProtectedConstructor,
                        privateConstructorList,
                        hasPrivateConstructor,

                        publicMethodList,
                        hasPublicMethod,
                        protectedMethodList,
                        hasProtectedMethod,
                        privateMethodList,
                        hasPrivateMethod,

                        comment,
                        isClass,
                        isInterface,
                        isException,
                        isEnum,
                        isAbstract
                )
                list.add(classInfo)
                list
            }
        }

        fun convertToConstructorInfoList(classDoc: ClassDoc): ArrayList<ConstructorInfo> {
            return classDoc.constructors().fold(ArrayList<ConstructorInfo>()) { list, doc ->
                val paramInfoList = convertToParamInfoList(doc)
                val constructorInfo = ConstructorInfo(
                        doc.name(),
                        doc.accessLevel(),
                        paramInfoList,
                        doc.commentText(),
                        doc.isPublic,
                        doc.isProtected,
                        doc.isPrivate
                )
                list.add(constructorInfo)
                list
            }
        }

        fun convertToMethodInfoList(classDoc: ClassDoc): ArrayList<MethodInfo> {
            return classDoc.methods().fold(ArrayList<MethodInfo>()) { list, doc ->
                doc.commentText()
                val paramInfoList = convertToParamInfoList(doc)
                doc.paramTags()
                val methodInfo = MethodInfo(
                        doc.name(),
                        doc.accessLevel(),
                        doc.returnType().typeName(),
                        paramInfoList,
                        doc.commentText(),
                        doc.isPublic,
                        doc.isProtected,
                        doc.isPrivate
                )
                list.add(methodInfo)
                list
            }
        }

        fun convertToParamInfoList(methodDoc: MethodDoc): ArrayList<ParamInfo> {
            return methodDoc.parameters().fold(ArrayList<ParamInfo>()) { list, doc ->
                val comment = methodDoc.paramTags()
                        .filter { tag -> tag.parameterName() == doc.name() }
                        .firstOrNull()
                        ?.parameterComment()
                        .orEmpty()
                val paramInfo = ParamInfo(doc.typeName(), doc.name(), comment)
                list.add(paramInfo)
                list
            }
        }

        fun convertToParamInfoList(constructorDoc: ConstructorDoc): ArrayList<ParamInfo> {
            return constructorDoc.parameters().fold(ArrayList<ParamInfo>()) { list, doc ->
                val comment = constructorDoc.paramTags()
                        .filter { tag -> tag.parameterName() == doc.name() }
                        .firstOrNull()
                        ?.parameterComment()
                        .orEmpty()
                val paramInfo = ParamInfo(doc.typeName(), doc.name(), comment)
                list.add(paramInfo)
                list
            }
        }

        @JvmStatic fun optionLength(option: String): Int {
            if (option == "-d") {
                return 2
            }
            return 0
        }
    }
}

