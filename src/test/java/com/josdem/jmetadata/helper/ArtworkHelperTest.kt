package com.josdem.jmetadata.helper

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@ContextConfiguration(classes = [ApplicationContextSingleton::class, ArtworkHelper::class])
internal class ArtworkHelperTest {
    @Autowired
    private lateinit var artworkHelper: ArtworkHelper

    private val log = LoggerFactory.getLogger(this::class.java)

    @Test
    fun `should create an artwork helper`(testInfo: TestInfo) {
        log.info(testInfo.displayName)
        assertNotNull(artworkHelper.createArtwork()) { "should create an artwork" }
    }
}
