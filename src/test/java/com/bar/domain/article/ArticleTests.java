package com.bar.domain.article;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import com.bar.application.ArticleService;
import com.bar.domain.bar.Bar;
import com.bar.domain.bar.BarId;
import com.bar.domain.exception.EmptyNameException;
import com.bar.domain.exception.NullBarIdException;
import com.bar.domain.exception.NullNameException;
import com.bar.domain.shared.Name;
import com.bar.domain.shared.Price;
import com.bar.infrastructure.repository.ArticleHistoryRepository;
import com.bar.infrastructure.repository.ArticleRepository;
import com.bar.infrastructure.repository.BarRepository;

@SpringBootTest
@ActiveProfiles("test")
public class ArticleTests {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleHistoryRepository articleHistoryRepository;

    @Autowired
    private ArticleService articleService; 

    @Autowired
    private BarRepository barRepository;

    @Test()
    @DisplayName("""
        When I try to create a new Article
        Then it is not null and save it into database
    """)
    void createAndSave() throws Exception {

        Bar bar = createBar();
        Article article = new Article(
            new Name("Coca Cola"), 
            new BarId(bar.getId().toString()),
            new Price(2.5),
            true);

        assertNotNull(article);
        assertNotNull(article.getId());
        assertEquals("Coca Cola", article.getName().toString());
        assertEquals(bar.getId().toString(), article.getBarId().toString());

        List<ArticleHistory> articleHistories = articleHistoryRepository.findAllByArticleId(article.getId());
        assertFalse(articleHistories.isEmpty());

        ArticleHistory articleHistory = articleHistories.get(0);
        assertEquals(1, articleHistory.getVersion());
        assertEquals("Coca Cola", articleHistory.getName().toString());
        assertEquals(2.5, articleHistory.getPrice().asDouble());
        assertEquals(article.getId(), articleHistory.getArticleId());
    }

    @Test()
    @DisplayName("""
        When I try to update an Article
        Then it is not null and save it into database
    """)
    void updateAndSave() throws Exception {

        Bar bar = createBar();
        Article article = new Article(
            new Name("Fanta Naranja 33cl"), 
            new BarId(bar.getId().toString()),
            new Price(1.5),
            true);

        articleService.createOrUpdateArticle(article);

        article.setPrice(new Price(1.75));
        article.setName(new Name("Fanta Naranja Lata"));

        articleService.createOrUpdateArticle(article);

        Optional<Article> savedArticleOpt = articleRepository.findById(article.getId());
        assertTrue(savedArticleOpt.isPresent(), "The article should be present in the database");

        assertNotNull(savedArticleOpt);
        assertEquals("Fanta Naranja Lata", savedArticleOpt.get().getName().toString());
        assertEquals(bar.getId().toString(), savedArticleOpt.get().getBarId().toString());
        assertEquals(1.75, savedArticleOpt.get().getPrice().asDouble());

        List<ArticleHistory> articleHistories = articleHistoryRepository.findAllByArticleId(article.getId());
        assertFalse(articleHistories.isEmpty());

        ArticleHistory articleHistory = articleHistories.get(0);
        assertEquals(1, articleHistory.getVersion());
        assertEquals("Fanta Naranja 33cl", articleHistory.getName().toString());
        assertEquals(1.5, articleHistory.getPrice().asDouble());
        assertEquals(article.getId(), articleHistory.getArticleId());

        ArticleHistory articleHistory2 = articleHistories.get(1);
        assertEquals(2, articleHistory2.getVersion());
        assertEquals("Fanta Naranja Lata", articleHistory2.getName().toString());
        assertEquals(1.75, articleHistory2.getPrice().asDouble());
        assertEquals(article.getId(), articleHistory2.getArticleId());
    }

    @Test()
    @DisplayName("""
        When I try to create and save a duplicated Article
        Then it returns a DataIntegrityViolationException 
    """)
    void createDuplicate() throws Exception {

        Bar bar = createBar();

        Article article = new Article(
            new Name("Polvoron Canela"), 
            new BarId(bar.getId().toString()),
            new Price(22.5),
            true);

        articleRepository.save(article);

        Article duplArticle = new Article(
            new Name("Polvoron Canela"), 
            new BarId(bar.getId().toString()),
            new Price(27.5),
            false);

        assertThrows(DataIntegrityViolationException.class, () -> {
            articleRepository.save(duplArticle);
            articleRepository.flush();
        });
    }

    @Test()
    @DisplayName("""
        When I try to create a Article without BarId
        Then it returns a NullBarIdException 
    """)
    void createWithNullBarId() throws Exception {

        assertThrows(NullBarIdException.class, () -> {

            new Article(
                new Name("Vino Blanco Bach"), 
                null,
                new Price(25),
                true);
        });
     }

    @Test()
    @DisplayName("""
        When I try to create a Article with empty name
        Then it returns a EmptyNameException
    """)
    void createWithEmptyName() throws Exception {
 
        Bar bar = createBar();
        assertThrows(EmptyNameException.class, () -> {

            new Article(
                new Name(""), 
                bar.getId(),
                new Price(22.5),
                true);
        });
    }

    @Test()
    @DisplayName("""
        When I try to create a Article with null name
        Then it returns a NullNameException
    """)
    void createWithNullName() throws Exception {
 
        Bar bar = createBar();
        assertThrows(NullNameException.class, () -> {

            new Article(
                new Name(null), 
                bar.getId(),
                new Price(22.5),
                true);
        });
    }

    private Bar createBar() {
        return barRepository.save(new Bar("Llúpol"));
    }

}