package com.example.bookstorev2.data.local.room.mapper

import com.example.bookstorev2.data.local.room.dto.BookDto
import com.example.bookstorev2.data.local.room.entity.BookDbEntity
import com.example.bookstorev2.domain.models.Book

fun BookDbEntity.toDomain(): Book =
    Book(
        key = key,
        title = title,
        imageUrl = imageUrl,
        category = category,
        description = description,
        price = price,
        date = date,
        author = author,
        favorite = favorite,
        read = read,
        selectedImage = selectedImage
    )

fun BookDto.toDomain(): Book =
    Book(
        key = key,
        title = title,
        imageUrl = imageUrl,
        category = category,
        description = description,
        price = price,
        date = date,
        author = author,
        favorite = favorite,
        read = read,
        selectedImage = selectedImage
    )

fun BookDto.toDb(): BookDbEntity =
    BookDbEntity(
        key = this.key,
        title = title,
        imageUrl = imageUrl,
        category = category,
        description = description,
        price = price,
        date = date,
        author = author,
        favorite = favorite,
        read = read,
        selectedImage = selectedImage
    )


fun Book.toDb(): BookDbEntity =
    BookDbEntity(
        key = key,
        title = title,
        imageUrl = imageUrl,
        category = category,
        description = description,
        price = price,
        date = date,
        author = author,
        favorite = favorite,
        read = read,
        selectedImage = selectedImage
    )

fun Book.toDto(): BookDto =
    BookDto(
        key = key,
        title = title,
        imageUrl = imageUrl,
        category = category,
        description = description,
        price = price,
        date = date,
        author = author,
        favorite = favorite,
        read = read,
        selectedImage = selectedImage
    )

fun List<BookDbEntity>.toDomain(): List<Book> =
    map { it.toDomain() }

fun List<BookDto>.dtoListToDb(): List<BookDbEntity> =
    map { it.toDb() }

fun List<Book>.bookListToDb(): List<BookDbEntity> =
    map {it.toDb()}