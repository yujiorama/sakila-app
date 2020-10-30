--
-- Name: address_city_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE address
    ADD CONSTRAINT address_city_id_fkey FOREIGN KEY (city_id) REFERENCES city (city_id) ON
        UPDATE CASCADE
        ON
            DELETE RESTRICT;

--
-- Name: city_country_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE city
    ADD CONSTRAINT city_country_id_fkey FOREIGN KEY (country_id) REFERENCES country (country_id) ON
        UPDATE CASCADE
        ON
            DELETE RESTRICT;

--
-- Name: staff_address_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE staff
    ADD CONSTRAINT staff_address_id_fkey FOREIGN KEY (address_id) REFERENCES address (address_id) ON
        UPDATE CASCADE
        ON
            DELETE RESTRICT;


--
-- Name: staff_store_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE staff
    ADD CONSTRAINT staff_store_id_fkey FOREIGN KEY (store_id) REFERENCES store (store_id);


--
-- Name: store_address_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE store
    ADD CONSTRAINT store_address_id_fkey FOREIGN KEY (address_id) REFERENCES address (address_id) ON
        UPDATE CASCADE
        ON
            DELETE RESTRICT;


--
-- Name: store_manager_staff_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE store
    ADD CONSTRAINT store_manager_staff_id_fkey FOREIGN KEY (manager_staff_id) REFERENCES staff (staff_id) ON
        UPDATE CASCADE
        ON
            DELETE RESTRICT;
