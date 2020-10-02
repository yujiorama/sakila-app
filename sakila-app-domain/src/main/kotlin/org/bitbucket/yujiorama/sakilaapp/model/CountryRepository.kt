package org.bitbucket.yujiorama.sakilaapp.model

import org.springframework.data.repository.CrudRepository

interface CountryRepository : CrudRepository<Country, Int>
