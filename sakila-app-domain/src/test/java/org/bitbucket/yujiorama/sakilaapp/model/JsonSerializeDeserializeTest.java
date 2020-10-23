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
        final var country = new Country(1, lastUpdate, "country");
        final var city = new City(1, lastUpdate, "city", country);
        final var address = new Address(1L, lastUpdate, "address", "address2", city, "101", "010", null);
        final var store = new Store(1, lastUpdate, address, null);
        final var staff = new Staff(1, lastUpdate, "aaa", "bbb", address, "aaa@example.com", store, false, "aaa", "bbb", null);
        store.withManagerStaff(staff);
        final var serialized = this.objectMapper.writeValueAsString(staff);
        Assertions.assertTrue(serialized.length() > 0, "serialized");
        Assertions.assertNotEquals("{}", serialized);
    }

    @Test
    public void testDeserialize() throws JsonProcessingException {

        final var json = "{\n" +
            "  \"staff_id\": 1,\n" +
            "  \"last_update\": \"2020-10-13T09:14:17.8159717\",\n" +
            "  \"first_name\": \"aaa\",\n" +
            "  \"last_name\": \"bbb\",\n" +
            "  \"address\": {\n" +
            "    \"address_id\": 1,\n" +
            "    \"last_update\": \"2020-10-13T09:14:17.8159717\",\n" +
            "    \"address\": \"address\",\n" +
            "    \"address2\": \"address2\",\n" +
            "    \"district\": \"distinct\",\n" +
            "    \"city\": {\n" +
            "      \"city_id\": 1,\n" +
            "      \"last_update\": \"2020-10-13T09:14:17.8159717\",\n" +
            "      \"city\": \"city\",\n" +
            "      \"country\": {\n" +
            "        \"country_id\": 1,\n" +
            "        \"last_update\": \"2020-10-13T09:14:17.8159717\",\n" +
            "        \"country\": \"country\"\n" +
            "      }\n" +
            "    },\n" +
            "    \"postal_code\": \"101\",\n" +
            "    \"phone\": \"010\"\n" +
            "  },\n" +
            "  \"email\": \"aaa@example.com\",\n" +
            "  \"store\": {\n" +
            "    \"store_id\": 1,\n" +
            "    \"last_update\": \"2020-10-13T09:14:17.8159717\",\n" +
            "    \"address\": {\n" +
            "      \"address_id\": 1,\n" +
            "      \"last_update\": \"2020-10-13T09:14:17.8159717\",\n" +
            "      \"address\": \"address\",\n" +
            "      \"address2\": \"address2\",\n" +
            "      \"district\": \"distinct\",\n" +
            "      \"city\": {\n" +
            "        \"city_id\": 1,\n" +
            "        \"last_update\": \"2020-10-13T09:14:17.8159717\",\n" +
            "        \"city\": \"city\",\n" +
            "        \"country\": {\n" +
            "          \"country_id\": 1,\n" +
            "          \"last_update\": \"2020-10-13T09:14:17.8159717\",\n" +
            "          \"country\": \"country\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"postal_code\": \"101\",\n" +
            "      \"phone\": \"010\"\n" +
            "    },\n" +
            "    \"manager_staff\": {\n" +
            "      \"staff_id\": 1,\n" +
            "      \"last_update\": \"2020-10-13T09:14:17.8159717\",\n" +
            "      \"first_name\": \"aaa\",\n" +
            "      \"last_name\": \"bbb\",\n" +
            "      \"address\": {\n" +
            "        \"address_id\": 1,\n" +
            "        \"last_update\": \"2020-10-13T09:14:17.8159717\",\n" +
            "        \"address\": \"address\",\n" +
            "        \"address2\": \"address2\",\n" +
            "        \"district\": \"distinct\",\n" +
            "        \"city\": {\n" +
            "          \"city_id\": 1,\n" +
            "          \"last_update\": \"2020-10-13T09:14:17.8159717\",\n" +
            "          \"city\": \"city\",\n" +
            "          \"country\": {\n" +
            "            \"country_id\": 1,\n" +
            "            \"last_update\": \"2020-10-13T09:14:17.8159717\",\n" +
            "            \"country\": \"country\"\n" +
            "          }\n" +
            "        },\n" +
            "        \"postal_code\": \"101\",\n" +
            "        \"phone\": \"010\"\n" +
            "      },\n" +
            "      \"email\": \"aaa@example.com\",\n" +
            "      \"store\": {\n" +
            "        \"store_id\": 1,\n" +
            "        \"last_update\": \"2020-10-13T09:14:17.8159717\",\n" +
            "        \"address\": {\n" +
            "          \"address_id\": 1,\n" +
            "          \"last_update\": \"2020-10-13T09:14:17.8159717\",\n" +
            "          \"address\": \"address\",\n" +
            "          \"address2\": \"address2\",\n" +
            "          \"district\": \"distinct\",\n" +
            "          \"city\": {\n" +
            "            \"city_id\": 1,\n" +
            "            \"last_update\": \"2020-10-13T09:14:17.8159717\",\n" +
            "            \"city\": \"city\",\n" +
            "            \"country\": {\n" +
            "              \"country_id\": 1,\n" +
            "              \"last_update\": \"2020-10-13T09:14:17.8159717\",\n" +
            "              \"country\": \"country\"\n" +
            "            }\n" +
            "          },\n" +
            "          \"postal_code\": \"101\",\n" +
            "          \"phone\": \"010\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"active\": false,\n" +
            "      \"username\": \"aaa\",\n" +
            "      \"password\": \"bbb\",\n" +
            "      \"picture\": null\n" +
            "    }\n" +
            "  },\n" +
            "  \"active\": false,\n" +
            "  \"username\": \"aaa\",\n" +
            "  \"password\": \"bbb\",\n" +
            "  \"picture\": null\n" +
            "}";

        final var deserialized = this.objectMapper.readValue(json, Staff.class);

        Assertions.assertEquals(1, deserialized.getId(), "getId");
    }
}
