package com.df.Onboarding.users.service;

import com.df.Onboarding.users.exceptions.InvalidUserNameException;
import com.df.Onboarding.users.model.ResultMessage;
import com.df.Onboarding.users.model.Users;

public interface Validate {
    public void checkValidUser (Users user, ResultMessage resultMessage, String idempotencyKey) throws InvalidUserNameException;
}
