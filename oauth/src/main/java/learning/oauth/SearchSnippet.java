package learning.oauth;

import java.util.Map;

public record SearchSnippet(
        String publishedAt,
        String channelId,
        String title,
        String description,
        Map<String, SearchThumbnail> thumbnails,
        String channelTitle) {

    public String shortDescription() {
        if (this.description.length() <= 100) {
            return this.description;
        }
        return this.description.substring(0, 100);
    }

    public SearchThumbnail thumbnail() {
        return this.thumbnails.entrySet().stream()
                .filter(entry -> entry.getKey().equals("default"))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(null);
    }
}
