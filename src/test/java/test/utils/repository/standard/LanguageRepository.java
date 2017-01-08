package test.utils.repository.standard;

import org.springframework.data.hazelcast.repository.HazelcastRepository;

import test.utils.domain.Language;

/**
 * <P>
 * Another repository class for tests.
 * </P>
 *
 * @author Neil Stevenson
 */
public interface LanguageRepository extends HazelcastRepository<Language, Integer> {
}
