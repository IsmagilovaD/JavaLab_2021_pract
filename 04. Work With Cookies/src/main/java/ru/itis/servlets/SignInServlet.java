package ru.itis.servlets;

import org.springframework.context.ApplicationContext;
import ru.itis.repositories.AccountsRepository;
import ru.itis.repositories.AccountsRepositoryJdbcTemplateImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/sign-in")
public class SignInServlet extends HttpServlet {
    private final static String AUTHENTICATION_COOKIE_NAME = "authenticationCookie";

    private final static int COLOR_COOKIE_MAX_AGE = 60*5;

    private AccountsRepository accountsRepository;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        ServletContext servletContext = servletConfig.getServletContext();
        ApplicationContext springContext = (ApplicationContext) servletContext.getAttribute("springContext");
        this.accountsRepository = springContext.getBean(AccountsRepositoryJdbcTemplateImpl.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/signIn.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (accountsRepository.findByLoginAndPassword(login,password).isPresent()){
            Cookie authenticationCookie = new Cookie(AUTHENTICATION_COOKIE_NAME, "пользователь_аутентифицирован");
            authenticationCookie.setMaxAge(COLOR_COOKIE_MAX_AGE);
            response.addCookie(authenticationCookie);
            response.sendRedirect("/accounts");
        } else response.sendRedirect("/sign-in");
    }

}
