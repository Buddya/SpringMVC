package ru.netology.servlet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.config.JavaConfig;
import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {
    private static final String API_POSTS_PATH = "/api/posts";
    private static final String SLASH = "/";
    private static final String API_POSTS_DIGIT_PATH = "/api/posts/\\d+";
    private PostController controller;

    @Override
    public void init() {
        final var appContext = new AnnotationConfigApplicationContext(JavaConfig.class);
        controller = appContext.getBean(PostController.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final var path = req.getRequestURI();
        if (path.equals(API_POSTS_PATH)) {
            controller.all(resp);
            return;
        }
        if (path.matches(API_POSTS_DIGIT_PATH)) {
            final var id = Long.parseLong(path.substring(path.lastIndexOf(SLASH)));
            controller.getById(id, resp);
            return;
        }
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final var path = req.getRequestURI();
        if (path.equals(API_POSTS_PATH)) {
            controller.save(req.getReader(), resp);
            return;
        }
        super.doPost(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final var path = req.getRequestURI();
        if (path.matches(API_POSTS_DIGIT_PATH)) {
            final var id = Long.parseLong(path.substring(path.lastIndexOf(SLASH)));
            controller.removeById(id, resp);
            return;
        }
        super.doDelete(req, resp);
    }
}

