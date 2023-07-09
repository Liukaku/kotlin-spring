package io.paketo.demo

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.cdimascio.dotenv.Dotenv
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.sql.DriverManager

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}

@RestController
class QuizAppApplication {
	@CrossOrigin(origins = ["*"])
	@GetMapping("/")
	fun aliveStatus(): String {
		return String.format("hello %s", "test")
	}

	@GetMapping("/getAll")
	fun getAll(): ArrayList<QuizTable> {
			val dotenv = Dotenv.load()
			val MYSQL_URL = dotenv["MYSQL_URL"]
			val MYSQL_NAME = dotenv["MYSQL_NAME"]
			val MYSQL_PWD = dotenv["MYSQL_PWD"]
			val mapper = ObjectMapper()
			val res: ArrayList<QuizTable> = ArrayList<QuizTable>()
			return try {
				Class.forName("com.mysql.cj.jdbc.Driver")
				val connection = DriverManager.getConnection(MYSQL_URL, MYSQL_NAME, MYSQL_PWD)
				val statement = connection.createStatement()
				val resultSet = statement.executeQuery("SELECT * FROM Quiz")
				val columnCount = resultSet.metaData.columnCount
				while (resultSet.next()) {
					val rowArr = ArrayList<String>()
					for (i in 1..columnCount) {
						rowArr.add(resultSet.getString(i))
					}
					val quiz = QuizTable(rowArr)
					res.add(quiz)
				}
				res
			} catch (e: Exception) {
				println(e)
				res
			}
		}

}