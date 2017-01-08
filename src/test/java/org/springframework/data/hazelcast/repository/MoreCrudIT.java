package org.springframework.data.hazelcast.repository;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.ActiveProfiles;

import test.utils.Constants;
import test.utils.TestDataHelper;
import test.utils.domain.Language;

/**
 * <P>
 * More C/R/U/D tests, as {@link CrudIT} is too big.
 * </P>
 *
 * @author Neil Stevenson
 */
@ActiveProfiles(Constants.SPRING_TEST_PROFILE_SINGLETON)
public class MoreCrudIT extends TestDataHelper {
	@Autowired 
	private CrudRepository<Language, Integer> languageRepository;

	@Before
	public void setUp() {
		super.setUp();
	}

	// https://github.com/hazelcast/spring-data-hazelcast/issues/18
	@Test
	public void doubleInsert() {
		int ZERO = 0;
		int ONE = 1;
		
		assertThat("Year 0 missing before", this.languageRepository.findOne(ZERO), nullValue());
		assertThat("Year 1 missing before", this.languageRepository.findOne(ONE), nullValue());

		Language yearZero = new Language();
		yearZero.setYear(ZERO);
		yearZero.setLanguage("Zero");

		this.languageRepository.save(yearZero);
		this.languageRepository.save(yearZero);
		
		Language yearOne = new Language();
		yearOne.setYear(ONE);
		yearOne.setLanguage("One");

		this.languageRepository.save(yearOne);
		this.languageRepository.save(yearOne);

		assertThat("Year 0 present during", this.languageRepository.findOne(ZERO), not(nullValue()));
		assertThat("Year 1 present during", this.languageRepository.findOne(ONE), not(nullValue()));

		this.languageRepository.delete(ZERO);
		this.languageRepository.delete(ONE);

		assertThat("Year 0 missing after", this.languageRepository.findOne(ZERO), nullValue());
		assertThat("Year 1 missing after", this.languageRepository.findOne(ONE), nullValue());
	}
	
	
}
