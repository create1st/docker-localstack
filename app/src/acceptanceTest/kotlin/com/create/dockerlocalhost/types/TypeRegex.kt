package com.create.dockerlocalhost.types

import com.create.dockerlocalhost.model.OrderStatus

val ORDER_STATUS_REGEX = regexFor(OrderStatus::class.java)

fun <T : Enum<T>> regexFor(clazz: Class<T>) = clazz.enumConstants
    .joinToString("|")