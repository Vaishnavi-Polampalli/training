package com.df.Onboarding.phone.repo;

import com.df.Onboarding.phone.model.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PhoneNumberRepo extends JpaRepository<PhoneNumber,Integer> {
}
