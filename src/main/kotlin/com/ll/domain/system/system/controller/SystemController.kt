package com.ll.domain.system.system.controller

class SystemController {
    fun printTitle() {
        println("== 명언 앱 ==")
    }

    fun printInput() {
        print("명령) ")
    }

    fun cmdError() {
        println("잘못된 명령어입니다.")
    }

    fun cmdExit() {
        println("프로그램을 종료합니다.")
    }
}