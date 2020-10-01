package org.bitbucket.yujiorama.sakilaapp.test.h2

import org.h2.tools.TriggerAdapter
import java.sql.Connection
import java.sql.ResultSet

class FilmInsertTrigger : TriggerAdapter() {

    override fun fire(conn: Connection?, oldRow: ResultSet?, newRow: ResultSet?) {
        with(conn) {
            with(this?.prepareStatement("""
                INSERT INTO film_text (film_id, title, description) VALUES (?, ?, ?);
            """.trimIndent().trimMargin())) {
                this?.setObject(1, newRow?.getInt("film_id"))
                this?.setObject(2, newRow?.getString("title"))
                this?.setObject(3, newRow?.getString("description"))
                this?.execute()
            }
            this?.commit()
        }
    }
}

class FilmUpdateTrigger : TriggerAdapter() {

    override fun fire(conn: Connection?, oldRow: ResultSet?, newRow: ResultSet?) {
        with(conn) {
            with(this?.prepareStatement("""
                UPDATE film_text
                SET title = ?, description = ?
                WHERE film_id = ?
                AND (
                    title <> ?
                    OR description <> ?
                )
            """.trimIndent().trimMargin())) {
                val filmId = newRow?.getInt("film_id")
                val title = newRow?.getString("title")
                val description = newRow?.getString("description")
                this?.setObject(1, title)
                this?.setObject(2, description)
                this?.setObject(3, filmId)
                this?.setObject(4, title)
                this?.setObject(5, description)
                this?.execute()
            }
            this?.commit()
        }
    }
}

class FilmDeleteTrigger : TriggerAdapter() {

    override fun fire(conn: Connection?, oldRow: ResultSet?, newRow: ResultSet?) {
        with(conn) {
            with(this?.prepareStatement("""
                DELETE FROM film_text WHERE film_id _ ?;
            """.trimIndent().trimMargin())) {
                this?.setObject(1, newRow?.getInt("film_id"))
                this?.execute()
            }
            this?.commit()
        }
    }
}
