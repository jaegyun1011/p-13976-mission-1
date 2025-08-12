package com.ll.domain.wiseSaying.wiseSaying.service

import com.ll.domain.wiseSaying.wiseSaying.entity.WiseSaying
import com.ll.global.bean.SingletonScope

class WiseSayingService {
    val wiseSayingRepository = SingletonScope.wiseSayingRepository

    fun write(author: String, content: String): WiseSaying {
        return wiseSayingRepository.save(WiseSaying(0, author, content))
    }

    fun findById(id: Int): WiseSaying? {
        return wiseSayingRepository.findById(id)
    }

    fun findAll(): List<WiseSaying> {
        return wiseSayingRepository.findAll()
    }

    fun isEmpty(): Boolean {
        return wiseSayingRepository.isEmpty()
    }

    fun delete(wiseSaying: WiseSaying) {
        wiseSayingRepository.delete(wiseSaying)
    }
}