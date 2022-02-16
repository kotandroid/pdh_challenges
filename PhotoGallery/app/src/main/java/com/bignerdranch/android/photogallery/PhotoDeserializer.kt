package com.bignerdranch.android.photogallery

import com.bignerdranch.android.photogallery.api.PhotoResponse
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class PhotoDeserializer : JsonDeserializer<PhotoResponse> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): PhotoResponse {
        return if (json != null) {
            val photos = json.asJsonObject
                .get("photos").asJsonObject
                .get("photo").asJsonArray
                .map { jsonPhoto ->
                    val photo: GalleryItem
                    jsonPhoto.asJsonObject.apply {
                        photo = GalleryItem(
                            get("title").asString,
                            get("id").asString,
                            get("url_s").asString
                        )
                    }
                    photo
                }
            PhotoResponse().apply {
                galleryItems = photos
            }
        } else {
            PhotoResponse().apply {
                galleryItems = listOf()
            }
        }
    }
}