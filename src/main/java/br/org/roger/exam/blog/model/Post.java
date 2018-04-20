package br.org.roger.exam.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 *
 * @author Marcos (mroger.oliveira@gmail.com)
 *
 */
@Entity
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "created_at", nullable = false)
    private Date publishedAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    public Post() { }

    public Post(final String title, final String description, final Date publishedAt) {
        this(null, title, description, publishedAt);
    }

    public Post(final Long id, final String title, final String description, final Date publishedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.publishedAt = publishedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(final Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
