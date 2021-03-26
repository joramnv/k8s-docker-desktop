package com.joram.model

import com.fasterxml.jackson.annotation.JsonProperty

data class WhoIsResponse(
    @JsonProperty("requestFrom") val requestFrom: String,
    @JsonProperty("responseFrom") val responseFrom: String,
    @JsonProperty("message") val message: String
)
