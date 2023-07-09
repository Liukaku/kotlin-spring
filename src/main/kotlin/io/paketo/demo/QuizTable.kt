package io.paketo.demo

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper

class QuizTable(Data: ArrayList<String>){

    var id: String? = null
    var quizTitle: String? = null
    var ownerId: String? = null

    init {
        id = Data[0];
        quizTitle = Data[1];
        ownerId = Data[2];
    }

    @JsonIgnore
    fun asJson(): String? {
        val mapper = ObjectMapper()
        return try {
            mapper.writeValueAsString(this)
        } catch (e: JsonProcessingException) {
            throw RuntimeException(e)
        }
    }

}