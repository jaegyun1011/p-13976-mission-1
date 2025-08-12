package com.ll.domain.wiseSaying.wiseSaying.controller

import com.ll.TestRunner
import com.ll.global.bean.SingletonScope
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class WiseSayingControllerTest {
    @BeforeEach
    fun setUp() {
        SingletonScope.wiseSayingRepository.clear()
        SingletonScope.wiseSayingRepository.tableDirPath
            .toFile()
            .mkdirs()
    }

    @Test
    fun `명언 등록`() {
        val result = TestRunner.run(
            """
            등록
            명언1
            작가1
        """
        )

        assertThat(result).contains("명언 : ")
        assertThat(result).contains("작가 : ")
        assertThat(result).contains("1번 명언이 등록되었습니다.")
    }

    @Test
    fun `명언 목록`() {
        val result = TestRunner.run(
            """
            등록
            명언1
            작가1
            등록
            명언2
            작가2
            목록
        """
        )

        assertThat(result).contains("1 / 작가1 / 명언1")
        assertThat(result).contains("2 / 작가2 / 명언2")
    }

    @Test
    fun `명언 삭제`() {
        val result = TestRunner.run(
            """
            등록
            명언1
            작가1
            등록
            명언2
            작가2
            삭제?id=1
        """
        )

        assertThat(result).contains("1번 명언을 삭제하였습니다.")
    }

    @Test
    fun `존재하지 않는 명언 삭제`() {
        val result = TestRunner.run(
            """
            등록
            명언1
            작가1
            등록
            명언2
            작가2
            삭제?id=3
        """
        )

        assertThat(result).contains("3번 명언은 존재하지 않습니다.")
    }

    @Test
    fun `명언 수정`() {
        val result = TestRunner.run(
            """
            등록
            명언1
            작가1
            등록
            명언2
            작가2
            수정?id=1
            명언1수정
            작가1수정
            목록
        """
        )

        assertThat(result).contains("1 / 작가1수정 / 명언1수정")
    }

    @Test
    fun `존재하지 않는 명언 수정`() {
        val result = TestRunner.run(
            """
            등록
            명언1
            작가1
            등록
            명언2
            작가2
            수정?id=3
        """
        )

        assertThat(result).contains("3번 명언은 존재하지 않습니다.")
    }
}