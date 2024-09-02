package learning.springboot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class VideoServiceTest {

    VideoService videoService;

    @Mock
    VideoRepository videoRepo;

    @BeforeEach
    void setUp() {
        this.videoService = new VideoService(videoRepo);
    }

    @Test
    void getVideosShouldReturnAll() {
        // given
        var video1 = new VideoEntity("alice", "Spring Boot 3 Intro", "Learn the basics!");
        var video2 = new VideoEntity("alice", "Spring Boot 3 Deep Dive", "Go deep!");
        when(videoRepo.findAll()).thenReturn(List.of(video1, video2));

        // when
        List<VideoEntity> videos = videoService.getVideos();

        // then
        assertThat(videos).containsExactly(video1, video2);
    }

    @Test
    void creatingANewVideoShouldReturnTheSameData() {
        // given
        given(videoRepo.saveAndFlush(any(VideoEntity.class)))
                .willReturn(new VideoEntity("alice", "name", "des"));

        // when
        VideoEntity newVideo = videoService.create(new NewVideo("name", "des"), "alice");

        // then
        assertThat(newVideo.getUsername()).isEqualTo("alice");
        assertThat(newVideo.getName()).isEqualTo("name");
        assertThat(newVideo.getDescription()).isEqualTo("des");
    }

    @Test
    void deletingAVideoShouldWork() {
        // given
        var entity = new VideoEntity("alice", "name", "des");
        entity.setId(1L);
        when(videoRepo.findById(1L)).thenReturn(Optional.of(entity));

        // when
        videoService.delete((1L));

        // then
        verify(videoRepo).findById(1L);
        verify(videoRepo).delete(entity);
    }
}
