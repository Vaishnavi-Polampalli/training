package com.df.Onboarding.users.repo;

import com.df.Onboarding.users.model.IdempotencyKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdempotencyKeyRepository extends JpaRepository<IdempotencyKey,String> {
}
