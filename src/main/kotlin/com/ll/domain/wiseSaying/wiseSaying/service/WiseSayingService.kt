package com.ll.domain.wiseSaying.wiseSaying.service

import com.ll.domain.wiseSaying.wiseSaying.entity.WiseSaying
import com.ll.global.bean.SingletonScope

class WiseSayingService {
    val wiseSayingRepository = SingletonScope.wiseSayingRepository

    fun write(author: String, content: String): WiseSaying {
        return wiseSayingRepository.save(WiseSaying(0, author, content))
    }
}