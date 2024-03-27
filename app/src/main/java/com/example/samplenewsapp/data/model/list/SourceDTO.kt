package com.example.samplenewsapp.data.model.list

import com.google.gson.annotations.SerializedName

data class SourceDTO(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String = ""
)
