package br.org.roger.exam.blog.controller;

import br.org.roger.exam.blog.BlogApplication;
import br.org.roger.exam.blog.model.Post;
import br.org.roger.exam.blog.repository.PostRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 *
 * @author Marcos (mroger.oliveira@gmail.com)
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogApplication.class)
@WebAppConfiguration
public class PostControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
        MediaType.APPLICATION_JSON.getSubtype(),
        Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    private Post post1;
    private Post post2;
    private Post post3;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.postRepository.deleteAll();

        post1 = this.postRepository.save(new Post("Title test 1", "Description test 1", new Date()));
        post2 = this.postRepository.save(new Post("Title test 2", "Description test 2", new Date()));
        post3 = this.postRepository.save(new Post("Title test 3", "Description test 3", new Date()));
    }

    @Test
    public void postsFound() throws Exception {
        mockMvc.perform(get("/blog/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void postsNotFound() throws Exception {
        this.postRepository.deleteAll();

        mockMvc.perform(get("/blog/posts"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void postNotFound() throws Exception {
        mockMvc.perform(get("/blog/posts/4"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void postFound() throws Exception {
        mockMvc.perform(get("/blog/posts/" + post2.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(post2.getId().intValue())))
                .andExpect(jsonPath("$.title", is("Title test 2")))
                .andExpect(jsonPath("$.description", is("Description test 2")));
    }

    @Test
    public void postCreate() throws Exception {
        mockMvc.perform(post("/blog/posts")
                .content(this.json(new Post("New Title Test", "New Description Test", new Date())))
                .contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    public void postCreateInvalid() throws Exception {
        mockMvc.perform(post("/blog/posts")
                .content(this.json(new Post(1000L, "New Title Test", "New Description Test", new Date())))
                .contentType(contentType))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postUpdate() throws Exception {
        mockMvc.perform(put("/blog/posts/" + post3.getId())
                .content(this.json(new Post("Updated Title Test", "Updated Description Test", null)))
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Updated Title Test")))
                .andExpect(jsonPath("$.description", is("Updated Description Test")));
    }

    @Test
    public void postUpdateNotFound() throws Exception {
        mockMvc.perform(put("/blog/posts/1000")
                .content(this.json(new Post("Updated Title Test", "Updated Description Test", null)))
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }

    @Test
    public void postUpdateRequiredTitleNotProvided() throws Exception {
        mockMvc.perform(put("/blog/posts/" + post3.getId())
                .content(this.json(new Post(null, "Updated Description Test", null)))
                .contentType(contentType))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postUpdateRequiredDescriptionNotProvided() throws Exception {
        mockMvc.perform(put("/blog/posts/" + post3.getId())
                .content(this.json(new Post("Updated Title Test", null, null)))
                .contentType(contentType))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postUpdateRequiredTitleAndDescriptionNotProvided() throws Exception {
        mockMvc.perform(put("/blog/posts/" + post3.getId())
                .content(this.json(new Post(null, null, null)))
                .contentType(contentType))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postDeleteAll() throws Exception {
        mockMvc.perform(delete("/blog/posts"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/blog/posts"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void postDeleteOne() throws Exception {
        mockMvc.perform(delete("/blog/posts/" + post3.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/blog/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void postDeleteNotFound() throws Exception {
        mockMvc.perform(delete("/blog/posts/1000"))
                .andExpect(status().isNotFound());
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

}
