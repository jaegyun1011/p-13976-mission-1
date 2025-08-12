package com.ll.domain.wiseSaying.dto

import com.ll.domain.wiseSaying.wiseSaying.entity.WiseSaying

class PageDto (
    val currentPage: Int,
    val totalPage: Int,
    val wiseSayingList: List<WiseSaying>
) {

}