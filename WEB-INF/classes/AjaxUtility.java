import java.io.IOException;
import java.util.ArrayList;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map.Entry;


public class AjaxUtility extends HttpServlet {
        public static MySQLDataStoreUtilities mysql = new MySQLDataStoreUtilities();
        private static final long serialVersionUID = 1L;

        protected void doGet(HttpServletRequest request,
                HttpServletResponse response) throws ServletException, IOException {
                StringBuffer sb = new StringBuffer();
                // sb.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
                boolean namesAdded = false;
                // response.setContentType("application/json");
                try {
                        String term = request.getParameter("searchId");
                        if(term.equals(""))
                        {
                                term=null;
                        }
                        HashMap<String,Product> data= mysql.autocompletion(term);

                        Iterator it = data.entrySet().iterator();
                        while (it.hasNext())
                        {
                        Map.Entry pi = (Map.Entry)it.next();
                        Product p=(Product)pi.getValue();
                        sb.append("<product>");
                        sb.append("<id>" + p.getName() + "</id>");
                        sb.append("<productName>" + p.getName() + "</productName >");
                        sb.append("</product>");
                        }
                        if(sb!=null || !sb.equals(""))
                        {
                        response.setContentType("text/xml");
                        response.getWriter().write("<products>" + sb.toString() + "</products>");      
                        }
                } catch (Exception e) {
                        System.err.println(e.getMessage());
                }
        }
        protected void doPost(HttpServletRequest request,
                HttpServletResponse response) throws ServletException, IOException {
                        // String term = request.getParameter("searchId");
                        // System.out.println("Data from ajax call in javascipt post method smartportables "+term);
                }
}