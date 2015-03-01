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
		out.println("<body ><form style='height:450px;width:800px;position:absolute;left:40%;top:50%;margin:-200px 0 0 -300px'>");
		out.println("<fieldset style='background:#eff3f6;border-top:1px solid #ADFF2F;border-bottom:1px solid #ADFF2F;padding:10px 20px'>");
		out.println("<label><strong>Bienvenido! Este programa calcula si un numero ingresado es o no primo circular.</strong></label><BR><BR><BR><BR>");    
        out.println("<label><strong>Por favor ingrese un numero:</strong></label><input type='text' name='numero' id='numero' tabindex='1' placeholder='Ingrese un numero aqui' autofocus>");
		out.println("<footer><BR><BR><input type='submit' value='Calcular' tabindex='2' style='-moz-border-radius:2px;-webkit-border-radius:2px;border-radius:15px;background:#a1d8f0;");
		out.println("background:-moz-linear-gradient(top, #badff3, #7acbed);background:-webkit-gradient(linear, left top, left bottom, from(#badff3), to(#7acbed));");
		out.println("-ms-filter: 'progid:DXImageTransform.Microsoft.gradient(startColorStr='#badff3', EndColorStr='#7acbed')';border:1px solid #7db0cc !important; cursor: pointer;");
		out.println("padding:11px 16px;font:bold 11px/14px Verdana, Tahomma, Geneva;text-shadow:rgba(0,0,0,0.2) 0 1px 0px;color:#fff;");
		out.println("-moz-box-shadow:inset rgba(255,255,255,0.6) 0 1px 1px, rgba(0,0,0,0.1) 0 1px 1px;-webkit-box-shadow:inset rgba(255,255,255,0.6) 0 1px 1px, rgba(0,0,0,0.1) 0 1px 1px;");
		out.println("box-shadow:inset rgba(255,255,255,0.6) 0 1px 1px, rgba(0,0,0,0.1) 0 1px 1px;margin-left:0px;float:left;padding:7px 21px;'></footer>");
        out.println("<BR><BR>");               
		String numero = req.getParameter("numero");   
		if(numero==null)
			{out.println("<label style='color:red;text-align:center'><strong>Ingrese un numero para validar si el mismo es primo circular!</strong></label>");}
		else			
			{
			if (isNumeric(numero)) 
				{
					int num = Integer.parseInt(numero);					
					if (isCircularPrime(num))
						{out.println("<label style='color:green;text-align:center'><strong>El Numero " + Integer.toString(num) + " Es Primo Circular</strong></label>");}
					else
						{out.println("<label style='color:blue;text-align:center'><strong>El Numero " + Integer.toString(num) + " No Es Primo Circular</strong></label>");}	              
				}  				
			else
				{out.println("<label style='color:red;text-align:center'><strong>Ingreso no valido, por favor ingrese un numero entero!</strong></label>");}			
			}        
        out.println("<BR><BR>");
        out.println("</form></fieldset><footer id='main'><a href='https://github.com/jpmal95/forherokujpmprogtestmeliwebapp.git'>Codigo disponible en Github</a></footer></BODY></HTML>"); 
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