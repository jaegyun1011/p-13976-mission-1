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
        if(wiseSayingService.isEmpty()) {
            println("등록된 명언이 없습니다.")
            return
        }

        val wiseSayingList = wiseSayingService.findAll()

        println("번호 / 작가 / 명언")
        println("----------------------")

        wiseSayingList.forEach {
            println("${it.id} / ${it.author} / ${it.content}")
        }
    }

    fun cmdDelete(rq: Rq) {
        val id = rq.getParamValueAsInt("id", 0)
        if(id == 0) {
            println("잘못된 ID입니다.")
            return
        }

        val wiseSaying = wiseSayingService.findById(id)
        if(wiseSaying == null) {
            println("${id}번 명언은 존재하지 않습니다.")
            return
        }

        wiseSayingService.delete(wiseSaying)

        println("${id}번 명언을 삭제하였습니다.")
    }

    fun cmdModify(rq: Rq) {
        val id = rq.getParamValueAsInt("id", 0)
        if(id == 0) {
            println("잘못된 ID입니다.")
            return
        }

        val wiseSaying = wiseSayingService.findById(id)
        if(wiseSaying == null) {
            println("${id}번 명언은 존재하지 않습니다.")
            return
        }

        println("명언(기존) : ${wiseSaying.content}")
        print("명언 : ")
        val content = readlnOrNull()!!.trim()

        println("작가(기존) : ${wiseSaying.author}")
        print("작가 : ")
        val author = readlnOrNull()!!.trim()

        wiseSayingService.modify(wiseSaying, author, content)

        println("${id}번 명언을 수정하였습니다.")
    }

    fun cmdBuild(rq: Rq) {
        wiseSayingService.build()

        println("data.json 파일의 내용이 갱신되었습니다.")
    }
}