package gestioneMagazzino;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.ordine.OrdiniTotali;

public class GestioneProdottiController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		OrdiniTotali ordiniMagazzino = mostraOrdiniTotali();
		session.setAttribute("ordini", ordiniMagazzino);
		resp.sendRedirect(req.getContextPath() + "/ordini.jsp");
	}

	private OrdiniTotali mostraOrdiniTotali() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
	
	
}
