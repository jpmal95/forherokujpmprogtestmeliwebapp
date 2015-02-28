import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;

public class ProgTestMELIwebapp extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {  
      showHome(req,resp); 
  }

  private void showHome(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
		resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<HTML>");
        out.println("<head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'><title>Numeros Primos Circulares</title></head>");
        out.println("<body><p><strong>Bienvenido! Este programa calcula si un numero ingresado es o no primo circular.</strong></p>");    
        out.println("<BR><BR>");
        out.println("<form>\n" + "Por favor ingrese un numero:<br>\n" + "<input type=\"text\" name=\"numero\" id=\"numero\" autofocus>\n" + "<br>\n" + "<input type=\"submit\" value=\"Calcular\" /></form>");
        out.println("<BR><BR>");
        out.println("<BR><BR>");        
		String numero = req.getParameter("numero");   
		if(numero==null)
			{out.println("Ingrese un numero para validar si el mismo es primo circular!");}
		else			
			{
			if (isNumeric(numero)) 
				{
					int num = Integer.parseInt(numero);					
					if (isCircularPrime(num))
						{out.println("El Numero " + Integer.toString(num) + " Es Primo Circular");}
					else
						{out.println("El Numero " + Integer.toString(num) + " No Es Primo Circular");}	              
				}  				
			else
				{out.println("Ingreso no valido, por favor ingrese un numero entero!");}			
			}        
        out.println("<BR><BR>");
        out.println("</BODY></HTML>"); 
  }
  
     /* Funcion para validar si un numero es primo circular */
        public static boolean isCircularPrime(int num) {
            String number = Integer.toString(num);
            if(isPrime(num)&& num>=10) //valida si el numero es primo y ademas mayor que 10 ya que no existen numeros primos circulares menores a 10
            {   
                if(number.contains("2")||number.contains("4")||number.contains("6")||number.contains("8")||number.contains("5")||number.contains("0"))
                    {return false;}
                else //verifica que no sea un "illegal" number y que la rotacion del numero tambien sea primo                
                {
                    for(int i = 0; i < number.length(); i++)
                    {
                        num = rotate(num);
			if(!isPrime(num)) 
                            {return false;}
                    }
		    return true; 
                }
            }             
            else
                {return false;}
        }
 
        /* Funcion para validar si un numero es primo */
        public static boolean isPrime(int num) {
            if(num == 1)
		{return false;}
            else if(num == 2)
                    {return true;}
            for(int i = 2; i <= num/2 + 1; i++)
		{
                    if(num%i==0)
                        {return false;}
		}
		return true;
	}
    
        /* Funcion para realizar la rotacion de un numero */
        public static int rotate(long number) {
            int numdigits = (int) Math.log10((double)number);
            int multiplier;
            multiplier = (int) Math.pow(10.0, (double)numdigits);
            int num = 0;
            long r = number % 10;
            number = number / 10;
            number = number + multiplier * r;
            num = (int) number;
            return num;
	}
        
        /* Funcion para verificar si el numero ingresado es numerico*/
        private static boolean isNumeric(String cadena){
            try {
		Integer.parseInt(cadena);
		return true;
            } 
            catch (NumberFormatException ae){
		return false;
            }
        }
        
	public static void main(String[] args) throws Exception {
		Server server = new Server(Integer.valueOf(System.getenv("PORT")));
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		server.setHandler(context);
		context.addServlet(new ServletHolder(new ProgTestMELIwebapp()),"/*");
		server.start();
		server.join();
	}
}