package com.cashless.self_order.util

import java.net.HttpURLConnection

object Constants {
    const val HTTP_UNAUTHORIZED = HttpURLConnection.HTTP_UNAUTHORIZED
    const val HTTP_SERVER_ERROR = HttpURLConnection.HTTP_INTERNAL_ERROR

    const val API_PREFIX = "api/"

    const val ENTER_SPACE_FORMAT = "\n"

    const val PAGE_DEFAULT = 1

    const val POSITION_DEFAULT = -1

    const val RELEASED = "RELEASED"

    const val MAX_QUANTITY_FOOD = 100

    const val FIRST_POSITION = 0

    const val PER_PAGE = 20

    const val PER_PAGE_FOR_CATEGORY = 50

    const val SPACE = " "

    const val QUANTITY_PEOPLE = "2名"

    const val PEOPLE = "人"

    const val TILDE = "~"

    const val TOTAL_DAY_OF_WEEK = 7

    const val MAX_WEEK_PREVIEW = 5

    const val MIN_WEEK_FORWARD = 1

    /**
     * The minimum minute around the next hour for searching client
     */
    const val MINIMUM_MINUTE_DURATION = 20

    const val ID_DEFAULT = 1

    const val INT_DEFAULT = -1

    const val STR_DEFAULT = ""

    const val BOOL_DEFAULT = false

    const val DEBOUNCE_DEFAULT: Long = 200

    const val BLANK = " "

    const val COMMA = ","

    const val QUESTION_MARK = "?"

    const val COMPARE = "="

    const val AND = "&"

    const val ID_NONE_DATA = -1

    const val POS_SEARCH_NO_CONDITION = 0

    /**
     *  (#,###円 × #)
     */
    const val FORMAT_MONEY_W_QUANTITY = "(%s円 × %d)" // include tax

    /**
     * #,###円 (税込)
     */
    const val FORMAT_MONEY = "%s円 (税込)" // include tax

    const val MIN_PHONE_LENGTH = 10

    const val FORMAT_MONEY_W_UNIT_JPY = "¥%s"

    const val QUANTITY_FOOD_DEFAULT = 1

    const val QUANTITY_PEOPLE_POS_DEFAULT = 1
    const val EMPTY = ""

    const val EXTRA_ARGS = "EXTRA_ARGS"


    object Bundles {
        const val ON_NEW_INTENT_EXTRA = "ON_NEW_INTENT_EXTRA"
        const val RELOGIN_EXTRA = "RELOGIN_EXTRA"
    }
}
