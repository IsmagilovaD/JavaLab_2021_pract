package ru.itis.servlets;

import org.springframework.context.ApplicationContext;
import ru.itis.models.Account;
import ru.itis.repositories.AccountsRepository;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/accounts")
public class AccountsServlet extends HttpServlet {

    private AccountsRepository accountsRepository;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        ServletContext servletContext = servletConfig.getServletContext();
        ApplicationContext springContext = (ApplicationContext) servletContext.getAttribute("springContext");
        this.accountsRepository = springContext.getBean(AccountsRepository.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        List<Account> accounts = accountsRepository.findAll();
        request.setAttribute("accounts", accounts);
        request.getRequestDispatcher("/jsp/account.jsp").forward(request, response);
    }
}

