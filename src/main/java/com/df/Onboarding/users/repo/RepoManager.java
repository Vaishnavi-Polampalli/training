package com.df.Onboarding.users.repo;

import com.df.Onboarding.users.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepoManager extends JpaRepository<Users,Integer> {

    Users findTop1ByFirstNameAndLastName(String firstName,String lastName);
}
