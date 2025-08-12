package com.ll.domain.wiseSaying.wiseSaying.repository

import com.ll.domain.wiseSaying.dto.PageDto
import com.ll.domain.wiseSaying.wiseSaying.entity.WiseSaying
import com.ll.global.app.AppConfig
import com.ll.standard.util.json.JsonUtil
import java.nio.file.Path

class WiseSayingRepository {
    val tableDirPath: Path = AppConfig.dbDirPath.resolve("wiseSaying")
    val pageSize: Int = 5

    init {
        tableDirPath
            .toFile()
            .mkdirs()
    }

    fun save(newWiseSaying: WiseSaying): WiseSaying {
        if(newWiseSaying.isNew())
            newWiseSaying.id = getNextId()

        saveOnDisk(newWiseSaying)

        return newWiseSaying
    }

    fun delete(wiseSaying: WiseSaying) {
        tableDirPath
            .resolve("${wiseSaying.id}.json")
            .toFile()
            .takeIf { it.exists() }
            ?.delete()
    }

    fun findById(id: Int): WiseSaying? {
        return tableDirPath
            .resolve("$id.json")
            .toFile()
            .takeIf { it.exists() }
            ?.readText()
            ?.let(WiseSaying.Companion::fromJsonStr)
    }

    fun findAll(): List<WiseSaying> {
        return tableDirPath.toFile()
            .listFiles()
            ?.filter { it.name != "data.json" }
            ?.filter { it.name.endsWith(".json") }
            ?.map { it.readText() }
            ?.map(WiseSaying.Companion::fromJsonStr)
            ?.sortedByDescending { it.id }
            .orEmpty()
    }

    fun makePage(page:Int, wiseSayingList: List<WiseSaying>): PageDto {
        val totalPage = (wiseSayingList.size - 1) / pageSize + 1

        val currentPage = if(page > totalPage)
            totalPage
        else if(page < 1)
            1
        else
            page

        val fromIdx = (currentPage - 1) * pageSize
        val toIdx = minOf(fromIdx + pageSize, wiseSayingList.size)
        val pagedList = wiseSayingList.subList(fromIdx, toIdx)

        return PageDto(currentPage, totalPage, pagedList)
    }

    fun findPageSearch(page: Int, keywordType: String, keyword: String): PageDto {
        val wiseSayingList = findAll()

        val selector: (WiseSaying) -> String = when (keywordType) {
            "author" -> { it -> it.author }
            "content" -> { it -> it.content }
            else -> { return makePage(page, wiseSayingList) }
        }

        val pureKeyword = keyword.replace("%", "")
        if (pureKeyword.isBlank()) {
            return makePage(page, wiseSayingList)
        }

        val filteredList = when {
            keyword.startsWith("%") && keyword.endsWith("%") ->
                wiseSayingList.filter { selector(it).contains(pureKeyword) }
            keyword.startsWith("%") ->
                wiseSayingList.filter { selector(it).endsWith(pureKeyword) }
            keyword.endsWith("%") ->
                wiseSayingList.filter { selector(it).startsWith(pureKeyword) }
            else ->
                wiseSayingList.filter { selector(it).contains(pureKeyword) }
        }

        return makePage(page, filteredList)
            .copy(search = true)
    }

    fun isEmpty(): Boolean {
        return tableDirPath
            .toFile()
            .listFiles()
            ?.filter { it.name != "data.json" }
            ?.none { it.name.endsWith(".json") }
            ?: true
    }

    private fun saveOnDisk(wiseSaying: WiseSaying) {
       tableDirPath
           .resolve("${wiseSaying.id}.json")
           .toFile()
           .writeText(wiseSaying.jsonStr)
    }

    private fun saveLastId(lastId: Int) {
        tableDirPath
            .resolve("lastId.txt")
            .toFile()
            .writeText(lastId.toString())
    }

    private fun loadLastId(): Int {
        return try {
            tableDirPath
                .resolve("lastId.txt")
                .toFile()
                .readText()
                .toInt()
        } catch(e: Exception) {
            0
        }
    }

    private fun getNextId(): Int {
        return (loadLastId() + 1).also {
            saveLastId(it)
        }
    }

    fun clear() {
        tableDirPath
            .toFile()
            .deleteRecursively()

        tableDirPath
            .toFile()
            .mkdirs()
    }

    fun build() {
        val mapList = findAll()
            .map(WiseSaying::map)

        JsonUtil.toString(mapList)
            .let {
                tableDirPath
                    .resolve("data.json")
                    .toFile()
                    .writeText(it)
            }
    }
}