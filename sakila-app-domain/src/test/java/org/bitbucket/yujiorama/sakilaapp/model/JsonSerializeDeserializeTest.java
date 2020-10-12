package org.bitbucket.yujiorama.sakilaapp.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {JsonSerializeDeserializeTest.TestConfig.class}
)
public class JsonSerializeDeserializeTest {

    @Configuration
    public static class TestConfig {
        @Bean
        public ObjectMapper objectMapper() {

            final var objectMapper = new ObjectMapper();
            objectMapper.registerModule(
                    new SimpleModule("localDateTimeModule").addSerializer(
                            new LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME)
                    ).addDeserializer(
                            LocalDateTime.class,
                            new LocalDateTimeDeserializer((DateTimeFormatter.ISO_DATE_TIME))
                    )
            );
            return objectMapper;
        }
    }

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void testSerialize() throws JsonProcessingException {

        final var lastUpdate = LocalDateTime.now();
        final var country = new CountryEntity(1, lastUpdate, "country");
        final var city = new CityEntity(1, lastUpdate, "city", country);
        final var address = new AddressEntity(1L, lastUpdate, "address", "address2", "distinct", city, "101", "010");
        final var store = new StoreEntity(1, lastUpdate, address, null);
        final var staff = new StaffEntity(1, lastUpdate, "aaa", "bbb", address, "aaa@example.com", store, false, "aaa", "bbb", null);
        store.setManagerStaff(staff);
        final var serialized = this.objectMapper.writeValueAsString(staff);
        Assertions.assertTrue(serialized.length() > 0, "serialized");
        final var deserialized = this.objectMapper.readValue(serialized, StaffEntity.class);
        Assertions.assertEquals(staff, deserialized);
    }
}
