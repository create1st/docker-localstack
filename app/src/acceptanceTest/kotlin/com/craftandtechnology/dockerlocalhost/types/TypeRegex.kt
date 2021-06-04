package com.craftandtechnology.dockerlocalhost.types

import com.craftandtechnology.dockerlocalhost.model.OrderStatus

val ORDER_STATUS_REGEX = regexFor(OrderStatus::class.java)

fun <T : Enum<T>> regexFor(clazz: Class<T>) = clazz.enumConstants
    .joinToString("|")