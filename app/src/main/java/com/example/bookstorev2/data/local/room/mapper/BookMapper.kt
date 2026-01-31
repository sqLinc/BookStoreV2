package com.example.bookstorev2.data.local.room.mapper

import com.example.bookstorev2.data.local.room.dto.BookDto
import com.example.bookstorev2.data.local.room.entity.BookDbEntity
import com.example.bookstorev2.domain.models.Book

fun BookDbEntity.toDomain(): Book =
    Book(
        key = key,
        title = title,
        base64Image = base64Image,
        category = category,
        description = description,
        price = price,
        date = date,
        author = author,
        favorite = favorite,
        read = read,
        imageUri = imageUri
    )

fun BookDto.toDomain(): Book =
    Book(
        key = key,
        title = title,
        base64Image = base64Image,
        category = category,
        description = description,
        price = price,
        date = date,
        author = author,
        favorite = favorite,
        read = read,
        imageUri = imageUri
    )

fun Book.toDb(): BookDbEntity =
    BookDbEntity(
        key = key,
        title = title,
        base64Image = base64Image!!,
        category = category,
        description = description,
        price = price,
        date = date,
        author = author,
        favorite = favorite,
        read = read,
        imageUri = imageUri
    )

fun List<BookDbEntity>.dbListToDomain(): List<Book> =
    map { it.toDomain() }


fun List<Book>.bookListToDb(): List<BookDbEntity> =
    map { it.toDb() }

fun List<BookDto>.dtoListToDomain(): List<Book> =
    map { it.toDomain() }