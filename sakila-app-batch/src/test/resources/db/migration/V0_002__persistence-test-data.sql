INSERT INTO country(country_id, country, last_update)
VALUES (9001, 'country', to_timestamp('2020-01-02 03:04:05.678', 'YYYY-MM-DD HH:MI:SS.MS'));
INSERT INTO city(city_id, city, country_id, last_update)
VALUES (9001, 'city', 9001, to_timestamp('2020-01-02 03:04:05.678', 'YYYY-MM-DD HH:MI:SS.MS'));
INSERT INTO address(address_id, address, district, city_id, phone, last_update)
VALUES (9001, 'address', 'district', 9001, '010', to_timestamp('2020-01-02 03:04:05.678', 'YYYY-MM-DD HH:MI:SS.MS'));
INSERT INTO store(store_id, address_id, manager_staff_id, last_update)
VALUES (9001, 9001, 9001, to_timestamp('2020-01-02 03:04:05.678', 'YYYY-MM-DD HH:MI:SS.MS'));
INSERT INTO staff(staff_id, first_name, last_name, address_id, store_id, email, active, username, password, last_update)
VALUES (9001, 'Mike', 'Hillyer', 9001, 9001, 'aaa@example.com', true, 'Mike',
        '8cb2237d0679ca88db6464eac60da96345513964', to_timestamp('2020-01-02 03:04:05.678', 'YYYY-MM-DD HH:MI:SS.MS'));
