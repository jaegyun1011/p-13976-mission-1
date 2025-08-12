package com.ll.domain.wiseSaying.wiseSaying.controller

import com.ll.global.bean.SingletonScope
import com.ll.global.rq.Rq

class WiseSayingController {
    val wiseSayingService = SingletonScope.wiseSayingService

    fun cmdWrite(rq: Rq) {
        print("명언 : ")
        val content = readlnOrNull()!!.trim()
        print("작가 : ")
        val author = readlnOrNull()!!.trim()

        val newWiseSaying = wiseSayingService.write(author, content)
        println("${newWiseSaying.id}번 명언이 등록되었습니다.")
    }

    fun cmdList(rq: Rq) {

    }

    fun cmdDelete(rq: Rq) {

    }

    fun cmdModify(rq: Rq) {

    }
}