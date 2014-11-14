package edu.iastate.controller;

import edu.iastate.hello.Sup;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.atomic.AtomicLong;


@Controller
@RequestMapping("/sup-world")
public class WhatsUpController {

    private static final String template = "What's up, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(method= RequestMethod.GET)
    public @ResponseBody Sup sayHello(
            @RequestParam(value="name", required=false, defaultValue="Stranger") String name)
    {
        return new Sup(counter.incrementAndGet(), String.format(template, name));
    }

}
