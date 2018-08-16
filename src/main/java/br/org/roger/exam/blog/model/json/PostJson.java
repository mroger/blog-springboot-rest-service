package br.org.roger.exam.blog.model.json;

import br.org.roger.exam.blog.exception.PostArgumentNotValidException;
import br.org.roger.exam.blog.model.Post;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Marcos (mroger.oliveira@gmail.com)
 *
 */
// https://docs.jboss.org/hibernate/validator/5.1/reference/en-US/html/chapter-message-interpolation.html#section-interpolation-with-message-expressions
// https://g00glen00b.be/validating-the-input-of-your-rest-api-with-spring/
// https://dzone.com/articles/implementing-validation-for-restful-services-with
public class PostJson {

    private Long id;
    @NotNull(message = "blog.post.request.title.notnull")
    private String title;
    @NotNull(message = "blog.post.request.description.notnull")
    @Size(min=5, message = "{blog.post.request.description.size}")
    private String description;
    //Só para testar validacao
    @Min(value = 350, message = "{blog.post.request.priceperview.min}")
    private double pricePerView;
    private Date publishedAt;
    private Date updatedAt;

    public PostJson() {
    }

    public PostJson(Long id, String title, String description, Date publishedAt, Date updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.publishedAt = publishedAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public double getPricePerView() {
        return pricePerView;
    }

    public void setPricePerView(double pricePerView) {
        this.pricePerView = pricePerView;
    }

    public static PostJson fromModel(Post post) {
        return new PostJson(post.getId(), post.getTitle(), post.getDescription(), post.getPublishedAt(), post.getUpdatedAt());
    }

    public static List<PostJson> fromModelList(List<Post> posts) {
        if (posts == null) {
            return new ArrayList<>();
        }
        return posts.stream()
            .map(PostJson::fromModel)
            .collect(Collectors.toList());
    }

    public Post updateModel(Post p) {
        if (this.getTitle() == null || this.getDescription() == null) {
            throw new PostArgumentNotValidException("Title and description are required");
        }
        p.setTitle(this.getTitle());
        p.setDescription(this.getDescription());
        p.setUpdatedAt(new Date());
        return p;
    }

    public Post createModel() {
        if (this.getTitle() == null || this.getDescription() == null) {
            throw new PostArgumentNotValidException("Title and description are required");
        }
        return new Post(this.getTitle(), this.getDescription(), new Date());

    }
}
