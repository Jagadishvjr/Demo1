package com.jagadishvjr.demo1.data.mapper

import com.jagadishvjr.demo1.data.remote.dto.FixureDto
import com.jagadishvjr.demo1.domain.model.Fixure

fun FixureDto.toDomian(): Fixure{
    return Fixure(
        id = id,
        name = name,
        resultInfo = resultInfo
    )
}