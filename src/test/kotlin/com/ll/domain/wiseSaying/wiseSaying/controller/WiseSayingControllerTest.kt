package com.ll.domain.wiseSaying.wiseSaying.controller

import com.ll.TestRunner
import com.ll.global.bean.SingletonScope
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class WiseSayingControllerTest {
    @BeforeEach
    fun setUp() {
        SingletonScope.wiseSayingRepository.clear()
    }

    @Test
    fun `등록`() {
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
    fun `등록_입력안함`() {
        val result = TestRunner.run(
            """
            등록
            
        """
        )

        assertThat(result).contains("명언을 입력해주세요.")
    }

    @Test
    fun `목록`() {
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
    fun `목록_empty`() {
        val result = TestRunner.run(
            """
            목록
        """
        )

        assertThat(result).contains("등록된 명언이 없습니다.")
    }

    @Test
    fun `삭제`() {
        val result = TestRunner.run(
            """
            등록
            명언1
            작가1
            등록
            명언2
            작가2
            삭제?id=1
            목록
        """
        )

        assertThat(result).contains("1번 명언을 삭제하였습니다.")
        assertThat(result).doesNotContain("1 / 작가1 / 명언1")
    }

    @Test
    fun `삭제_존재하지 않는 명언`() {
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
    fun `수정`() {
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
    fun `수정_존재하지 않는 명언`() {
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

    @Test
    fun `목록_검색_내용`() {
        val result = TestRunner.run(
            """
            등록
            명언1AA
            작가1AA
            등록
            명언2BB
            작가2AA
            등록
            명언3
            작가3
            목록?keywordType=content&keyword=AA
        """
        )

        assertThat(result).contains("1 / 작가1AA / 명언1AA")
        assertThat(result).doesNotContain("2 / 작가2AA / 명언2BB")
        assertThat(result).doesNotContain("3 / 작가3 / 명언3")
    }

    @Test
    fun `목록_검색_작가`() {
        val result = TestRunner.run(
            """
            등록
            명언1AA
            작가1AA
            등록
            명언2BB
            작가2AA
            등록
            명언3
            작가3
            목록?keywordType=author&keyword=AA
        """
        )

        assertThat(result).contains("1 / 작가1AA / 명언1AA")
        assertThat(result).contains("2 / 작가2AA / 명언2BB")
        assertThat(result).doesNotContain("3 / 작가3 / 명언3")
    }

    @Test
    fun `목록_검색 결과 없음`() {
        val result = TestRunner.run(
            """
            등록
            명언1AA
            작가1AA
            등록
            명언2BB
            작가2AA
            등록
            명언3
            작가3
            목록?keywordType=author&keyword=CC
        """
        )

        assertThat(result).contains("검색 키워드와 일치하는 명언이 없습니다.")
    }

    @Test
    fun `목록_페이징`() {
        val result = TestRunner.run(
            """
            등록
            명언1
            작가1
            등록
            명언2
            작가2
            등록
            명언3
            작가3
            등록
            명언4
            작가4
            등록
            명언5
            작가5
            등록
            명언6
            작가6
            등록
            명언7
            작가7
            목록?page=2
        """
        )

        assertThat(result).contains("1 / 작가1 / 명언1")
        assertThat(result).contains("2 / 작가2 / 명언2")
        assertThat(result).doesNotContain("3 / 작가3 / 명언3")
        assertThat(result).doesNotContain("4 / 작가4 / 명언4")
        assertThat(result).doesNotContain("5 / 작가5 / 명언5")
        assertThat(result).doesNotContain("6 / 작가6 / 명언6")
        assertThat(result).doesNotContain("7 / 작가7 / 명언7")
    }
}