package Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Service.UsuarioServiceImpl;
import Repository.UsuarioRepository;

/**
 * AtivarVotante
 */
@WebServlet("/AtivarVotante")
public class AtivarVotante extends HttpServlet{
    
    private static final long serialVersionUID = -4324402981961070857L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sessao = req.getSession();
        UsuarioServiceImpl userServ = new UsuarioServiceImpl();
        UsuarioRepository userRepo = new UsuarioRepository();

        try {
            userServ.AtivarVotante(req.getParameter("tituloUser"));
            req.setAttribute("usuariosAtivos", userRepo.buscarUsuariosAtivos());
            req.setAttribute("usuariosInativos", userRepo.buscarUsuariosInativos());
            sessao.getServletContext().getRequestDispatcher("/dynamic/mesario.jsp").forward(req, resp);
        } catch (Exception e) {
            //TODO: handle exception
            sessao.setAttribute("ativado", false);
            e.getStackTrace();
            sessao.getServletContext().getRequestDispatcher("/dynamic/mesario.jsp").forward(req, resp);
        }
    }
}