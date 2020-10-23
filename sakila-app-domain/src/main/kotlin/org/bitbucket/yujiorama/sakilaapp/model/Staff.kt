package org.bitbucket.yujiorama.sakilaapp.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaFormat
import org.bitbucket.yujiorama.sakilaapp.DateTimePattern
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.MappedCollection
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import java.time.LocalDateTime

@Table("staff")
data class Staff(
    @Id
    @Column("staff_id")
    @JsonProperty("staff_id")
    val id: Int?,

    @Column("last_update")
    @JsonProperty("last_update")
    @JsonSchemaFormat(DateTimePattern.ENTITY_DATETIME_PATTERN)
    val lastUpdate: LocalDateTime = LocalDateTime.now(),

    @Column("first_name")
    @JsonProperty("first_name")
    val firstName: String = "",

    @Column("last_name")
    @JsonProperty("last_name")
    val lastName: String = "",

    @MappedCollection(idColumn = "address_id")
    @JsonProperty("address")
    val address: Address = Address(),

    @Column("email")
    @JsonProperty("email")
    val email: String?,

    @MappedCollection(idColumn = "store_id")
    @JsonProperty("store")
    val store: Store = Store(),

    @Column("active")
    @JsonProperty("active")
    val active: Boolean = false,

    @Column("username")
    @JsonProperty("username")
    val username: String = "",

    @Column("password")
    @JsonProperty("password")
    val password: String?,

    @Column("picture")
    @JsonProperty("picture")
    val picture: ByteArray?,
) {
    @PersistenceConstructor
    constructor() : this(id = null, email = null, password = null, picture = null)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Staff

        if (id != other.id) return false
        if (lastUpdate != other.lastUpdate) return false
        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false
        if (address != other.address) return false
        if (email != other.email) return false
        if (store != other.store) return false
        if (active != other.active) return false
        if (username != other.username) return false
        if (password != other.password) return false
        if (picture != null) {
            if (other.picture == null) return false
            if (!picture.contentEquals(other.picture)) return false
        } else if (other.picture != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + lastUpdate.hashCode()
        result = 31 * result + firstName.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + address.hashCode()
        result = 31 * result + (email?.hashCode() ?: 0)
        result = 31 * result + store.hashCode()
        result = 31 * result + active.hashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + (password?.hashCode() ?: 0)
        result = 31 * result + (picture?.contentHashCode() ?: 0)
        return result
    }

    fun withId(id: Int?): Staff = this.copy(id = id)

    fun withLastUpdate(lastUpdate: LocalDateTime): Staff = this.copy(lastUpdate = lastUpdate)

    fun withFirstName(firstName: String): Staff = this.copy(firstName = firstName)

    fun withLastName(lastName: String): Staff = this.copy(lastName = lastName)

    fun withAddress(address: Address): Staff = this.copy(address = address)

    fun withEmail(email: String?): Staff = this.copy(email = email)

    fun withStore(store: Store): Staff = this.copy(store = store)

    fun withActive(active: Boolean): Staff = this.copy(active = active)

    fun withUsername(username: String): Staff = this.copy(username = username)
    fun withPassword(password: String?): Staff = this.copy(password = password)
    fun withPicture(picture: ByteArray?): Staff = this.copy(picture = picture)
}

interface StaffRepository : CrudRepository<Staff, Int> {
    fun findAllByOrderByFirstNameAscLastNameAsc(): List<Staff>
}
