package com.perfumeworld.repository.impl;

import org.springframework.stereotype.Repository;

import com.perfumeworld.model.User;
import com.perfumeworld.repository.UserRepo;


@Repository
public class UserRepoImpl extends AbstractRepoImpl<Integer, User> implements UserRepo {

}
