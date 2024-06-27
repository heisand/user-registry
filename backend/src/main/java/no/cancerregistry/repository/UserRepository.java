package no.cancerregistry.repository;

import no.cancerregistry.repository.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}

