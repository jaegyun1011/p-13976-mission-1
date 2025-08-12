package com.ll

import com.ll.global.bean.SingletonScope.systemController
import com.ll.global.bean.SingletonScope.wiseSayingController
import com.ll.global.rq.Rq

class App {
    fun run() {
        systemController.printTitle()

        while(true) {
            systemController.printInput()

            val input = readlnOrNull()!!.trim()
            val rq = Rq(input)

            when(rq.command) {
                "종료" -> break
                "등록" -> wiseSayingController.cmdWrite()
                "목록" -> wiseSayingController.cmdList(rq)
                "삭제" -> wiseSayingController.cmdDelete(rq)
                "수정" -> wiseSayingController.cmdModify(rq)
                "빌드" -> wiseSayingController.cmdBuild()
                else -> systemController.cmdError()
            }
        }

        systemController.cmdExit()
    }
}