package com.ll.domain.wiseSaying.wiseSaying.service

import com.ll.domain.wiseSaying.dto.PageDto
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

    fun findPageSearch(page: Int, keywordType: String, keyword: String): PageDto {
        return wiseSayingRepository.findPageSearch(page, keywordType, keyword)
    }

    fun isEmpty(): Boolean {
        return wiseSayingRepository.isEmpty()
    }

    fun delete(wiseSaying: WiseSaying) {
        wiseSayingRepository.delete(wiseSaying)
    }

    fun modify(wiseSaying: WiseSaying, author: String, content: String) {
        wiseSaying.modifyAuthor(author)
        wiseSaying.modifyContent(content)

        wiseSayingRepository.save(wiseSaying)
    }

    fun build() {
        wiseSayingRepository.build()
    }
}