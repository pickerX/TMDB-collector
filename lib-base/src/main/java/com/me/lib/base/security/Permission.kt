package com.me.lib.base.security

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Permission(val value: Array<String>)