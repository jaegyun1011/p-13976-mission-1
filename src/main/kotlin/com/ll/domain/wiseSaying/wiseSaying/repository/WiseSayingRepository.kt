package com.ll.domain.wiseSaying.wiseSaying.repository

import com.ll.domain.wiseSaying.wiseSaying.entity.WiseSaying
import com.ll.global.app.AppConfig
import java.nio.file.Path

class WiseSayingRepository {
    val tableDirPath: Path
        get() {
            return AppConfig.dbDirPath.resolve("wiseSaying")
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

    fun isEmpty(): Boolean {
        return tableDirPath.toFile()
            .listFiles()
            ?.filter { it.name != "data.json" }
            ?.none { it.name.endsWith(".json") }
            ?: true
    }

    private fun mkTableDirsIfNotExists() {
        tableDirPath.toFile().mkdirs()
    }

    private fun saveOnDisk(wiseSaying: WiseSaying) {
        mkTableDirsIfNotExists()

        val wiseSayingFile = tableDirPath.resolve("${wiseSaying.id}.json")
        wiseSayingFile.toFile().writeText(wiseSaying.jsonStr)
    }

    internal fun saveLastId(lastId: Int) {
        mkTableDirsIfNotExists()

        tableDirPath.resolve("lastId.txt")
            .toFile()
            .writeText(lastId.toString())
    }

    internal fun loadLastId(): Int {
        return try {
            tableDirPath.resolve("lastId.txt")
                .toFile()
                .readText()
                .toInt()
        } catch (e: Exception) {
            0
        }
    }

    private fun getNextId(): Int {
        return (loadLastId() + 1).also {
            saveLastId(it)
        }
    }

    fun clear() {
        tableDirPath.toFile().deleteRecursively()
    }
}