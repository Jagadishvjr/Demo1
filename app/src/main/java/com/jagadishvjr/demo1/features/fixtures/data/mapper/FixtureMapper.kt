package com.jagadishvjr.demo1.features.fixtures.data.mapper

import com.jagadishvjr.demo1.features.fixtures.data.remote.dto.FixtureDto
import com.jagadishvjr.demo1.features.fixtures.domain.model.Fixture

fun FixtureDto.toDomain(): Fixture {
    return Fixture(
        id = id,
        name = name,
        resultInfo = resultInfo
    )
}
