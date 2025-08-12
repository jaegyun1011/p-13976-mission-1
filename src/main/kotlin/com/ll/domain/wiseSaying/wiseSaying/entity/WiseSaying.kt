package com.ll.domain.wiseSaying.wiseSaying.entity

data class WiseSaying(
    var id: Int,
    var author: String,
    var content: String,
) {
    fun modifyContent(content: String) {
        this.content = content
    }

    fun modifyAuthor(author: String) {
        this.author = author
    }

    fun isNew(): Boolean {
        return id == 0
    }

    val jsonStr: String
        get() {
            return """
                {
                    "id": $id,
                    "author": "$author",
                    "content": "$content"
                }
            """.trimIndent()
        }

    val map: Map<String, Any>
        get() {
            return mapOf(
                "id" to id,
                "author" to author,
                "content" to content
            )
        }
}