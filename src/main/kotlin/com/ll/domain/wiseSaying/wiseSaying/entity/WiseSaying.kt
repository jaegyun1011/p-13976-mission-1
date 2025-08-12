package com.ll.domain.wiseSaying.wiseSaying.entity

data class WiseSaying(
    var id: Int = 0,
    var content: String,
    var author: String,
) {
    fun modifyContent(content: String) {
        this.content = content
    }

    fun modifyAuthor(author: String) {
        this.author = author
    }
}