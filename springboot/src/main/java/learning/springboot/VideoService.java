package learning.springboot;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.annotation.PostConstruct;

@Service
public class VideoService {

    // private List<Video> videos = List.of(
    // new Video("Need help with your SPRING BOOT 3 APP?"),
    // new Video("Don't do THIS to your own CODE!"),
    // new Video("SECRETS to fix BROKEN CODE!"));

    private final VideoRepository videoRepo;

    public VideoService(VideoRepository videoRepo) {
        this.videoRepo = videoRepo;
    }

    public List<VideoEntity> getVideos() {
        return videoRepo.findAll();
    }

    public VideoEntity create(NewVideo newVideo, String username) {
        return videoRepo.saveAndFlush(new VideoEntity(username, newVideo.name(), newVideo.description()));
    }

    // public List<VideoEntity> search(VideoSearch search) {

    // if (StringUtils.hasText(search.name()) &&
    // StringUtils.hasText(search.description())) {
    // return
    // videoRepo.findByNameContainsOrDescriptionContainsAllIgnoreCase(search.name(),
    // search.description());
    // }

    // if (StringUtils.hasText(search.name())) {
    // return videoRepo.findByNameContainsIgnoreCase(search.name());
    // }

    // if (StringUtils.hasText(search.description())) {
    // return videoRepo.findByDescriptionContainsIgnoreCase(search.description());
    // }

    // return Collections.emptyList();
    // }

    public List<VideoEntity> search(UniversalSearch search) {
        VideoEntity probe = new VideoEntity();

        if (StringUtils.hasText(search.value())) {
            probe.setName(search.value());
            probe.setDescription(search.value());
        }

        Example<VideoEntity> example = Example.of(probe,
                ExampleMatcher.matchingAny()
                        .withIgnoreCase()
                        .withStringMatcher(StringMatcher.CONTAINING));

        return videoRepo.findAll(example);
    }

    public void delete(Long videoId) {
        videoRepo.findById(videoId)
                .map(videoEntity -> {
                    videoRepo.delete(videoEntity);
                    return true;
                })
                .orElseThrow(() -> new RuntimeException("No video at " + videoId));
    }

    @PostConstruct
    void initDatabase() {
        videoRepo.save(new VideoEntity("alice", "Need HELP with your SPRING BOOT 3 App?",
                "SPRING BOOT 3 will only speed things up and make it super SIMPLE to serve templates and raw data."));
        videoRepo.save(new VideoEntity("alice", "Don't do THIS to your own CODE!",
                "As a pro developer, never ever EVER do this to your code. Because you'll ultimately be doing it to YOURSELF!"));
        videoRepo.save(new VideoEntity("bob", "SECRETS to fix BROKEN CODE!",
                "Discover ways to not only debug your code, but to regain your confidence and get back in the game as a software developer."));
    }

}
