package learning.springboot;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class VideoRepoH2Test {

    @Autowired
    VideoRepository videoRepo;

    @BeforeEach
    void setUp() {
        videoRepo.saveAll(List.of(
                new VideoEntity("alice",
                        "Need HELP with your SPRING BOOT 3 App?",
                        "SPRING BOOT 3 will only speed things up."),
                new VideoEntity("alice",
                        "Don't do THIS to your own CODE!",
                        "As a pro developer, never ever EVER do this to your code."),
                new VideoEntity("bob",
                        "SECRETS to fix BROKEN CODE!",
                        "Discover ways to not only debug your code")));
    }

    @Test
    void findAllShouldProduceAllVideos() {
        List<VideoEntity> videos = videoRepo.findAll();
        assertThat(videos).hasSize(3);
    }

    @Test
    void findByNameShouldRetrieveOneEntry() {
        List<VideoEntity> videos = videoRepo.findByNameContainsIgnoreCase("SpRinG bOOt 3");
        assertThat(videos).hasSize(1);
        assertThat(videos).extracting(VideoEntity::getName)
                .containsExactlyInAnyOrder("Need HELP with your SPRING BOOT 3 App?");
    }

    @Test
    void findByNameOrDescriptionShouldFindTwo() {
        List<VideoEntity> videos = videoRepo.findByNameContainsOrDescriptionContainsAllIgnoreCase("CoDe", "YOUR CODE");
        assertThat(videos).hasSize(2);
        assertThat(videos).extracting(VideoEntity::getDescription).contains(
                "As a pro developer, never ever EVER do this to your code.",
                "Discover ways to not only debug your code");
    }
}
