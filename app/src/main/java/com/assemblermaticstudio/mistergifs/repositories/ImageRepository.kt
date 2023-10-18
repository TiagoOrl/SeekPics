package com.assemblermaticstudio.mistergifs.repositories

import com.assemblermaticstudio.mistergifs.persistence.ImageDAO
import com.assemblermaticstudio.mistergifs.services.PexelsService

class ImageRepository(
    private val service: PexelsService,
    private val dao: ImageDAO
) {

}