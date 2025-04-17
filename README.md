# RestClient Rest Code
## ✅ RestClient springboot 3 버전 이상에서 나온 RestTemplate 같이 http 요청 할 수 있는 class

```
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
```

---

# 내부 클래스에 static을 붙여야 하는 경우

## ✅ 언제 static을 붙여야 할까?

- 내부 클래스가 외부 클래스의 인스턴스 필드나 메서드에 전혀 접근하지 않을 때
- 단순히 데이터 전달용(DTO)으로만 사용될 때

이런 경우에는 내부 클래스를 `static`으로 선언하는 것이 맞습니다.

## 💡 이유

- 불필요하게 외부 클래스의 인스턴스와 연결될 필요가 없으므로,  
  `static`으로 선언해서 메모리 낭비를 막고, 의도를 명확히 할 수 있습니다.
- 실제로 JUnit 테스트 클래스에서 DTO, 응답 객체 등은 거의 항상 static 내부 클래스로 선언합니다.

## ✨ 정리

> 단순 DTO, 외부 인스턴스 접근 필요 없음 → `static class`로 선언하는 것이 맞는 선택!

## 🔎 추가 Tip

- 만약 내부 클래스가 외부 클래스의 필드/메서드를 사용해야 한다면 `static`을 빼고,  
  그렇지 않다면 `static`을 붙이는 것이 좋은 습관입니다.

---
