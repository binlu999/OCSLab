package com.blogspot.vozis.springvelocity.web;

import com.blogspot.vozis.springvelocity.data.FeedService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller
 *
 * @author sergej.sizov
 */
@Controller
public class WebController {

    private FeedService feedService;

    @Autowired
    public WebController(FeedService feedService) {
        this.feedService = feedService;
    }

    @RequestMapping(value ="feed/list", method = RequestMethod.GET)
    public String list(ModelMap model) {
        model.put("feeds", feedService.getFeeds());
        return "list";
    }

    @RequestMapping("feed/detail/{id}")
    public String detail(@PathVariable(value = "id") Integer feedId, ModelMap model) {
        model.put("feed", feedService.getFeedById(feedId));
        return "detail";
    }
}


