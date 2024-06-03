package it.unisa.control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/PageHandler")
public class PageHandler extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String[] RESTRICTED_PATHS = {
        "META-INF/context.xml",
        "WEB-INF/web.xml"
    };

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = request.getParameter("page");
        if (isRestrictedPath(page)) {
            // Se il percorso è limitato, reindirizza a una pagina sicura
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
        } else {
            // Altrimenti, procedi con il normale flusso di gestione della pagina
            request.getRequestDispatcher("/WEB-INF/" + page).forward(request, response);
        }
    }

    private boolean isRestrictedPath(String page) {
        if (page == null) {
            return true;
        }
        for (String restrictedPath : RESTRICTED_PATHS) {
            if (page.equalsIgnoreCase(restrictedPath)) {
                return true;
            }
        }
        return false;
    }
}