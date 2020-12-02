package com.me.lib.base.utils


fun StringBuilder.replaceLast(char: CharSequence): StringBuilder {
    dropLast(1)
    append(char)
    return this
}
