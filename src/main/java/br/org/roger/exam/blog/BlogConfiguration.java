package br.org.roger.exam.blog;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author Marcos (mroger.oliveira@gmail.com)
 *
 */
@Configuration
@EnableJpaRepositories
@EnableTransactionManagement
public class BlogConfiguration {
}
