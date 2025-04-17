package kuke.board.article.api;

import kuke.board.article.service.response.ArticleResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArticleApiTest {

    /**
     * RestClient springboot 3 버전 이상에서 나온 RestTemplate 같이 http 요청 할 수 있는 class
     */

    RestClient restClient = RestClient.create("http://localhost:9000"); // BaseUrl 지정

    @Test
    @DisplayName("createTest")
    void createTest() {
        ArticleCreateRequest request = new ArticleCreateRequest(
                "hi", "my content", 1L, 1L
        );
        ArticleResponse response = create(request);
        assertThat(request.getTitle()).isEqualTo(response.getTitle());
        assertThat(request.getContent()).isEqualTo(response.getContent());
        assertThat(request.getWriterId()).isEqualTo(response.getWriterId());
        assertThat(request.getBoardId()).isEqualTo(response.getBoardId());
    }

    @Test
    @DisplayName("readTest")
    void readTest() {
        ArticleCreateRequest request = new ArticleCreateRequest(
                "hi", "my content", 1L, 1L
        );
        ArticleResponse create = create(request);
        ArticleResponse response = read(create.getArticleId());
        assertThat(request.getTitle()).isEqualTo(response.getTitle());
        assertThat(request.getContent()).isEqualTo(response.getContent());
        assertThat(request.getWriterId()).isEqualTo(response.getWriterId());
        assertThat(request.getBoardId()).isEqualTo(response.getBoardId());
    }

    @Test
    @DisplayName("updateTest")
    void updateTest() {
        ArticleCreateRequest request = new ArticleCreateRequest(
                "hi", "my content", 1L, 1L
        );
        ArticleResponse create = create(request);
        ArticleUpdateRequest articleUpdateRequest = new ArticleUpdateRequest("hi 2", "my content 22");
        update(create.getArticleId(), articleUpdateRequest);
        ArticleResponse response = read(create.getArticleId());
        assertThat(articleUpdateRequest.getTitle()).isEqualTo(response.getTitle());
        assertThat(articleUpdateRequest.getContent()).isEqualTo(response.getContent());
    }

    @Test
    @DisplayName("deleteTest")
    void deleteTest() {
        ArticleCreateRequest request = new ArticleCreateRequest(
                "hi", "my content", 1L, 1L
        );
        ArticleResponse create = create(request);
        restClient.delete()
                .uri("/v1/articles/{articleId}", create.getArticleId())
                .retrieve();

        assertThrows(
                HttpServerErrorException.InternalServerError.class, () -> read(create.getArticleId())
        );
    }

    ArticleResponse create(ArticleCreateRequest request) {
        return restClient.post()
                .uri("/v1/articles")
                .body(request)
                .retrieve()
                .body(ArticleResponse.class);
    }

    ArticleResponse read(Long articleId) {
        return restClient.get()
                .uri("/v1/articles/{articleId}", articleId)
                .retrieve()
                .body(ArticleResponse.class);
    }

    void update(Long articleId, ArticleUpdateRequest articleUpdateRequest) {
        restClient.put()
                .uri("/v1/articles/{articleId}", articleId)
                .body(articleUpdateRequest)
                .retrieve();
    }

    @Getter
    @AllArgsConstructor
    static class ArticleCreateRequest {
        private String title;
        private String content;
        private Long writerId;
        private Long boardId;
    }

    @Getter
    @AllArgsConstructor
    static class ArticleUpdateRequest {
        private String title;
        private String content;
    }

}
