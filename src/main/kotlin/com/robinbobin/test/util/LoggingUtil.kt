package com.robinbobin.test.util

import org.slf4j.LoggerFactory

inline fun <reified T> getLogger() = requireNotNull(LoggerFactory.getLogger(T::class.java))
