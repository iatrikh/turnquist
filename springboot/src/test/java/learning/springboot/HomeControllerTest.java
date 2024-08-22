package learning.springboot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = HomeController.class)
public class HomeControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    VideoService videoService;

    @Test
    @WithMockUser
    void indexPageHasSeveralHtmlForms() throws Exception {

        String html = mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Username: user")))
                .andExpect(content().string(containsString("Authorities: [ROLE_USER]")))
                .andReturn()
                .getResponse().getContentAsString();

        assertThat(html).contains(
                "<form action=\"/logout\"",
                "<form action=\"/universal-search\"",
                "<form action=\"/new-video\"");
    }

    @Test
    @WithMockUser
    void postNewVideoShouldWork() throws Exception {

        mvc.perform(
                post("/new-video")
                        .param("name", "new video")
                        .param("description", "new desc")
                        .with(csrf()))
                .andExpect(redirectedUrl("/"));

        verify(videoService).create(
                new NewVideo(
                        "new video",
                        "new desc"),
                "user");
    }
}
