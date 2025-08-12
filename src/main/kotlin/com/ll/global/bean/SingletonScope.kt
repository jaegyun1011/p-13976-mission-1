package com.ll.global.bean

import com.ll.domain.system.system.controller.SystemController
import com.ll.domain.wiseSaying.wiseSaying.controller.WiseSayingController

object SingletonScope {
    val systemController by lazy { SystemController() }
    val wiseSayingController by lazy { WiseSayingController() }
}