package learning.oauth;

public record SearchResult(
        String kind,
        String etag,
        SearchId id,
        SearchSnippet snippet) {

}
