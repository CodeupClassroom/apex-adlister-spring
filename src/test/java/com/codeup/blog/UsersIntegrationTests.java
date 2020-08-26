package com.codeup.blog;

import com.codeup.blog.daos.UsersRepository;
import org.junit.AfterClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogApplication.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsersIntegrationTests {

    String userTest = "test-user";
    String userPass = "password";

    @Autowired
    private MockMvc mvc;

    @Autowired
    UsersRepository userDao;

    @Test
    public void contextLoads() {
        // Sanity Test, just to make sure the MVC bean is working
        assertNotNull(mvc);
    }

    @Test
    public void testARegisterUserFormView() throws Exception {
        this.mvc.perform(get("/sign-up"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Create an account")))
                .andExpect(content().string(containsString("Password")));
    }

    @Test
    public void testBRegisterUserFormSubmission() throws Exception {
        this.mvc.perform(
                post("/sign-up").with(csrf())
                        .param("username", userTest)
                        .param("email", "test-mail@mail.com")
                        .param("password", userPass))
                .andExpect(redirectedUrl("/login"))
                .andExpect(status().isFound());
    }

    @Test
    public void testCLoginUserFormView() throws Exception {
        this.mvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Log In")))
                .andExpect(content().string(containsString("Password")));
    }

    @Test
    public void testDLoginUserFormSubmission() throws Exception {
        this.mvc.perform(
                post("/login").with(csrf())
                        .param("username", userTest)
                        .param("password", userPass))
                .andExpect(redirectedUrl("/ads"))
                .andExpect(status().isFound());
        tearDown();
    }

    public void tearDown(){
        System.out.println("after");
        System.out.println("userDao.findByUsername(userTest).getId() = " + userDao.findByUsername(userTest).getId());
        userDao.deleteById(userDao.findByUsername(userTest).getId());
        System.out.println(userTest + " deleted");
    }

}