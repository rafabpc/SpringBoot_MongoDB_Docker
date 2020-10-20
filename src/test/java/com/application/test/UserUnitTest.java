package com.application.test;

import com.application.factory.UserFactory;
import com.application.model.User;
import com.application.repository.UserRepository;
import com.application.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class UserUnitTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @Test
    public void testCreateUser(){
        User user = UserFactory.getGeneratedUser();
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        User savedUser = userService.save(user);
        Assert.assertTrue(user.getUserName().equals(savedUser.getUserName()));
    }

    @Test
    public void testFindAll(){
        Mockito.when(userRepository.findAll()).thenReturn(UserFactory.getGeneratedList());
        List users = (List) userService.findAll();
        Assert.assertTrue(users.size() == 1);
    }

}
