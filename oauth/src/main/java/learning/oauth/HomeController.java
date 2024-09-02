package learning.oauth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final Youtube youtube;

    public HomeController(Youtube youtube) {
        this.youtube = youtube;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("channelVideos",
                youtube.channelVideos("UCjukbYOd6pjrMpNMFAOKYyw",
                        10,
                        Youtube.Sort.VIEW_COUNT));

        return "index";
    }
}
