package com.ll.domain.system.system.controller

import com.ll.TestRunner
import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test

class SystemControllerTest {
    @Test
    fun `종료`() {
        val result = TestRunner.run(
            """
            종료
            """
        )

        assertThat(result).contains("프로그램을 종료합니다.")
    }
}